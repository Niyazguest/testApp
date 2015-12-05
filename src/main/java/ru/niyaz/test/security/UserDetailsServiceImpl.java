package ru.niyaz.test.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.niyaz.test.dao.UserDao;
import ru.niyaz.test.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 06.09.15.
 */

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        list.add(new SimpleGrantedAuthority("ROLE_USER"));
        User user = userDao.getUser(username);
        if (user == null)
            throw new UsernameNotFoundException("Login error");
        UserDetailsImpl userDetails = new UserDetailsImpl(user.getUserId(), user.getName(), user.getLogin(), user.getPassword(), list);
        userDetails.setCurrentUser(user);
        return userDetails;
    }

}
