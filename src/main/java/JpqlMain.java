import jpql.Member;

import javax.persistence.*;
import java.util.List;

public class JpqlMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            for(int i=0; i<100; i++) {
                Member member = new Member();
                member.setUsername("HELLO"+i);
                member.setAge(i);
                em.persist(member);
            }
            em.flush();
            em.clear();

            //entity call by jpql also managed by persistence context
            List<Member> resultList = em.createQuery("select m from Member m order by m.age desc", Member.class)
                    .setFirstResult(50)
                    .setMaxResults(100)
                    .getResultList();
            System.out.println("resultList.size() = " + resultList.size());
            for(Member temp : resultList){
                System.out.println(temp.toString());
            }
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();
    }
}
