package kr.co.momdeal.config;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;

import kr.co.momdeal.exception.ServiceException;
import kr.co.momdeal.vo.common.PageListVO;
import kr.co.momdeal.vo.common.PaginationVO;

@Component
@Aspect
public class ControllerConfigAOP {

//	@Resource
//	private ZManageService zms;
	private ObjectMapper om;
	
	final Logger log = LoggerFactory.getLogger(ControllerConfigAOP.class);
	
	public ControllerConfigAOP() {
		this.om = new ObjectMapper();
		om.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
	}

	// page 기본값 1
	// pageSize 기본값 10
	@Around("execution(java.io.Closeable kr.co.momdeal.controller..*.*(..))")
	public Object paingJoin(ProceedingJoinPoint pjp) throws Throwable {
		Object[] args = pjp.getArgs();
		log.debug("args=>{}", args);
		for (Object arg : args) {
			if (arg instanceof PaginationVO) {
				PaginationVO page = (PaginationVO) arg;
				log.debug("arg=>{}", arg);
				if (page.getPageNum() == null) {
					page.setPageNum(1);
				} 
				if (page.getPageSize() == null) {
					page.setPageSize(50);
				}
				if(page.getStart()!=null) {
					int start = page.getStart();
					int length = page.getLength();
					if(start==0) {
						start = 1;
					}
					if(length==0) {
						length = 50;
					}
					page.setPageNum(start/length+1);
					page.setPageSize(length);
				}
			}
		} 
		Object obj = pjp.proceed();
		if (obj instanceof Page) {
			PageListVO plvo = new PageListVO((Page<?>) obj);
			return plvo;
		}
		return obj;
	}

	@Around("execution(* kr.co.momdeal.controller..*.*(org.springframework.validation.BindingResult,..)) "
			+ "|| execution(* kr.co.momdeal.controller..*.*(..,org.springframework.validation.BindingResult))")
	public Object validationJoin(ProceedingJoinPoint pjp) throws Throwable {
		
		Object[] args = pjp.getArgs();
		log.debug("pjp=>{}", pjp);
		for (Object arg : args) {
			if (arg instanceof BindingResult) {
				BindingResult br = (BindingResult) arg;
				if (br.hasErrors()) {
					String errorMsg = br.toString();
					if (errorMsg.indexOf("Invalid image file type") != -1) {
						errorMsg = "업로드된 파일이 이미지가 아닙니다.";
					}
					throw new ServiceException(errorMsg);
				}
			}
		}
		Object obj = pjp.proceed();
		return obj;
	}

//	@Around("execution(String com.shop.controller.common.URIController.*(..))")
//	public Object authJoing(ProceedingJoinPoint pjp) throws Throwable {
//		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//		HttpSession hs = req.getSession();
//		// 개발중에는 세션처리 안함.
//		if (hs.getAttribute("zm") == null) {
////			ZManage zm = new ZManage();
////			zm.setId("adm");
////			zm.setPwd("1234");
////			hs.setAttribute("zm", zms.login(zm));
//			// return "/sv/admin/login";
//		}
//		Object obj = pjp.proceed();
//		return obj;
//	}


	// 아임포트 콜백으로 remote ip가 52.78.100.19, 52.78.48.223이 아닐경우 익셉션 발생시킴.
//	@Around("execution(* kr.co.momdeal.controller.PayController.doPaymentCheck(..))")
//	public Object paymentJoin(ProceedingJoinPoint pjp) throws Throwable {
//		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//		final String remoteIp = req.getRemoteAddr();
//		if( !"52.78.100.19".equals(remoteIp) && !"52.78.48.223".equals(remoteIp)) {
//			throw new ServiceException("해당 IP : " + remoteIp + " 는 차단될 수 있습니다.","err03");
//		} 
//		Object obj = pjp.proceed();
//		return obj;
//	}
	@Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
	public void restcontroller() {
	}

	@Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
	public void postmapping() {
	}
	@Around("restcontroller() && postmapping() && args(.., @ModelAttribute json)")
	public Object logPostMethods(ProceedingJoinPoint joinPoint, Object json)
			throws Throwable {
		log.info(json.toString());
	    MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
	    Annotation[][] annotationMatrix = methodSignature.getMethod().getParameterAnnotations();
	    String fieldName = null;
	    for (Annotation[] annotations : annotationMatrix) {
	      for (Annotation annotation : annotations) {
	        if (!(annotation instanceof ModelAttribute))
	          continue;
	        ModelAttribute modelAttribute = (ModelAttribute) annotation;
	        if(!"".equals(modelAttribute.value())) {
	        	fieldName = modelAttribute.value();
	        }
	      }
	    }

	    if(fieldName!=null) {
	    	final Object[] args = joinPoint.getArgs();
			for (final Object arg : args) {
				final Class<?> clazz = arg.getClass();
				final Field[] fields = clazz.getDeclaredFields();
				for(final Field f:fields) {
					if(f.getName().equals(fieldName)) {
						final Type type = f.getGenericType();
						final List<?> value = om.readValue(json.toString(),om.constructType(type));
						f.setAccessible(true);
						f.set(arg, value);
					}
				}
			} 
	    }
		Object result;
		try {
			result = joinPoint.proceed();
			log.debug("result=>{}",result);
			return result;
		} catch (Throwable t) {
			throw t;
		}
	}
}