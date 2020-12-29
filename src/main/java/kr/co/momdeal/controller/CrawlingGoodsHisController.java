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

import kr.co.momdeal.service.CrawlingGoodsHisService;
import kr.co.momdeal.vo.CrawlingGoodsHisVO;
import kr.co.momdeal.vo.common.PaginationVO;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CrawlingGoodsHisController {

	@Resource
	private CrawlingGoodsHisService cghService;

	@GetMapping("/cr/cghs")
	public  Closeable selectCGHList(CrawlingGoodsHisVO cgh, PaginationVO page){
		return cghService.selectCGHList(cgh, page);
	}
	@GetMapping("/cr/cgh/{cghNum}")
	public  CrawlingGoodsHisVO selectCGH(@PathVariable("cghNum") int cghNum){
		return cghService.selectCGH(cghNum);
	}
	@PostMapping("/cr/cgh")
	public  Map<String,Object> insertCGH(@ModelAttribute  CrawlingGoodsHisVO cgh) {
		return cghService.insertCGH(cgh);
	}
	@PostMapping("/cr/cgh/mod")
	public Map<String,Object> updateCGH(@ModelAttribute CrawlingGoodsHisVO cgh) {
		return cghService.updateCGH(cgh);
	}
	@PostMapping("/cr/cghs/del")
	public Map<String,Object> deleteCGH(@RequestBody List<Integer> nums) {
		return cghService.deleteCGH(nums);
	}
}
