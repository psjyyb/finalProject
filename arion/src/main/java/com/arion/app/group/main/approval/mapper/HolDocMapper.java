package com.arion.app.group.main.approval.mapper;

import com.arion.app.group.main.approval.service.HolDocVO;

public interface HolDocMapper {
	public void insertHolDoc(HolDocVO holDocVO);
	
	public HolDocVO holDocInfo(HolDocVO holDocVO);
}
