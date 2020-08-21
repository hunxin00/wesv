package com.nh.wenet.nh.frame.security.vo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

@Data
@AllArgsConstructor
public class UserInfo implements UserDetails {
    private String id;
    private String passWord;
    private String userName;

    @JsonIgnore
    private final Date lastPasswordResetDate;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getAuthorities;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return passWord;
    }

    @Override
    public String getUsername() {
        return passWord;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

}
