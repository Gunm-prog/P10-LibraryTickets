package com.emilie.Lib7.Authentication.Repository;

import com.emilie.Lib7.Authentication.entities.UserJwt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author emilie
 */
@Repository
public interface UserJwtRepository extends JpaRepository<UserJwt,Integer> {

     Optional<UserJwt> findByUsername (String username);

}

