/*
 * Copyright 2019. National Association of Insurance Commissioners.
 */

package org.naic.persistence;

import org.naic.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    @Query("select u from UserProfile  u where u.username=?#{principal.username}")
    public UserProfile findUserProfileByUsername();

    @Query("select count(*) from UserProfile  u where u.email=?#{email}")
    public Integer countByEmail(String email);
}
