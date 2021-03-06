package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Team;
import jpabook.jpashop.domain.items.Book;

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
            Member member = new Member();
            member.setName("member");
            Team team = new Team();
            team.setName("team");
            member.setTeam(team);
            em.persist(team);
            em.persist(member);
            em.flush();
            em.clear();

            Member findMember = em.find(Member.class, member.getId());

            System.out.println("findMember = " + findMember.getTeam());

            System.out.println("findMember = " + findMember.getTeam().getClass());


            tx.commit();

        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();
    }
}
