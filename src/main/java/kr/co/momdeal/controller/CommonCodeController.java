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

import kr.co.momdeal.service.CommonCodeService;
import kr.co.momdeal.vo.CommonCodeVO;
import kr.co.momdeal.vo.common.PaginationVO;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CommonCodeController {

	@Resource
	private CommonCodeService codService;

	@GetMapping("/co/cods")
	public  Closeable selectCODList(CommonCodeVO cod, PaginationVO page){
		return codService.selectCODList(cod, page);
	}
	@GetMapping("/co/cod/{codNum}")
	public  CommonCodeVO selectCOD(@PathVariable("codNum") int codNum){
		return codService.selectCOD(codNum);
	}
	@PostMapping("/co/cod")
	public  Map<String,Object> insertCOD(@ModelAttribute  CommonCodeVO cod) {
		return codService.insertCOD(cod);
	}
	@PostMapping("/co/cod/mod")
	public Map<String,Object> updateCOD(@ModelAttribute CommonCodeVO cod) {
		return codService.updateCOD(cod);
	}
	@PostMapping("/co/cods/del")
	public Map<String,Object> deleteCOD(@RequestBody List<Integer> nums) {
		return codService.deleteCOD(nums);
	}
}
