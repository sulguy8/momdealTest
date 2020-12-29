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

import kr.co.momdeal.service.CustomerQuestionService;
import kr.co.momdeal.vo.CustomerQuestionVO;
import kr.co.momdeal.vo.common.PaginationVO;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CustomerQuestionController {

	@Resource
	private CustomerQuestionService cuqService;

	@GetMapping("/cu/cuqs")
	public  Closeable selectCUQList(CustomerQuestionVO cuq, PaginationVO page){
		return cuqService.selectCUQList(cuq, page);
	}
	@GetMapping("/cu/cuq/{cuqNum}")
	public  CustomerQuestionVO selectCUQ(@PathVariable("cuqNum") int cuqNum){
		return cuqService.selectCUQ(cuqNum);
	}
	@PostMapping("/cu/cuq")
	public  Map<String,Object> insertCUQ(@ModelAttribute  CustomerQuestionVO cuq) {
		return cuqService.insertCUQ(cuq);
	}
	@PostMapping("/cu/cuq/mod")
	public Map<String,Object> updateCUQ(@ModelAttribute CustomerQuestionVO cuq) {
		return cuqService.updateCUQ(cuq);
	}
	@PostMapping("/cu/cuqs/del")
	public Map<String,Object> deleteCUQ(@RequestBody List<Integer> nums) {
		return cuqService.deleteCUQ(nums);
	}
}
