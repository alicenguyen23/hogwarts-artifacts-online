package edu.tcu.cs.hogwartsartifactsonline.hogwartsuser;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collection;

public class MyUserPrincipal implements UserDetails {
    private final HogwartsUser hogwartsUser;

    public MyUserPrincipal(HogwartsUser hogwartsUser) {
        this.hogwartsUser = hogwartsUser;
    }

    public HogwartsUser getHogwartsUser() {
        return hogwartsUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // convert a user's role from space-delimited string to a list of SimpleGrantedAuthority objects
        // before conversion, we need to add this "ROLE_" prefix to each role name
        return Arrays.stream(StringUtils.tokenizeToStringArray(this.hogwartsUser.getRoles(), " "))
                .map(role -> new SimpleGrantedAuthority("ROLE " + role))
                .toList();
    }

    @Override
    public String getPassword() {
        return this.hogwartsUser.getPassword();
    }

    @Override
    public String getUsername() {
        return this.hogwartsUser.getUsername();
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
        return this.hogwartsUser.isEnabled();
    }
}
