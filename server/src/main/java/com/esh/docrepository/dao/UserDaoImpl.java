package com.esh.docrepository.dao;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.esh.docrepository.model.User;

public class UserDaoImpl extends AbstractDao<User>implements UserDao {

    public UserDaoImpl() {
        super(User.class);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return find(username);
    }

}
