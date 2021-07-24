package hellojpa.persistance;

import hellojpa.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaUpdate {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Member member1 = em.find(Member.class, 1L);
            member1.setName("changeName");
            /*
            update문을 작성하거나 따로 작업한게 없지만, 값을 확인해보면 name이 바뀐걸 확인할 수 있다.
            JPA를 사용함으로 써 데이터베이스를 컬렉션 값 변경하듯이 쓸 수 있다.

            이를 가능하게 하는것이 변경 감지

            변경감지 (Dirty Checking)
            jdbc에서 commit이 감지되면
            엔티티와 스냅샷을 비교한다.
            변경된 내용이 있다면 update쿼리를 스기 지연 sql저장소에 저장
            이후 flush > 실제 데이터베이스에 반영된다.

            스냅샷?
            내가 최초로 읽어온 데이터 값
            */

            System.out.println("===========================");
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();
    }
}
