package com.emilie.Lib7.Authentication.Repository;

import com.emilie.Lib7.Authentication.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author emilie
 */
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    User findByUsername (String username);

}

