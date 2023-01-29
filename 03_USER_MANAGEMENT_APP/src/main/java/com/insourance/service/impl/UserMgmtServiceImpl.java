package com.insourance.service.impl;

import com.insourance.binding.ActivateAccount;
import com.insourance.binding.Login;
import com.insourance.binding.User;
import com.insourance.entity.UserMaster;
import com.insourance.repository.UserMasterRepository;
import com.insourance.service.UserMgmtService;
import com.insourance.utils.EmailUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
@Service
public class UserMgmtServiceImpl implements UserMgmtService {

    @Autowired
    public UserMasterRepository repository;
    @Autowired
    private EmailUtils emailUtils;

    @Override
    public boolean saveUser(User user) {
        UserMaster entity = new UserMaster();
        BeanUtils.copyProperties(user, entity);
        entity.setPassword(generateRandomPwd());
        entity.setAccStatus("In-Active");
        UserMaster save = repository.save(entity);
        String subject = "Your Registration Success";
        String fileName = "REG-EMAIL-BODY.txt";
        String body = readEmailBody(entity.getFullName(), entity.getPassword(), fileName);
        emailUtils.sendEmail(user.getEmail(), subject, body);
        //send registration email

        if (save.getUserId() != null) {
            return true;
        } else {
            return false;
        }
//       return save.getUserId()!=null;
    }

    @Override
    public boolean activateUserAccount(ActivateAccount activateAcc) {
        //first check the Temp password that are given are correct or not
        UserMaster entity = new UserMaster();
        entity.setEmail(activateAcc.getEmail());
        entity.setPassword(activateAcc.getTempPwd());
        //example will prepare a query like, select * from user_master where email=? and pwd =?
        Example<UserMaster> of = Example.of(entity);
        List<UserMaster> findAll = repository.findAll(of);
        //if the password is not matched in database then it will return false
        if (findAll.isEmpty()) {
            return false;
        } else {
            UserMaster userMaster = findAll.get(0);
            userMaster.setPassword(activateAcc.getNewPwd());
            userMaster.setAccStatus("Active");
            repository.save(userMaster);
            return true;
        }
    }

    @Override
    public List<User> getAllUser() {
        List<UserMaster> findAll = repository.findAll();
        List<User> users = new ArrayList<>();
        for (UserMaster entity : findAll) {
            User user = new User();
            BeanUtils.copyProperties(entity, user);
            users.add(user);
        }
        return users;
    }

    @Override
    public User getUserById(Integer userId) {
        Optional<UserMaster> findById = repository.findById(userId);
        User user = new User();
        if (findById.isPresent()) {
            UserMaster userMaster = findById.get();
            BeanUtils.copyProperties( userMaster,user);
            return user;
        }
        return null;
    }

    @Override
    public boolean deleteUserById(Integer userId) {
        try {
            repository.deleteById(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean changeAccStatus(Integer userId, String accStatus) {
        Optional<UserMaster> findById = repository.findById(userId);
        if (findById.isPresent()) {
            UserMaster userMaster = findById.get();
            userMaster.setAccStatus(accStatus);
            repository.save(userMaster);
            return true;
        }
        return false;
    }

    @Override
    public String login(Login login) {
//        1st Approach
        UserMaster entity = new UserMaster();
        entity.setEmail(login.getEmail());
        entity.setPassword(login.getPassword());
        //select * from user_master where email=? and password=?;
        Example<UserMaster> of = Example.of(entity);
        List<UserMaster> findAll = repository.findAll(of);
        if (findAll.isEmpty()) {
            return "Invalid Credentials";
        } else {
            UserMaster userMaster = findAll.get(0);
            if (userMaster.getAccStatus().equals("Active")) {
                return "Success";
            } else {
                return "Account not Activated";
            }
        }
        //second Approach
//        UserMaster entity = repository.findByEmailandPassword(login.getEmail(), login.getPassword());
//        if (entity==null) {
//            return "Invalid Credential";
//        } else {
//            if (entity.getAccStatus().equals("Active")) {
//                return "Success";
//            } else {
//                return "Account Not Activated";
//            }
//        }
    }

    @Override
    public String forgotPwd(String email) {
        UserMaster entity = repository.findByEmail(email);
        if (entity == null) {
            return "Invalid Email";
        }
        //todo :send pwd to user in email
        String subject = "Forgot Password";
        String fileName = "RECOVER-PWD-BODY.txt";
        String body = readEmailBody(entity.getFullName(), entity.getPassword(), fileName);
        boolean sendEmail = emailUtils.sendEmail(email, subject, body);
        if (sendEmail) {
            return "Password sent to your Registered Email";
        }
        return null;
    }

    private String generateRandomPwd() {
        // create a string of all characters
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        // create random string builder
        StringBuilder sb = new StringBuilder();
        // create an object of Random class
        Random random = new Random();
        // specify length of random string
        int length = 6;
        for (int i = 0; i < length; i++) {
            // generate random index number
            int index = random.nextInt(alphabet.length());
            // get character specified by index
            // from the string
            char randomChar = alphabet.charAt(index);
            // append the character to string builder
            sb.append(randomChar);
        }
        String randomString = sb.toString();
        return randomString;
    }

    private String readEmailBody(String fullName, String pwd, String fileName) {
        String url = "";
        String mailBody = null;
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);

            StringBuffer buffer = new StringBuffer();
            String line = br.readLine();
            //here while is taken bcoz file is containing multiple line . i need to read the data line by line
            //so in place of if we are king while loop.
            while (line != null) {
                buffer.append(line);
                //process the data line by line cycle forming everytime it will replaced with 1st line then second line ,3rd

                line = br.readLine();
            }
            br.close();
            buffer.toString();
            mailBody = mailBody.replace("{FULLNAME}", fullName);
            mailBody = mailBody.replace("{TEMP-PWD}", pwd);
            mailBody = mailBody.replace("{URL}", url);
            mailBody = mailBody.replace("PWD", url);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return mailBody;
    }
}

