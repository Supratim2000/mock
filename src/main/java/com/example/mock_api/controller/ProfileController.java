package com.example.mock_api.controller;

import com.example.mock_api.dto.Profile;
import com.example.mock_api.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    @PostMapping("/add")
    public ResponseEntity<Profile> addProfile(@RequestBody(required = true) Profile profile) {
        Profile createdProfile = profileService.addProfile(profile);
        if (createdProfile == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProfile);
    }

    @PutMapping("/update")
    public ResponseEntity<Profile> updateProfile(@RequestBody(required = true) Profile profile) {
        Profile updatedProfile = profileService.updateProfile(profile);
        return ResponseEntity.ok(updatedProfile);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profile> fetchProfileById(@PathVariable String id) {
        Profile profile = profileService.fetchProfileById(id);
        if (profile == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(profile);
    }
}
