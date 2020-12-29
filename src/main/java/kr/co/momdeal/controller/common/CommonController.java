package kr.co.momdeal.controller.common;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.momdeal.service.CommonCodeService;
import kr.co.momdeal.vo.CommonCodeVO;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CommonController {

	public static Map<String, Map<String, String>> codMap = Collections.synchronizedMap(new LinkedHashMap<>());
//	public static Map<String, Map<String, String>> lofMap = Collections.synchronizedMap(new LinkedHashMap<>());
//	public static List<LocationFilterVO> lofList = Collections.synchronizedList(new ArrayList<>());
//	public static Map<String, Map<String, Map<String, Map<String, Object>>>> gocTicMap = Collections
//			.synchronizedMap(new LinkedHashMap<>());
//	public static Map<String, Map<String, Map<String, Map<String, Object>>>> mapAll = Collections
//			.synchronizedMap(new LinkedHashMap<>());
//	public static List<CustomerPointConfigVO> cpcList = Collections.synchronizedList(new ArrayList<>());
//	public static List<GoodsCateVO> gocTicList = Collections.synchronizedList(new ArrayList<>());
//	public static List<GoodsCateVO> gocMapAll = Collections.synchronizedList(new ArrayList<>());
//	public static List<GoodsCateVO> gocDlvList = Collections.synchronizedList(new ArrayList<>());
	@Resource
	private CommonCodeService codService;
//	@Resource
//	private LocationFilterService lofService;
//	@Resource
//	private CustomerPointConfigService cpcService;
//	@Resource
//	private GoodsCateService gocService;
//	@Resource
//	private FileUtils fu;
//	@Resource
//	ShopFilterService shfService;
//	@Resource
//	private BankCodeInfoService bciService;

	@GetMapping("/system/all")
	public Map<String, Object> getAllCommonCode() {
		Map<String, Object> allMap = new LinkedHashMap<>();
		allMap.put("codMap", getCodMap());
		return allMap;
	}

	@GetMapping("/system/codMap")
	public Map<String, Map<String, String>> getCodMap() {
		if (codMap.isEmpty()) {
			codMap.clear();
			CommonCodeVO initCod = new CommonCodeVO();
			initCod.setActive("1");
			List<CommonCodeVO> codList = codService.selectCODList(initCod);
			for (CommonCodeVO cod : codList) {
				if (!codMap.containsKey(cod.getCodType())) {
					codMap.put(cod.getCodType(), new LinkedHashMap<String, String>());
				}
				codMap.get(cod.getCodType()).put(cod.getCodKey(), cod.getCodVal());
			}
		}
		return codMap;
	}

//	@GetMapping("/system/cpcList")
//	public List<CustomerPointConfigVO> getCpcList() {
//		if (cpcList.isEmpty()) {
//			cpcList = cpcService.selectCPCAllList();
//		}
//		return cpcList;
//	}
//
//	@GetMapping("/system/lofMap")
//	public Map<String, Map<String, String>> getLofMap() {
//		if (lofMap.isEmpty()) {
//			lofMap.clear();
//			lofList = lofService.selectLOFAllList();
//			for (LocationFilterVO lof : lofList) {
//				if (!lofMap.containsKey(lof.getLofSido())) {
//					lofMap.put(lof.getLofSido(), new LinkedHashMap<String, String>());
//				}
//				lofMap.get(lof.getLofSido()).put(lof.getLofGugun(), lof.getLofNum().toString());
//			}
//		}
//		return lofMap;
//	}
//
//	private Map<String, Map<String, Map<String, Map<String, Object>>>> getGocCate(List<GoodsCateVO> gocList,
//			Map<String, Map<String, Map<String, Map<String, Object>>>> gocMap) {
//		for (GoodsCateVO goc : gocList) {
//			if (!gocMap.containsKey(goc.getGocDep1())) {
//				gocMap.put(goc.getGocDep1(), new LinkedHashMap<>());
//			}
//			if (!gocMap.get(goc.getGocDep1()).containsKey(goc.getGocDep2())) {
//				gocMap.get(goc.getGocDep1()).put(goc.getGocDep2(), new LinkedHashMap<>());
//			}
//
//			if (!gocMap.get(goc.getGocDep1()).get(goc.getGocDep2()).containsKey(goc.getGocDep3())) {
//				gocMap.get(goc.getGocDep1()).get(goc.getGocDep2()).put(goc.getGocDep3(), new LinkedHashMap<>());
//			}
//			gocMap.get(goc.getGocDep1()).get(goc.getGocDep2()).get(goc.getGocDep3()).put(goc.getGocDep4(),
//					goc.getGocNum());
//		}
//		return gocMap;
//	}
//
//	@GetMapping("/system/gocTicMap")
//	public Map<String, Map<String, Map<String, Map<String, Object>>>> getGocTicMap() {
//		if (gocTicMap.isEmpty()) {
//			gocTicMap.clear();
//			GoodsCateVO pGoc = new GoodsCateVO();
//			pGoc.setGocType(1);
//			pGoc.setActive("1");
//			gocTicList = gocService.selectGOCAllList(pGoc);
//			getGocCate(gocTicList, gocTicMap);
//		}
//		log.info("gocTicMap => {}", gocTicMap);
//		return gocTicMap;
//	}
//	@GetMapping("/system/mapAll")
//	public Map<String, Map<String, Map<String, Map<String, Object>>>> getMapAll() {
//		if (mapAll.isEmpty()) {
//			mapAll.clear();
//			GoodsCateVO pGoc = new GoodsCateVO();
//			gocMapAll = gocService.selectGOCAllList(pGoc);
//			getGocCate(gocMapAll, mapAll);
//		}
//		log.info("gocTicMap => {}", mapAll);
//		return mapAll;
//	}
//
//	@GetMapping("/system/gocDlvMap")
//	public Map<String, Map<String, Map<String, Map<String, Object>>>> getGocMapDlv() {
//		if (gocDlvMap.isEmpty()) {
//			gocDlvMap.clear();
//			GoodsCateVO pGoc = new GoodsCateVO();
//			pGoc.setGocType(2);
//			pGoc.setActive("1");
//			gocDlvList = gocService.selectGOCAllList(pGoc);
//			getGocCate(gocDlvList, gocDlvMap);
//		}
//		return gocDlvMap;
//	}

	@GetMapping("/system/init")
	public void initAllMap() {
//		gocTicMap.clear();
//		gocDlvMap.clear();
//		lofMap.clear();
		codMap.clear();
//		cpcList.clear();
	}

//	@GetMapping(value = "/system/shfs/{shfType}")
//	public List<ShopFilterVO> getSHFList(@PathVariable Integer shfType) {
//		return shfService.selectSHFListByType(shfType);
//	}
//
//	@GetMapping(value = "/system/bciList")
//	public List<BankCodeInfoVO> getBCIList() {
//		return bciService.selectBCIList();
//	}
//
//	@GetMapping(value = "/system/shfs")
//	public List<ShopFilterVO> getSHFList() {
//		ShopFilterVO shf = new ShopFilterVO();
//		shf.setActive("1");
//		return shfService.selectSHFList(shf);
//	}

	@GetMapping(value = "/now/date")
	public Map<String,Integer> getNowDate() {
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		// Add one to month {0 - 11}
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		Map<String,Integer> rMap = new HashMap<>();
		rMap.put("year", year);
		rMap.put("month", month);
		rMap.put("day", day);
		return rMap;
	}
}
