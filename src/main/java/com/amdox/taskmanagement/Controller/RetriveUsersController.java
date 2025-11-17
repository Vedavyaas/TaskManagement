package com.amdox.taskmanagement.Controller;

import com.amdox.taskmanagement.Service.RetriveUsersService;
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
    public List<UserDTO> getAllUserByOrganization(@RequestBody UserDTO userDTO) {
        return retriveUsersService.getUsersByOrganization(userDTO);
    }

    @GetMapping("/get/domain")
    public List<UserDTO> getAllUsers(@RequestBody UserDTO userDTO) {
        return retriveUsersService.getUsersByDomain(userDTO);
    }

    @GetMapping("/get/email")
    public List<UserDTO> getAllUsersByEmail(@RequestBody UserDTO userDTO, @RequestParam String email) {
        return retriveUsersService.getUserByEmail(userDTO, email);
    }

    @GetMapping("/get/username")
    public List<UserDTO> getAllUsersByUsername(@RequestBody UserDTO userDTO, @RequestParam String username) {
        return retriveUsersService.getUsersByUsername(userDTO, username);
    }

    @GetMapping("/get/companyName")
    public List<UserDTO> getAllUsersByCompanyName(@RequestBody UserDTO userDTO, @RequestParam String companyName) {
        return retriveUsersService.getUsersByCompanyName(userDTO, companyName);
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

