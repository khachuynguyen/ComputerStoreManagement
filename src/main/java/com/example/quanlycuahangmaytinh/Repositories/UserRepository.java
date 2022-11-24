package com.example.quanlycuahangmaytinh.Repositories;

import com.example.quanlycuahangmaytinh.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    @Query(value = "select * from user where email = :email",nativeQuery = true)
    Optional<User> findByEmail(@Param("email") String email);
    @Query(value = "select * from user where email = :email and password =:password",nativeQuery = true)
    Optional<User> findByEmailAndPassWord(@Param("email") String email, @Param("password") String password);
    @Query(value = "select * from user where email = :email and name =:username",nativeQuery = true)
    Optional<User> findByEmailAndUserName(@Param("email") String email, @Param("username") String userName);
}
