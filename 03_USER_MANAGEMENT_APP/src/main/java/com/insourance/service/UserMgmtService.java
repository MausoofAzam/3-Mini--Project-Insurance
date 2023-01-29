package com.insourance.service;

import com.insourance.binding.ActivateAccount;
import com.insourance.binding.Login;
import com.insourance.binding.User;

import java.util.List;

public interface UserMgmtService {

    public boolean saveUser(User user) throws Exception;
    public boolean activateUserAccount(ActivateAccount activateAcc);
    public List<User> getAllUser();
    public User getUserById(Integer userId);
    public boolean deleteUserById(Integer userId);
    public boolean changeAccStatus(Integer userId, String accStatus);
    public String login(Login login);
    public String forgotPwd(String email);

}
