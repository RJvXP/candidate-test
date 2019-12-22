/*
 * Copyright 2019. National Association of Insurance Commissioners.
 */

package org.naic.service;

import org.naic.model.UserProfile;
import org.naic.persistence.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ValidateUserProfileService implements  IValidateUserProfileService {

    @Autowired
    UserProfileRepository userProfileRepository;

    @Override
    public List<String> validateUserProfile(UserProfile userProfile) {
       List<String> errorMessages = new ArrayList<>();
        if (StringUtils.isEmpty(userProfile.getFirstName())) {
            errorMessages.add("First Name is Required");
        }
        if (StringUtils.isEmpty(userProfile.getLastName())) {
            errorMessages.add("Last Name is Required");
        }
        if (StringUtils.isEmpty(userProfile.getEmail())) {
            errorMessages.add("Email is Required");
        }
        if (userProfileRepository.countByEmail(userProfile.getEmail()) >= 1 ) {
            errorMessages.add("Email is not Unique");
        }

        return errorMessages;
    }
}
