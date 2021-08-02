package hellojpa.persistance;

import hellojpa.member.MemberBySeq;
import hellojpa.member.MemberByTable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaTable {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            //비영속
            MemberByTable member = new MemberByTable();
            
            //영속
            System.out.println("========before=======");
            em.persist(member);
            System.out.println("========after=======");

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();
    }
}
