package com.liakhovskyi.dao;

import com.liakhovskyi.model.User;

public interface UserDao {

    User addUser(User user);

    User getByToken(String token);

}
