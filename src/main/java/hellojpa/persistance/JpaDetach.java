package hellojpa.persistance;

import hellojpa.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaDetach {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            //persistance
            Member member1 = em.find(Member.class, 1L);
            //dirty chacking
            member1.setName("Detach");
            //detach
            em.detach(member1);
            //준영속 상태로 바꼇기 때문에 update문은 실행되지 않는다.
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
