package kr.co.momdeal.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import kr.co.momdeal.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FileUtils {

	private static String imageExecute;
	private static String middleSize;
	private static String middleQuality;
	private static String smallSize;
	private static String smallQuality;

//	@Value("${imagemagic.execute}")
//    private void setImageExecute(String imageExecute){
//		FileUtils.imageExecute = imageExecute;
//    }
//	@Value("${imagemagic.middleSize}")
//    private void setMiddleSize(String middleSize){
//		FileUtils.middleSize = middleSize;
//    }
//	@Value("${imagemagic.middleQuality}")
//    private void setMiddleQuality(String middleQuality){
//		FileUtils.middleQuality = middleQuality;
//    }
//
//	@Value("${imagemagic.smallSize}")
//    private void setSmallSize(String smallSize){
//		FileUtils.smallSize = smallSize;
//    }
//	@Value("${imagemagic.middleQuality}")
//    private void setSmallQuality(String smallQuality){
//		FileUtils.smallQuality = smallQuality;
//    }
	
	private static final String BASE_PATH;
	static {
		String osName = System.getProperty("os.name");
		String comName = null;
		try {
			comName = InetAddress.getLocalHost().getHostName();
			
		} catch (UnknownHostException e) {
		}
		if(comName!=null) {
			if(comName.equals("DESKTOP-HI7I5VV")) {
				BASE_PATH = "E:\\study\\workspace\\new-shop-ing\\src\\main\\webapp\\resources\\file\\";
			}else if(comName.equals("DESKTOP-UF721UP")){
				BASE_PATH = "D:\\dev\\eclipse\\runtime\\shop-ing\\src\\main\\webapp\\resources\\file\\";
			}else if(comName.equals("DESKTOP-F6357NG")){
				BASE_PATH = "D:\\dev\\workspace\\git\\shop-ing2\\src\\main\\webapp\\resources\\file\\";
			}else if(comName.equals("koitt03a-PC")){
				BASE_PATH = "C:\\ict\\workspace\\gits\\shop-ing\\src\\main\\webapp\\resources\\file\\";
			}else if (comName.equals("DESKTOP-NRCCLVT")){
				BASE_PATH = "C:\\Users\\runtime\\git\\shop-ing\\src\\main\\webapp\\resources\\file";
			}else if (comName.equals("DESKTOP-8PG3SJ8")){
				BASE_PATH = "C:\\Users\\Administrator\\git\\shop-ing\\src\\main\\webapp\\resources\\file";
			}else if (comName.equals("DESKTOP-M1RIPVG")){
				BASE_PATH = "D:\\dev\\javaworks\\shop-ing\\src\\main\\webapp\\resources\\file";
			}else {
				BASE_PATH = "E:\\study\\workspace\\new-shop-ing\\src\\main\\webapp\\resources\\file\\";
			}
		}else if(osName.toLowerCase().indexOf("window")!=-1) {
			BASE_PATH = "E:\\study\\workspace\\new-shop-ing\\src\\main\\webapp\\resources\\file\\";
		}else {
			BASE_PATH = "/data/files/";
		}
	}

	
	public InputStream getInputStreamFromFile(String fileName) throws IOException {
		
		if(new File(BASE_PATH + fileName).exists()) {
			return new FileInputStream(BASE_PATH + fileName);
		}
		return null;
	}
	public File getFile(String fileName) {
		return new File(BASE_PATH + fileName);
	}
	
	public String saveFile(MultipartFile mf,String path) {
		String orgFileName = Long.toString(System.nanoTime()) + "." + FilenameUtils.getExtension(mf.getOriginalFilename());
		String fileName = path + getMiddlePath() + orgFileName;
		String mFileName = path + getMiddlePath() + "m_" + orgFileName;
		String sFileName = path + getMiddlePath() + "s_" + orgFileName;
		File file = new File(BASE_PATH + fileName);
		if(!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		try {
			Files.copy(mf.getInputStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
			if("/data/files/".equals(BASE_PATH)) {
				reSizeImg(BASE_PATH + fileName,FileUtils.middleQuality, FileUtils.middleSize, BASE_PATH + mFileName);
				reSizeImg(BASE_PATH + fileName,FileUtils.smallQuality, FileUtils.smallSize, BASE_PATH + sFileName);
			}
		} catch (Exception e) {
			log.error("error => {} " ,e);
			
			throw new ServiceException(e,"파일 저장 시 에러");
		}
		return path + getMiddlePath() + orgFileName;
	}
	public <T> boolean autoSaveFile(T obj, String path) {
		List<File> fileList = new ArrayList<File>();
		final Method[] methods = obj.getClass().getDeclaredMethods();
		try {
			for(Method method : methods) {
				
				if(method.getReturnType().getName().equals(MultipartFile.class.getName())) {
					Object mfObj = method.invoke(obj);
					if(mfObj!=null) {
						MultipartFile mf = (MultipartFile)mfObj;
						String orgFileName = Long.toString(System.nanoTime()) + "." + FilenameUtils.getExtension(mf.getOriginalFilename());
						String fileName = path + getMiddlePath() + orgFileName;
						String mFileName = path + getMiddlePath() + "m_" + orgFileName;
						String sFileName = path + getMiddlePath() + "s_" + orgFileName;
						String getName = method.getName();
						String setName = "set" + getName.substring(3) + "Name";
						ClassUtil.invoke(obj, setName, fileName);
						File file = new File(BASE_PATH + fileName);
						if(!file.getParentFile().exists()) {
							file.getParentFile().mkdirs();
						}
						Files.copy(mf.getInputStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
						if("/data/files/".equals(BASE_PATH)) {
							reSizeImg(BASE_PATH + fileName,FileUtils.middleQuality, FileUtils.middleSize, BASE_PATH + mFileName);
							reSizeImg(BASE_PATH + fileName,FileUtils.smallQuality, FileUtils.smallSize, BASE_PATH + sFileName);
						}
						fileList.add(file);
					}
				}
			}
		}catch(Exception e) {
			log.error("error => {} " ,e);
			for(File file : fileList) {
				file.delete();
			}
			
			throw new ServiceException(e,"파일 저장 시 에러");
		}
		return true;
	}
	
	private  boolean reSizeImg(String image_path, String quality, String size, String output_image) throws Exception {
	    ProcessBuilder pb = new ProcessBuilder(FileUtils.imageExecute,image_path,"-quality",quality,"-resize",size,output_image);  
	    pb.redirectErrorStream(true);
	    try {
	        Process p = pb.start();
	        BufferedReader br = new BufferedReader( new InputStreamReader(p.getInputStream() ));
	        String line = null;  
	        while((line=br.readLine())!=null){  
	            log.debug(line);  
	         }
	    }catch(Exception e) {  
	    	throw e;
	    }

	    return true;
	}

	public boolean renameFile(String target, String rename) {
		File tFile = new File(BASE_PATH + target);
		if(tFile.exists()) {
			File rFile = new File(BASE_PATH+rename);
			return tFile.renameTo(rFile);			
		}
		return false;
	}
	
	public boolean deleteFile(String fileName) {
		File delFile = new File(BASE_PATH + fileName);
		if(delFile.exists()) {
			return delFile.delete();
		}
		return true;
	}

	public boolean deleteFiles(List<String> fileNames) {
		for(String fileName:fileNames) {
			if(fileName==null) continue;
			deleteFile(fileName);
		}
		return true;
	}
	private static String getMiddlePath() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH ) + 1;
		String mStr = month + "";
		if(month<10) {
			mStr = "0" + mStr;
		}
		String yStr = year + "";
		return "/" + yStr +"/"+ mStr + "/";
	}
	
	public static void main(String[] args) throws UnknownHostException {
		String path = "E:\\study\\workspace\\new-shop-ing\\src\\main\\webapp\\resources\\file";
		File root = new File(path);
		File[] subs = root.listFiles();
		for(File sub:subs ) {
			File[] years = sub.listFiles();
			for(File year:years) {
				if(!year.isDirectory()) continue;
				File[] months = year.listFiles();
				System.out.println(year.getName());
				for(File month:months) {
					File[] files = month.listFiles();
					for(File file:files) {
						String fileName = file.getName();
						String mFileName = "m_" + fileName;
						String sFileName = "s_" + fileName;
						String fPath = file.getParent() +"\\";
						try {
//							reSizeImg(fPath + fileName,"90%", "500x", fPath + mFileName);
//							reSizeImg(fPath + fileName,"90%", "200x", fPath + sFileName);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
				
	}
}
