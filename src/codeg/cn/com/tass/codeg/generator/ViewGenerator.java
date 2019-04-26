package cn.com.tass.codeg.generator;

import java.io.File;
import java.io.FileWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.com.tass.codeg.db.AnalysisDB;
import cn.com.tass.codeg.model.ColumnInfo;
import cn.com.tass.codeg.model.TableInfo;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * 标题: 视图代码生成器
 * <p>
 * 描述: 视图代码生成器
 * <p>
 * 版权: Copyright (c) 2016
 * <p>
 * 公司: 
 * <p>
 * 
 * @author
 * @created 2016-3-7 下午03:26:53
 * @version 1.0
 */
public class ViewGenerator {

	public static void main(String[] args) {
		//genListPage("sys", "t_sys_log", "Log", "日志");
		//genListPage("sys", "t_sys_student", "Student", "学生");
		///genListPage2("sys", "t_sys_student", "Student", "学生");
	
		genAddPage("version", "t_hsm_version", "HsmVersion", "宿主机版本");
	}
	
	public static void genAddPage(String moduleName,String tableName,String className,String classComment){
		try {
			TableInfo tableInfo = AnalysisDB.getTableInfoByName(tableName);
			Map<String, Object> parasMap=new HashMap<String, Object>();
		
			parasMap.put("e_ModuleName", moduleName);
			parasMap.put("e_ClassName", className);
			parasMap.put("e_ClassComment", classComment);
			parasMap.put("e_SysTime", new Date());
			
			ColumnInfo priKeyCol=null;
			for (ColumnInfo column : tableInfo.getColumns()) {
				if(column.getExtra().equals("auto_increment")){
					priKeyCol=column;
					break;
				}
			}
			if(priKeyCol!=null)
				tableInfo.getColumns().remove(priKeyCol);
			
			parasMap.put("e_Tableinfo", tableInfo);
			
			Configuration configuration=new Configuration();
			configuration.setDirectoryForTemplateLoading(new File("src/cn/com/tass/codeg/ftl")) ;
			configuration.setObjectWrapper(new DefaultObjectWrapper());
		
			
			
			String newClassName = className.substring(0, 1).toLowerCase() + className.substring(1);
			String 	savaPath=PackageDirUtil.getViewsPath(moduleName,newClassName);
			savaPath=savaPath+"\\"+newClassName+"Add.jsp";
			FileWriter	out = new FileWriter(new File(savaPath)) ;
			
			Template template=configuration.getTemplate("Add.ftl");
			//template.process(parasMap,  new OutputStreamWriter(System.out));
			template.process(parasMap,  out);
			
			System.out.println("---------------"+newClassName+"Add.jsp--------------");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void genListPage(String moduleName,String tableName,String className,String classComment){
		try {
			TableInfo tableInfo = AnalysisDB.getTableInfoByName(tableName);
			Map<String, Object> parasMap=new HashMap<String, Object>();
		
			parasMap.put("e_ModuleName", moduleName);
			parasMap.put("e_ClassName", className);
			parasMap.put("e_ClassComment", classComment);
			parasMap.put("e_SysTime", new Date());
			
			ColumnInfo priKeyCol=null;
			for (ColumnInfo column : tableInfo.getColumns()) {
				if(column.getExtra().equals("auto_increment")){
					priKeyCol=column;
					break;
				}
			}
			if(priKeyCol!=null)
				tableInfo.getColumns().remove(priKeyCol);
			
			parasMap.put("e_Tableinfo", tableInfo);
			
			Configuration configuration=new Configuration();
			configuration.setDirectoryForTemplateLoading(new File("src/main/java/cn/com/tass/codeg/ftl")) ;
			configuration.setObjectWrapper(new DefaultObjectWrapper());
		
			
			
			String newClassName = className.substring(0, 1).toLowerCase() + className.substring(1);
			String 	savaPath=PackageDirUtil.getViewsPath(moduleName,newClassName);
			savaPath=savaPath+"\\"+newClassName+"List.jsp";
			FileWriter	out = new FileWriter(new File(savaPath)) ;
			
			Template template=configuration.getTemplate("view_list.ftl");
			//template.process(parasMap,  new OutputStreamWriter(System.out));
			template.process(parasMap,  out);
			
			System.out.println("---------------"+newClassName+"List.jsp--------------");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void genListPage2(String moduleName,String tableName,String className,String classComment){
		try {
			TableInfo tableInfo = AnalysisDB.getTableInfoByName(tableName);
			Map<String, Object> parasMap=new HashMap<String, Object>();
		
			parasMap.put("e_ModuleName", moduleName);
			parasMap.put("e_ClassName", className);
			parasMap.put("e_ClassComment", classComment);
			parasMap.put("e_SysTime", new Date());
			
			ColumnInfo priKeyCol=null;
			for (ColumnInfo column : tableInfo.getColumns()) {
				if(column.getExtra().equals("auto_increment")){
					priKeyCol=column;
					break;
				}
			}
			if(priKeyCol!=null)
				tableInfo.getColumns().remove(priKeyCol);
			
			parasMap.put("e_Tableinfo", tableInfo);
			
			Configuration configuration=new Configuration();
			configuration.setDirectoryForTemplateLoading(new File("src/cn/com/tass/codeg/ftl")) ;
			configuration.setObjectWrapper(new DefaultObjectWrapper());
		
			
			
			String newClassName = className.substring(0, 1).toLowerCase() + className.substring(1);
			String 	savaPath=PackageDirUtil.getViewsPath(moduleName,newClassName);
			savaPath=savaPath+"\\"+newClassName+"List2.jsp";
			FileWriter	out = new FileWriter(new File(savaPath)) ;
			
			Template template=configuration.getTemplate("view_list2.ftl");
			//template.process(parasMap,  new OutputStreamWriter(System.out));
			template.process(parasMap,  out);
			
			System.out.println("---------------"+newClassName+"List2.jsp--------------");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
