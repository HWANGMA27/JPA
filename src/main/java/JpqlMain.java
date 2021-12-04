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

            //entity call by jpql also managed by persistence context
            //하이버네이터 5.1부터 연관관계가 없는 엔티티간의 조인도 지원한다.
            String query = "select m from Member m where m.type = jpql.MemberType.ADMIN";
            String queryBinding = "select m from Member m where m.type = :userType";
            List<Member> list = em.createQuery(query, Member.class)
                    .getResultList();
            List<Member> bindingList = em.createQuery(query, Member.class)
                    .setParameter("userType", MemberType.ADMIN)
                    .getResultList();
            System.out.println(list.size());
            System.out.println(bindingList.size());
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();
    }
}
