package com.amdox.taskmanagement.Controller;

import com.amdox.taskmanagement.Assests.NewAccount;
import com.amdox.taskmanagement.Service.UserAccountCreationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class UserAccountCreationController {
    private final UserAccountCreationService userAccountCreationService;

    public UserAccountCreationController(UserAccountCreationService userAccountCreationService) {
        this.userAccountCreationService = userAccountCreationService;
    }

    @PostMapping("/verify")
    public ResponseEntity<Map<String, String>> verifyAccount(@RequestBody NewAccount newAccount) {
        String response = userAccountCreationService.verifyAccount(newAccount);
        return ResponseEntity.ok(Collections.singletonMap("message", response));
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createUserAccount(@RequestBody NewAccount newAccount, @RequestParam String OTP) {
        String response = userAccountCreationService.createAccount(newAccount, OTP);
        return new ResponseEntity<>(Collections.singletonMap("message", response), HttpStatus.CREATED);
    }
}