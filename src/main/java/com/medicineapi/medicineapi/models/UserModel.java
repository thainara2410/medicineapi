package com.medicineapi.medicineapi.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


import com.medicineapi.medicineapi.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserModel{
    //private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private long id;
    @Column
    private String nome;
    @Column(unique = true, nullable = false)
    private String email;
    @Column (unique = true)
    private String coren;
    @Column
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column
    private String password;
    @ManyToMany
    private Collection<RoleModel> roles;

    public long getId(){
        return id;
    }
    

}

