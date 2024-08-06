package com.arion.app.security.handler;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.arion.app.security.service.LoginUserVO;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub
		String redirectUrl = "/login";
		
        HttpSession session = request.getSession(true);
                
		LoginUserVO userDetail = (LoginUserVO) authentication.getPrincipal();
		
		session.setAttribute("companyCode", userDetail.getCompanyCode());
	    session.setAttribute("loginId", userDetail.getUsername());
	    session.setAttribute("rankName", userDetail.getRankName());
	    session.setAttribute("department", userDetail.getDepartmentName());
	    session.setAttribute("empName", userDetail.getEmpName());
		
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority authority : authorities) {
			String roleName = authority.getAuthority();
			if (roleName.equals("ROLE_ADMIN")) {
				redirectUrl = "/admin";
				break;
			} else if (roleName.equals("ROLE_USER")) {
				redirectUrl = "/groupAdmin";
				break;
			} else if (roleName.equals("ROLE_일반사원") || roleName.equals("ROLE_사원관리자")) {
				redirectUrl = "/group";
				break;
			}
		}

		response.sendRedirect(redirectUrl);

	}

}
