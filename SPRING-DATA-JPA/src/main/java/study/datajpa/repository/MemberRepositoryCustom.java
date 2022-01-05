package study.datajpa.repository;

import study.datajpa.entity.Member;

import java.util.List;

//MemberRepository 인터페이스에 커스텀 jpql쿼리를 추가하고 싶을때
public interface MemberRepositoryCustom {
    List<Member> findMemberCustom();
}
