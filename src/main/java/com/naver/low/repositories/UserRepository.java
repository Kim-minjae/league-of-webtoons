package com.naver.low.repositories;

import com.naver.low.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserEmail(String email);

}
