package com.insourance.controller;

import com.insourance.binding.ActivateAccount;
import com.insourance.binding.Login;
import com.insourance.binding.User;
import com.insourance.service.UserMgmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserRestController {
    @Autowired
    private UserMgmtService service;

    @PostMapping("/user")
    public ResponseEntity<String > userReg(@RequestBody User user) throws Exception {
        boolean saveUser = service.saveUser(user);
        if (saveUser){
            return  new ResponseEntity<>("Registration Success", HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>("Registration Failed",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/activate")
    public ResponseEntity<String > activateAccount(@RequestBody ActivateAccount acc){
        boolean activateUserAcc = service.activateUserAccount(acc);
        if (activateUserAcc){
            return new ResponseEntity<>("Account Activated",HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Invalid Temporary Password",HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("users")
    public ResponseEntity<List<User>> getAllusers(){
        List<User> listUser = service.getAllUser();
        return new ResponseEntity<>(listUser,HttpStatus.OK);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Integer userId){
        User userById = service.getUserById(userId);
            return new ResponseEntity<>(userById,HttpStatus.OK);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<String > deleteUserById(@PathVariable Integer userId){
        boolean isDeleted = service.deleteUserById(userId);
        if (isDeleted){
            return new ResponseEntity<>("Deleted Successfully",HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Failed to delete",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/status/{userId}/{status}")
    public ResponseEntity<String > statusChange(@PathVariable Integer userId,@PathVariable String status){
        boolean isChanged = service.changeAccStatus(userId, status);
        if (isChanged){
            return new ResponseEntity<>("Status Changed",HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Failed to Change",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody Login login){
        String status = service.login(login);
        return new ResponseEntity<>(status,HttpStatus.OK);
    }
    @GetMapping("/forgotPwd/{email}")
    public ResponseEntity<String > forgotPwd(@PathVariable String email){
        String forgotPwd = service.forgotPwd(email);
        return new ResponseEntity<>(forgotPwd,HttpStatus.OK);
    }
}
