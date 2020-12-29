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

import kr.co.momdeal.service.EventApplicantInfoService;
import kr.co.momdeal.vo.EventApplicantInfoVO;
import kr.co.momdeal.vo.common.PaginationVO;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class EventApplicantInfoController {

	@Resource
	private EventApplicantInfoService eaiService;

	@GetMapping("/ev/eais")
	public  Closeable selectEAIList(EventApplicantInfoVO eai, PaginationVO page){
		return eaiService.selectEAIList(eai, page);
	}
	@GetMapping("/ev/eai/{eaiNum}")
	public  EventApplicantInfoVO selectEAI(@PathVariable("eaiNum") int eaiNum){
		return eaiService.selectEAI(eaiNum);
	}
	@PostMapping("/ev/eai")
	public  Map<String,Object> insertEAI(@ModelAttribute  EventApplicantInfoVO eai) {
		return eaiService.insertEAI(eai);
	}
	@PostMapping("/ev/eai/mod")
	public Map<String,Object> updateEAI(@ModelAttribute EventApplicantInfoVO eai) {
		return eaiService.updateEAI(eai);
	}
	@PostMapping("/ev/eais/del")
	public Map<String,Object> deleteEAI(@RequestBody List<Integer> nums) {
		return eaiService.deleteEAI(nums);
	}
}
