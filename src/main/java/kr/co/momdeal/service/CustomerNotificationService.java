package kr.co.momdeal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import kr.co.momdeal.exception.ServiceException;
import kr.co.momdeal.mapper.CustomerNotificationMapper;
import kr.co.momdeal.utils.FileUtils;
import kr.co.momdeal.vo.CustomerNotificationVO;
import kr.co.momdeal.vo.common.PaginationVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerNotificationService {

	@Resource
	private CustomerNotificationMapper cunMapper;
	
	@Resource
	private FileUtils fu;
	
	private String path = "cun";
	
	public Page<CustomerNotificationVO> selectCUNList(CustomerNotificationVO cun,PaginationVO page) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return cunMapper.selectCUNList(cun);
	}
	
	public List<CustomerNotificationVO> selectCUNList() {
		return cunMapper.selectCUNList(null);
	}
	
	public CustomerNotificationVO selectCUN(int CUN_NUM) {
		return cunMapper.selectCUN(CUN_NUM);
	}
		
	public Map<String,Object> updateCUN(CustomerNotificationVO cun) {
		Map<String,Object> rMap = new HashMap<>();
		if(!fu.autoSaveFile(cun, path)) {
			throw new ServiceException("파일 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		int rCnt = cunMapper.updateCUN(cun);
		if(rCnt!=1) {
			throw new ServiceException("디비 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		rMap.put("cnt", rCnt);
		rMap.put("result","ok");
		return rMap;
	}
	
	public Map<String,Object> insertCUN(CustomerNotificationVO cun) {
		Map<String,Object> rMap = new HashMap<>();
		if(!fu.autoSaveFile(cun, path)) {
			throw new ServiceException("파일 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		int rCnt = cunMapper.insertCUN(cun);
		if(rCnt!=1) {
			throw new ServiceException("DB 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		rMap.put("cnt", rCnt);
		rMap.put("result","ok");
		return rMap;
	}
	public Map<String,Object> deleteCUN(List<Integer> nums) {
		Map<String,Object> rMap = new HashMap<>();
		int rCnt = 0;
		for(int num : nums) {
			rCnt += cunMapper.deleteCUN(num);
		}
		if(rCnt!=nums.size()) {
			throw new ServiceException("삭제 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		rMap.put("cnt", rCnt);
		rMap.put("result","ok");
		return rMap;
	}
}