package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    //파라미터가 길어지면 메소드명도 길어지는 단점이 있다.
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    //네임드 쿼리가 최우선 순위로 호출됨
    List<Member> findByUsername(@Param("username") String username);

    /**
     * 조건이 많아져도 메소드명은 그대로라는 장점이 있다.
     * 실무에서 많이 사용
     * 또다른 장점으로는 쿼리에 오류가 있다면, 컴파일단에서 오류가 발생한다.
     */
    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUserNameList();

    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    //in으로 조회
    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") List<String> names);

    List<Member> findListByUsername(String username);
    Member findMemberByUsername(String username);
    Optional<Member> findOptionalByUsername(String username);
}
