package cn.com.tass.chsmc.utils;


/**
 * 描述: 修复Centos 6 Sleep函数修改系统时间不正常的BUG
 * <p>
 * 版权: Copyright (c) 2016
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author 卢灿 [lucan@tass.com.cn]
 * @created 2016-10-26 上午11:39:43
 * @version 1.0
 */
public class SleepUtil {

	/**
	 * 是否WINDOWS操作系统
	 * 
	 * @return
	 */
	private static boolean isWindows() {
		String osName = System.getProperty("os.name");
		if (osName.indexOf("Windows") >= 0)
			return true;
		else
			return false;
	}
	
	public static void sleep(int sencond){
		try {
			if(isWindows()){
				Thread.sleep(sencond*1000);
			}else {
				String cmd = "sleep " + String.valueOf(sencond);
				Runtime.getRuntime().exec(cmd).waitFor();
			}
		} catch (Exception e) {
			//do noting
		}
	}
	
	public static void msleep(int mSencond){
		try {
			if(isWindows()){
				Thread.sleep(mSencond);
			}else {
				float second = ((float) mSencond) / (float) 1000;
				String cmd = "sleep " + String.valueOf(second);
				Runtime.getRuntime().exec(cmd).waitFor();
			}
		} catch (Exception e) {
			//do noting
		}
	}
}
