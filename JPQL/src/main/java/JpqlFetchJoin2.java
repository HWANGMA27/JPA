import jpql.Member;
import jpql.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

// One To Many
public class JpqlFetchJoin2 {
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

            //패치조인으로 팀과 회원을 함께 조인해서 지연로딩이 발생하지 않음
            //패치조인은 객체 그래프를 sql 하나로 조회하는 개념
            String query = "select distinct t from Team t join fetch t.members";
            List<Team> resultList = em.createQuery(query, Team.class).getResultList();
            for(Team team : resultList){
                System.out.println(team.getName() + " " + team.getMembers().size());
                //팀A에 맴버 2명이 있기 때문에 팀A가 중복 출력된다.
                //중복제거를 하는 방법으로는 distinct가 있다.
                //sql에서 중복제거도 하지만 엔티티에 중복도 제거할 수 있다.
            }
            /*패치조인의 한계
              1.패치 조인 대상은 별칭을 줄 수 없다.
                별칭을 줘서 필터를 거는순간 jpa의 기본 가정이 깨져버린다.
                team - member 의 관계에서 team으로 member를 조회했을 때 전부 조회되는것이 jpa의 기본 전제 조건
              2. 둘 이상의 컬렉션은 패치조인 할 수 없다 (뻥튀기가 될 수 있음)
              3. 컬렉션을 페치 조인하면 페이징 api를 사용할 수 없다.
                 1:1, 다:1은 뻥튀기가 없어서 가능하지만, 1:다는 매우 위험하다.
                 페이징을 메모리에서 작업하게되는데 페이징을 걸어도 대상 테이블의 데이터를 다 끌어오기때문에 장애나기 딱 좋다.
                 해결방법1. 조인 테이블의 선행 후행 테이블을 뒤집어서 해결할 수 있다.
                 해결방법2. @BathSize(size = 100) 어노테이션을 옵션으로 적용한다.
                         전체 조회해야될 맴버가 150명이면 인쿼리로 100개/50개 나눠서 2번의 쿼리가 실행된다.
                         글로벌 세팅으로 깔고 시작하기도 한다.(persistance.xml)
             */
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();
    }
}
