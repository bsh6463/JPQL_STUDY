package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{

                Member member =  new Member();
                member.setAge(10);
                member.setUsername("관리자");
                member.setType(MemberType.ADMIN);

                Team team  = new Team();
                team.setName("team");
                em.persist(team);

                member.changeTeam(team);
                em.persist(member);


            em.flush();
            em.clear();

            String query = "select size(t.members) from Team t ";
            List<Integer> resultList = em.createQuery(query, Integer.class).getResultList();

            for (Integer s : resultList) {
                System.out.println("s = " + s);
            }


            tx.commit();

        }catch (Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally {
            em.close();
        }

        emf.close();

    }


}
