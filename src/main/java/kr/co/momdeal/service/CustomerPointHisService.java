package kr.co.momdeal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import kr.co.momdeal.exception.ServiceException;
import kr.co.momdeal.mapper.CustomerPointHisMapper;
import kr.co.momdeal.utils.FileUtils;
import kr.co.momdeal.vo.CustomerPointHisVO;
import kr.co.momdeal.vo.common.PaginationVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerPointHisService {

	@Resource
	private CustomerPointHisMapper cphMapper;
	
	@Resource
	private FileUtils fu;
	
	private String path = "cph";
	
	public Page<CustomerPointHisVO> selectCPHList(CustomerPointHisVO cph,PaginationVO page) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return cphMapper.selectCPHList(cph);
	}
	
	public List<CustomerPointHisVO> selectCPHList() {
		return cphMapper.selectCPHList(null);
	}
	
	public CustomerPointHisVO selectCPH(int CPH_NUM) {
		return cphMapper.selectCPH(CPH_NUM);
	}
		
	public Map<String,Object> updateCPH(CustomerPointHisVO cph) {
		Map<String,Object> rMap = new HashMap<>();
		if(!fu.autoSaveFile(cph, path)) {
			throw new ServiceException("파일 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		int rCnt = cphMapper.updateCPH(cph);
		if(rCnt!=1) {
			throw new ServiceException("디비 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		rMap.put("cnt", rCnt);
		rMap.put("result","ok");
		return rMap;
	}
	
	public Map<String,Object> insertCPH(CustomerPointHisVO cph) {
		Map<String,Object> rMap = new HashMap<>();
		if(!fu.autoSaveFile(cph, path)) {
			throw new ServiceException("파일 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		int rCnt = cphMapper.insertCPH(cph);
		if(rCnt!=1) {
			throw new ServiceException("DB 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		rMap.put("cnt", rCnt);
		rMap.put("result","ok");
		return rMap;
	}
	public Map<String,Object> deleteCPH(List<Integer> nums) {
		Map<String,Object> rMap = new HashMap<>();
		int rCnt = 0;
		for(int num : nums) {
			rCnt += cphMapper.deleteCPH(num);
		}
		if(rCnt!=nums.size()) {
			throw new ServiceException("삭제 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		rMap.put("cnt", rCnt);
		rMap.put("result","ok");
		return rMap;
	}
}