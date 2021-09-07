package hellojpa.persistance;

import hellojpa.member.Member;
import hellojpa.member.Movie;
import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Member member1 = new Member();
            member1.setUsername("member1");
            em.persist(member1);

            em.flush();
            em.clear();
            Member refMember = em.getReference(Member.class, member1.getId());
            //proxy extends original entity, so when compare instance we should use isInstance instead of ==
            System.out.println("refMember.getClass() = " + refMember.getClass());
            // check entity that already initialized
            System.out.println("isLoaded = " + emf.getPersistenceUnitUtil().isLoaded(refMember));
            //force initialize entity
            Hibernate.initialize(refMember);

            em.detach(refMember);
            //after detach entity, we can't find proxy
            refMember.getUsername();

            tx.commit();
        }catch (Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally {
            em.close();
        }

        emf.close();
    }
}
