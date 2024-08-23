package com.arion.app.group.main.service.impl;

import java.time.ZonedDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arion.app.group.main.mapper.AttMapper;
import com.arion.app.group.main.service.AttService;
import com.arion.app.group.main.service.AttVO;

@Service
public class AttServiceImpl implements AttService{

	@Autowired
	private AttMapper attMapper;
	    @Override
	    public int startAtt(AttVO attVO) {
	        Date now = new Date();
	        attVO.setAttDate(now);

	        // 출근 기록 조회
	        AttVO existingAttVO = attMapper.selectAttendanceByDate(attVO.getEmpNo(), now);
	        if (existingAttVO != null) {
	            return -1; // 이미 출근 기록이 있는 경우
	        }

	        if (now.getHours() >= 9) {
	            attVO.setState("지각");
	        } else {
	            attVO.setState("정상출근");
	        }
	        attVO.setStartTime(now);

	        // 출근 기록 추가
	        int result = attMapper.startAtt(attVO);

	        // 성공적으로 기록이 추가되면 attNo가 설정됩니다.
	        return result == 1 ? attVO.getAttNo() : -1;
	    }

	    @Override
	    public AttVO getAttendanceByDate(Integer empNo, Date attDate) {
	        return attMapper.selectAttendanceByDate(empNo, attDate);
	    }

	    @Override
	    public int endAtt(AttVO attVO) {
	        attVO.setEndTime(new Date());
	        return attMapper.endAtt(attVO);
	    }

}
