package com.synrgy.binarfud.Binarfud.repository;

import com.synrgy.binarfud.Binarfud.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<Users, UUID> {

    @Procedure(procedureName = "insert_user_data")
    void insertUserData(String username, String emailAddress, String password);

    Optional<Users> findByUsername(String username);

    List<Users> findByUsernameLike(String s);
}
