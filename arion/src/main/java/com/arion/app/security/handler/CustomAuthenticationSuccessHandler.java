package com.arion.app.security.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.arion.app.group.main.mapper.MainMapper;
import com.arion.app.group.main.service.SubModuleVO;
import com.arion.app.group.main.service.getModuleVO;
import com.arion.app.security.service.LoginUserVO;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	MainMapper mainMapper;
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
	    session.setAttribute("ceoName", userDetail.getCeoName());
		session.setAttribute("employeeNo", userDetail.getEmployeeNo());
		session.setAttribute("siteResp", userDetail.getSiteResp());
	 

	    System.out.println("세션에 저장되는 정보들");
	    System.out.println("회사코드 : " + userDetail.getCompanyCode());
	    System.out.println("아이디 : " + userDetail.getUsername());
	    System.out.println("직급 : " + userDetail.getRankName());
	    System.out.println("부서이름 : " + userDetail.getDepartmentName());
	    System.out.println("사원이름 : " + userDetail.getEmpName());
	    System.out.println("대표이름 : " + userDetail.getCeoName());
	    System.out.println("사원번호 : " + userDetail.getEmployeeNo());
	    System.out.println("사이트권한 : " + userDetail.getSiteResp());

	    	getModuleVO  getmodule= new getModuleVO();
	        // 상위 모듈 리스트 조회
	        List<getModuleVO> modules = mainMapper.getModules(userDetail.getCompanyCode(), userDetail.getUsername());
	        // 하위 모듈 리스트 조회
	        List<SubModuleVO> subModules = mainMapper.getSubModules(userDetail.getCompanyCode(), userDetail.getUsername());

	        // 상위 모듈과 하위 모듈을 조합
	        for (getModuleVO module : modules) {
	            module.setSubModules(new ArrayList<>());
	            for (SubModuleVO subModule : subModules) {
	                if (module.getModuleNo() != null && subModule.getModuleNo() != null 
	                        && subModule.getModuleNo().equals(module.getModuleNo())) {
	                    module.getSubModules().add(subModule);
	                }
	            }
	        }
	        session.setAttribute("modules", modules);
	        
	
	    
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority authority : authorities) {
			String roleName = authority.getAuthority();
			if (roleName.equals("ROLE_ADMIN")) {
				redirectUrl = "/admin";
				break;
			} else if (roleName.equals("ROLE_USER")) {
				redirectUrl = "/groupAdmin/choice";
				break;
			} else if (roleName.equals("ROLE_NO")) {
				redirectUrl = "/home";
				break;
			} else if (roleName.equals("ROLE_일반사원") || roleName.equals("ROLE_사원관리자")) {
				redirectUrl = "/group";
				break;
			}
		}

		response.sendRedirect(redirectUrl);

	}

}
