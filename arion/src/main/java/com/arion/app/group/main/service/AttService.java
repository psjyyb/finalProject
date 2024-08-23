package com.arion.app.group.main.service;

import java.util.Date;

public interface AttService {
    int startAtt(AttVO attVO);
    AttVO getAttendanceByDate(Integer empNo, Date attDate);
    int endAtt(AttVO attVO);
}
