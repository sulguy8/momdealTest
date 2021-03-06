package kr.co.momdeal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import kr.co.momdeal.exception.ServiceException;
import kr.co.momdeal.mapper.EventInfoMapper;
import kr.co.momdeal.utils.FileUtils;
import kr.co.momdeal.vo.EventInfoVO;
import kr.co.momdeal.vo.common.PaginationVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EventInfoService {

	@Resource
	private EventInfoMapper eviMapper;
	
	@Resource
	private FileUtils fu;
	
	private String path = "evi";
	
	public Page<EventInfoVO> selectEVIList(EventInfoVO evi,PaginationVO page) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return eviMapper.selectEVIList(evi);
	}
	
	public List<EventInfoVO> selectEVIList() {
		return eviMapper.selectEVIList(null);
	}
	
	public EventInfoVO selectEVI(int EVI_NUM) {
		return eviMapper.selectEVI(EVI_NUM);
	}
		
	public Map<String,Object> updateEVI(EventInfoVO evi) {
		Map<String,Object> rMap = new HashMap<>();
		if(!fu.autoSaveFile(evi, path)) {
			throw new ServiceException("파일 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		int rCnt = eviMapper.updateEVI(evi);
		if(rCnt!=1) {
			throw new ServiceException("디비 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		rMap.put("cnt", rCnt);
		rMap.put("result","ok");
		return rMap;
	}
	
	public Map<String,Object> insertEVI(EventInfoVO evi) {
		Map<String,Object> rMap = new HashMap<>();
		if(!fu.autoSaveFile(evi, path)) {
			throw new ServiceException("파일 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		int rCnt = eviMapper.insertEVI(evi);
		if(rCnt!=1) {
			throw new ServiceException("DB 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		rMap.put("cnt", rCnt);
		rMap.put("result","ok");
		return rMap;
	}
	public Map<String,Object> deleteEVI(List<Integer> nums) {
		Map<String,Object> rMap = new HashMap<>();
		int rCnt = 0;
		for(int num : nums) {
			rCnt += eviMapper.deleteEVI(num);
		}
		if(rCnt!=nums.size()) {
			throw new ServiceException("삭제 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		rMap.put("cnt", rCnt);
		rMap.put("result","ok");
		return rMap;
	}
}