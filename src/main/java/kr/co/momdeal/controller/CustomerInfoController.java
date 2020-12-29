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

import kr.co.momdeal.service.CustomerInfoService;
import kr.co.momdeal.vo.CustomerInfoVO;
import kr.co.momdeal.vo.common.PaginationVO;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CustomerInfoController {

	@Resource
	private CustomerInfoService cuiService;

	@GetMapping("/cu/cuis")
	public  Closeable selectCUIList(CustomerInfoVO cui, PaginationVO page){
		return cuiService.selectCUIList(cui, page);
	}
	@GetMapping("/cu/cui/{cuiNum}")
	public  CustomerInfoVO selectCUI(@PathVariable("cuiNum") int cuiNum){
		return cuiService.selectCUI(cuiNum);
	}
	@PostMapping("/cu/cui")
	public  Map<String,Object> insertCUI(@ModelAttribute  CustomerInfoVO cui) {
		return cuiService.insertCUI(cui);
	}
	@PostMapping("/cu/cui/mod")
	public Map<String,Object> updateCUI(@ModelAttribute CustomerInfoVO cui) {
		return cuiService.updateCUI(cui);
	}
	@PostMapping("/cu/cuis/del")
	public Map<String,Object> deleteCUI(@RequestBody List<Integer> nums) {
		return cuiService.deleteCUI(nums);
	}
}
