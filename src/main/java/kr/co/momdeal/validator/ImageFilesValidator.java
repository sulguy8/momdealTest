package kr.co.momdeal.validator;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

public class ImageFilesValidator implements ConstraintValidator<ValidImages, List<MultipartFile>> {
    @Override
    public void initialize(ValidImages constraintAnnotation) {}

    @Override
    public boolean isValid(List<MultipartFile> mutilpartFiles, ConstraintValidatorContext context) {
    	if(mutilpartFiles==null){
    		return true;
    	}
    	for(MultipartFile multipartFile : mutilpartFiles) {
	        String contentType = multipartFile.getContentType();
	        if(!isSupportedContentType(contentType)) {
	        	return false;
	        }
    	}
    	return true;
    }

    private boolean isSupportedContentType(String contentType) {
        return contentType.equals("image/png")
                || contentType.equals("image/jpg")
                || contentType.equals("image/jpeg")
                || contentType.equals("image/gif")
                || contentType.equals("image/tiff");
    }

}
