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
                member.setUsername("team");

                Team team  = new Team();
                team.setName("team");
                em.persist(team);

                member.changeTeam(team);
                em.persist(member);


            em.flush();
            em.clear();

            //member 와 team을 inner join함. 이제 team을 쓸 수 있음.
            String query = "select m from Member m left outer join Team t on m.username = t.name";

            List<Member> resultList = em.createQuery(query, Member.class)
                    .getResultList();

            System.out.println("resultList.size() = " + resultList.size());
            for (Member member1 : resultList) {
                System.out.println("member1.toString() = " + member1.toString());
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
