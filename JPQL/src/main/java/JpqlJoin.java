import jpql.Member;
import jpql.MemberType;
import jpql.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpqlJoin {
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

            /*묵시적 내부 조인 발생
              눈에 보이지 않는 조인이 실제 쿼리가 날라갈 때 발상함으로 나중에 쿼리를 추적할때 시간이 걸리고 튜닝시에도 어려움이 발생한다.
              실무에서는 사용하지 않는것을 권장한다.
            */
            String query = "select m.team from Member m";

            /*명시적 조인
              가능하면 명시적 조인으로 눈에 보이게 작성해야 한다.
              명시적 조인을 하면 별칭을 얻어서 다시 경로 탐색을 시작할 수 있다는 장점이 있다.
            */
            String query2 = "select t from Member m inner join m.team t";

            List<Team> resultList = em.createQuery(query2, Team.class).getResultList();
            for(Team temp : resultList){
                System.out.println(temp.getName());
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
