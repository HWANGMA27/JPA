package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.datajpa.entity.Team;

// JPA Repository만 상속받으면 해당 엔티티에 대한 crud를 갖는 구현체를 알아서 생성한다.
public interface TeamRepository extends JpaRepository<Team, Long> {
}
