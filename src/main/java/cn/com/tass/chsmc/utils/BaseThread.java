package cn.com.tass.chsmc.utils;
/**
 * 描述: 线程扩展，自带Sleep功能
 * <p>
 * 版权: Copyright (c) 2016
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author 卢灿 [lucan@tass.com.cn]
 * @created 2016-10-24 上午11:09:32
 * @version 1.0
 */
public class BaseThread extends Thread{

	private final Object waitObj = new Object();

	protected void sleep(int second){
		
        synchronized (waitObj) {
            try {
                waitObj.wait(second * 1000);
            } catch (InterruptedException e) {
                //do noting
            }
        }
	}
	
	protected void usleep(int millsecond){
		
        synchronized (waitObj) {
            try {
                waitObj.wait(millsecond);
            } catch (InterruptedException e) {
                //do noting
            }
        }
	}
}
