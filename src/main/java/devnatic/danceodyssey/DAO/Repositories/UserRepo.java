package devnatic.danceodyssey.DAO.Repositories;

import devnatic.danceodyssey.DAO.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    @Query("select u from User u where u.email = :email")
     User findByEmailLike(String email);
    User findByEmail(String email);

    @Query("select u from User u where u.userName = :userName")
    User findByUserName(String userName);

}

