package com.insourance.repository;

import com.insourance.entity.UserMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface UserMasterRepository extends JpaRepository<UserMaster, Integer> {
    public UserMaster findByEmailAndPassword(String email, String pwd);
    public UserMaster findByEmail(String email);

}
