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
 * 标题: 控制器代码生成器
 * <p>
 * 描述: 控制器代码生成器
 * <p>
 * 版权: Copyright (c) 2016
 * <p>
 * 公司:
 * <p>
 * 
 * @created 2016-3-4 下午05:42:56
 * @version 1.0
 */
public class ControllerGenerator {

	public static void main(String[] args) {
		
			genControllerCode("sys", "User", "用户");
		}
		

		public static void genControllerCode(String moduleName,String className,String classComment){
			
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
			
				String 	savaPath=PackageDirUtil.getPackageControllerDir(moduleName);
				savaPath=savaPath+"\\"+className+"Controller.java";
				FileWriter	out = new FileWriter(new File(savaPath)) ;
				
				Template template=configuration.getTemplate("controller.ftl");
				template.process(parasMap,  out);
				
				System.out.println("---------------"+className+"Controller.java--------------");
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
}
