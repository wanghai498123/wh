package cn.com.tass.codeg.generator;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.com.tass.codeg.db.AnalysisDB;
import cn.com.tass.codeg.model.ColumnInfo;
import cn.com.tass.codeg.model.TableInfo;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 标题:
 * <p>
 * 描述:
 * <p>
 * 版权: Copyright (c) 2016
 * <p>
 * 公司: 
 * <p>
 * 
 * @author 
 * @created 2016-3-3 上午11:46:49
 * @version 1.0
 */
public class ModelGenerator {

	public static void main(String[] args) throws IOException, TemplateException {

//		TableInfo tableInfo = AnalysisDB.getTableInfoByName("t_sys_log");
//		for (ColumnInfo columnInfo : tableInfo.getColumns()) {
//			//System.out.println(columnInfo.getColumnName());
//		}
//
//		
//		
//		Map<String, Object> parasMap=new HashMap<String, Object>();
//		parasMap.put("moduleName", "sysmanage");
//		parasMap.put("className", "Log");
//		parasMap.put("tableinfo", tableInfo);
//		parasMap.put("sysTime", new Date());
//		
//		Configuration configuration=new Configuration();
//		configuration.setDirectoryForTemplateLoading(new File("src/ftl")) ;
//		configuration.setObjectWrapper(new DefaultObjectWrapper());
//	
//		Template template=configuration.getTemplate("entity.ftl");
//		template.process(parasMap,  new OutputStreamWriter(System.out));
		
		//genSingleEntity("t_sys_log", "systemmanage", "Log");
		//genOnetoManyEntity("sys","t_sys_usergroup","UserGroup","t_sys_user","User");
		
		//makePackageDir("cn.com.tass.chsm.modules.sys2.model");
		
	}
	
	
	/**
	 * 生成单表的实体对象
	 * @param tableName
	 * @param moduleName
	 * @param className
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
			configuration.setDirectoryForTemplateLoading(new File("src/ftl")) ;
			configuration.setObjectWrapper(new DefaultObjectWrapper());
		
			Template template=configuration.getTemplate("single_entity.ftl");
			template.process(parasMap,  new OutputStreamWriter(System.out));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void genOnetoManyEntity(String moduleName,String oneTableName,String oneClassName,String manyTableName,String manyClassName){
		
		try {
			TableInfo oneTableInfo = AnalysisDB.getTableInfoByName(oneTableName);
			TableInfo manyTableInfo = AnalysisDB.getTableInfoByName(manyTableName);
			
			//获取joinColumnName
			String joinColumnName="";
			boolean findJoinColumn=false;
			for (ColumnInfo columnInfo : oneTableInfo.getColumns()) {
				if(columnInfo.getExtra().toLowerCase().equals("auto_increment"))
				{
					joinColumnName=columnInfo.getColumnName();
					break;
				}
			}
			for (ColumnInfo columnInfo : manyTableInfo.getColumns()) {
				if(columnInfo.getColumnName().equals(joinColumnName)){
					findJoinColumn=true;
					break;
				}
			}
			if(!findJoinColumn)
				throw new Exception("未找到匹配的Joincolumn字段");
				
			genOneEndEntity(moduleName, oneTableInfo, oneClassName, manyClassName,joinColumnName);
			genManyEndEntity(moduleName,manyTableInfo,oneClassName,manyClassName,joinColumnName);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void genOneEndEntity(String moduleName,TableInfo oneTableInfo,String oneClassName,String manyClassName,String joinColumn){
		try {

			//One表实体对象
			Map<String, Object> parasMap=new HashMap<String, Object>();
			parasMap.put("e_bOneType", true);
			parasMap.put("e_moduleName", moduleName);
			parasMap.put("e_oneclassName", oneClassName);
			parasMap.put("e_manyClassName", manyClassName);
			parasMap.put("e_tableinfo", oneTableInfo);
			parasMap.put("e_joinColumn", joinColumn);
			parasMap.put("e_sysTime", new Date());
			
			Configuration configuration=new Configuration();
			configuration.setDirectoryForTemplateLoading(new File("src/ftl")) ;
			configuration.setObjectWrapper(new DefaultObjectWrapper());
		
			Template template=configuration.getTemplate("one_many_entity.ftl");
			template.process(parasMap,  new OutputStreamWriter(System.out));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void genManyEndEntity(String moduleName,TableInfo manyTableInfo,String oneClassName,String manyClassName,String joinColumn){
		
		try {
			//One表实体对象
			Map<String, Object> parasMap=new HashMap<String, Object>();
			parasMap.put("e_bOneType", false);
			parasMap.put("e_moduleName", moduleName);
			parasMap.put("e_oneClassName", oneClassName);
			parasMap.put("e_manyClassName", manyClassName);
			parasMap.put("e_tableinfo", manyTableInfo);
			parasMap.put("e_joinColumn", joinColumn);
			parasMap.put("e_sysTime", new Date());
			
			Configuration configuration=new Configuration();
			configuration.setDirectoryForTemplateLoading(new File("src/ftl")) ;
			configuration.setObjectWrapper(new DefaultObjectWrapper());
		
			Template template=configuration.getTemplate("one_many_entity.ftl");
			template.process(parasMap,  new OutputStreamWriter(System.out));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@SuppressWarnings("unused")
	private static void makePackageDir(String packName){
		
		try {
			File file=new File(".");
			String basePath=file.getCanonicalPath();
			String packPath=basePath+"\\src\\"+packName.replace(".", "\\");
			System.out.println(packPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
			//System.out.println(file.getCanonicalPath());
//			File file =new File("D:\\Users\\QPING\\Desktop\\JavaScript");    
//			//如果文件夹不存在则创建    
//			if  (!file .exists()  && !file .isDirectory())      
//			{       
//			    System.out.println("//不存在");  
//			    file.mkdirs();    
//			} else   
//			{  
//			    System.out.println("//目录存在");  
//			}  
		
		
	}
}
