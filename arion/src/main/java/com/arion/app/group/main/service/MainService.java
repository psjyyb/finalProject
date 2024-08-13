package com.arion.app.group.main.service;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

public interface MainService {
    @Cacheable("modulesCache")
    List<getModuleVO> getmoduleList(getModuleVO getmoduleVO);
    
}