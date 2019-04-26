package cn.com.tass.chsmc.modules.common.controller;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;

import cn.com.tass.chsmc.constant.CommonConst;
import cn.com.tass.chsmc.constant.SessionConst;
import cn.com.tass.chsmc.modules.system.model.User;
import cn.com.tass.chsmc.utils.CSVUtil;
import cn.com.tass.chsmc.utils.ExcelUtil;
import cn.com.tass.chsmc.utils.PDFExportUtils;
import cn.com.tass.chsmc.web.DateEditor;

/**
 * 标题: Controller基类
 * <p>
 * 描述: Controller基类
 * <p>
 * 版权: Copyright (c) 2015
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author 卢灿 [lucan@tass.com.cn]
 * @created 2016-2-15 下午02:49:58
 * @version 1.0
 */
public class BaseController {

	@Autowired
	MessageSource messageSource;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder){
		
		//对于需要转换为Date类型的属性，使用DateEditor进行处理  
	    binder.registerCustomEditor(Date.class, new DateEditor()); 
	}
	
	/**
	 * 获取当前Language的资源名称
	 * @param textName  资源名称
	 * @param locale  当前语言
	 * @return
	 */
	protected String getResourceText(String textName){
		
		return messageSource.getMessage(textName, null, LocaleContextHolder.getLocale());
	}
	
	/**
	 * 获取请求
	 * @return
	 */
	protected HttpServletRequest getRequest(){
		
		 HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		 
		 return request;
	}
	
	/**
	 * 获取Session
	 * @return
	 */
	protected HttpSession getSession(){
		
		return getRequest().getSession();
	}
	
	/**
	 * 获取当前用户
	 * @return User
	 */
	public User getLoginUser() {
		return (User) getSession().getAttribute(SessionConst.UserInfo);
	}
	
	/**
	 * 获取当前WEB发布的物理路径
	 * @return
	 */
	protected String getWebContextRealPath(){
		
		return System.getProperty(CommonConst.WEB_ROOT);
	}

	/**
	 * HTTP请求获取第一个文件
	 * 
	 * @param request
	 * @return
	 */
	protected MultipartFile getFirstMultipartFile(HttpServletRequest request) {

		String fileName = "";
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		if (multipartRequest != null) {
			Iterator<String> iterator = multipartRequest.getFileNames();
			while (iterator.hasNext()) {
				fileName = iterator.next();
				break;
			}
		}
		if (fileName != null) {
			return multipartRequest.getFile(fileName);
		} else {
			return null;
		}
	}
	
	/**
	 * 是否存在附件
	 * @param request
	 * @return
	 */
	protected boolean isExistMultipartFile(HttpServletRequest request,String fieldName){
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
		if(multipartRequest!=null){
			MultipartFile upgradeFile = multipartRequest.getFile(fieldName);  
			if(upgradeFile!=null&&upgradeFile.getSize()>0)
				return true;
			else {
				return false;
			}
		}else {
			return false;
		}
	}
	
	/**
	 * 获取下载的文件名称
	 * @param request
	 * @param fileNames
	 * @return
	 */
	protected  String getAttachFileName(HttpServletRequest request, String fileNames) {
		String encodeFileName = fileNames;
		try {
			String agent = request.getHeader("USER-AGENT");
			if (null != agent && -1 != agent.indexOf("MSIE") || null != agent && -1 != agent.indexOf("Trident")) {// ie
				encodeFileName = URLEncoder.encode(fileNames, "UTF8");

			} else if (null != agent && -1 != agent.indexOf("Mozilla")) {// 火狐,chrome等

				encodeFileName = new String(fileNames.getBytes("UTF-8"), "iso-8859-1");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encodeFileName;
	}
	
	/**
	 * 将文件写入下载流
	 * @param fileName
	 * @param response
	 */
	protected void writeDownloadFile(String fileName,String filePath,HttpServletRequest request,HttpServletResponse response){
		
		String encodedfileName =getAttachFileName(request, fileName);
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName=" + encodedfileName);
		InputStream inputStream =null;
		try {
			inputStream = new FileInputStream(filePath);
			OutputStream os = response.getOutputStream();
			byte[] b = new byte[1024];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}
			os.flush();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally{
			if(inputStream!=null){
				try {
					inputStream.close();
				} catch (Exception e2) {}
			}
		}
	}
	
	/**
	 * 通用表格导出
	 * @param exportType 导出类型
	 * @param headerTitle 标题
	 * @param fileName 导出文件名
	 * @param titleList 标题列表
	 * @param keyList 对应键列表
	 * @param valueMaps 数据MAPS
	 * @param request HTTP请求
	 * @param response HTTP应答
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected void commonExport(Integer exportType, final String headerTitle, String fileName, List<String> titleList,
			List<String> keyList, final List<Map> valueMaps, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		final String[] title = new String[titleList.size()];
		final String[] keys = new String[keyList.size()];
		titleList.toArray(title);
		keyList.toArray(keys);

		if (exportType == 1) {
			ExcelUtil.export(fileName + ".xls", title, keys, valueMaps, response, request);
		} else if (exportType == 2 || exportType == 3) {// 导出pdf或者word
			PDFExportUtils fex = new PDFExportUtils() {
				@Override
				public void fillPDFData(Document doc) throws Exception {
					doc.open();
					Font headerFont = PDFExportUtils.getChineseFont(14, false, true, Color.black);
					Paragraph paragraph = new Paragraph(headerTitle, headerFont);
					paragraph.setAlignment("center");
					paragraph.setSpacingAfter(10);
					doc.add(paragraph);

					Font bodyFont = PDFExportUtils.getChineseFont(12, false, false, Color.black);
					PdfPTable pdfPTable = PDFExportUtils.getPdfTable(title, null, keys, valueMaps, headerFont,bodyFont, null);
					doc.add(pdfPTable);
					doc.close();
				}
			};

			if (exportType == 2) {
				fex.exportPDF(fileName + ".pdf", response, request, PDFExportUtils.EXPORT_TYPE_PDF, false);
			} else if (exportType == 3) {
				fex.exportPDF(fileName + ".doc", response, request, PDFExportUtils.EXPORT_TYPE_RTF, false);
			}
		} else {
			CSVUtil.export(fileName, title, keys, valueMaps, response, request);
		}
	}
}
