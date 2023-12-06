package com.smk.bi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smk.bi.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUsername(String username);
}
