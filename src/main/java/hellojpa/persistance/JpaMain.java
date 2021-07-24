package hellojpa.persistance;

import hellojpa.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            //비영속
            Member member = new Member(2L, "Per");
            
            //영속
            System.out.println("========before=======");
            em.persist(member);
            System.out.println("========after=======");

            tx.commit();

            //영속 엔티티의 동일성 보장
            Member a = em.find(Member.class, 2L);
            Member b = em.find(Member.class, 2L);
            //같은 트랜젝션 안에서 실행되면 1차 캐시에서 반환되기 때문에 같은 엔티티를 반
            System.out.println(a==b);
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();
    }
}
