import jpql.Member;
import jpql.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

// Many To One
public class JpqlFetchJoin {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Team teamA = new Team();
            teamA.setName("팀A");

            Team teamB = new Team();
            teamB.setName("팀B");

            Member member = new Member();
            member.setUsername("회원1");
            member.setTeam(teamA);

            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setTeam(teamA);


            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.setTeam(teamB);

            em.persist(teamA);
            em.persist(teamB);
            em.persist(member);
            em.persist(member2);
            em.persist(member3);

            em.flush();
            em.clear();

            String query = "select m from Member m Join fetch m.team";
            List<Member> resultList = em.createQuery(query, Member.class).getResultList();
            for(Member temp : resultList){
                System.out.println(temp.getUsername() + " " + temp.getTeam().getName());
                //회원1 , 팀A(SQL)
                //회원2 , 팀A(1차캐시) <-회원1이 조회한 팀A가 이미 1차 캐시에 올라와있기 때문
                //회원3 , 팀B(SQL) <- 팀B는 1차캐시에 없음으로 쿼리 실행
                //총 2번 실행된다.

                //모든회원이 팀이 다르면 n번 실행된다 이를 n+1문제
            }

            //지연로딩으로 설정을 해도 쿼리에 fetch가 우선적으로 적용된다.
            String queryFetch = "select m from Member m Join fetch m.team";
            List<Member> resultList2 = em.createQuery(queryFetch, Member.class).getResultList();
            for(Member temp : resultList2) {
                System.out.println(temp.getUsername() + " " + temp.getTeam().getName());
                //프록시가 아니라 실제 엔티티
                //추가 쿼리가 발생하지 않는다
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
