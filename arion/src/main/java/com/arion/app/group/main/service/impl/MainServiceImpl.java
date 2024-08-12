package com.arion.app.group.main.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arion.app.group.main.mapper.MainMapper;
import com.arion.app.group.main.service.MainService;
import com.arion.app.group.main.service.SubModuleVO;
import com.arion.app.group.main.service.getModuleVO;

@Service
public class MainServiceImpl implements MainService {

    @Autowired
    private MainMapper mainMapper;

    @Override
    public List<getModuleVO> getmoduleList(getModuleVO getmoduleVO) {
        // 상위 모듈 리스트 조회
        List<getModuleVO> modules = mainMapper.getModules(getmoduleVO.getCompanyCode(), getmoduleVO.getEmployeeId());
        // 하위 모듈 리스트 조회
        List<SubModuleVO> subModules = mainMapper.getSubModules();

        // 상위 모듈과 하위 모듈을 조합
        for (getModuleVO module : modules) {  // 각 모듈에 대해 빈 하위 모듈 리스트를 초기화합니다.
            module.setSubModules(new ArrayList<>()); // 초기화

            for (SubModuleVO subModule : subModules) {
            	 // 현재 모듈의 번호와 하위 모듈의 번호가 일치하는 경우
                if (module.getModuleNo() != null && subModule.getModuleNo() != null 
                        && subModule.getModuleNo().equals(module.getModuleNo())) {
                    // 해당 하위 모듈을 현재 모듈의 하위 모듈 리스트에 추가합니다.
                    module.getSubModules().add(subModule);
                }
            }
        }
        // 4. 상위 모듈과 연결된 하위 모듈을 포함한 리스트를 반환합니다.
        return modules;
    }
}
