package com.ginkgocap.parasol.oauth2.domain.user;

import com.ginkgocap.parasol.oauth2.domain.shared.Repository;

/**
 * @author Shengzhao Li
 */

public interface UserRepository extends Repository {

    User findByGuid(String guid);

    void saveUser(User user);

    void updateUser(User user);

    User findByUsername(String username);

}