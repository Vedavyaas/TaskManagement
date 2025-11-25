package com.amdox.taskmanagement.Controller;

import com.amdox.taskmanagement.Assests.UserDTO;
import com.amdox.taskmanagement.Service.RetriveUsersService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RetriveUsersController {
    private final RetriveUsersService retriveUsersService;

    public RetriveUsersController(RetriveUsersService retriveUsersService) {
        this.retriveUsersService = retriveUsersService;
    }

    @GetMapping("/get/organization")
    public List<UserDTO> getAllUserByOrganization() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return retriveUsersService.getUsersByOrganization(userName);
    }

    @GetMapping("/get/domain")
    public List<UserDTO> getAllUsers() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return retriveUsersService.getUsersByDomain(userName);
    }

    @GetMapping("/get/email")
    public List<UserDTO> getUserByEmail(@RequestParam String email) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return retriveUsersService.getUserByEmail(userName, email);
    }

    @GetMapping("/get/username")
    public List<UserDTO> getUserByUsername(@RequestParam String username) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return retriveUsersService.getUsersByUsername(userName, username);
    }

    @GetMapping("/get/companyName")
    public List<UserDTO> getAllUsersByCompanyName() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return retriveUsersService.getUsersByCompanyName(userName);
    }

    @GetMapping("/public/email")
    public List<UserDTO> getUserByEmailPublic(@RequestParam String email) {
        return retriveUsersService.getUserByEmailPublic(email);
    }

    @GetMapping("/public/username")
    public List<UserDTO> getUserByUsernamePublic(@RequestParam String username) {
        return retriveUsersService.getUserByUsernamePublic(username);
    }
}