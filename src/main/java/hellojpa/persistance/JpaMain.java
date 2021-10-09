package hellojpa.persistance;

import hellojpa.member.*;
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
            Address address = new Address("city", "street", "zipcode");
            Member2 member = new Member2();
            member.setUsername("test");
            member.setHomeAdress(address);
            member.setWorkPeriod(new Period());
            em.persist(member);
            //entity value type shouldn't be share
            //not making setter can be solution of this problem
            //or declare access modifier as private

            //Sol1 copy value
            Address newAddress = new Address(address.getCity(), address.getStreet(), address.getZipcode());

            Member2 member2 = new Member2();
            member2.setUsername("test2");
            member2.setHomeAdress(newAddress);
            member2.setWorkPeriod(new Period());
            em.persist(member2);

            //Sol2 make new entity
            Address newAddress2 = new Address("new city", address.getStreet(), address.getZipcode());
            member2.setHomeAdress(newAddress2);


            //member.getHomeAdress().setCity("newCity");
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
