package ltoss.dma.login.repository;

import ltoss.dma.login.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "update user ui set ui.last_login = :last_login where ui.username = :username", nativeQuery = true)
    int setLastLoginForUser(@Param("last_login") LocalDateTime last_login, @Param("username") String username);

    @Modifying
    @Query(value = "update user ui" +
            "   set ui.name = :name" +
            "     , ui.position = :position" +
            "     , ui.tel = :tel" +
            "     , ui.email = :email" +
            " where ui.user_id = :user_id", nativeQuery = true)
    int updateUser(@Param("name") String name,
                   @Param("position") String position,
                   @Param("tel") String tel,
                   @Param("email") String email,
                   @Param("user_id") Long user_id
    );

    void deleteByUsername(String username);
}
