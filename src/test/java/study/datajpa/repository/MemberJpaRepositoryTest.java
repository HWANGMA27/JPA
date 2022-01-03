package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {

    @Autowired MemberJpaRepository memberJpaRepository;
    @PersistenceContext
    EntityManager em;

    @Test
    public void test() throws Exception{
        //given
        Member member = new Member("memberA");
        //when
        Member saveMember = memberJpaRepository.save(member);
        //then
        Member findMember = memberJpaRepository.find(member.getId());

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void basicCRUD() throws Exception{
        //given
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);
        //when
        Member findMember1 = memberJpaRepository.findById(member1.getId()).get();
        Member findMember2 = memberJpaRepository.findById(member2.getId()).get();

        //단건 조회 검증
        Assertions.assertThat(findMember1).isEqualTo(member1);
        Assertions.assertThat(findMember2).isEqualTo(member2);

        //리스트 조회 검증
        List<Member> all = memberJpaRepository.findAll();
        Assertions.assertThat(all.size()).isEqualTo(2);

        //삭제 검증
        memberJpaRepository.delete(member1);
        memberJpaRepository.delete(member2);
        Assertions.assertThat(memberJpaRepository.count()).isEqualTo(0);
    }

    @Test
    public void findByUsernameAndAgeGreaterThan() throws Exception{
        //given
        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("AAA", 20);
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);
        //when
        List<Member> findMemberList = memberJpaRepository.findByUsernameAndAgeGreaterThan("AAA", 15);
        //then
        Assertions.assertThat(findMemberList.size()).isEqualTo(1);
    }

    @Test
    public void testNamedQuery() throws Exception{
        //given
        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("BBB", 20);
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);
        //when
        List<Member> findMemberList = memberJpaRepository.findByUsername("AAA");
        //then
        Assertions.assertThat(findMemberList.size()).isEqualTo(1);
    }

    @Test
    public void paging(){
        memberJpaRepository.save(new Member("member1", 10));
        memberJpaRepository.save(new Member("member11", 10));
        memberJpaRepository.save(new Member("member12", 10));
        memberJpaRepository.save(new Member("member13", 10));
        memberJpaRepository.save(new Member("member14", 10));
        memberJpaRepository.save(new Member("member15", 10));
        memberJpaRepository.save(new Member("member16", 10));

        int age = 10;
        int offset = 0;
        int limit =3;

        //when
        List<Member> members = memberJpaRepository.findByPage(age, offset, limit);
        long totalCount = memberJpaRepository.totalCount(age);

        //then
        assertThat(members.size()).isEqualTo(3);
        assertThat(totalCount).isEqualTo(7);
    }

    @Test
    public void bulkUpdate(){
        memberJpaRepository.save(new Member("member1", 10));
        memberJpaRepository.save(new Member("member11", 20));
        memberJpaRepository.save(new Member("member12", 20));
        memberJpaRepository.save(new Member("member13", 11));
        memberJpaRepository.save(new Member("member14", 22));
        memberJpaRepository.save(new Member("member15", 10));
        memberJpaRepository.save(new Member("member16", 10));
//        em.flush();
//        em.clear();

        int age = 20;

        //when
        int resultCnt = memberJpaRepository.bulkAgePlus(age);

        //then
        assertThat(resultCnt).isEqualTo(3);
    }
}