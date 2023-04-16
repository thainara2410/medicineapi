package com.medicineapi.medicineapi.dto;

import java.util.List;

import lombok.Data;

@Data
public class CreateUserRoleDTO {

  private long idUser;

  private List<Long> idsRoles;

}
