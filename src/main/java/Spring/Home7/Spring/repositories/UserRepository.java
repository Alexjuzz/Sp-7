package Spring.Home7.Spring.repositories;

import Spring.Home7.Spring.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends  JpaRepository<User, Long>  {
}
