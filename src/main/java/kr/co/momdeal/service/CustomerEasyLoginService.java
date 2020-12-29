package kr.co.momdeal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import kr.co.momdeal.exception.ServiceException;
import kr.co.momdeal.mapper.CustomerEasyLoginMapper;
import kr.co.momdeal.utils.FileUtils;
import kr.co.momdeal.vo.CustomerEasyLoginVO;
import kr.co.momdeal.vo.common.PaginationVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerEasyLoginService {

	@Resource
	private CustomerEasyLoginMapper celMapper;
	
	@Resource
	private FileUtils fu;
	
	private String path = "cel";
	
	public Page<CustomerEasyLoginVO> selectCELList(CustomerEasyLoginVO cel,PaginationVO page) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return celMapper.selectCELList(cel);
	}
	
	public List<CustomerEasyLoginVO> selectCELList() {
		return celMapper.selectCELList(null);
	}
	
	public CustomerEasyLoginVO selectCEL(int CEL_NUM) {
		return celMapper.selectCEL(CEL_NUM);
	}
		
	public Map<String,Object> updateCEL(CustomerEasyLoginVO cel) {
		Map<String,Object> rMap = new HashMap<>();
		if(!fu.autoSaveFile(cel, path)) {
			throw new ServiceException("파일 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		int rCnt = celMapper.updateCEL(cel);
		if(rCnt!=1) {
			throw new ServiceException("디비 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		rMap.put("cnt", rCnt);
		rMap.put("result","ok");
		return rMap;
	}
	
	public Map<String,Object> insertCEL(CustomerEasyLoginVO cel) {
		Map<String,Object> rMap = new HashMap<>();
		if(!fu.autoSaveFile(cel, path)) {
			throw new ServiceException("파일 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		int rCnt = celMapper.insertCEL(cel);
		if(rCnt!=1) {
			throw new ServiceException("DB 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		rMap.put("cnt", rCnt);
		rMap.put("result","ok");
		return rMap;
	}
	public Map<String,Object> deleteCEL(List<Integer> nums) {
		Map<String,Object> rMap = new HashMap<>();
		int rCnt = 0;
		for(int num : nums) {
			rCnt += celMapper.deleteCEL(num);
		}
		if(rCnt!=nums.size()) {
			throw new ServiceException("삭제 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		rMap.put("cnt", rCnt);
		rMap.put("result","ok");
		return rMap;
	}
}