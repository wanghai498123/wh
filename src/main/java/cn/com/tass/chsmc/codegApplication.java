package cn.com.tass.chsmc;

import java.io.FileNotFoundException;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@SpringBootApplication//(exclude = {DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
public class codegApplication {
	private static ConfigurableApplicationContext context;
	private static String[] args;	
	
	public static ConfigurableApplicationContext getContext() {
		return context;
	}

	public static void setContext(ConfigurableApplicationContext context) {
		codegApplication.context = context;
	}

	public static String[] getArgs() {
		return args;
	}

	public static void setArgs(String[] args) {
		codegApplication.args = args;
	}

	public static void main(String[] args) throws FileNotFoundException {
		ConfigurableApplicationContext context=SpringApplication.run(codegApplication.class, args);
	}
	
	private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }     
    
    /**
     * 配置上传文件大小的配置
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
       MultipartConfigFactory factory = new MultipartConfigFactory();
       //  单个数据大小
       factory.setMaxFileSize("102400KB");
       /// 总上传数据大小
       factory.setMaxRequestSize("102400KB");
       return factory.createMultipartConfig();
    }
    
}