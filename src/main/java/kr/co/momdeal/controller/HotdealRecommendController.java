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

import kr.co.momdeal.service.HotdealRecommendService;
import kr.co.momdeal.vo.HotdealRecommendVO;
import kr.co.momdeal.vo.common.PaginationVO;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class HotdealRecommendController {

	@Resource
	private HotdealRecommendService horService;

	@GetMapping("/hd/hors")
	public  Closeable selectHORList(HotdealRecommendVO hor, PaginationVO page){
		return horService.selectHORList(hor, page);
	}
	@GetMapping("/hd/hor/{horNum}")
	public  HotdealRecommendVO selecthor(@PathVariable("horNum") int horNum){
		return horService.selectHOR(horNum);
	}
	@PostMapping("/hd/hor")
	public  Map<String,Object> inserthor(@ModelAttribute  HotdealRecommendVO hor) {
		return horService.insertHOR(hor);
	}
	@PostMapping("/hd/hor/mod")
	public Map<String,Object> updatehor(@ModelAttribute HotdealRecommendVO hor) {
		return horService.updateHOR(hor);
	}
	@PostMapping("/hd/hors/del")
	public Map<String,Object> deletehor(@RequestBody List<Integer> nums) {
		return horService.deleteHOR(nums);
	}
}
