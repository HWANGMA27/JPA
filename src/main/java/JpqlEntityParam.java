import jpql.Member;
import jpql.MemberType;
import jpql.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpqlEntityParam {
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

            // 엔티티 자체를 통으로 파라미터로 입력받을 수 있다.
            String query = "select m from Member m where m = :member";
            List<Member> findMember = em.createQuery(query, Member.class).setParameter("member", member).getResultList();
            for(Member temp : findMember){
                System.out.println(temp.getUsername());
            }
            // 위 아래 쿼리 둘다 같은 실행쿼리로 변환된다.
            // Member의 pk가 id이기 때문에 같은 쿼리로 변환되어 실행된다.
            String query2 = "select m from Member m where m.id = :memberId";
            List<Member> findMember2 = em.createQuery(query2, Member.class).setParameter("memberId", member.getId()).getResultList();
            for(Member temp : findMember2){
                System.out.println(temp.getUsername());
            }

            // 연관관계에 있는 테이블도 외래키(FK)로 파라미터 검색이 가능하다.
            String query3 = "select m from Member m where m.team = :team";
            List<Member> findMember3 = em.createQuery(query3, Member.class).setParameter("team", team).getResultList();
            for(Member temp : findMember3){
                System.out.println(temp.getUsername());
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
