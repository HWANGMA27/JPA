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
            em.persist(team);
            em.persist(member);

            em.flush();
            em.clear();

            String query = "select " +
                                "case when m.age <=10 then '학생요금' "+
                                "when m.age >=60 then '경로요금' "+
                                "else '일반요금' end "+
                            "from Member m";
            String query2 = "select coalesce(m.username, '이름없는 회원') from Member m";
            String query3 = "select nullif(m.username, 'HELLO') as username from Member m";
            List<String> resultList = em.createQuery(query3, String.class).getResultList();
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
