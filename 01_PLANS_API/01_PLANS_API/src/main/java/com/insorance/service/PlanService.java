package com.insorance.service;

import com.insorance.entity.Plan;

import java.util.List;
import java.util.Map;

public interface PlanService {

    //for getting dropdown data we used map here.used to display dropdown data
    public Map<Integer ,String > getPlanCategories();
    public boolean savePlan(Plan plan);
    public List<Plan> getAllPlans();
    public  Plan getPlanById(Integer planId);
    public boolean updatePlan(Plan plan);
    public boolean deletePlanById(Integer planId);
    public boolean planStatusChange(Integer planId, String status);



}
