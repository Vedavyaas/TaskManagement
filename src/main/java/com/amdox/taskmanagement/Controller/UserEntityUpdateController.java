package com.amdox.taskmanagement.Controller;

import com.amdox.taskmanagement.Service.UserEntityUpdateService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserEntityUpdateController {
    private final UserEntityUpdateService userEntityUpdateService;

    public UserEntityUpdateController(UserEntityUpdateService userEntityUpdateService) {
        this.userEntityUpdateService = userEntityUpdateService;
    }

    @PutMapping("/update/username")
    public String updateUsername(@RequestParam String email, @RequestParam String username, @RequestParam String password) {
        return userEntityUpdateService.updateUsername(email, username, password);
    }

    @PutMapping("/update/fullName")
    public String updateFullName(@RequestParam String email, @RequestParam String fullName, @RequestParam String password) {
        return userEntityUpdateService.updateFullName(email, fullName, password);
    }

    @PutMapping("/update/organization")
    public String updateOrganization(@RequestParam String email, @RequestParam String organization, @RequestParam String password) {
        return userEntityUpdateService.updateOrganization(email, organization, password);
    }

    @PutMapping("/update/domain")
    public String updateDomain(@RequestParam String email, @RequestParam String domain, @RequestParam String password) {
        return userEntityUpdateService.updateDomain(email, domain, password);
    }

    @PutMapping("/update/companyName")
    public String updateCompanyName(@RequestParam String email, @RequestParam String companyName, @RequestParam String password) {
        return userEntityUpdateService.updateCompanyName(email, companyName, password);
    }
}
