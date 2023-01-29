package com.insorance.controller;

import com.insorance.constants.AppConstants;
import com.insorance.entity.Plan;
import com.insorance.properties.AppProps;
import com.insorance.service.PlanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class PlanRestController {

    private PlanService planService;

    private  Map<String,String> messages;
    //constructor injection messages for same class appProps constructor parameter
    public PlanRestController(PlanService planService, AppProps appProps) {
        this.planService = planService;
        this.messages = appProps.getMessages();
        System.out.println(this.messages);
    }

    @GetMapping("/categories")
    public ResponseEntity<Map<Integer,String >> planCategories(){
        Map<Integer, String> categories = planService.getPlanCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping("/plan")
    public ResponseEntity<String> savePlan(@RequestBody Plan plan){
        String responseMsg = AppConstants.EMPTY_STR;
        boolean isSaved = planService.savePlan(plan);
        if(isSaved){

             responseMsg = messages.get(AppConstants.PLAN_SAVE_SUCC);

        }else {
             responseMsg = messages.get(AppConstants.PLAN_SAVE_FAIL);
        }
        return new ResponseEntity<>(responseMsg,HttpStatus.CREATED);
    }

    @GetMapping("/plans")
    public ResponseEntity<List<Plan>> plans(){

        List<Plan> allPlans = planService.getAllPlans();
        return  new ResponseEntity<>(allPlans,HttpStatus.OK);
    }
    @GetMapping("plan/{planId}")
    public ResponseEntity<Plan> editPlan(@PathVariable Integer planId){
        Plan planById = planService.getPlanById(planId);
        return new ResponseEntity<>(planById,HttpStatus.OK);
    }
    @DeleteMapping("plan/{planId}")
    public ResponseEntity<String> deletePlan(@PathVariable Integer planId){
        String responseMsg = AppConstants.EMPTY_STR;

        boolean isDeleted = planService.deletePlanById(planId);
        if(isDeleted){
            responseMsg = messages.get(AppConstants.PLAN_DELETE_SUCC);
        }else {
            responseMsg = messages.get(AppConstants.PLAN_DELETE_FAIL);
        }
        return new ResponseEntity<>(responseMsg,HttpStatus.OK);
    }
    @PutMapping("/plan")
    public ResponseEntity<String> updatePlan(@RequestBody Plan plan){

        boolean isUpdated = planService.updatePlan(plan);
        String responseMsg = AppConstants.EMPTY_STR;

        if(isUpdated){
            responseMsg =messages.get(AppConstants.PLAN_UPDATE_SUCC);
        }else {
            responseMsg =messages.get(AppConstants.PLAN_UPDATE_FAIL);
        }
        return new ResponseEntity<>(responseMsg,HttpStatus.OK);
    }

    @PutMapping("/status-change/{planId}/{status}")
    public ResponseEntity<String> statusChange(@PathVariable Integer planId,@PathVariable String status){
        String responseMsg = AppConstants.EMPTY_STR;

        boolean iaStatusChange = planService.planStatusChange(planId, status);
        if(iaStatusChange){
            responseMsg = messages.get(AppConstants.PLAN_STATUS_CHANGE);
        }else {
            responseMsg = messages.get(AppConstants.PLAN_STATUS_CHANGE_FAIL);
        }
        return new ResponseEntity<>(responseMsg,HttpStatus.OK);
    }
}

