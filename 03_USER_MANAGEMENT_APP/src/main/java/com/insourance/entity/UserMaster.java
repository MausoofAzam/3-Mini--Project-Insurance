package com.insourance.entity;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
@Data
@Entity
@Table(name = "USER_MASTER")
public class UserMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String fullName;
    private String email;
    private Long mobile;
    private String gender;
    private LocalDate dob;
    private Long ssn;
    private String password;
    private String accStatus;
    @Column(updatable = false)
    @CreationTimestamp
    private LocalDate createdDate;
    @Column(insertable = false)
    @UpdateTimestamp
    private LocalDate updatedDate;
    private  String createdBy;
    private String updatedBy;

}
