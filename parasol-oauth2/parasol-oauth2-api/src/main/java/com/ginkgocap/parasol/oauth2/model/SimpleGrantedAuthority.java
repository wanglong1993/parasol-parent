package com.ginkgocap.parasol.oauth2.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

public final class SimpleGrantedAuthority implements GrantedAuthority {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private final String role;

    public SimpleGrantedAuthority(String role) {
        this.role = role;
    }

    public String getAuthority() {
        return role;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof SimpleGrantedAuthority) {
            return role.equals(((SimpleGrantedAuthority) obj).role);
        }

        return false;
    }

    public int hashCode() {
        return this.role.hashCode();
    }

    public String toString() {
        return this.role;
    }
}
