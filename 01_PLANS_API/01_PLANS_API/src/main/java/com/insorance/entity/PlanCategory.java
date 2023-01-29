package com.insorance.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
@Entity
@Data
@Table(name = "PLAN_CATEGORY")
public class PlanCategory {
    @Id
    @Column(name = "CATEGORY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Integer categoryId;

    @Column(name = "CATEGORY_NAME")
    private String categoryName;
    @Column(name = "ACTIVATE")
    private String activeSw;
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Column(name = "UPDATED_BY")
    private String updatedBy;
    @Column(name = "CREATED_DATE",updatable = false)
    @CreationTimestamp
    private LocalDate createDate;
    @Column(name = "UPDATED_DATE",insertable = false)
    @UpdateTimestamp
    private LocalDate updateDate;

}
