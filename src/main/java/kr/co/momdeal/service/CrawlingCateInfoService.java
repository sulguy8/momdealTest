package kr.co.momdeal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import kr.co.momdeal.exception.ServiceException;
import kr.co.momdeal.mapper.CrawlingCateInfoMapper;
import kr.co.momdeal.utils.FileUtils;
import kr.co.momdeal.vo.CrawlingCateInfoVO;
import kr.co.momdeal.vo.common.PaginationVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CrawlingCateInfoService {

	@Resource
	private CrawlingCateInfoMapper cciMapper;
	
	@Resource
	private FileUtils fu;
	
	private String path = "cci";
	
	public Page<CrawlingCateInfoVO> selectCCIList(CrawlingCateInfoVO cci,PaginationVO page) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return cciMapper.selectCCIList(cci);
	}
	
	public List<CrawlingCateInfoVO> selectCCIList() {
		return cciMapper.selectCCIList(null);
	}
	
	public CrawlingCateInfoVO selectCCI(int CCI_NUM) {
		return cciMapper.selectCCI(CCI_NUM);
	}
		
	public Map<String,Object> updateCCI(CrawlingCateInfoVO cci) {
		Map<String,Object> rMap = new HashMap<>();
		if(!fu.autoSaveFile(cci, path)) {
			throw new ServiceException("파일 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		int rCnt = cciMapper.updateCCI(cci);
		if(rCnt!=1) {
			throw new ServiceException("디비 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		rMap.put("cnt", rCnt);
		rMap.put("result","ok");
		return rMap;
	}
	
	public Map<String,Object> insertCCI(CrawlingCateInfoVO cci) {
		Map<String,Object> rMap = new HashMap<>();
		if(!fu.autoSaveFile(cci, path)) {
			throw new ServiceException("파일 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		int rCnt = cciMapper.insertCCI(cci);
		if(rCnt!=1) {
			throw new ServiceException("DB 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		rMap.put("cnt", rCnt);
		rMap.put("result","ok");
		return rMap;
	}
	public Map<String,Object> deleteCCI(List<Integer> nums) {
		Map<String,Object> rMap = new HashMap<>();
		int rCnt = 0;
		for(int num : nums) {
			rCnt += cciMapper.deleteCCI(num);
		}
		if(rCnt!=nums.size()) {
			throw new ServiceException("삭제 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		rMap.put("cnt", rCnt);
		rMap.put("result","ok");
		return rMap;
	}
}