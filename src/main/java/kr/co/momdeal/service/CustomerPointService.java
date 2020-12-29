package kr.co.momdeal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import kr.co.momdeal.exception.ServiceException;
import kr.co.momdeal.mapper.CustomerPointMapper;
import kr.co.momdeal.utils.FileUtils;
import kr.co.momdeal.vo.CustomerPointVO;
import kr.co.momdeal.vo.common.PaginationVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerPointService {

	@Resource
	private CustomerPointMapper cupMapper;
	
	@Resource
	private FileUtils fu;
	
	private String path = "cup";
	
	public Page<CustomerPointVO> selectCUPList(CustomerPointVO cup,PaginationVO page) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return cupMapper.selectCUPList(cup);
	}
	
	public List<CustomerPointVO> selectCUPList() {
		return cupMapper.selectCUPList(null);
	}
	
	public CustomerPointVO selectCUP(int CUP_NUM) {
		return cupMapper.selectCUP(CUP_NUM);
	}
		
	public Map<String,Object> updateCUP(CustomerPointVO cup) {
		Map<String,Object> rMap = new HashMap<>();
		if(!fu.autoSaveFile(cup, path)) {
			throw new ServiceException("파일 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		int rCnt = cupMapper.updateCUP(cup);
		if(rCnt!=1) {
			throw new ServiceException("디비 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		rMap.put("cnt", rCnt);
		rMap.put("result","ok");
		return rMap;
	}
	
	public Map<String,Object> insertCUP(CustomerPointVO cup) {
		Map<String,Object> rMap = new HashMap<>();
		if(!fu.autoSaveFile(cup, path)) {
			throw new ServiceException("파일 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		int rCnt = cupMapper.insertCUP(cup);
		if(rCnt!=1) {
			throw new ServiceException("DB 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		rMap.put("cnt", rCnt);
		rMap.put("result","ok");
		return rMap;
	}
	public Map<String,Object> deleteCUP(List<Integer> nums) {
		Map<String,Object> rMap = new HashMap<>();
		int rCnt = 0;
		for(int num : nums) {
			rCnt += cupMapper.deleteCUP(num);
		}
		if(rCnt!=nums.size()) {
			throw new ServiceException("삭제 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		rMap.put("cnt", rCnt);
		rMap.put("result","ok");
		return rMap;
	}
}