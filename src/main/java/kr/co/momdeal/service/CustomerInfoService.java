package kr.co.momdeal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import kr.co.momdeal.exception.ServiceException;
import kr.co.momdeal.mapper.CustomerInfoMapper;
import kr.co.momdeal.utils.FileUtils;
import kr.co.momdeal.vo.CustomerInfoVO;
import kr.co.momdeal.vo.common.PaginationVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerInfoService {

	@Resource
	private CustomerInfoMapper cuiMapper;
	
	@Resource
	private FileUtils fu;
	
	private String path = "cui";
	
	public Page<CustomerInfoVO> selectCUIList(CustomerInfoVO cui,PaginationVO page) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return cuiMapper.selectCUIList(cui);
	}
	
	public List<CustomerInfoVO> selectCUIList() {
		return cuiMapper.selectCUIList(null);
	}
	
	public CustomerInfoVO selectCUI(int CUI_NUM) {
		return cuiMapper.selectCUI(CUI_NUM);
	}
		
	public Map<String,Object> updateCUI(CustomerInfoVO cui) {
		Map<String,Object> rMap = new HashMap<>();
		if(!fu.autoSaveFile(cui, path)) {
			throw new ServiceException("파일 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		int rCnt = cuiMapper.updateCUI(cui);
		if(rCnt!=1) {
			throw new ServiceException("디비 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		rMap.put("cnt", rCnt);
		rMap.put("result","ok");
		return rMap;
	}
	
	public Map<String,Object> insertCUI(CustomerInfoVO cui) {
		Map<String,Object> rMap = new HashMap<>();
		if(!fu.autoSaveFile(cui, path)) {
			throw new ServiceException("파일 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		int rCnt = cuiMapper.insertCUI(cui);
		if(rCnt!=1) {
			throw new ServiceException("DB 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		rMap.put("cnt", rCnt);
		rMap.put("result","ok");
		return rMap;
	}
	public Map<String,Object> deleteCUI(List<Integer> nums) {
		Map<String,Object> rMap = new HashMap<>();
		int rCnt = 0;
		for(int num : nums) {
			rCnt += cuiMapper.deleteCUI(num);
		}
		if(rCnt!=nums.size()) {
			throw new ServiceException("삭제 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		rMap.put("cnt", rCnt);
		rMap.put("result","ok");
		return rMap;
	}
}