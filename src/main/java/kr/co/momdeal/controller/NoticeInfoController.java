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

import kr.co.momdeal.service.NoticeInfoService;
import kr.co.momdeal.vo.NoticeInfoVO;
import kr.co.momdeal.vo.common.PaginationVO;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class NoticeInfoController {

	@Resource
	private NoticeInfoService noiService;

	@GetMapping("/co/nois")
	public  Closeable selectNOIList(NoticeInfoVO noi, PaginationVO page){
		return noiService.selectNOIList(noi, page);
	}
	@GetMapping("/co/noi/{noiNum}")
	public  NoticeInfoVO selectNOI(@PathVariable("noiNum") int noiNum){
		return noiService.selectNOI(noiNum);
	}
	@PostMapping("/co/noi")
	public  Map<String,Object> insertNOI(@ModelAttribute  NoticeInfoVO noi) {
		return noiService.insertNOI(noi);
	}
	@PostMapping("/co/noi/mod")
	public Map<String,Object> updateNOI(@ModelAttribute NoticeInfoVO noi) {
		return noiService.updateNOI(noi);
	}
	@PostMapping("/co/nois/del")
	public Map<String,Object> deleteNOI(@RequestBody List<Integer> nums) {
		return noiService.deleteNOI(nums);
	}
}
