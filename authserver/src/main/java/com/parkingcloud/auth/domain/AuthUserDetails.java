package com.parkingcloud.auth.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

/**
 * Created by caryc on 2017/4/6.
 */
public class AuthUserDetails implements UserDetails {
    private final User user;
    private final Collection<? extends GrantedAuthority> authorities;

    private AuthUserDetails(User user, Collection<? extends GrantedAuthority> authorities){
        this.user = user;
        this.authorities = authorities;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getPhoneNumber();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getLocked()==0;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //TODO, check user last password reset date and
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getEnabled()==1;
    }


    public static AuthUserDetails createJwtUser(User user, Collection<? extends GrantedAuthority> authorities){
        return new AuthUserDetails(user,authorities);
    }
}
