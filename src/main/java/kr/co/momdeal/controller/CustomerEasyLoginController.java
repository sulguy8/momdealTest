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

import kr.co.momdeal.service.CustomerEasyLoginService;
import kr.co.momdeal.vo.CustomerEasyLoginVO;
import kr.co.momdeal.vo.common.PaginationVO;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CustomerEasyLoginController {

	@Resource
	private CustomerEasyLoginService celService;

	@GetMapping("/cu/cels")
	public  Closeable selectCELList(CustomerEasyLoginVO cel, PaginationVO page){
		return celService.selectCELList(cel, page);
	}
	@GetMapping("/cu/cel/{celNum}")
	public  CustomerEasyLoginVO selectCEL(@PathVariable("celNum") int celNum){
		return celService.selectCEL(celNum);
	}
	@PostMapping("/cu/cel")
	public  Map<String,Object> insertCEL(@ModelAttribute  CustomerEasyLoginVO cel) {
		return celService.insertCEL(cel);
	}
	@PostMapping("/cu/cel/mod")
	public Map<String,Object> updateCEL(@ModelAttribute CustomerEasyLoginVO cel) {
		return celService.updateCEL(cel);
	}
	@PostMapping("/cu/cels/del")
	public Map<String,Object> deleteCEL(@RequestBody List<Integer> nums) {
		return celService.deleteCEL(nums);
	}
}
