package kr.co.momdeal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import kr.co.momdeal.exception.ServiceException;
import kr.co.momdeal.mapper.CustomerFaqMapper;
import kr.co.momdeal.utils.FileUtils;
import kr.co.momdeal.vo.CustomerFaqVO;
import kr.co.momdeal.vo.common.PaginationVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerFaqService {

	@Resource
	private CustomerFaqMapper cufMapper;
	
	@Resource
	private FileUtils fu;
	
	private String path = "cuf";
	
	public Page<CustomerFaqVO> selectCUFList(CustomerFaqVO cuf,PaginationVO page) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return cufMapper.selectCUFList(cuf);
	}
	
	public List<CustomerFaqVO> selectCUFList() {
		return cufMapper.selectCUFList(null);
	}
	
	public CustomerFaqVO selectCUF(int CUF_NUM) {
		return cufMapper.selectCUF(CUF_NUM);
	}
		
	public Map<String,Object> updateCUF(CustomerFaqVO cuf) {
		Map<String,Object> rMap = new HashMap<>();
		if(!fu.autoSaveFile(cuf, path)) {
			throw new ServiceException("파일 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		int rCnt = cufMapper.updateCUF(cuf);
		if(rCnt!=1) {
			throw new ServiceException("디비 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		rMap.put("cnt", rCnt);
		rMap.put("result","ok");
		return rMap;
	}
	
	public Map<String,Object> insertCUF(CustomerFaqVO cuf) {
		Map<String,Object> rMap = new HashMap<>();
		if(!fu.autoSaveFile(cuf, path)) {
			throw new ServiceException("파일 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		int rCnt = cufMapper.insertCUF(cuf);
		if(rCnt!=1) {
			throw new ServiceException("DB 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		rMap.put("cnt", rCnt);
		rMap.put("result","ok");
		return rMap;
	}
	public Map<String,Object> deleteCUF(List<Integer> nums) {
		Map<String,Object> rMap = new HashMap<>();
		int rCnt = 0;
		for(int num : nums) {
			rCnt += cufMapper.deleteCUF(num);
		}
		if(rCnt!=nums.size()) {
			throw new ServiceException("삭제 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		rMap.put("cnt", rCnt);
		rMap.put("result","ok");
		return rMap;
	}
}