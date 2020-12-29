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

import kr.co.momdeal.service.CustomerPointService;
import kr.co.momdeal.vo.CustomerPointVO;
import kr.co.momdeal.vo.common.PaginationVO;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CustomerPointController {

	@Resource
	private CustomerPointService cupService;

	@GetMapping("/cu/cups")
	public  Closeable selectCUPList(CustomerPointVO cup, PaginationVO page){
		return cupService.selectCUPList(cup, page);
	}
	@GetMapping("/cu/cup/{cupNum}")
	public  CustomerPointVO selectCUP(@PathVariable("cupNum") int cupNum){
		return cupService.selectCUP(cupNum);
	}
	@PostMapping("/cu/cup")
	public  Map<String,Object> insertCUP(@ModelAttribute  CustomerPointVO cup) {
		return cupService.insertCUP(cup);
	}
	@PostMapping("/cu/cup/mod")
	public Map<String,Object> updateCUP(@ModelAttribute CustomerPointVO cup) {
		return cupService.updateCUP(cup);
	}
	@PostMapping("/cu/cups/del")
	public Map<String,Object> deleteCUP(@RequestBody List<Integer> nums) {
		return cupService.deleteCUP(nums);
	}
}
