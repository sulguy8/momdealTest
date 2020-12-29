package kr.co.momdeal.config;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class CrossConfig implements WebMvcConfigurer {


	@Resource
	private AuthInterceptor adminInterceptor;


	@Value("${allrowed.origins}")
	private String allrowedOrigins;
//	@Autowired
//	private SessionAdvice sessionAdvice;

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);

		String osName = System.getProperty("os.name");
		log.info("{}=>",osName);
		if(osName.toLowerCase().indexOf("window")!=-1) {
			config.addAllowedOrigin("http://localhost");
			config.addAllowedOrigin("http://localhost:4200");
			config.addAllowedOrigin("https://localhost");
			config.addAllowedOrigin("https://localhost:4200");
			config.addAllowedOrigin("http://localhost:8100");
			config.addAllowedOrigin("https://localhost:8100");
		}
		if(osName.toLowerCase().indexOf("mac")!=-1) {
			config.addAllowedOrigin("http://localhost");
			config.addAllowedOrigin("http://localhost:4200");
			config.addAllowedOrigin("https://localhost");
			config.addAllowedOrigin("https://localhost:4200");
			config.addAllowedOrigin("http://localhost:8100");
			config.addAllowedOrigin("https://localhost:8100");
		}
		String[] origins = allrowedOrigins.split(",");
		for(String origin:origins) {
			config.addAllowedOrigin(origin);
		}
		config.addAllowedHeader("*");
		config.addAllowedMethod("OPTIONS");
		config.addAllowedMethod("GET");
		config.addAllowedMethod("POST");
		config.addAllowedMethod("PUT");
		config.addAllowedMethod("DELETE");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(adminInterceptor)
		.excludePathPatterns("/**/*.css","/**/*.js")
		.excludePathPatterns("/test")
		.addPathPatterns("/**"); 
		
		WebMvcConfigurer.super.addInterceptors(registry);
		
			//	.excludePathPatterns("/**"); // pattern 매핑, pattern 제외
		/*
		 * registry.addInterceptor(sessionAdvice).addPathPatterns("/**").
		 * excludePathPatterns("/all/**")
		 * .excludePathPatterns("/login").excludePathPatterns("/sign/**").
		 * excludePathPatterns("/sign")
		 * .excludePathPatterns("/admini").excludePathPatterns("/admin").
		 * excludePathPatterns("/admin/**");
		 */ 
	}

}
