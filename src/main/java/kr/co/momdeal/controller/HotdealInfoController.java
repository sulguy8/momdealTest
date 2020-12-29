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

import kr.co.momdeal.service.HotdealInfoService;
import kr.co.momdeal.vo.HotdealInfoVO;
import kr.co.momdeal.vo.common.PaginationVO;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class HotdealInfoController {

	@Resource
	private HotdealInfoService hoiService;

	@GetMapping("/hd/hois")
	public  Closeable selectHOIList(HotdealInfoVO hoi, PaginationVO page){
		return hoiService.selectHOIList(hoi, page);
	}
	@GetMapping("/hd/hoi/{hoiNum}")
	public  HotdealInfoVO selectHOI(@PathVariable("hoiNum") int hoiNum){
		return hoiService.selectHOI(hoiNum);
	}
	@PostMapping("/hd/hoi")
	public  Map<String,Object> insertHOI(@ModelAttribute  HotdealInfoVO hoi) {
		return hoiService.insertHOI(hoi);
	}
	@PostMapping("/hd/hoi/mod")
	public Map<String,Object> updateHOI(@ModelAttribute HotdealInfoVO hoi) {
		return hoiService.updateHOI(hoi);
	}
	@PostMapping("/hd/hois/del")
	public Map<String,Object> deleteHOI(@RequestBody List<Integer> nums) {
		return hoiService.deleteHOI(nums);
	}
}
