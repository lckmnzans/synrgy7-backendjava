package com.synrgy.binarfud.Binarfud.security.service;

import com.synrgy.binarfud.Binarfud.model.Users;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public class UserDetailsImpl implements UserDetails {

    private String username;
    private String password;
    private List<GrantedAuthority> authorities;

    public UserDetailsImpl(String username, String password, List<GrantedAuthority> authorityList) {
        this.username = username;
        this.password = password;
        this.authorities = authorityList;
    }

    public static UserDetails build(Users user) {
        List<GrantedAuthority> authorityList = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(user.getUsername(), user.getPassword(), authorityList);
    }

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

    @Override
    public boolean isEnabled() {
        return true ;
    }
}
