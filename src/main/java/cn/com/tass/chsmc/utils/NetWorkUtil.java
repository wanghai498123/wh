package cn.com.tass.chsmc.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述: 网络工具辅助工具类
 * <p>
 * 版权: Copyright (c) 2016
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author 卢灿 [lucan@tass.com.cn]
 * @created 2016年10月17日 下午5:01:52
 * @version 1.0
 */
public class NetWorkUtil {
	
	private static final String newLine = System.getProperty("line.separator").toString();
	
	/**
	 * 检查某主机网络是否可达
	 * @param host 主机IP地址或域名 
	 * @return
	 */
	public static boolean isHostReachable(String host){
		
		return ping(host, 3, 2000);
	}
	
	/**
	 * PING主机，查看是否能够PING通
	 * @param ipAddress 主机地址
	 * @param pingTimes PING次数
	 * @param timeOut 超时时间
	 * @return
	 */
	public static boolean ping(String ipAddress, int pingTimes, int timeOut) {

		String pingCommand = "ping " + ipAddress + " -n " + pingTimes + " -w " + timeOut;

		//Linux Ping指令参数不一致
		if (!isWindows()) {
			pingCommand = "ping " + ipAddress + " -c " + pingTimes + " -w " + timeOut;
		}
		
		try {
			int connCnt = 0;
			ExecHelper execHelper = ExecHelper.execUsingShell(pingCommand);
			String pinResult = execHelper.getOutput();
			
			String[] lines = pinResult.split(newLine);
			for (String line : lines) {
				connCnt += getPingResult(line);
			}

			if (connCnt>0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 获取PING结果 1. windows:若line含有=18ms TTL=16字样,说明已经ping通,返回1,否則返回0. 2.
	 * linux:若line 含有ttl=64 time=0.039 ms，说明已经ping通，返回1,否则返回0
	 * 
	 * @param line
	 * @return
	 */
	private static int getPingResult(String line) {

		String regex ;
		if (isWindows()) {
			regex = "(\\d+ms)(\\s+)(TTL=\\d+)";
		} else {
			regex = "(ttl=\\d+)(\\s+)(.+ms)";
		}
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(line);
		while (matcher.find()) {
			return 1;
		}
		return 0;
	}

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
}
