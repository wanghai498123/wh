package cn.com.tass.codeg.generator;

import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.com.tass.codeg.db.AnalysisDB;
import cn.com.tass.codeg.model.TableInfo;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * 标题: 单表实体对象生成器
 * <p>
 * 描述: 单表实体对象生成器
 * <p>
 * 版权: Copyright (c) 2016
 * <p>
 * 公司: 
 * <p>
 * 
 * @author
 * @created 2016-3-4 下午02:14:55
 * @version 1.0
 */
public class SingleModelGenerator {

	public static void main(String[] args) {

		genSingleEntity("drift", "t_vsm_drift_except", "DriftExcept");
	}
	
	/**
	 * /**
	 * 生成单表的实体对象
	 * @param moduleName 对应实体类的模块名
	 * @param tableName  对应实体类的表明
	 * @param className  对应实体类的类名
	 */
	public static void genSingleEntity(String moduleName,String tableName,String className){
		
		try {
			TableInfo tableInfo = AnalysisDB.getTableInfoByName(tableName);
			Map<String, Object> parasMap=new HashMap<String, Object>();
			parasMap.put("moduleName", moduleName);
			parasMap.put("className", className);
			parasMap.put("tableinfo", tableInfo);
			parasMap.put("sysTime", new Date());
			
			Configuration configuration=new Configuration();
			URL url=SingleModelGenerator.class.getResource("/");
			String path=url.getFile();
			path=path.substring(0,path.indexOf("target/classes/"));
			path=path+"src/main/java/cn/com/tass/codeg/ftl";
			configuration.setDirectoryForTemplateLoading(new File(path)) ;
			configuration.setObjectWrapper(new DefaultObjectWrapper());
		
			String 	savaPath=PackageDirUtil.getPackageModelDir(moduleName);
			savaPath=savaPath+"\\"+className+".java";
			FileWriter	out = new FileWriter(new File(savaPath)) ;
			
			Template template=configuration.getTemplate("single_entity.ftl");
			template.process(parasMap,  out);

			System.out.println("---------------"+className+".java--------------");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
