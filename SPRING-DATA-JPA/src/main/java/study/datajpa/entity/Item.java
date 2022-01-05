package study.datajpa.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;
import study.datajpa.entity.base.BaseEntity;

import javax.persistence.Access;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseEntity implements Persistable<String> {

    /**
     * 새로운 객체를 save할 때
     * 새로운 객체일 경우 persist를 아닐경우엔 merge를 호출한다 (SimpleJpaRepository 참조)
     * 새로운 객체인지에 대한 판단은 식별자가 null인지 아닌지로 판단을 하는데
     * generatedValue가 아닌 다른방법으로 id를 부여할 경우 새로운 엔티티이지만 식별자가 null이 아닐 수 있다.
     * 고로 merge가 실행되는데 merge는 새로운 객체가 아니라는 가정하에 실행되기때문에 db에서 해당 id를 가지고 select를 해온다.
     * 이런경우 persist를 사용하기 위해 Persistable 인터페이스를 상속받아 getId와 isNew 메소드를 오버라이드 해주면 된다.
     * 새로운 객체인지 판단하는 기준을 id의 null여부가 아니라, createTime의 null 여부로 판단하게 된다.
     */

    @Id
    private String id;

    public Item(String id){
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return getCreatedTime() == null;
    }
}
