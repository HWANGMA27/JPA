package study.datajpa.entity;

import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class Member {

    @Id @GeneratedValue
    private Long id;

    private String username;

    //Jpa가 entity를 생성할 떄 기본 생성자가 있어야한다.
    //protected or public 가능
    protected Member(){}

    public Member(String username){
        this.username = username;
    }

    public void changeUsername(String username){
        this.username = username;
    }
}
