import jpql.Address;
import jpql.Member;
import jpql.MemberDTO;
import jpql.Order;

import javax.persistence.*;
import java.util.List;

public class JpqlMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Member member = new Member();
            member.setUsername("HELLO");
            member.setAge(10);
            em.persist(member);
            em.flush();
            em.clear();

            //entity call by jpql also managed by persistence context
            List resultList = em.createQuery("select m.username, m.age from Member m ").getResultList();

            //How to get scala return value 1
            Object obj = resultList.get(0);
            Object[] result = (Object[])obj;
            System.out.println("username = " + result[0]);
            System.out.println("age = " + result[1]);

            //How to get scala return value 2
            List<Object[]> resultList2 = em.createQuery("select m.username, m.age from Member m ").getResultList();
            Object[] result2 = resultList2.get(0);
            System.out.println("username = " + result2[0]);
            System.out.println("age = " + result2[1]);

            //How to get scala return value 3
            //create dto and store data
            List<MemberDTO> dto = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m ").getResultList();
            MemberDTO memberDTO = dto.get(0);
            System.out.println("username = " + memberDTO.getUsername());
            System.out.println("age = " + memberDTO.getAge());
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();
    }
}
