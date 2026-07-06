package com.example.mock_api.service;

import com.example.mock_api.dto.Profile;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Getter
@Service
public class ProfileService {
    private final List<Profile> profiles = new ArrayList<>();

    public Profile updateProfile(Profile updatedProfile) {
        Profile existingProfile = fetchProfileById(updatedProfile.getId());

        if (existingProfile == null) {
            profiles.add(updatedProfile);
            return updatedProfile;
        }

        existingProfile.setName(updatedProfile.getName());
        existingProfile.setAvatarUrl(updatedProfile.getAvatarUrl());

        return existingProfile;
    }

    public Profile addProfile(Profile profile) {
        Profile existingProfile = fetchProfileById(profile.getId());

        if (existingProfile != null) {
            return null;
        }

        profiles.add(profile);
        return profile;
    }

    public Profile fetchProfileById(String id) {
        return profiles.stream()
                .filter(profile -> profile.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
