package com.ginkgocap.parasol.oauth2.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ginkgocap.parasol.user.model.UserLoginRegister;

public class SecurityUserDetails implements UserDetails {

    private static final long serialVersionUID = 3957586021470480642L;
    
    protected String salt;
    
    protected UserLoginRegister userLoginRegister;

    protected List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();

    public SecurityUserDetails() {
    }

    public SecurityUserDetails(UserLoginRegister userLoginRegister) {
        this.userLoginRegister = userLoginRegister;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return this.grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.userLoginRegister.getPassword();
    }

    @Override
    public String getUsername() {
        return userLoginRegister.getPassport();
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
        return true;
    }

    public UserLoginRegister user() {
        return userLoginRegister;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("{user=").append(userLoginRegister);
        sb.append('}');
        return sb.toString();
    }

	public String getSalt() {
		return userLoginRegister.getSalt();
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
}