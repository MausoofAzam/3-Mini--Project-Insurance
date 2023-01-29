package com.insorance.entity;

import lombok.Data;
import org.hibernate.query.criteria.internal.predicate.PredicateImplementor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "ELIGIBILITY_DETAILS")
@Data
public class EligibilityDetails {
    @Id
    //here this data is used only for reading so no generated value
    private Integer eligId;
    private  String name;
    private Long mobile;
    private  String email;
    private Character gender;
    private Long ssn;
    private String planName;
    private  String planStatus;
    private LocalDate planStartDate;
    private  LocalDate planEndDate;
    private LocalDate createdDate;
    private LocalDate updatedDate;
    private  String createdBy;
    private String updatedBy;
}
