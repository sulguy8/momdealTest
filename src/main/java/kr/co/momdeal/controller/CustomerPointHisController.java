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

import kr.co.momdeal.service.CustomerPointHisService;
import kr.co.momdeal.vo.CustomerPointHisVO;
import kr.co.momdeal.vo.common.PaginationVO;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CustomerPointHisController {

	@Resource
	private CustomerPointHisService cphService;

	@GetMapping("/cu/cphs")
	public  Closeable selectCPHList(CustomerPointHisVO cph, PaginationVO page){
		return cphService.selectCPHList(cph, page);
	}
	@GetMapping("/cu/cph/{cphNum}")
	public  CustomerPointHisVO selectCPH(@PathVariable("cphNum") int cphNum){
		return cphService.selectCPH(cphNum);
	}
	@PostMapping("/cu/cph")
	public  Map<String,Object> insertCPH(@ModelAttribute  CustomerPointHisVO cph) {
		return cphService.insertCPH(cph);
	}
	@PostMapping("/cu/cph/mod")
	public Map<String,Object> updateCPH(@ModelAttribute CustomerPointHisVO cph) {
		return cphService.updateCPH(cph);
	}
	@PostMapping("/cu/cphs/del")
	public Map<String,Object> deleteCPH(@RequestBody List<Integer> nums) {
		return cphService.deleteCPH(nums);
	}
}
