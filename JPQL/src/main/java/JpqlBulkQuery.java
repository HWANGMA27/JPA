import jpql.Member;
import jpql.MemberType;
import jpql.Team;

import javax.persistence.*;
import java.util.List;

public class JpqlBulkQuery {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Team team = new Team();
            team.setName("A");
            em.persist(team);

            Member member = new Member();
            member.setUsername("HELLO");
            em.persist(member);
            Member member2 = new Member();
            member2.setUsername("HELLO1");
            em.persist(member2);
            Member member3 = new Member();
            member3.setUsername("HELLO2");
            em.persist(member3);
            //JPQL 쿼리 실행 전 자동으로 flush가 된다.

            /* 쿼리 한번으로 여러 row를 변경한다.
             벌크 연산이라고 표현

             사용하는 이유?
             dirty check로 변경하게 되면, member수 만큼 update문이 실행되어 성능저하가 일어난다.

             주의사항
             영속성 컨텍스트를 무시하고 db에 직접 쿼리를 날리기 때문에 데이터 정합성이 안맞을 수 있다.

             해결방법
             1. 벌크 연산을 먼저 실행 후 조회 실행
             2. 조회한 결과가 영속성 컨텍스트에 있는 경우 벌크 연산 후 영속성 컨텍스트를 초기화하고 다시 조회한다.
            */
            int count = em.createQuery("update Member m set m.age = 20").executeUpdate();

            System.out.println(count);
            System.out.println(member.getAge());

            em.clear();
            //clear한 후 다시 조회해 와야 update된 데이터가 출력된다.
            Member findMember = em.find(Member.class, member.getId());
            System.out.println(findMember.getAge());
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();
    }
}
