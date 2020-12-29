package kr.co.momdeal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import kr.co.momdeal.exception.ServiceException;
import kr.co.momdeal.mapper.EventApplicantInfoMapper;
import kr.co.momdeal.utils.FileUtils;
import kr.co.momdeal.vo.EventApplicantInfoVO;
import kr.co.momdeal.vo.common.PaginationVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EventApplicantInfoService {

	@Resource
	private EventApplicantInfoMapper eaiMapper;
	
	@Resource
	private FileUtils fu;
	
	private String path = "eai";
	
	public Page<EventApplicantInfoVO> selectEAIList(EventApplicantInfoVO eai,PaginationVO page) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return eaiMapper.selectEAIList(eai);
	}
	
	public List<EventApplicantInfoVO> selectEAIList() {
		return eaiMapper.selectEAIList(null);
	}
	
	public EventApplicantInfoVO selectEAI(int EAI_NUM) {
		return eaiMapper.selectEAI(EAI_NUM);
	}
		
	public Map<String,Object> updateEAI(EventApplicantInfoVO eai) {
		Map<String,Object> rMap = new HashMap<>();
		if(!fu.autoSaveFile(eai, path)) {
			throw new ServiceException("파일 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		int rCnt = eaiMapper.updateEAI(eai);
		if(rCnt!=1) {
			throw new ServiceException("디비 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		rMap.put("cnt", rCnt);
		rMap.put("result","ok");
		return rMap;
	}
	
	public Map<String,Object> insertEAI(EventApplicantInfoVO eai) {
		Map<String,Object> rMap = new HashMap<>();
		if(!fu.autoSaveFile(eai, path)) {
			throw new ServiceException("파일 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		int rCnt = eaiMapper.insertEAI(eai);
		if(rCnt!=1) {
			throw new ServiceException("DB 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		rMap.put("cnt", rCnt);
		rMap.put("result","ok");
		return rMap;
	}
	public Map<String,Object> deleteEAI(List<Integer> nums) {
		Map<String,Object> rMap = new HashMap<>();
		int rCnt = 0;
		for(int num : nums) {
			rCnt += eaiMapper.deleteEAI(num);
		}
		if(rCnt!=nums.size()) {
			throw new ServiceException("삭제 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		rMap.put("cnt", rCnt);
		rMap.put("result","ok");
		return rMap;
	}
}