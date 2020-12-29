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

import kr.co.momdeal.service.CategoryInfoService;
import kr.co.momdeal.vo.CategoryInfoVO;
import kr.co.momdeal.vo.common.PaginationVO;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CategoryInfoController {

	@Resource
	private CategoryInfoService caiService;

	@GetMapping("/co/cais")
	public  Closeable selectCAIList(CategoryInfoVO cai, PaginationVO page){
		return caiService.selectCAIList(cai, page);
	}
	@GetMapping("/co/cai/{caiNum}")
	public  CategoryInfoVO selectCAI(@PathVariable("caiNum") int caiNum){
		return caiService.selectCAI(caiNum);
	}
	@PostMapping("/co/cai")
	public  Map<String,Object> insertCAI(@ModelAttribute  CategoryInfoVO cai) {
		return caiService.insertCAI(cai);
	}
	@PostMapping("/co/cai/mod")
	public Map<String,Object> updateCAI(@ModelAttribute CategoryInfoVO cai) {
		return caiService.updateCAI(cai);
	}
	@PostMapping("/co/cais/del")
	public Map<String,Object> deleteCAI(@RequestBody List<Integer> nums) {
		return caiService.deleteCAI(nums);
	}
}
