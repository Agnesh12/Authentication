package com.auth.loginauthentication.Repository;

import com.auth.loginauthentication.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
