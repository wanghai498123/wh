package cn.com.tass.codeg.generator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Properties;
import cn.com.tass.codeg.db.AnalysisDB;
import cn.com.tass.codeg.model.ColumnInfo;
import cn.com.tass.codeg.model.TableInfo;

/**
 * 标题: 资源文件生成器
 * <p>
 * 描述: 资源文件生成器
 * <p>
 * 版权: Copyright (c) 2016
 * <p>
 * 公司: 
 * <p>
 * 
 * @author
 * @created 2016-3-7 下午01:59:09
 * @version 1.0
 */
public class ResourceFileGenerator {

	public static void main(String[] args) {

		//genCNResouceCode("userManage", "t_sys_role", "Role", "角色");
		//genEnResouceCode("userManage", "t_sys_role", "Role", "角色");
		genResouceCode("drift", "t_vsm_drift_except", "DriftExcept","VSM异常表");
	}
	
	public static void genResouceCode(String moduleName, String tableName, String className, String classComment){
		genCNResouceCode(moduleName, tableName, className, classComment);
		genEnResouceCode(moduleName, tableName, className, classComment);
	}

	public static void genCNResouceCode(String moduleName, String tableName, String className, String classComment) {

		try {
			FileOutputStream fOutputStream = null;
			TableInfo tableInfo = AnalysisDB.getTableInfoByName(tableName);
			String resourcesPath = PackageDirUtil.getResourcesPath();
			OrderedProperties properties = new OrderedProperties();
			File infile = new File(resourcesPath + "\\" + moduleName + "_message_zh_CN.txt");
			if (infile.exists() && infile.isFile()) {
				FileInputStream fInputStream = null;
				fInputStream = new FileInputStream(infile);
				//BufferedReader bf = new BufferedReader(new InputStreamReader(fInputStream, "utf-8"));
				properties.load(fInputStream);
				fInputStream.close();
			}

			setZh_CNPropeties(properties, moduleName, className, classComment);
			setFieldPropeties(properties,moduleName, className,tableInfo,true);
			
			File outfile = new File(resourcesPath + "\\" + moduleName + "_message_zh_CN.txt");

			fOutputStream = new FileOutputStream(outfile);
			//BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fOutputStream, "utf-8"));
			properties.store(fOutputStream, null);
			fOutputStream.flush();
			fOutputStream.close();

			System.out.println("---------------" + moduleName + "_message_zh_CN.txt--------------");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void genEnResouceCode(String moduleName, String tableName, String className, String classComment) {

		try {
			FileOutputStream fOutputStream = null;
			TableInfo tableInfo = AnalysisDB.getTableInfoByName(tableName);
			String resourcesPath = PackageDirUtil.getResourcesPath();
			OrderedProperties properties = new OrderedProperties();
			File infile = new File(resourcesPath + "\\" + moduleName + "_message_en_US.txt");
			if (infile.exists() && infile.isFile()) {
				FileInputStream fInputStream = null;
				fInputStream = new FileInputStream(infile);
				//BufferedReader bf = new BufferedReader(new InputStreamReader(fInputStream, "utf-8"));
				properties.load(fInputStream);
				fInputStream.close();
			}

			setEnPropeties(properties, moduleName, className, classComment);
			setFieldPropeties(properties,moduleName, className,tableInfo,false);
			File outfile = new File(resourcesPath + "\\" + moduleName + "_message_en_US.txt");

			fOutputStream = new FileOutputStream(outfile);
			//BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fOutputStream, "utf-8"));
			properties.store(fOutputStream, null);
			fOutputStream.flush();
			fOutputStream.close();

			System.out.println("---------------" + moduleName + "_message_en_US.txt--------------");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void setZh_CNPropeties(Properties properties, String moduleName, String className,
			String classComment) {

		String newClassName = className.substring(0, 1).toLowerCase() + className.substring(1);
		String addKey = moduleName + "." + newClassName + ".add";
		String addKeyComment = "新增" + classComment;

		String editKey = moduleName + "." + newClassName + ".edit";
		String editKeyComment = "修改" + classComment;

		String delKey = moduleName + "." + newClassName + ".delete";
		String delKeyComment = "删除" + classComment;

		properties.setProperty(addKey, addKeyComment);
		properties.setProperty(editKey, editKeyComment);
		properties.setProperty(delKey, delKeyComment);
		
		properties.setProperty(moduleName + "." + newClassName+"."+newClassName+"List", classComment+"列表");
		properties.setProperty(moduleName + "." + newClassName+".RecordExist", newClassName+"已存在");
	}

	private static void setEnPropeties(Properties properties, String moduleName, String className, String classComment) {

		String newClassName = className.substring(0, 1).toLowerCase() + className.substring(1);
		String addKey = moduleName + "." + newClassName + ".add";
		String addKeyComment = "add" + className;

		String editKey = moduleName + "." + newClassName + ".edit";
		String editKeyComment = "update" + className;

		String delKey = moduleName + "." + newClassName + ".delete";
		String delKeyComment = "delete" + className;

		properties.setProperty(addKey, addKeyComment);
		properties.setProperty(editKey, editKeyComment);
		properties.setProperty(delKey, delKeyComment);
		
		properties.setProperty(moduleName + "." + newClassName+"."+newClassName+"List", className+"List");
		properties.setProperty(moduleName + "." + newClassName+".RecordExist", newClassName+"Exist");
	}
	
	private static void setFieldPropeties(Properties properties,String moduleName, String className,TableInfo tableInfo,boolean isCn){
		
		String newClassName = className.substring(0, 1).toLowerCase() + className.substring(1);
		for (ColumnInfo colInfo : tableInfo.getColumns()) {
			String key=moduleName + "." + newClassName +"."+colInfo.getColumnName();
			if(isCn){
				properties.setProperty(key, colInfo.getColumnComment());
			}else {
				properties.setProperty(key, colInfo.getColumnName());
			}
		}
	}
}
