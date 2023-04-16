package com.medicineapi.medicineapi.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.medicineapi.medicineapi.enums.Status;
import com.medicineapi.medicineapi.models.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long>  {
        public Optional<UserModel> findByEmail(String username); 
        
        public List<UserModel> findByStatus(Status status);

        @Query("SELECT u from UserModel u JOIN FETCH u.roles where email = :email ")
        UserModel findByEmailFetchRoles(@Param("email") String email);

}
