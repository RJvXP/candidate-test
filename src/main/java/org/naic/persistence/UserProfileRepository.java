/*
 * Copyright 2019. National Association of Insurance Commissioners.
 */

package org.naic.persistence;

import org.naic.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    @Query("select u from UserProfile  u where u.username=?#{principal.username}")
    public UserProfile findUserProfileByUsername();

    @Query("select u from UserProfile  u where u.email = :email")
    public List<UserProfile> findAllByEmail(@Param("email") String email);
}
