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

import kr.co.momdeal.service.CustomerFaqService;
import kr.co.momdeal.vo.CustomerFaqVO;
import kr.co.momdeal.vo.common.PaginationVO;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CustomerFaqController {

	@Resource
	private CustomerFaqService cufService;

	@GetMapping("/co/cufs")
	public  Closeable selectCUFList(CustomerFaqVO cuf, PaginationVO page){
		return cufService.selectCUFList(cuf, page);
	}
	@GetMapping("/co/cuf/{cufNum}")
	public  CustomerFaqVO selectCUF(@PathVariable("cufNum") int cufNum){
		return cufService.selectCUF(cufNum);
	}
	@PostMapping("/co/cuf")
	public  Map<String,Object> insertCUF(@ModelAttribute  CustomerFaqVO cuf) {
		return cufService.insertCUF(cuf);
	}
	@PostMapping("/co/cuf/mod")
	public Map<String,Object> updateCUF(@ModelAttribute CustomerFaqVO cuf) {
		return cufService.updateCUF(cuf);
	}
	@PostMapping("/co/cufs/del")
	public Map<String,Object> deleteCUF(@RequestBody List<Integer> nums) {
		return cufService.deleteCUF(nums);
	}
}
