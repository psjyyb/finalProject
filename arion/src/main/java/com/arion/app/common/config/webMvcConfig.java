package com.arion.app.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class webMvcConfig implements WebMvcConfigurer{
	
    // Logger 인스턴스 생성
    private static final Logger logger = LoggerFactory.getLogger(webMvcConfig.class);

	
	@Value("${file.upload.url}")
	private String uploadPath;

	//경로 등록
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String path = "file:///"+uploadPath + '/';
		System.out.println(path);
		logger.info("Resource Path: " + path); // 로그 출력
		registry
			.addResourceHandler("/files/**")    // URL
			.addResourceLocations(path ); //실제 경로
	}

}
