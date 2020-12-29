package kr.co.momdeal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import kr.co.momdeal.exception.ServiceException;
import kr.co.momdeal.mapper.CustomerQuestionMapper;
import kr.co.momdeal.utils.FileUtils;
import kr.co.momdeal.vo.CustomerQuestionVO;
import kr.co.momdeal.vo.common.PaginationVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerQuestionService {

	@Resource
	private CustomerQuestionMapper cuqMapper;
	
	@Resource
	private FileUtils fu;
	
	private String path = "cuq";
	
	public Page<CustomerQuestionVO> selectCUQList(CustomerQuestionVO cuq,PaginationVO page) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return cuqMapper.selectCUQList(cuq);
	}
	
	public List<CustomerQuestionVO> selectCUQList() {
		return cuqMapper.selectCUQList(null);
	}
	
	public CustomerQuestionVO selectCUQ(int CUQ_NUM) {
		return cuqMapper.selectCUQ(CUQ_NUM);
	}
		
	public Map<String,Object> updateCUQ(CustomerQuestionVO cuq) {
		Map<String,Object> rMap = new HashMap<>();
		if(!fu.autoSaveFile(cuq, path)) {
			throw new ServiceException("파일 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		int rCnt = cuqMapper.updateCUQ(cuq);
		if(rCnt!=1) {
			throw new ServiceException("디비 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		rMap.put("cnt", rCnt);
		rMap.put("result","ok");
		return rMap;
	}
	
	public Map<String,Object> insertCUQ(CustomerQuestionVO cuq) {
		Map<String,Object> rMap = new HashMap<>();
		if(!fu.autoSaveFile(cuq, path)) {
			throw new ServiceException("파일 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		int rCnt = cuqMapper.insertCUQ(cuq);
		if(rCnt!=1) {
			throw new ServiceException("DB 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		rMap.put("cnt", rCnt);
		rMap.put("result","ok");
		return rMap;
	}
	public Map<String,Object> deleteCUQ(List<Integer> nums) {
		Map<String,Object> rMap = new HashMap<>();
		int rCnt = 0;
		for(int num : nums) {
			rCnt += cuqMapper.deleteCUQ(num);
		}
		if(rCnt!=nums.size()) {
			throw new ServiceException("삭제 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		rMap.put("cnt", rCnt);
		rMap.put("result","ok");
		return rMap;
	}
}