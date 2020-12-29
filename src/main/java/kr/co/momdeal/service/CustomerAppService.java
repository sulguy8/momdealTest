package kr.co.momdeal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import kr.co.momdeal.exception.ServiceException;
import kr.co.momdeal.mapper.CustomerAppMapper;
import kr.co.momdeal.utils.FileUtils;
import kr.co.momdeal.vo.CustomerAppVO;
import kr.co.momdeal.vo.common.PaginationVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerAppService {

	@Resource
	private CustomerAppMapper cuaMapper;
	
	@Resource
	private FileUtils fu;
	
	private String path = "cua";
	
	public Page<CustomerAppVO> selectCUAList(CustomerAppVO cua,PaginationVO page) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return cuaMapper.selectCUAList(cua);
	}
	
	public List<CustomerAppVO> selectCUAList() {
		return cuaMapper.selectCUAList(null);
	}
	
	public CustomerAppVO selectCUA(int CUA_NUM) {
		return cuaMapper.selectCUA(CUA_NUM);
	}
		
	public Map<String,Object> updateCUA(CustomerAppVO cua) {
		Map<String,Object> rMap = new HashMap<>();
		if(!fu.autoSaveFile(cua, path)) {
			throw new ServiceException("파일 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		int rCnt = cuaMapper.updateCUA(cua);
		if(rCnt!=1) {
			throw new ServiceException("디비 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		rMap.put("cnt", rCnt);
		rMap.put("result","ok");
		return rMap;
	}
	
	public Map<String,Object> insertCUA(CustomerAppVO cua) {
		Map<String,Object> rMap = new HashMap<>();
		if(!fu.autoSaveFile(cua, path)) {
			throw new ServiceException("파일 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		int rCnt = cuaMapper.insertCUA(cua);
		if(rCnt!=1) {
			throw new ServiceException("DB 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		rMap.put("cnt", rCnt);
		rMap.put("result","ok");
		return rMap;
	}
	public Map<String,Object> deleteCUA(List<Integer> nums) {
		Map<String,Object> rMap = new HashMap<>();
		int rCnt = 0;
		for(int num : nums) {
			rCnt += cuaMapper.deleteCUA(num);
		}
		if(rCnt!=nums.size()) {
			throw new ServiceException("삭제 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		rMap.put("cnt", rCnt);
		rMap.put("result","ok");
		return rMap;
	}
}