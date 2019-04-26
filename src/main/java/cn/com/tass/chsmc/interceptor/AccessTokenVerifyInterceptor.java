package cn.com.tass.chsmc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class AccessTokenVerifyInterceptor extends BaseInterceptor {
    
    private Logger logger = LoggerFactory.getLogger(AccessTokenVerifyInterceptor.class);
    
    //@Autowired
    //private FFAccessTokenService tokenService;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // TODO Auto-generated method stub
        
        logger.info("AccessToken executing ...");
        logger.error("232134");
        return true;    
    }
}