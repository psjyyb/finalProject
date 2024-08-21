package com.arion.app.group.main.mail.service;

import com.arion.app.common.service.FileVO;
import lombok.Data;

@Data
public class MailFileVO extends FileVO {
    private int mailNo; // 메일 ID, 메일과 파일을 연관시키기 위해 사용
}
