package cn.com.tass.codeg.generator;

import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * 标题: 服务代码生成器
 * <p>
 * 描述: 服务代码生成器
 * <p>
 * 版权: Copyright (c) 2016
 * <p>
 * 公司: 
 * <p>
 * 
 * @author 
 * @created 2016-3-4 下午03:54:54
 * @version 1.0
 */
public class ServiceGenerator {

public static void main(String[] args) {
		
	genServiceCode("sys", "User", "用户");
	}
	
	public static void genServiceCode(String moduleName,String className,String classComment){
		genServiceInterface(moduleName,className, classComment);
		genServiceImpl(moduleName,className, classComment);
	}
	
	public static void genServiceInterface(String moduleName,String className,String classComment){
		
		try {
			Map<String, Object> parasMap=new HashMap<String, Object>();
			parasMap.put("e_ModuleName", moduleName);
			parasMap.put("e_ClassName", className);
			parasMap.put("e_ClassComment", classComment);
			parasMap.put("e_SysTime", new Date());
			
			Configuration configuration=new Configuration();
			URL url=ServiceGenerator.class.getResource("/");
			String path=url.getFile();
			path=path.substring(0,path.indexOf("target/classes/"));
			path=path+"src/main/java/cn/com/tass/codeg/ftl";
			configuration.setDirectoryForTemplateLoading(new File(path));
			//configuration.setDirectoryForTemplateLoading(new File("src/cn/com/tass/codeg/ftl")) ;
			configuration.setObjectWrapper(new DefaultObjectWrapper());
		
			String 	savaPath=PackageDirUtil.getPackageServiceDir(moduleName);
			savaPath=savaPath+"\\"+className+"Service.java";
			FileWriter	out = new FileWriter(new File(savaPath)) ;
			
			Template template=configuration.getTemplate("service_interface.ftl");
			template.process(parasMap,  out);
			
			System.out.println("---------------"+className+"Service.java--------------");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void genServiceImpl(String moduleName,String className,String classComment){
		
		try {
			Map<String, Object> parasMap=new HashMap<String, Object>();
			parasMap.put("e_ModuleName", moduleName);
			parasMap.put("e_ClassName", className);
			parasMap.put("e_ClassComment", classComment);
			parasMap.put("e_SysTime", new Date());
			
			Configuration configuration=new Configuration();
			URL url=ServiceGenerator.class.getResource("/");
			String path=url.getFile();
			path=path.substring(0,path.indexOf("target/classes/"));
			path=path+"src/main/java/cn/com/tass/codeg/ftl";
			configuration.setDirectoryForTemplateLoading(new File(path));
			//configuration.setDirectoryForTemplateLoading(new File("src/cn/com/tass/codeg/ftl")) ;
			configuration.setObjectWrapper(new DefaultObjectWrapper());
		
			String 	savaPath=PackageDirUtil.getPackageServiceImplDir(moduleName);
			savaPath=savaPath+"\\"+className+"ServiceImpl.java";
			FileWriter	out = new FileWriter(new File(savaPath)) ;
			
			Template template=configuration.getTemplate("service_impl.ftl");
			template.process(parasMap,  out);
			
			System.out.println("---------------"+className+"ServiceImpl.java--------------");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
