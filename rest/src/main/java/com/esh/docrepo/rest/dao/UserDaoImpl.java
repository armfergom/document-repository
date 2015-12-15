package com.esh.docrepo.rest.dao;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.esh.docrepo.rest.model.User;

@Service
public class UserDaoImpl extends AbstractDao<User>implements UserDao {

    public UserDaoImpl() {
        super(User.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return find(username);
    }

}
