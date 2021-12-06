package jpql;

import javax.persistence.*;

/*
    Named query : 쿼리를 미리 정의해서 이름을 부여해놓고 사용하는 jpql
    1. 정적 쿼리만 가능하다 : +해서 문자열을 붙여서 사용할 수 없다
    2. 어노테이션이나 xml에 정의한다.
    3. 애플리케이션 로딩시점에 초기화 후 재사용한다.
    4. 애플리케이션 로딩시점에 쿼리를 검증한다 */
@Entity
@NamedQuery(
        name = "Member.findByUsername",
        query = "select m from Member m where m.username = :username"
)
public class Member {

    @Id @GeneratedValue
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @Enumerated(EnumType.STRING)
    private MemberType type;

    public MemberType getType() {
        return type;
    }

    public void setType(MemberType type) {
        this.type = type;
    }

    public void changeTeam(Team team){
        this.team = team;
        team.getMembers().add(this);
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                ", team=" + team +
                '}';
    }
}
