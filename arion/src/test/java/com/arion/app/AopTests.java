package com.arion.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class AopTests {
	
	@Autowired
	PasswordEncoder passwordE;

	@Test
	public void testEncoder() {
		String password = "admin";

		// DB에 저장된 암호화된 비밀번호
		String enPwd = passwordE.encode(password);
		System.out.println("enPwd : " + enPwd);

		boolean matchResult = passwordE.matches(password, enPwd);
		System.out.println("match : " + matchResult);
		
	}
}
