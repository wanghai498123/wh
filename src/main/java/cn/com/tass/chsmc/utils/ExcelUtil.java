package cn.com.tass.chsmc.utils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * </br>  
 * 类  名   称：ExcelUtil   
 * </br> 
 * 类  描   述：Execel导出工具类				
 * </br>
 * 创  建   人：zhaoweiwei  
 * </br>  	
 * 创建时间：2013-5-12 下午3:54:24    
 * </br>  
 * 修  改   人：zhaoweiwei   	
 * </br>   
 * 修改时间：2013-5-12 下午3:54:24    
 * </br>  
 * 修改备注：    
 * </br>  
 * @version  v0.1
 *
 */
public class ExcelUtil {
	/**
	 * *
	 * 
	 * @param filename
	 *            保存到客户端的文件名
	 * @param title
	 *            标题行 例：String[]{"名称","地址"}
	 * @param key
	 *            从查询结果List取得的MAP的KEY顺序，需要和title顺序匹配，例：String[]{"name","address"
	 *            }
	 * @param values
	 *            结果集
	 * @param httpServletResponse
	 * @throws IOException
	 */
	@SuppressWarnings({  "deprecation", "unchecked" })
	public static void export(String filename, String[] title, String[] key,
			List<Map> values, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest)
			throws IOException {
		String filename2 = "";

		String agent = httpServletRequest.getHeader("User-Agent");
		boolean isMSIE = (agent != null && agent.toLowerCase().matches(".*(msie\\s|trident.*rv:)([\\w.]+).*"));
		if (isMSIE) {
			filename2 = URLEncoder.encode(filename, "UTF8");
		} else {
			filename2 = new String(filename.getBytes(), "iso-8859-1");
		}

		ServletOutputStream servletOutputStream = httpServletResponse
				.getOutputStream();
		HSSFWorkbook workbook = null;
		httpServletResponse.setHeader("Content-disposition",
				"attachment; filename=\"" + filename2 + "\"");
		httpServletResponse.setContentType("application/x-download");
		workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		HSSFRow row = null;
		HSSFCell cell = null;
		row = sheet.createRow((short) 0);
		for (int i = 0; title != null && i < title.length; i++) {
			cell = row.createCell((short) i);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new HSSFRichTextString(title[i]));
		}
		Map map = null;
		for (int i = 0; values != null && i < values.size(); i++) {
			row = sheet.createRow((short) (i + 1));
			map = values.get(i);
			for (int i2 = 0; i2 < key.length; i2++) {
				cell = row.createCell((short) (i2));
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				if (map.get(key[i2]) == null) {
					cell.setCellValue(new HSSFRichTextString(""));
				} else {
					cell.setCellValue(new HSSFRichTextString(CharUtil.nullToString(map.get(key[i2]))
							.toString()));
				}
			}
		}
		workbook.write(servletOutputStream);
		servletOutputStream.flush();
		servletOutputStream.close();
	}
}
