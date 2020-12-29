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

import kr.co.momdeal.service.CustomerAppService;
import kr.co.momdeal.vo.CustomerAppVO;
import kr.co.momdeal.vo.common.PaginationVO;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CustomerAppController {

	@Resource
	private CustomerAppService cuaService;

	@GetMapping("/cu/cuas")
	public  Closeable selectCUAList(CustomerAppVO cua, PaginationVO page){
		return cuaService.selectCUAList(cua, page);
	}
	@GetMapping("/cu/cua/{cuaNum}")
	public  CustomerAppVO selectCUA(@PathVariable("cuaNum") int cuaNum){
		return cuaService.selectCUA(cuaNum);
	}
	@PostMapping("/cu/cua")
	public  Map<String,Object> insertCUA(@ModelAttribute  CustomerAppVO cua) {
		return cuaService.insertCUA(cua);
	}
	@PostMapping("/cu/cua/mod")
	public Map<String,Object> updateCUA(@ModelAttribute CustomerAppVO cua) {
		return cuaService.updateCUA(cua);
	}
	@PostMapping("/cu/cuas/del")
	public Map<String,Object> deleteCUA(@RequestBody List<Integer> nums) {
		return cuaService.deleteCUA(nums);
	}
}
