package kr.co.momdeal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import kr.co.momdeal.exception.ServiceException;
import kr.co.momdeal.mapper.NoticeInfoMapper;
import kr.co.momdeal.utils.FileUtils;
import kr.co.momdeal.vo.NoticeInfoVO;
import kr.co.momdeal.vo.common.PaginationVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NoticeInfoService {

	@Resource
	private NoticeInfoMapper noiMapper;
	
	@Resource
	private FileUtils fu;
	
	private String path = "noi";
	
	public Page<NoticeInfoVO> selectNOIList(NoticeInfoVO noi,PaginationVO page) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return noiMapper.selectNOIList(noi);
	}
	
	public List<NoticeInfoVO> selectNOIList() {
		return noiMapper.selectNOIList(null);
	}
	
	public NoticeInfoVO selectNOI(int NOI_NUM) {
		return noiMapper.selectNOI(NOI_NUM);
	}
		
	public Map<String,Object> updateNOI(NoticeInfoVO noi) {
		Map<String,Object> rMap = new HashMap<>();
		if(!fu.autoSaveFile(noi, path)) {
			throw new ServiceException("파일 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		int rCnt = noiMapper.updateNOI(noi);
		if(rCnt!=1) {
			throw new ServiceException("디비 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		rMap.put("cnt", rCnt);
		rMap.put("result","ok");
		return rMap;
	}
	
	public Map<String,Object> insertNOI(NoticeInfoVO noi) {
		Map<String,Object> rMap = new HashMap<>();
		if(!fu.autoSaveFile(noi, path)) {
			throw new ServiceException("파일 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		int rCnt = noiMapper.insertNOI(noi);
		if(rCnt!=1) {
			throw new ServiceException("DB 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		rMap.put("cnt", rCnt);
		rMap.put("result","ok");
		return rMap;
	}
	public Map<String,Object> deleteNOI(List<Integer> nums) {
		Map<String,Object> rMap = new HashMap<>();
		int rCnt = 0;
		for(int num : nums) {
			rCnt += noiMapper.deleteNOI(num);
		}
		if(rCnt!=nums.size()) {
			throw new ServiceException("삭제 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		rMap.put("cnt", rCnt);
		rMap.put("result","ok");
		return rMap;
	}
}