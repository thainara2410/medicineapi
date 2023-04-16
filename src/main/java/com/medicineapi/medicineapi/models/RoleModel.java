package com.medicineapi.medicineapi.models;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
public class RoleModel {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private long id;
    private String name;

    public RoleModel(long id) {
        this.id = id;
    }

}