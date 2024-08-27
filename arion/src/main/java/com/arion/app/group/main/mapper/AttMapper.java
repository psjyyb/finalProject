package com.arion.app.group.main.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.arion.app.group.main.service.AttVO;

@Mapper
public interface AttMapper {
	  int startAtt(AttVO attVO);

	    // 출근 기록 조회
	    AttVO selectAttendanceByDate(@Param("empNo") Integer empNo, @Param("attDate") Date attDate);
	    int endAtt(AttVO attVO);
	    // 출근상태조회
	    AttVO selectAttendanceStatus(int empNo);
}
