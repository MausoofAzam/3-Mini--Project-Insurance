package com.insorance.service.impl;

import com.insorance.entity.Plan;
import com.insorance.entity.PlanCategory;
import com.insorance.repository.PlanCategoryRepo;
import com.insorance.repository.PlanRepo;
import com.insorance.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PlanServiceImpl implements PlanService {
    @Autowired
    private PlanRepo planRepo;
    @Autowired
    private PlanCategoryRepo categoryRepo;
    @Override
    public Map<Integer, String> getPlanCategories() {
        List<PlanCategory> categories = categoryRepo.findAll();
        Map<Integer,String > categoryMap = new HashMap<>();

        categories.forEach(category -> {
            categoryMap.put(category.getCategoryId(), category.getCategoryName());
        });
        return  categoryMap;
    }

    @Override
    public boolean savePlan(Plan plan) {
        Plan newPlan = planRepo.save(plan);
       /* if(newPlan.getPlanId()!=null){
            return true;
        }else {
            return false;
        }*/
//      return   newPlan.getPlanId()!=null ?true:false;
        return newPlan.getPlanId() !=null;
    }

    @Override
    public List<Plan> getAllPlans() {
       return planRepo.findAll();
    }

    @Override
    public Plan getPlanById(Integer planId) {
        Optional<Plan> findById = planRepo.findById(planId);
        if (findById.isPresent()){
            return findById.get();
        }else {
            return null;
        }
    }

    @Override
    public boolean updatePlan(Plan plan) {
        Plan update = planRepo.save(plan);
        /*if(plan.getPlanId()!=null){
            return true;
        }
        return false;*/
        return plan.getPlanId()!=null;
    }

    @Override
    public boolean deletePlanById(Integer planId) {
        boolean status = false;
       try {
           planRepo.deleteById(planId);
           status =true;
       }catch (Exception e){
           e.printStackTrace();
       }
       return status;
    }

    @Override
    public boolean planStatusChange(Integer planId, String status) {

        Optional<Plan> findById = planRepo.findById(planId);
        if (findById.isPresent()){
            Plan plan = findById.get();
            plan.setActiveSw(status);
            planRepo.save(plan);
            return true;
        }
        return false;
    }
}
