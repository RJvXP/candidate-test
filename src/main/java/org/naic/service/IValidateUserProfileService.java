/*
 * Copyright 2019. National Association of Insurance Commissioners.
 */

package org.naic.service;

import org.naic.model.UserProfile;

import java.util.List;

public interface IValidateUserProfileService {

    public List<String> validateUserProfile(UserProfile userProfile);
}
