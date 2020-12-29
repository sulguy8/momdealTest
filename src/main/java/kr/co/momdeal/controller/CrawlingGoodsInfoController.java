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

import kr.co.momdeal.service.CrawlingGoodsInfoService;
import kr.co.momdeal.vo.CrawlingGoodsInfoVO;
import kr.co.momdeal.vo.common.PaginationVO;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CrawlingGoodsInfoController {

	@Resource
	private CrawlingGoodsInfoService cgiService;

	@GetMapping("/cr/cgis")
	public  Closeable selectCGIList(CrawlingGoodsInfoVO cgi, PaginationVO page){
		return cgiService.selectCGIList(cgi, page);
	}
	@GetMapping("/cr/cgi/{cgiNum}")
	public  CrawlingGoodsInfoVO selectCGI(@PathVariable("cgiNum") int cgiNum){
		return cgiService.selectCGI(cgiNum);
	}
	@PostMapping("/cr/cgi")
	public  Map<String,Object> insertCGI(@ModelAttribute  CrawlingGoodsInfoVO cgi) {
		return cgiService.insertCGI(cgi);
	}
	@PostMapping("/cr/cgi/mod")
	public Map<String,Object> updateCGI(@ModelAttribute CrawlingGoodsInfoVO cgi) {
		return cgiService.updateCGI(cgi);
	}
	@PostMapping("/cr/cgis/del")
	public Map<String,Object> deleteCGI(@RequestBody List<Integer> nums) {
		return cgiService.deleteCGI(nums);
	}
}
