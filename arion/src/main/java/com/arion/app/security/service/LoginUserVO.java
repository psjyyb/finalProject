package com.arion.app.security.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginUserVO implements UserDetails {

    private UserVO userVO;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auths = new ArrayList<>();
        if ("company".equals(userVO.getLoginType())) {
            auths.add(new SimpleGrantedAuthority("ROLE_" + userVO.getSiteResp())); // 사이트 권한 추가
        } else if ("employee".equals(userVO.getLoginType())) {
            auths.add(new SimpleGrantedAuthority("ROLE_" + userVO.getEmployeeResp())); // 직원 권한 추가
        }
        return auths;
    }

    @Override
    public String getPassword() {
        return userVO.getLoginPw();
    }

    @Override
    public String getUsername() {
        return userVO.getLoginId();
    }
    
    public String getCompanyCode() {
        return userVO.getCompanyCode();
    }
    
    public String getDepartmentName() {
        return userVO.getDepartmentName();
    }
    
    public String getRankName() {
        return userVO.getRankName();
    }
    
    public String getEmpName() {
        return userVO.getEmpName();
    }
    
    public int getEmployeeNo() {
    	return userVO.getEmployeeNo();

    }
    
    public String getCeoName() {
    	return userVO.getCeoName();

    }
    
    public String getSiteResp() {
    	return userVO.getSiteResp();

    }
    public String getCompanyName() {
    	return userVO.getCompanyName();
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
