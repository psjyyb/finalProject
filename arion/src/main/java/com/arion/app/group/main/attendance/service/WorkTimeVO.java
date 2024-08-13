package com.arion.app.group.main.attendance.service;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.arion.app.group.board.service.BoardVO;

import lombok.Data;

@Data
public class WorkTimeVO {

	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate attdate;
	private String worktimehour;
	private Integer worktimeminute;
	private String standardworktimehour;
	private Integer standardworktimeminute;
	private String intervalhour;
	private Integer intervalminute;
	
	
}
