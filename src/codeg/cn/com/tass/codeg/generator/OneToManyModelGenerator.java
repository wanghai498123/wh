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
 * 标题: 一对多实体类生成器
 * <p>
 * 描述: 一对多实体类生成器
 * <p>
 * 版权: Copyright (c) 2016
 * <p>
 * 公司: 
 * <p>
 * 
 * @author 
 * @created 2016-3-4 下午03:07:52
 * @version 1.0
 */
public class OneToManyModelGenerator {

	public static void main(String[] args) {
		
		genOnetoManyEntity("sys","t_sys_usergroup","UserGroup","t_sys_user","User");
	}

	/**
	 * 生成一对多实体类
	 * @param moduleName 实体类对应的模块名
	 * @param oneTableName One端对应的表名
	 * @param oneClassName One端对应的类名
	 * @param manyTableName Many端对应的表名
	 * @param manyClassName Many端对应的类名
	 */
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
			configuration.setDirectoryForTemplateLoading(new File("src/cn/com/tass/codeg/ftl")) ;
			configuration.setObjectWrapper(new DefaultObjectWrapper());
		
			String 	savaPath=PackageDirUtil.getPackageModelDir(moduleName);
			savaPath=savaPath+"\\"+oneClassName+".java";
			FileWriter	out = new FileWriter(new File(savaPath)) ;
			
			Template template=configuration.getTemplate("one_many_entity.ftl");
			template.process(parasMap,  out);
			
			System.out.println("---------------生成"+oneClassName+"实体类成功------------");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void genManyEndEntity(String moduleName,TableInfo manyTableInfo,String oneClassName,String manyClassName,String joinColumn){
		
		try {
			//Many表实体对象
			Map<String, Object> parasMap=new HashMap<String, Object>();
			parasMap.put("e_bOneType", false);
			parasMap.put("e_moduleName", moduleName);
			parasMap.put("e_oneClassName", oneClassName);
			parasMap.put("e_manyClassName", manyClassName);
			parasMap.put("e_tableinfo", manyTableInfo);
			parasMap.put("e_joinColumn", joinColumn);
			parasMap.put("e_sysTime", new Date());
			
			Configuration configuration=new Configuration();
			configuration.setDirectoryForTemplateLoading(new File("src/cn/com/tass/codeg/ftl")) ;
			configuration.setObjectWrapper(new DefaultObjectWrapper());
		
			String 	savaPath=PackageDirUtil.getPackageModelDir(moduleName);
			savaPath=savaPath+"\\"+manyClassName+".java";
			FileWriter	out = new FileWriter(new File(savaPath)) ;
			
			Template template=configuration.getTemplate("one_many_entity.ftl");
			template.process(parasMap,  out);
			
			System.out.println("---------------生成"+manyClassName+"实体类成功------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
