package hellojpa.persistance;

import hellojpa.member.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaBatch {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Member member1 = new Member(10L, "A");
            Member member2 = new Member(20L, "B");

            //commit 전까지 쌓아놓고 있다가 한번에 insert처리를 한다.
            em.persist(member1);
            em.persist(member2);
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
