package cn.com.tass.chsmc.web.view;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.RedirectView;

/**
 * 标题: RedirectView扩展
 * <p>
 * 描述: 用于去掉重定向当没有Session的时候在URL后面添加jsessionID
 * <p>
 * 版权: Copyright (c) 2016
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author S
 * @created 2016-3-1 上午09:47:47
 * @version 1.0
 */
public class ExtendRedirectView extends RedirectView {
	
    public ExtendRedirectView(String url, boolean contextRelative) {
        super(url, contextRelative);
        
        
    }

    /** copied from @link{RedirectView#sendRedirect} and removed calls to
     * reponse.encodeRedirectURL()
     */
    protected void sendRedirect( HttpServletRequest request,
            HttpServletResponse response, String targetUrl,
            boolean http10Compatible ) throws IOException {
    	if(targetUrl.indexOf("toLogin?")>0)
    		targetUrl=targetUrl.substring(0,targetUrl.indexOf("toLogin?")+7);
        if (http10Compatible) {
            // Always send status code 302.
            response.sendRedirect(targetUrl);
        }
        else {
            // Correct HTTP status code is 303, in particular for POST requests.
            response.setStatus(303);
            response.setHeader("Location", targetUrl);
        }
    }
}
