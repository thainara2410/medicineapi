package com.medicineapi.medicineapi.repository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medicineapi.medicineapi.enums.RoleName;
import com.medicineapi.medicineapi.enums.Status;

import com.medicineapi.medicineapi.models.RoleModel;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel, Long>  {

    //RoleModel findByRoleName(String string);  
    RoleModel findByName(String string); 
}
