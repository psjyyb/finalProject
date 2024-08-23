package com.arion.app.admin.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import com.arion.app.admin.mapper.AdminMapper;
import com.arion.app.admin.service.AdminService;
import com.arion.app.admin.service.AdminVO;
import com.arion.app.admin.service.ChartVO;
import com.arion.app.admin.service.ModuleFileVO;
import com.arion.app.admin.service.ModuleVO;
import com.arion.app.admin.service.QnAVO;

@Service
public class AdminServiceImpl implements AdminService {
	@Value("${file.upload.path}") // 실행되는 시점에 환경변수에 접근하여 값을 가져온다 (운영체제에 따라 바뀌는값을 알아서 찾아 넣어준다)
	private String uploadPath;

	private AdminMapper adminMapper;

	public AdminServiceImpl(AdminMapper adminmapper) {
		this.adminMapper = adminmapper;
	}

	@Override
	public List<AdminVO> subListSelect() {
		return adminMapper.selectsubList();
	}

	@Override
	public AdminVO subInfoSelect(AdminVO adminVO) {
		return adminMapper.selectSubInfo(adminVO);
	}

	@Override
	public List<ModuleVO> modListSelect() {
		return adminMapper.selectModList();
	}

	@Override
	public List<AdminVO> endSunListSelect() {
		return adminMapper.selectEndSubList();
	}

	@Override
	public List<QnAVO> qnaListSelect() {
		return adminMapper.selectQnAList();
	}

	@Override
	public QnAVO qnaInfoSelect(QnAVO qnaVO) {
		return adminMapper.selectQnAInfo(qnaVO);
	}

	@Override
	public int qnaReply(QnAVO qnaVO) {
		return adminMapper.insertReply(qnaVO);
	}

	@Transactional
	@Override
	public int moduleInsert(@ModelAttribute ModuleVO moduleVO) {
		UUID uuid = UUID.randomUUID();
		MultipartFile icon = moduleVO.getModuleIcons();
		String iconName = uuid + "_" + icon.getOriginalFilename();
		moduleVO.setModuleIcon(iconName);
		int result = adminMapper.insertModule(moduleVO);

		String saveNames = uploadPath + '/' + iconName;
		Path savePaths = Paths.get(saveNames);
		// Paths.get() 메서드는 특정 경로의 파일 정보를 가져옵니다.(경로 정의하기)
		try {
			icon.transferTo(savePaths);
			// uploadFile에 파일을 업로드 하는 메서드 transferTo(file)
		} catch (IOException e) {
			e.printStackTrace();
		}
		filesPro(moduleVO);
		return result;
	}
	@Transactional
	@Override
	public int modUpdate(ModuleVO moduleVO) {
		MultipartFile icon = moduleVO.getModuleIcons();
		if(icon != null) {
			UUID uuid = UUID.randomUUID();
			String iconName = uuid + "_" + icon.getOriginalFilename();
			moduleVO.setModuleIcon(iconName);

			String saveNames = uploadPath + '/' + iconName;
			Path savePaths = Paths.get(saveNames);
			// Paths.get() 메서드는 특정 경로의 파일 정보를 가져옵니다.(경로 정의하기)
			try {
				icon.transferTo(savePaths);
				// uploadFile에 파일을 업로드 하는 메서드 transferTo(file)
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			adminMapper.updateMod(moduleVO);
		
		if (moduleVO.getUploadFiles() != null) {
			adminMapper.deleteModFile(moduleVO);
			filesPro(moduleVO);
		}
		return 1;
	}

	@Override
	public List<ModuleFileVO> modFileSelect(ModuleVO moduleVO) {
		return adminMapper.selectModFile(moduleVO);
	}

	@Override
	public ModuleVO modSelect(ModuleVO moduleVO) {
		return adminMapper.selectMod(moduleVO);
	}

	@Override
	public int filesPro(ModuleVO moduleVO) {
		UUID uuid = UUID.randomUUID();
		MultipartFile[] files = moduleVO.getUploadFiles();
		String[] contents = moduleVO.getModuleNotices();
		int i = 1;
		for (MultipartFile file : files) {
			ModuleFileVO mvo = new ModuleFileVO();
			String fileName = uuid + "_" + file.getOriginalFilename();
			mvo.setModFileOriginalname(file.getOriginalFilename());
			mvo.setModFileName(fileName);
			mvo.setModNo(moduleVO.getModuleNo());
			mvo.setModFileType(file.getContentType());
			mvo.setModFilePath(uploadPath);
			mvo.setModFileTurn(i);
			mvo.setModFileContent(contents[i - 1]);
			i++;
			int fileResult = adminMapper.insertModuleFile(mvo);
			String saveName = uploadPath + '/' + fileName;
			Path savePath = Paths.get(saveName);
			// Paths.get() 메서드는 특정 경로의 파일 정보를 가져옵니다.(경로 정의하기)
			try {
				file.transferTo(savePath);
				// uploadFile에 파일을 업로드 하는 메서드 transferTo(file)
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return 1;
	}
	@Override
	public int modDelete(int moduleNo) {
		return adminMapper.deleteMod(moduleNo);
	}
	@Override
	public List<ChartVO> pieChart() {
		return adminMapper.pieChart();
	}
	@Override
	public List<ChartVO> areaChart() {
		return adminMapper.areaChart();
	}

}
