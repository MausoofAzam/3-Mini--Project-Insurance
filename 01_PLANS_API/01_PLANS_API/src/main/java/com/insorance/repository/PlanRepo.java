package com.insorance.repository;

import com.insorance.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepo extends JpaRepository<Plan,Integer> {
}
