package com.amdox.taskmanagement.Controller;

import com.amdox.taskmanagement.Assests.NewAccount;
import com.amdox.taskmanagement.Service.UserAccountCreationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAccountCreationController {
    private final UserAccountCreationService userAccountCreationService;

    public UserAccountCreationController(UserAccountCreationService userAccountCreationService) {
        this.userAccountCreationService = userAccountCreationService;
    }

    @PostMapping("/verify")
    public String verifyAccount(@RequestBody NewAccount newAccount) {
        return userAccountCreationService.verifyAccount(newAccount);
    }

    @PostMapping("/create")
    public String createUserAccount(@RequestBody NewAccount newAccount, @RequestParam String OTP) {
        return userAccountCreationService.createAccount(newAccount, OTP);
    }
}