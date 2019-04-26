package cn.com.tass.chsmc.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class FFWebMvcConfigurer extends WebMvcConfigurerAdapter {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // TODO Auto-generated method stub
        registry.addViewController("/error").setViewName("404.html");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE); 
        super.addViewControllers(registry);    
    }
        
     @Override 
     public void configurePathMatch(PathMatchConfigurer configurer) { 
         configurer.setUseSuffixPatternMatch(false); 
         super.configurePathMatch(configurer); 
     }
     
     @Override
     public void addInterceptors(InterceptorRegistry registry) {
         
         registry.addInterceptor(new AccessTokenVerifyInterceptor())
                  .addPathPatterns("/**")
                  .excludePathPatterns("/access-token");
         
         super.addInterceptors(registry);
         
         System.out.println("开始开始咯。。。。");    
    } 
}