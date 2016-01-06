package com.ginkgocap.parasol.oauth2.domain.shared.security;

import com.ginkgocap.parasol.user.model.UserLoginRegister;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class OauthUserDetails implements UserDetails {

    private static final long serialVersionUID = 3957586021470480642L;

//    protected static final String ROLE_USER = "ROLE_USER";
//    protected static final GrantedAuthority DEFAULT_USER_ROLE = new SimpleGrantedAuthority(ROLE_USER);
    
    protected String salt;
    
    protected UserLoginRegister userLoginRegister;

    protected List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();

    public OauthUserDetails() {
    }

    public OauthUserDetails(UserLoginRegister userLoginRegister) {
        this.userLoginRegister = userLoginRegister;
//        initialAuthorities();
    }

//    private void initialAuthorities() {
        //Default, everyone have it
//        this.grantedAuthorities.add(DEFAULT_USER_ROLE);
//    }

    /**
     * Return authorities, more information see {@link #initialAuthorities()}
     *
     * @return Collection of GrantedAuthority
     */
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