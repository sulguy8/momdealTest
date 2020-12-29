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

import kr.co.momdeal.service.EventInfoService;
import kr.co.momdeal.vo.EventInfoVO;
import kr.co.momdeal.vo.common.PaginationVO;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class EventInfoController {

	@Resource
	private EventInfoService eviService;

	@GetMapping("/ev/evis")
	public  Closeable selectEVIList(EventInfoVO evi, PaginationVO page){
		return eviService.selectEVIList(evi, page);
	}
	@GetMapping("/ev/evi/{eviNum}")
	public  EventInfoVO selectEVI(@PathVariable("eviNum") int eviNum){
		return eviService.selectEVI(eviNum);
	}
	@PostMapping("/ev/evi")
	public  Map<String,Object> insertEVI(@ModelAttribute  EventInfoVO evi) {
		return eviService.insertEVI(evi);
	}
	@PostMapping("/ev/evi/mod")
	public Map<String,Object> updateEVI(@ModelAttribute EventInfoVO evi) {
		return eviService.updateEVI(evi);
	}
	@PostMapping("/ev/evis/del")
	public Map<String,Object> deleteEVI(@RequestBody List<Integer> nums) {
		return eviService.deleteEVI(nums);
	}
}
