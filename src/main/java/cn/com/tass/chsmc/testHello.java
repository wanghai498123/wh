package cn.com.tass.chsmc;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class testHello {
	    @RequestMapping("/hello")
	    public String hello(){
	        return "Greetings from Spring Boot!";
	    }
}
