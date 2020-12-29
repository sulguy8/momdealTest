package kr.co.momdeal.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class ClassUtil {

	// clazz : invoke 대상 오브젝트 레퍼런스
	// methodName : invoke할 메서드
	// args : invoke할 메서드의 파라메터
	public static <T> Object invoke(T clazz, String methodName,Object... args) {
		Method[] methods = clazz.getClass().getDeclaredMethods();
		for(Method method : methods) {
			if(methodName.equals(method.getName())) {
				try {
					return method.invoke(clazz, args);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	public static <T> List<MultipartFile> getMFList(final T obj,final String name){
		final Method[] methods = obj.getClass().getDeclaredMethods();
		final List<MultipartFile> mfList = new ArrayList<>();
		for(Method method : methods) {
			if(method.getReturnType().getName().equals(MultipartFile.class.getName())) {
				if(method.getName().indexOf(name)!=-1) {
					String setName = "set" + method.getName().replace(name,"");
					Object o= null;
					try {
						o = method.invoke(obj);
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(o==null || o==Void.TYPE) {
						mfList.add(null);
					}else {
						System.out.println(setName);
						mfList.add((MultipartFile) o);
					}
				}
			}
		}
		return mfList;
	}
}
