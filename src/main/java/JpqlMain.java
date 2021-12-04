import jpql.Member;
import jpql.MemberType;
import jpql.Team;

import javax.persistence.*;
import java.util.List;

public class JpqlMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Team team = new Team();
            team.setName("A");
            Member member = new Member();
            member.setUsername("HELLO");
            member.setAge(9);
            member.setTeam(team);
            member.setType(MemberType.ADMIN);

            Member member2 = new Member();
            member2.setUsername("HELLO2");
            member2.setAge(19);
            member2.setTeam(team);
            member2.setType(MemberType.ADMIN);
            em.persist(team);
            em.persist(member);
            em.persist(member2);

            em.flush();
            em.clear();

            String query = "select group_concat(m.username) from Member m";
            List<String> resultList = em.createQuery(query, String.class).getResultList();
            for(String temp : resultList){
                System.out.println(temp);
            }
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();
    }
}
