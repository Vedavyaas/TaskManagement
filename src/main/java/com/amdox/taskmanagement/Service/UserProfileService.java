package com.amdox.taskmanagement.Service;

import com.amdox.taskmanagement.Entity.UserProfile;
import com.amdox.taskmanagement.Repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepo;

    public UserProfile createProfile(UserProfile profile) {
        if(userProfileRepo.findByUserOfficialEmail(profile.getUserOfficialEmail()).isPresent()) {
            throw new RuntimeException("Profile already exists for email: " + profile.getUserOfficialEmail());
        }
        return userProfileRepo.save(profile);
    }

    public UserProfile getProfileByEmail(String email) {
        return userProfileRepo.findByUserOfficialEmail(email)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
    }

    public List<UserProfile> getAllProfiles() {
        return userProfileRepo.findAll();
    }

    public UserProfile updateProfile(Long id, UserProfile updatedProfile) {
        UserProfile existing = userProfileRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        
        existing.setUserName(updatedProfile.getUserName());
        existing.setDesignation(updatedProfile.getDesignation());
        existing.setDepartment(updatedProfile.getDepartment());
        existing.setOrganizationName(updatedProfile.getOrganizationName());
        
        return userProfileRepo.save(existing);
    }
}
