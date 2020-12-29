package kr.co.momdeal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import kr.co.momdeal.exception.ServiceException;
import kr.co.momdeal.mapper.CommonCodeMapper;
import kr.co.momdeal.utils.FileUtils;
import kr.co.momdeal.vo.CommonCodeVO;
import kr.co.momdeal.vo.common.PaginationVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CommonCodeService {

	@Resource
	private CommonCodeMapper codMapper;
	
	@Resource
	private FileUtils fu;
	
	private String path = "cod";
	
	public Page<CommonCodeVO> selectCODList(CommonCodeVO cod,PaginationVO page) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return codMapper.selectCODList(cod);
	}

	public List<CommonCodeVO> selectCODList() {
		return codMapper.selectCODList(null);
	}

	public List<CommonCodeVO> selectCODList(CommonCodeVO cod) {
		return codMapper.selectCODList(cod);
	}
	
	public CommonCodeVO selectCOD(int COD_NUM) {
		return codMapper.selectCOD(COD_NUM);
	}
		
	public Map<String,Object> updateCOD(CommonCodeVO cod) {
		Map<String,Object> rMap = new HashMap<>();
		if(!fu.autoSaveFile(cod, path)) {
			throw new ServiceException("파일 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		int rCnt = codMapper.updateCOD(cod);
		if(rCnt!=1) {
			throw new ServiceException("디비 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		rMap.put("cnt", rCnt);
		rMap.put("result","ok");
		return rMap;
	}
	
	public Map<String,Object> insertCOD(CommonCodeVO cod) {
		Map<String,Object> rMap = new HashMap<>();
		if(!fu.autoSaveFile(cod, path)) {
			throw new ServiceException("파일 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		int rCnt = codMapper.insertCOD(cod);
		if(rCnt!=1) {
			throw new ServiceException("DB 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		rMap.put("cnt", rCnt);
		rMap.put("result","ok");
		return rMap;
	}
	public Map<String,Object> deleteCOD(List<Integer> nums) {
		Map<String,Object> rMap = new HashMap<>();
		int rCnt = 0;
		for(int num : nums) {
			rCnt += codMapper.deleteCOD(num);
		}
		if(rCnt!=nums.size()) {
			throw new ServiceException("삭제 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		rMap.put("cnt", rCnt);
		rMap.put("result","ok");
		return rMap;
	}
}