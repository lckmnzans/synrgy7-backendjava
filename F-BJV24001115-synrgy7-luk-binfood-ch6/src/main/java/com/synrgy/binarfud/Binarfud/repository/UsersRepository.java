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
    void insertUserData(String name, String username, String emailAddress, String password);

    @Procedure(procedureName = "delete_user_data")
    void deleteUserData(UUID id);

    @Procedure(procedureName = "update_user_username")
    void updateUserUsername(UUID id, String username);

    Optional<Users> findByUsernameAndDeleted(String username, Boolean deleted);

    List<Users> findByUsernameLike(String s);

    Optional<Users> findByUsername(String username);
}
