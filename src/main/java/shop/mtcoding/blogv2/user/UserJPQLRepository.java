package shop.mtcoding.blogv2.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/*
/jpql에서는 insert , update ,delete 지원안함
 */
public interface UserJPQLRepository extends JpaRepository< User, Integer> {
    @Query(value = "select u from User u where u.id = :id")
    Optional<User> mFindById(@Param("id") Integer id);

    @Query(value = "select u from User u where u.username = :username")
    User findByUsername(@Param("username") String username);

}
