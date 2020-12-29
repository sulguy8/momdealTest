package kr.co.momdeal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import kr.co.momdeal.exception.ServiceException;
import kr.co.momdeal.mapper.CrawlingGoodsHisMapper;
import kr.co.momdeal.utils.FileUtils;
import kr.co.momdeal.vo.CrawlingGoodsHisVO;
import kr.co.momdeal.vo.common.PaginationVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CrawlingGoodsHisService {

	@Resource
	private CrawlingGoodsHisMapper cghMapper;
	
	@Resource
	private FileUtils fu;
	
	private String path = "cgh";
	
	public Page<CrawlingGoodsHisVO> selectCGHList(CrawlingGoodsHisVO cgh,PaginationVO page) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return cghMapper.selectCGHList(cgh);
	}
	
	public List<CrawlingGoodsHisVO> selectCGHList() {
		return cghMapper.selectCGHList(null);
	}
	
	public CrawlingGoodsHisVO selectCGH(int CGH_NUM) {
		return cghMapper.selectCGH(CGH_NUM);
	}
		
	public Map<String,Object> updateCGH(CrawlingGoodsHisVO cgh) {
		Map<String,Object> rMap = new HashMap<>();
		if(!fu.autoSaveFile(cgh, path)) {
			throw new ServiceException("파일 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		int rCnt = cghMapper.updateCGH(cgh);
		if(rCnt!=1) {
			throw new ServiceException("디비 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		rMap.put("cnt", rCnt);
		rMap.put("result","ok");
		return rMap;
	}
	
	public Map<String,Object> insertCGH(CrawlingGoodsHisVO cgh) {
		Map<String,Object> rMap = new HashMap<>();
		if(!fu.autoSaveFile(cgh, path)) {
			throw new ServiceException("파일 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		int rCnt = cghMapper.insertCGH(cgh);
		if(rCnt!=1) {
			throw new ServiceException("DB 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		rMap.put("cnt", rCnt);
		rMap.put("result","ok");
		return rMap;
	}
	public Map<String,Object> deleteCGH(List<Integer> nums) {
		Map<String,Object> rMap = new HashMap<>();
		int rCnt = 0;
		for(int num : nums) {
			rCnt += cghMapper.deleteCGH(num);
		}
		if(rCnt!=nums.size()) {
			throw new ServiceException("삭제 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.");
		}
		rMap.put("cnt", rCnt);
		rMap.put("result","ok");
		return rMap;
	}
}