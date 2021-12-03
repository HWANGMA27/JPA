import jpql.Member;
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
            em.persist(team);
            em.persist(member);

            em.flush();
            em.clear();

            //entity call by jpql also managed by persistence context
            //하이버네이터 5.1부터 연관관계가 없는 엔티티간의 조인도 지원한다.
            String query = "select m from Member m join m.team t on t.name = 'A' ";
            List<Member> list = em.createQuery(query, Member.class)
                    .getResultList();
            System.out.println(list.size());
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();
    }
}
