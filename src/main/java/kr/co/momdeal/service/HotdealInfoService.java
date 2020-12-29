package kr.co.momdeal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import kr.co.momdeal.exception.ServiceException;
import kr.co.momdeal.mapper.HotdealInfoMapper;
import kr.co.momdeal.utils.FileUtils;
import kr.co.momdeal.vo.HotdealInfoVO;
import kr.co.momdeal.vo.common.PaginationVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HotdealInfoService {

	@Resource
	private HotdealInfoMapper hoiMapper;
	
	@Resource
	private FileUtils fu;
	
	private String path = "hoi";
	
	public Page<HotdealInfoVO> selectHOIList(HotdealInfoVO hoi,PaginationVO page) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return hoiMapper.selectHOIList(hoi);
	}
	
	public List<HotdealInfoVO> selectHOIList() {
		return hoiMapper.selectHOIList(null);
	}
	
	public HotdealInfoVO selectHOI(int HOI_NUM) {
		return hoiMapper.selectHOI(HOI_NUM);
	}
		
	public Map<String,Object> updateHOI(HotdealInfoVO hoi) {
		Map<String,Object> rMap = new HashMap<>();
		if(!fu.autoSaveFile(hoi, path)) {
			throw new ServiceException("파일 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		int rCnt = hoiMapper.updateHOI(hoi);
		if(rCnt!=1) {
			throw new ServiceException("디비 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		rMap.put("cnt", rCnt);
		rMap.put("result","ok");
		return rMap;
	}
	
	public Map<String,Object> insertHOI(HotdealInfoVO hoi) {
		Map<String,Object> rMap = new HashMap<>();
		if(!fu.autoSaveFile(hoi, path)) {
			throw new ServiceException("파일 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		int rCnt = hoiMapper.insertHOI(hoi);
		if(rCnt!=1) {
			throw new ServiceException("DB 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		rMap.put("cnt", rCnt);
		rMap.put("result","ok");
		return rMap;
	}
	public Map<String,Object> deleteHOI(List<Integer> nums) {
		Map<String,Object> rMap = new HashMap<>();
		int rCnt = 0;
		for(int num : nums) {
			rCnt += hoiMapper.deleteHOI(num);
		}
		if(rCnt!=nums.size()) {
			throw new ServiceException("삭제 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		rMap.put("cnt", rCnt);
		rMap.put("result","ok");
		return rMap;
	}
}