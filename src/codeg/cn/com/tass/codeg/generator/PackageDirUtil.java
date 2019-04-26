package cn.com.tass.codeg.generator;

import cn.com.tass.codeg.ConfigConsts;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * 标题: 基于包名称代码目录辅助类
 * <p>
 * 描述: 基于包名称代码目录辅助类
 * <p>
 * 版权: Copyright (c) 2016
 * <p>
 * 公司:
 * <p>
 * 
 * @author 
 * @created 2016-3-4 下午02:15:32
 * @version 1.0
 */
public class PackageDirUtil {

	public static String getPackageControllerDir(String moduleName) throws Exception{
		String modelPath = ConfigConsts.SRC_BASEPATH + "." + moduleName + ".controller";
		try {
			String packPath=covertPackagePath(modelPath);
			makeDirs(packPath);
			return packPath;
		} catch (Exception e) {
			throw new Exception("根据模块名获取Model路径失败.");
		}
	}
	
	public static String getPackageServiceDir(String moduleName) throws Exception{
		String modelPath = ConfigConsts.SRC_BASEPATH + "." + moduleName + ".service";
		try {
			String packPath=covertPackagePath(modelPath);
			makeDirs(packPath);
			return packPath;
		} catch (Exception e) {
			throw new Exception("根据模块名获取Model路径失败.");
		}
	}
	
	public static String getPackageServiceImplDir(String moduleName) throws Exception{
		String modelPath = ConfigConsts.SRC_BASEPATH + "." + moduleName + ".service.impl";
		try {
			String packPath=covertPackagePath(modelPath);
			makeDirs(packPath);
			return packPath;
		} catch (Exception e) {
			throw new Exception("根据模块名获取Model路径失败.");
		}
	}
	
	public static String getPackageDaoDir(String moduleName) throws Exception{
		String modelPath = ConfigConsts.SRC_BASEPATH + "." + moduleName + ".dao";
		try {
			String packPath=covertPackagePath(modelPath);
			makeDirs(packPath);
			return packPath;
		} catch (Exception e) {
			throw new Exception("根据模块名获取Model路径失败.");
		}
	}
	
	public static String getPackageDaoImplDir(String moduleName) throws Exception{
		String modelPath = ConfigConsts.SRC_BASEPATH + "." + moduleName + ".dao.impl";
		try {
			String packPath=covertPackagePath(modelPath);
			makeDirs(packPath);
			return packPath;
		} catch (Exception e) {
			throw new Exception("根据模块名获取Model路径失败.");
		}
	}
	
	
	public static String getPackageModelDir(String moduleName) throws Exception {

		String modelPath = ConfigConsts.SRC_BASEPATH + "." + moduleName + ".model";
		try {
			String packPath=covertPackagePath(modelPath);
			makeDirs(packPath);
			return packPath;
		} catch (Exception e) {
			throw new Exception("根据模块名获取Model路径失败.");
		}
	}
	
	public static String getResourcesPath() throws Exception{
		
		try {
//			File file = new File(".");
//			String basePath = file.getCanonicalPath();
//			String packPath = basePath + "\\config\\resources";

			URL url=PackageDirUtil.class.getResource("/");
			String basePath=url.getFile();
			basePath=basePath.substring(0,basePath.indexOf("target/classes/"));
			String packPath = basePath+"src/main/resources/messages";

			makeDirs(packPath);
			return packPath;
		} catch (Exception e) {
			throw new Exception("资源文件路径.");
		}
	}
	
	public static String getViewsPath(String moduleName,String className) throws Exception{
		try {
//			File file = new File(".");
//			String basePath = file.getCanonicalPath();
//			String packPath = basePath + "\\WebRoot\\WEB-INF\\views\\"+moduleName+"\\"+className;

			URL url=PackageDirUtil.class.getResource("/");
			String basePath=url.getFile();
			basePath=basePath.substring(0,basePath.indexOf("target/classes/"));
			String packPath = basePath+"src/main/webapp/WEB-INF/views/" + moduleName+"/" + className;

			makeDirs(packPath);
			return packPath;
		} catch (Exception e) {
			throw new Exception("视图路径.");
		}
	}
	
	/**
	 * 创建目录
	 * @param dir
	 */
	private static  void makeDirs(String dir) {
		File file = new File(dir);

		if (!file.exists() && !file.isDirectory())
			file.mkdirs();
	}
	
	private static String covertPackagePath(String packageName) throws IOException{
		
		//File file = new File(".");
		//String basePath = file.getCanonicalPath();
		//String packPath = basePath + "\\src\\" + packageName.replace(".", "\\");

		URL url=PackageDirUtil.class.getResource("/");
		String basePath=url.getFile();
		basePath=basePath.substring(0,basePath.indexOf("target/classes/"));
		String packPath = basePath+"src/main/java/"+ packageName.replace(".", "\\");

		
		return packPath;
	}
}
