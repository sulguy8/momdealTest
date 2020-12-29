package kr.co.momdeal.controller;

import java.io.Closeable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.co.momdeal.service.CrawlingCateInfoService;
import kr.co.momdeal.vo.CrawlingCateInfoVO;
import kr.co.momdeal.vo.common.PaginationVO;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CrawlingCateInfoController {

	@Resource
	private CrawlingCateInfoService cciService;

	@GetMapping("/cr/ccis")
	public  Closeable selectCCIList(CrawlingCateInfoVO cci, PaginationVO page){
		return cciService.selectCCIList(cci, page);
	}
	@GetMapping("/cr/cci/{cciNum}")
	public  CrawlingCateInfoVO selectCCI(@PathVariable("cciNum") int cciNum){
		return cciService.selectCCI(cciNum);
	}
	@PostMapping("/cr/cci")
	public  Map<String,Object> insertCCI(@ModelAttribute  CrawlingCateInfoVO cci) {
		return cciService.insertCCI(cci);
	}
	@PostMapping("/cr/cci/mod")
	public Map<String,Object> updateCCI(@ModelAttribute CrawlingCateInfoVO cci) {
		return cciService.updateCCI(cci);
	}
	@PostMapping("/cr/ccis/del")
	public Map<String,Object> deleteCCI(@RequestBody List<Integer> nums) {
		return cciService.deleteCCI(nums);
	}
}
