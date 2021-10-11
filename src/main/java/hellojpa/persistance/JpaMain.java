package hellojpa.persistance;

import hellojpa.entity.Address;
import hellojpa.entity.AddressEntity;
import hellojpa.member.*;

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
            MemberCollection member = new MemberCollection();
            member.setUsername("member");
            member.setHomeAdress(new Address("homeCity", "street", "10000"));

            member.getFavoriteFood().add("치킨");
            member.getFavoriteFood().add("피자");
            member.getFavoriteFood().add("족발");


//            member.getAddressHistory().add(new Address("old1", "street", "10000"));
//            member.getAddressHistory().add(new Address("old2", "street", "10000"));
//            collection value cascade update when member updated
            em.persist(member);
            em.flush();
            em.clear();

            System.out.println("=============== TEST LAZY LOADING ===============");
            MemberCollection findMember = em.find(MemberCollection.class, member.getId());
//            List<AddressEntity> addressHistory = findMember.getAddressHistory();
//
            System.out.println("=============== UPDATE VALUE TYPE COLLECTION ===============");
            findMember.getFavoriteFood().remove("치킨");
            findMember.getFavoriteFood().add("한식");

            findMember.getAddressHistory().remove(new AddressEntity("old1", "street", "10000"));
            findMember.getAddressHistory().add(new AddressEntity("newCity1", "street", "10000"));

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
