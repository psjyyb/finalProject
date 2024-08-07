package com.arion.app.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class webMvcConfig implements WebMvcConfigurer{
	@Value("${file.upload.path}")
	private String uploadPath;

	//경로 등록
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
			.addResourceHandler("/files/**")    // URL
			.addResourceLocations("file:///"+uploadPath , ""); //실제 경로
	}

}
