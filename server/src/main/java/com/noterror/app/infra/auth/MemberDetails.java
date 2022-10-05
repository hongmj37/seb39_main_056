<<<<<<< HEAD
package com.noterror.app.infra.auth;


import com.noterror.app.api.entity.member.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class MemberDetails extends Member implements UserDetails {

    private final CustomAuthorityUtils authorityUtils;

    public MemberDetails(Member member, CustomAuthorityUtils authorityUtils) {
        this.authorityUtils = authorityUtils;
        setMemberId(member.getMemberId());
        setEmail(member.getEmail());
        setPassword(member.getPassword());
        setRoles(member.getRoles());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorityUtils.createAuthorities(this.getRoles());
    }

    @Override
    public String getUsername() {
        return getEmail();
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
}
=======
package com.noterror.app.infra.auth;

import com.noterror.app.api.domain.entity.member.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class MemberDetails extends Member implements UserDetails {

    private final CustomAuthorityUtils authorityUtils;

    public MemberDetails(Member member, CustomAuthorityUtils authorityUtils) {
        this.authorityUtils = authorityUtils;
        setMemberId(member.getMemberId());
        setEmail(member.getEmail());
        setPassword(member.getPassword());
        setRoles(member.getRoles());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorityUtils.createAuthorities(this.getRoles());
    }

    @Override
    public String getUsername() {
        return getEmail();
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
}
>>>>>>> 3ccc649b13e31b48388eeebf66b5113d94af03ad
