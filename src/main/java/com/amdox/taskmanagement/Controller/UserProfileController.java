package com.amdox.taskmanagement.Controller;

import com.amdox.taskmanagement.Entity.UserProfile;
import com.amdox.taskmanagement.Service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profiles")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @PostMapping
    public ResponseEntity<UserProfile> createProfile(@RequestBody UserProfile profile) {
        return ResponseEntity.ok(userProfileService.createProfile(profile));
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserProfile> getProfile(@PathVariable String email) {
        return ResponseEntity.ok(userProfileService.getProfileByEmail(email));
    }

    @GetMapping
    public ResponseEntity<List<UserProfile>> getAllProfiles() {
        return ResponseEntity.ok(userProfileService.getAllProfiles());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserProfile> updateProfile(@PathVariable Long id, @RequestBody UserProfile profile) {
        return ResponseEntity.ok(userProfileService.updateProfile(id, profile));
    }
}
