package ru.niyaz.test.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.niyaz.test.entity.User;

import java.util.Collection;
import java.util.List;

/**
 * Created by user on 06.09.15.
 */
public class UserDetailsImpl implements UserDetails {

    private Long userId;
    private List<GrantedAuthority> grantedAuthorityList;
    private String username;
    private String password;
    private String name;
    private User currentUser;

    public UserDetailsImpl(Long userId, String name, String username, String password, List<GrantedAuthority> grantedAuthorityList) {
        this.grantedAuthorityList = grantedAuthorityList;
        this.username = username;
        this.password = password;
        this.userId = userId;
        this.name = name;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorityList;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
