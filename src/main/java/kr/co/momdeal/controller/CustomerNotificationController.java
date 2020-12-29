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

import kr.co.momdeal.service.CustomerNotificationService;
import kr.co.momdeal.vo.CustomerNotificationVO;
import kr.co.momdeal.vo.common.PaginationVO;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CustomerNotificationController {

	@Resource
	private CustomerNotificationService cunService;

	@GetMapping("/cu/cuns")
	public  Closeable selectCUNList(CustomerNotificationVO cun, PaginationVO page){
		return cunService.selectCUNList(cun, page);
	}
	@GetMapping("/cu/cun/{cunNum}")
	public  CustomerNotificationVO selectCUN(@PathVariable("cunNum") int cunNum){
		return cunService.selectCUN(cunNum);
	}
	@PostMapping("/cu/cun")
	public  Map<String,Object> insertCUN(@ModelAttribute  CustomerNotificationVO cun) {
		return cunService.insertCUN(cun);
	}
	@PostMapping("/cu/cun/mod")
	public Map<String,Object> updateCUN(@ModelAttribute CustomerNotificationVO cun) {
		return cunService.updateCUN(cun);
	}
	@PostMapping("/cu/cuns/del")
	public Map<String,Object> deleteCUN(@RequestBody List<Integer> nums) {
		return cunService.deleteCUN(nums);
	}
}
