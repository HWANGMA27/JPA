package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired private MemberRepository memberRepository;
    @Autowired private TeamRepository teamRepository;
    @PersistenceContext private EntityManager em;

    @Test
    public void test() throws Exception{
        //given
        Member member = new Member("MemberA");
        Member saveMember = memberRepository.save(member);
        //when
        Optional<Member> byId = memberRepository.findById(saveMember.getId());
        Member findMember = byId.get();

        //then
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void basicCRUD() throws Exception{
        //given
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);
        //when
        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();

        //단건 조회 검증
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        //리스트 조회 검증
        List<Member> all = memberRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        //삭제 검증
        memberRepository.delete(member1);
        memberRepository.delete(member2);
        assertThat(memberRepository.count()).isEqualTo(0);
    }

    @Test
    public void findByUsernameAndAgeGreaterThan() throws Exception{
        //given
        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("AAA", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);
        //when
        List<Member> findMemberList = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15);
        //then
        assertThat(findMemberList.size()).isEqualTo(1);
    }
    
    @Test
    public void testNamedQuery() throws Exception{
        //given
        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("BBB", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);
        //when
        List<Member> findMemberList = memberRepository.findByUsername("AAA");
        //then
        assertThat(findMemberList.size()).isEqualTo(1);
    }

    @Test
    public void testQuery() throws Exception{
        //given
        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("BBB", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);
        //when
        List<Member> findMemberList = memberRepository.findUser("AAA", 10);
        //then
        assertThat(findMemberList.get(0)).isEqualTo(member1);
    }

    @Test
    public void testUsernameList(){
        //given
        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("BBB", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);
        //when
        List<String> usernameList = memberRepository.findUserNameList();
        //then
        for (String name: usernameList) {
            System.out.println("name : " + name);
        }
    }


    @Test
    public void testMemberTeam(){
        //given
        Team team1 = new Team("teamA");
        teamRepository.save(team1);
        Member member1 = new Member("AAA", 10);
        member1.setTeam(team1);
        memberRepository.save(member1);

        //when
        List<MemberDto> dtoList = memberRepository.findMemberDto();
        //then
        for (MemberDto memberDto: dtoList) {
            System.out.println(memberDto);
        }
    }

    @Test
    public void testByNames(){
        //given
        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("BBB", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);
        //when
        List<Member> usernameList = memberRepository.findByNames(Arrays.asList("AAA", "BBB"));
        //then
        for (Member member: usernameList) {
            System.out.println("name : " + member.getUsername());
        }
    }

    @Test
    public void returnTypeTest(){
        //given
        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("BBB", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> listMember = memberRepository.findListByUsername("AAA");
        Member member = memberRepository.findMemberByUsername("AAA");
        Optional<Member> optionalMember = memberRepository.findOptionalByUsername("AAA");
    }

    @Test
    public void paging(){
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member11", 10));
        memberRepository.save(new Member("member12", 10));
        memberRepository.save(new Member("member13", 10));
        memberRepository.save(new Member("member14", 10));
        memberRepository.save(new Member("member15", 10));
        memberRepository.save(new Member("member16", 10));

        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "username"));

        //when
        Page<Member> page = memberRepository.findByAge(10, pageRequest);

        //then
        List<Member> content = page.getContent();

        assertThat(content.size()).isEqualTo(5); //가져온 컨텐츠 갯수
        assertThat(page.getTotalElements()).isEqualTo(7); //전체 컨텐츠 갯수
        assertThat(page.getNumber()).isEqualTo(0); //현재 페이지 번호
        assertThat(page.isFirst()).isTrue();
        assertThat(page.hasNext()).isTrue();
    }

    @Test
    public void bulkUpdate(){
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member11", 20));
        memberRepository.save(new Member("member12", 20));
        memberRepository.save(new Member("member13", 11));
        memberRepository.save(new Member("member14", 22));
        memberRepository.save(new Member("member15", 10));
        memberRepository.save(new Member("member16", 10));

        int age = 20;

        //when
        int resultCnt = memberRepository.bulkAgePlus(age);

        //then
        assertThat(resultCnt).isEqualTo(3);
    }

    @Test
    public void findMemberLazy(){
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);
        Member member1 = new Member("member1");
        member1.setTeam(teamA);
        Member member2 = new Member("member2");
        member2.setTeam(teamB);
        memberRepository.save(member1);
        memberRepository.save(member2);

        em.flush();
        em.clear();

        for (Member member : memberRepository.findAll()) {
            System.out.println("member = " + member);
            System.out.println(member.getTeam());
        }
    }

    @Test
    public void queryHint() throws Exception{
        //given
        Member member1 = new Member("member1", 10);
        memberRepository.save(member1);
        em.flush();
        em.clear();

        //when
        Member findMember = memberRepository.findReadOnlyByUsername("member1");
        findMember.setUsername("member2");
        em.flush();
        em.clear();
    }

    @Test
    public void lock() throws Exception{
        //given
        Member member1 = new Member("member1", 10);
        memberRepository.save(member1);
        em.flush();
        em.clear();

        //when
        List<Member> result = memberRepository.findLockByUsername("member1");
        //then
    }
}