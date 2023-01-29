package com.insorance.repository;

import com.insorance.entity.EligibilityDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface EligibilityDetailsRepo extends JpaRepository<EligibilityDetails, Serializable> {
    @Query("select distinct (planName) from EligibilityDetails")//will retrieve unique data from column
    public List<String> findPlanNames();
    @Query("select distinct(planStatus) from EligibilityDetails") //will retrieve unique status
    public List<String> findPlanStatuses();


}
