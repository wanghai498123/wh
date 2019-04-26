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
 * 标题: DAO层代码生成器
 * <p>
 * 描述: DAO层代码生成器
 * <p>
 * 版权: Copyright (c) 2016
 * <p>
 * 公司:
 * <p>
 * 
 * @author 
 * @created 2016-3-4 下午03:32:50
 * @version 1.0
 */
public class DaoGenerator {

	public static void main(String[] args) {
		
		genDaoCode("sys", "User", "用户");
	}
	
	public static void genDaoCode(String moduleName,String className,String classComment){
		genDaoInterface(moduleName,className, classComment);
		genDaoImpl(moduleName,className, classComment);
	}
	
	public static void genDaoInterface(String moduleName,String className,String classComment){
		
		try {
			Map<String, Object> parasMap=new HashMap<String, Object>();
			parasMap.put("e_ModuleName", moduleName);
			parasMap.put("e_ClassName", className);
			parasMap.put("e_ClassComment", classComment);
			parasMap.put("e_SysTime", new Date());
			
			Configuration configuration=new Configuration();
			URL url=DaoGenerator.class.getResource("/");
			String path=url.getFile();
			path=path.substring(0,path.indexOf("target/classes/"));
			path=path+"src/main/java/cn/com/tass/codeg/ftl";
			configuration.setDirectoryForTemplateLoading(new File(path));
			//configuration.setDirectoryForTemplateLoading(new File("src/cn/com/tass/codeg/ftl")) ;
			configuration.setObjectWrapper(new DefaultObjectWrapper());
		
			String 	savaPath=PackageDirUtil.getPackageDaoDir(moduleName);
			savaPath=savaPath+"\\"+className+"Dao.java";
			FileWriter	out = new FileWriter(new File(savaPath)) ;
			
			Template template=configuration.getTemplate("dao_interface.ftl");
			template.process(parasMap,  out);
			
			System.out.println("---------------"+className+"Dao.java--------------");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void genDaoImpl(String moduleName,String className,String classComment){
		
		try {
			Map<String, Object> parasMap=new HashMap<String, Object>();
			parasMap.put("e_ModuleName", moduleName);
			parasMap.put("e_ClassName", className);
			parasMap.put("e_ClassComment", classComment);
			parasMap.put("e_SysTime", new Date());
			
			Configuration configuration=new Configuration();
			URL url=DaoGenerator.class.getResource("/");
			String path=url.getFile();
			path=path.substring(0,path.indexOf("target/classes/"));
			path=path+"src/main/java/cn/com/tass/codeg/ftl";
			configuration.setDirectoryForTemplateLoading(new File(path));
			//configuration.setDirectoryForTemplateLoading(new File("src/cn/com/tass/codeg/ftl")) ;
			configuration.setObjectWrapper(new DefaultObjectWrapper());
		
			String 	savaPath=PackageDirUtil.getPackageDaoImplDir(moduleName);
			savaPath=savaPath+"\\"+className+"DaoImpl.java";
			FileWriter	out = new FileWriter(new File(savaPath)) ;
			
			Template template=configuration.getTemplate("dao_impl.ftl");
			template.process(parasMap,  out);
			
			System.out.println("---------------"+className+"DaoImpl.java--------------");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
