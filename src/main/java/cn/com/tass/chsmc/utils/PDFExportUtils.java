package cn.com.tass.chsmc.utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.MultiplePiePlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.TextAnchor;
import org.jfree.ui.VerticalAlignment;
import org.jfree.util.TableOrder;

import com.alibaba.fastjson.JSONObject;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.html.HtmlWriter;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.rtf.RtfWriter2;

/**
 * 类  名   称：PDFExportUtils   
 * </br> 
 * 类  描   述：文件导出工具类   				
 * </br>
 * 创  建   人：think  
 * </br>  	
 * 创建时间：2013-5-13 上午10:30:28    
 * </br>  
 * 修  改   人：think   	
 * </br>   
 * 修改时间：2013-5-13 上午10:30:28    
 * </br>  
 * 修改备注：    
 * </br>  
 * @version  v0.1
 *
 */
public abstract class PDFExportUtils {

	public static final String EXPORT_TYPE_PDF = "PDF";

	public static final String EXPORT_TYPE_RTF = "RTF";

	public static final String EXPORT_TYPE_HTML = "HTML";

	@SuppressWarnings("unchecked")
	private static Map columColorField = new HashMap<String, Object>();

	public void exportPDF(String fileName, HttpServletResponse response, HttpServletRequest request, String fileType, boolean vertical) throws Exception {

		Rectangle rectPageSize = new Rectangle(PageSize.A4);// 定义A4页面大小
		Document doc = new Document(rectPageSize, 50, 50, 50, 50);// 其余4个参数，设置了页面的4个边距
		OutputStream out = response.getOutputStream();

		String fileName2 = "";

		String agent = request.getHeader("User-Agent");
		boolean isMSIE = (agent != null && agent.toLowerCase().matches(".*(msie\\s|trident.*rv:)([\\w.]+).*"));
		if (isMSIE) {
			fileName2 = URLEncoder.encode(fileName, "UTF8");
		} else {
			fileName2 = new String(fileName.getBytes(), "iso-8859-1");
		}
		response.setHeader("Content-disposition", "attachment;filename=\"" + fileName2 + "\"");
		response.setContentType("application/x-msdownload;charset=UTF-8");

		if (EXPORT_TYPE_PDF.equalsIgnoreCase(fileType)) {
			PdfWriter.getInstance(doc, out);
		} else if (EXPORT_TYPE_RTF.equalsIgnoreCase(fileType)) {
			RtfWriter2.getInstance(doc, out);
		} else if (EXPORT_TYPE_HTML.equalsIgnoreCase(fileType)) {
			HtmlWriter.getInstance(doc, out);
		}

		fillPDFData(doc);

		out.flush();
	}

	/**
	 * 导出文件(报表)
	 * @param uploadPath 临时文件夹
	 * @param charDataList 图标list
	 * @param listDataList 列表list
	 * @param fileType 文件类型
	 * @return  String
	 * @throws Exception 异常
	 */
	@SuppressWarnings("unchecked")
	public String exportFile(String uploadPath, List charDataList, List listDataList, String fileType,
			Map<String, Object> replaceParam, String fileTitle) throws Exception {
		if (EXPORT_TYPE_HTML.equals(fileType)) {
			Rectangle rectPageSize = new Rectangle(PageSize.A4);// 定义A4页面大小
			Document doc = new Document(rectPageSize, 50, 50, 50, 50);
			fillPDFData(doc, charDataList, listDataList, fileType, replaceParam, fileTitle);
			return "";
		}
		Rectangle rectPageSize = new Rectangle(PageSize.A4);// 定义A4页面大小
		Document doc = new Document(rectPageSize, 50, 50, 50, 50);// 其余4个参数，设置了页面的4个边距
		OutputStream outputStream = null;
		String pdfPath = "";
		// 暂时屏蔽截取 2013-12-01@shqi
		/*
		 * if(fileTitle.length() > 10){ fileTitle = fileTitle.substring(0,10) + "..."; }
		 */
		if (EXPORT_TYPE_PDF.equalsIgnoreCase(fileType)) {
			pdfPath = uploadPath + "/" + fileTitle + ".pdf";
			outputStream = new FileOutputStream(new File(pdfPath));
			PdfWriter.getInstance(doc, outputStream);
		} else if (EXPORT_TYPE_RTF.equalsIgnoreCase(fileType)) {
			pdfPath = uploadPath + "/" + fileTitle + ".doc";
			outputStream = new FileOutputStream(new File(pdfPath));
			RtfWriter2.getInstance(doc, outputStream);
		} else if (EXPORT_TYPE_HTML.equalsIgnoreCase(fileType)) {
			pdfPath = uploadPath + "/" + fileTitle + ".pdf";
			outputStream = new FileOutputStream(new File(pdfPath));
			HtmlWriter.getInstance(doc, outputStream);
		}
		if (outputStream != null) {
			outputStream.flush();
		}
		fillPDFData(doc, charDataList, listDataList, fileType, replaceParam, fileTitle);
		return pdfPath;
	}

	public abstract void fillPDFData(Document doc) throws Exception;

	@SuppressWarnings("unchecked")
	public void fillPDFData(Document doc, List charDataList, List listDataList,
			String fileType, Map<String, Object> replaceParam, String fileTitle) throws Exception {

	}

	@SuppressWarnings("unchecked")
	public static Table getTable(String[] headers, Color headerColor, String[] keys, List<Map> values, Font headerFont, Font bodyFont, Color[] stripeColor) throws Exception {
		Table table = new Table(headers.length);
		for (int j = 0; j < headers.length; j++) {
			Chunk thChunk = new Chunk(headers[j], headerFont);
			Cell cell = new Cell(thChunk);
			cell.setHeader(true); // 设为头部
			cell.setHorizontalAlignment(Cell.ALIGN_CENTER);// 横向距中
			cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
			if (headerColor != null) {
				cell.setBackgroundColor(headerColor);
			}
			table.addCell(cell);
		}
		int i = 0;
		for (Map map : values) {
			for (String key : keys) {
				Paragraph tdPara = new Paragraph((String) CharUtil.nullToString(map.get(key)), bodyFont);
				Cell cell = new Cell(tdPara);
				cell.setHorizontalAlignment(Cell.ALIGN_CENTER);// 横向距中
				cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
				if (stripeColor != null && stripeColor.length == 2) {
					if (i % 2 == 0) {
						cell.setBackgroundColor(stripeColor[0]);
					} else {
						cell.setBackgroundColor(stripeColor[1]);
					}
				}
				table.addCell(cell);
			}
			i++;
		}
		return table;
	}

	/**
	 * 创建一个PDF表格
	 * @param headers  列标题
	 * @param headerColor  表头背景色
	 * @param keys    列关键字
	 * @param values  表格数据
	 * @param headerFont  表头字体
	 * @param bodyFont   表格内容字体
	 * @param stripeColor  行背景色,隔行
	 * @return PdfPTable
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static PdfPTable getPdfTable(String[] headers, Color headerColor, String[] keys, List<Map> values, Font headerFont, Font bodyFont, Color[] stripeColor) throws Exception {
		if (headers == null) {
			return new PdfPTable(1);
		}
		PdfPTable table = new PdfPTable(headers.length);
		table.setWidthPercentage(100);
		for (int j = 0; j < headers.length; j++) {
			PdfPCell headerCell = new PdfPCell(new Paragraph(headers[j], headerFont));
			headerCell.setHorizontalAlignment(Cell.ALIGN_LEFT);// 横向距中
			headerCell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
			if (headerColor != null) {
				headerCell.setBackgroundColor(headerColor);
			}
			headerCell.setMinimumHeight(14f);
			headerCell.setPaddingTop(5f);
			headerCell.setPaddingBottom(5f);
			headerCell.setBorder(1);
			table.addCell(headerCell);
		}
		int i = 0;
		for (Map map : values) {
			for (String key : keys) {
				Paragraph tdPara = new Paragraph(String.valueOf(CharUtil.nullToString(map.get(key))), bodyFont);
				PdfPCell bodyCell = new PdfPCell(tdPara);
				bodyCell.setHorizontalAlignment(Cell.ALIGN_LEFT);// 横向距中
				bodyCell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
				// bodyCell.setMinimumHeight(12f);
				bodyCell.setPaddingTop(5f);
				bodyCell.setPaddingBottom(5f);
				bodyCell.setBorder(1);
				if (stripeColor != null && stripeColor.length == 2) {
					if (i % 2 == 0) {
						bodyCell.setBackgroundColor(stripeColor[0]);
					} else {
						bodyCell.setBackgroundColor(stripeColor[1]);
					}
				}

				table.addCell(bodyCell);
			}
			i++;
		}
		return table;
	}

	/**
	* 创建一个PDF表格
	* @param headers  列标题
	* @param headerColor  表头背景色
	* @param keys    列关键字
	* @param values  表格数据
	* @param headerFont  表头字体
	* @param bodyFont   表格内容字体
	* @param stripeColor  行背景色,隔行
	* @return Table
	* @throws Exception
	*/
	@SuppressWarnings("unchecked")
	public static Table appendTable(Table table, String[] headers, Color headerColor, String[] keys, List<Map> values, Font headerFont, Font bodyFont, Color[] stripeColor) throws Exception {
		for (int j = 0; j < headers.length; j++) {
			Cell headerCell = new Cell(new Paragraph(headers[j], headerFont));
			headerCell.setHorizontalAlignment(Cell.ALIGN_LEFT);// 横向距中
			headerCell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
			if (headerColor != null) {
				headerCell.setBackgroundColor(headerColor);
			}
			headerCell.setBorder(1);
			table.addCell(headerCell);
		}
		int i = 0;
		for (Map map : values) {
			for (String key : keys) {
				Paragraph tdPara = new Paragraph(String.valueOf(CharUtil.nullToString(map.get(key))), bodyFont);
				Cell bodyCell = new Cell(tdPara);
				bodyCell.setHorizontalAlignment(Cell.ALIGN_LEFT);// 横向距中
				bodyCell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
				bodyCell.setBorder(1);
				if (stripeColor != null && stripeColor.length == 2) {
					if (i % 2 == 0) {
						bodyCell.setBackgroundColor(stripeColor[0]);
					} else {
						bodyCell.setBackgroundColor(stripeColor[1]);
					}
				}

				table.addCell(bodyCell);
			}
			i++;
		}
		return table;
	}

	/**
	 * 获取中文字体
	 * @param fontSize 字号
	 * @param vertical 字的排版（横向、竖向）
	 * @param isBold 是否加粗
	 * @param color  字体颜色
	 * @return Font
	 */
	public static Font getChineseFont(int fontSize, boolean vertical, boolean isBold, Color color) {
		BaseFont bfChinese;
		Font fontChinese = null;
		int fontBold = 0;
		String fontVertical = vertical ? "UniGB-UCS2-V" : "UniGB-UCS2-H";
		try {
			bfChinese = BaseFont.createFont("STSong-Light", fontVertical, BaseFont.NOT_EMBEDDED);
			fontBold = isBold ? Font.BOLD : Font.NORMAL;
			fontChinese = new Font(bfChinese, fontSize, fontBold, color);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fontChinese;
	}

	/**
	* 块，PDF中，最小的单位
	* @param content 转换内容
	* @param font 字体
	* @return Chunk
	*/
	public static Chunk getChunk(String content, Font font) {
		Chunk chunk = new Chunk(content, font);
		return chunk;
	}

	/**
	* 短句
	* @param content
	* @param font
	* @return Phrase
	*/
	public static Phrase getPhrase(String content, Font font) {
		Phrase phrase = new Phrase(content, font);
		return phrase;
	}

	/**
	* 设置字段
	*
	* @param content 内容
	* @param font 字体
	* @return Paragraph
	*/
	public static Paragraph getPara(String content, Font font) {
		Paragraph para = new Paragraph(content, font);
		return para;
	}

	/**
	* 设置图片对象
	* 
	* @param imgPath
	*            图片物理地址
	* @param alignMent
	*            对齐方式 Image.RIGHT, MIDDLE, LEFT ,TEXTWRAP(环绕）, UNDERLYING(背景）
	*            或以用｜并在一起用，如Image.RIGHT | Image.UNDERLYING
	* @param absoluteX
	*            横坐标
	* @param absoluteY
	*            纵坐标
	* @return Image
	*/
	public static Image getImage(String imgPath, int alignMent,
			float absoluteX, float absoluteY) {
		Image img = null;
		try {
			img = Image.getInstance(imgPath);// 取得图片对象
			// 设置与文字对齐方式
			img.setAlignment(alignMent);// Image.RIGHT, MIDDLE, LEFT
			// ,TEXTWRAP(环绕）, UNDERLYING(背景）
			if (absoluteX != -1 && absoluteY != -1) {// 戓x, y均不等于－1
				img.setAbsolutePosition(absoluteX, absoluteY);// 设置绝对位置
			}
		} catch (Exception e) {
			// log.debug("创建图片对象错误");
			e.printStackTrace();
		}
		return img;
	}

	/**
	* 设置图片对象
	* 
	* @param bufImage
	*            图片流
	* @param imageType
	*            图片类型
	* @param alignMent
	*            对齐方式 Image.RIGHT, MIDDLE, LEFT ,TEXTWRAP(环绕）, UNDERLYING(背景）
	*            或以用｜并在一起用，如Image.RIGHT | Image.UNDERLYING
	* @param absoluteX
	*            横坐标
	* @param absoluteY
	*            纵坐标
	* @return Image
	*/
	public static Image getImage(BufferedImage bufImage, String imageType,
			int alignMent, float absoluteX, float absoluteY) {
		Image img = null;
		try {
			ByteArrayOutputStream imageByte = new ByteArrayOutputStream();
			ImageIO.write(bufImage, imageType, imageByte);
			img = Image.getInstance(imageByte.toByteArray());
			img.setBackgroundColor(Color.white);
			// 设置与文字对齐方式
			img.setAlignment(alignMent);// Image.RIGHT, MIDDLE, LEFT
			// ,TEXTWRAP(环绕）, UNDERLYING(背景）
			if (absoluteX != -1 && absoluteY != -1) {// 戓x, y均不等于－1
				img.setAbsolutePosition(absoluteX, absoluteY);// 设置绝对位置
			}
		} catch (Exception e) {
			// log.debug("创建图片对象错误");
			e.printStackTrace();
		}
		return img;
	}

	/**
	 * 线性图 
	 * @param json 组成元素：dataProvider ：数据元，categoryField：类别域，valueField：值域，title：标题，categoryAxisTitle：横坐标标题，valueAxisTitle，纵坐标标题
	 * @return JFreeChart
	 */
	@SuppressWarnings("unchecked")
	public static JFreeChart createLineChart(JSONObject json) {
		DefaultCategoryDataset localDefaultCategoryDataset = new DefaultCategoryDataset();
		List data = json.getObject("dataProvider", List.class);
		String categoryField = json.getString("categoryField");
		String valueField = json.getString("valueField");
		String title = json.getString("title");
		String categoryAxisTitle = json.getString("categoryAxisTitle");
		String valueAxisTitle = json.getString("valueAxisTitle");

		if (data != null) {
			for (int i = 0; i < data.size(); i++) {
				Map m = (Map) data.get(i);
				localDefaultCategoryDataset.addValue(
						Double.valueOf(m.get(valueField).toString()), "SMC",
						(String) m.get(categoryField));
			}
		}

		JFreeChart localJFreeChart = ChartFactory.createLineChart(title,
				categoryAxisTitle, valueAxisTitle, localDefaultCategoryDataset,
				PlotOrientation.VERTICAL, false, true, false);
		configFont(localJFreeChart);
		CategoryPlot localCategoryPlot = (CategoryPlot) localJFreeChart
				.getPlot();
		// 配置字体
		java.awt.Font xfont = new java.awt.Font("UniGB-UCS2-V",
				java.awt.Font.PLAIN, 12);// X轴
		java.awt.Font yfont = new java.awt.Font("UniGB-UCS2-V",
				java.awt.Font.PLAIN, 12);// Y轴
		// X 轴
		CategoryAxis domainAxis = localCategoryPlot.getDomainAxis();
		domainAxis.setLabelFont(xfont);// 轴标题
		domainAxis.setTickLabelFont(xfont);// 轴数值
		domainAxis.setTickLabelPaint(Color.BLACK); // 字体颜色
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45); // 横轴上的label斜显示

		// Y 轴
		ValueAxis rangeAxis = localCategoryPlot.getRangeAxis();
		rangeAxis.setLabelFont(yfont);
		rangeAxis.setLabelPaint(Color.black); // 字体颜色
		rangeAxis.setTickLabelFont(yfont);

		NumberAxis localNumberAxis = (NumberAxis) localCategoryPlot
				.getRangeAxis();
		localNumberAxis.setStandardTickUnits(NumberAxis
				.createIntegerTickUnits());
		LineAndShapeRenderer localLineAndShapeRenderer = (LineAndShapeRenderer) localCategoryPlot
				.getRenderer();
		localLineAndShapeRenderer.setBaseShapesVisible(true);
		localLineAndShapeRenderer.setDrawOutlines(true);
		localLineAndShapeRenderer.setUseFillPaint(true);
		localLineAndShapeRenderer.setBaseFillPaint(Color.white);
		localLineAndShapeRenderer.setSeriesStroke(0, new BasicStroke(1.0F));
		localLineAndShapeRenderer.setSeriesOutlineStroke(0, new BasicStroke(
				1.0F));
		localLineAndShapeRenderer.setSeriesShape(0, new Ellipse2D.Double(-2.0D,
				-2.0D, 5.0D, 5.0D));
		return localJFreeChart;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	public static JFreeChart createMutiLine(JSONObject json) {
		List data = json.getObject("dataProvider", List.class);
		String categoryField = json.getString("categoryField");

		String title = json.getString("title");
		String categoryAxisTitle = json.getString("categoryAxisTitle");
		String valueAxisTitle = json.getString("valueAxisTitle");

		List dataList = new ArrayList();

		for (int i = 0; data != null && i < data.size(); i++) {
			Map dataMap = (Map) data.get(i);
			Set keySet = dataMap.keySet();
			if (keySet != null && keySet.size() > 0) {
				int j = 0;
				String titleValue = "";
				for (Object key : keySet) {
					if (j == 0) {
						titleValue = CharUtil.objectToString(dataMap.get(key));
					} else if (j % 2 == 1) {
						String typeValue = CharUtil.objectToString(dataMap.get("value" + j));
						String value = CharUtil.objectToString(dataMap.get("value" + (j + 1)));
						String[] str = new String[3];
						str[0] = titleValue;
						str[1] = typeValue;
						str[2] = value;
						if (value == null || "".equals(value)) {
							j++;
							continue;
						}
						dataList.add(str);
					}
					j++;
				}
			}
		}

		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		for (int i = 0; dataList != null && i < dataList.size(); i++) {
			String[] valueList = (String[]) dataList.get(i);
			dataSet.addValue(Integer.parseInt(valueList[2]), valueList[1], valueList[0]);
		}

		JFreeChart chart = null;

		chart = ChartFactory.createLineChart
				(title, categoryAxisTitle, valueAxisTitle, dataSet,
						PlotOrientation.VERTICAL, // 绘制方向
						true, // 是否显示图例
						true, // 是否采用标准生成器
						false // 是否生成超链接
				);

		configFont(chart);
		CategoryPlot localCategoryPlot = (CategoryPlot) chart
				.getPlot();
		// 配置字体
		java.awt.Font xfont = new java.awt.Font("UniGB-UCS2-V",
				java.awt.Font.PLAIN, 12);// X轴
		java.awt.Font yfont = new java.awt.Font("UniGB-UCS2-V",
				java.awt.Font.PLAIN, 12);// Y轴
		// X 轴
		CategoryAxis domainAxis = localCategoryPlot.getDomainAxis();
		domainAxis.setLabelFont(xfont);// 轴标题
		domainAxis.setTickLabelFont(xfont);// 轴数值
		domainAxis.setTickLabelPaint(Color.BLACK); // 字体颜色
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45); // 横轴上的label斜显示

		// Y 轴
		ValueAxis rangeAxis = localCategoryPlot.getRangeAxis();
		rangeAxis.setLabelFont(yfont);
		rangeAxis.setLabelPaint(Color.black); // 字体颜色
		rangeAxis.setTickLabelFont(yfont);
		NumberAxis localNumberAxis = (NumberAxis) localCategoryPlot
				.getRangeAxis();
		localNumberAxis.setStandardTickUnits(NumberAxis
				.createIntegerTickUnits());
		LineAndShapeRenderer localLineAndShapeRenderer = (LineAndShapeRenderer) localCategoryPlot
				.getRenderer();
		localLineAndShapeRenderer.setBaseShapesVisible(true);
		localLineAndShapeRenderer.setDrawOutlines(true);
		localLineAndShapeRenderer.setUseFillPaint(true);
		localLineAndShapeRenderer.setBaseFillPaint(Color.white);
		localLineAndShapeRenderer.setSeriesStroke(0, new BasicStroke(1.0F));
		localLineAndShapeRenderer.setSeriesOutlineStroke(0, new BasicStroke(
				1.0F));
		localLineAndShapeRenderer.setSeriesShape(0, new Ellipse2D.Double(-2.0D,
				-2.0D, 5.0D, 5.0D));
		return chart;
	}

	/**
	 * 柱状图
	 * @param json 组成元素：dataProvider ：数据元，categoryField：类别域，valueField：值域，title：标题，categoryAxisTitle：横坐标标题，valueAxisTitle，纵坐标标题
	 * @return JFreeChart
	 */
	@SuppressWarnings("unchecked")
	public static JFreeChart createBarChart3D(JSONObject json) {
		DefaultCategoryDataset localDefaultCategoryDataset = new DefaultCategoryDataset();
		Map data = null;
		if (json.get("dataProvider") instanceof LinkedHashMap) {
			data = json.getObject("dataProvider", LinkedHashMap.class);
		} else {
			data = json.getObject("dataProvider", Map.class);
		}
		String categoryField = json.getString("categoryField");
		String valueField = json.getString("valueField");
		String title = json.getString("title");
		String categoryAxisTitle = json.getString("categoryAxisTitle");
		String valueAxisTitle = json.getString("valueAxisTitle");
		String isCustom = json.getString("isCustom");
		JSONObject originColor = json.getJSONObject("originColor");

		if (data != null) {
			Iterator iter = data.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				List ls = (List) entry.getValue();
				for (int i = 0; i < ls.size(); i++) {
					Map m = (Map) ls.get(i);
					localDefaultCategoryDataset.addValue(
							Double.valueOf(m.get(valueField).toString()), (String) entry.getKey(),
							(String) m.get(categoryField));
					// 颜色方案
					if (m.get("typeID") != null && originColor.get((Integer) m.get("typeID") + "") != null) {
						String color = originColor.get((Integer) m.get("typeID") + "").toString().substring(1);
						getColumColorField().put((String) m.get(categoryField), new Color(Integer.parseInt(color, 16)));
					}
				}
			}
		}

		JFreeChart localJFreeChart = ChartFactory.createBarChart3D(title,
				categoryAxisTitle, valueAxisTitle, localDefaultCategoryDataset,
				PlotOrientation.VERTICAL, false, true, false);
		configFont(localJFreeChart);
		CategoryPlot localCategoryPlot = (CategoryPlot) localJFreeChart
				.getPlot();
		if (!"no".equals(isCustom)) {
			CustomBarRenderer3D localCustomBarRenderer3D = new CustomBarRenderer3D();
			localCustomBarRenderer3D.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			localCustomBarRenderer3D.setBaseItemLabelsVisible(true);
			localCustomBarRenderer3D.setItemLabelAnchorOffset(10.0D);
			localCustomBarRenderer3D.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
			localCategoryPlot.setRenderer(localCustomBarRenderer3D);
		}
		// 配置字体
		java.awt.Font xfont = new java.awt.Font("UniGB-UCS2-V",
				java.awt.Font.PLAIN, 12);// X轴
		java.awt.Font yfont = new java.awt.Font("UniGB-UCS2-V",
				java.awt.Font.PLAIN, 12);// Y轴
		// Y 轴
		ValueAxis rangeAxis = localCategoryPlot.getRangeAxis();
		// rangeAxis.setAutoTickUnitSelection(false);
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());// 纵坐标设置为整数
		rangeAxis.setLabelFont(yfont);
		rangeAxis.setLabelPaint(Color.black); // 字体颜色
		rangeAxis.setTickLabelFont(yfont);
		CategoryAxis localCategoryAxis = localCategoryPlot.getDomainAxis();
		localCategoryAxis.setLabelFont(xfont);// 轴标题
		localCategoryAxis.setTickLabelFont(xfont);// 轴数值
		localCategoryAxis.setTickLabelPaint(Color.BLACK); // 字体颜色
		localCategoryAxis
				.setCategoryLabelPositions(CategoryLabelPositions.UP_45); // 横轴上的label斜显示
		localCategoryAxis.setCategoryLabelPositions(CategoryLabelPositions
				.createUpRotationLabelPositions(0.3926990816987241D));
		CategoryItemRenderer localCategoryItemRenderer = localCategoryPlot
				.getRenderer();

		localCategoryItemRenderer.setBaseItemLabelsVisible(true);
		BarRenderer localBarRenderer = (BarRenderer) localCategoryItemRenderer;
		localBarRenderer.setItemMargin(0.2D);
		return localJFreeChart;
	}

	/**
	 * 堆栈形式柱状图
	 * 
	 * @param json
	 *            组成元素：dataProvider
	 *            ：数据元，categoryField：类别域，valueField：值域，title：标题
	 *            ，categoryAxisTitle：横坐标标题，valueAxisTitle，纵坐标标题
	 * @return JFreeChart
	 */
	@SuppressWarnings("unchecked")
	public static JFreeChart createStackedBarChart3D(JSONObject json) {
		DefaultCategoryDataset localDefaultCategoryDataset = new DefaultCategoryDataset();
		List ls = json.getObject("dataProvider", List.class);
		String categoryField = json.getString("categoryField");
		String valueField = json.getString("valueField");
		String title = json.getString("title");
		String categoryAxisTitle = json.getString("categoryAxisTitle");
		String legendField = json.getString("legendField");
		String valueAxisTitle = json.getString("valueAxisTitle");
		JSONObject originColor = json.getJSONObject("originColor");
		List<Color> colorList = new ArrayList<Color>();
		if (ls != null) {
			for (int i = 0; i < ls.size(); i++) {
				Map m = (Map) ls.get(i);
				localDefaultCategoryDataset.addValue(
						Double.valueOf(m.get(valueField).toString()),
						(String) m.get(legendField), (String) m.get(categoryField));

				// 颜色方案
				if (m.get("typeID") != null
						&& originColor.get((Integer) m.get("typeID") + "") != null) {
					String color = originColor.get((Integer) m.get("typeID") + "")
							.toString().substring(1);
					colorList.add(new Color(Integer.parseInt(color, 16)));
				}
			}
		}

		JFreeChart localJFreeChart = ChartFactory.createStackedBarChart3D(
				title, categoryAxisTitle, valueAxisTitle,
				localDefaultCategoryDataset, PlotOrientation.VERTICAL, true,
				true, false);
		configFont(localJFreeChart);
		CategoryPlot localCategoryPlot = (CategoryPlot) localJFreeChart
				.getPlot();
		localCategoryPlot.setBackgroundPaint(null);// 去除默认背景
		localCategoryPlot.setRangeGridlinePaint(Color.gray);// 网格线颜色
		localCategoryPlot.setRangeGridlineStroke(new BasicStroke(0.4F));// 粗细设置

		// 配置字体
		java.awt.Font xfont = new java.awt.Font("UniGB-UCS2-V",
				java.awt.Font.PLAIN, 12);// X轴
		java.awt.Font yfont = new java.awt.Font("UniGB-UCS2-V",
				java.awt.Font.PLAIN, 12);// Y轴
		// Y 轴
		ValueAxis rangeAxis = localCategoryPlot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());// 纵坐标设置为整数
		rangeAxis.setLabelFont(yfont);
		rangeAxis.setLabelPaint(Color.black); // 字体颜色
		rangeAxis.setTickLabelFont(yfont);
		// X 轴
		CategoryAxis localCategoryAxis = localCategoryPlot.getDomainAxis();
		localCategoryAxis.setLabelFont(xfont);// 轴标题
		localCategoryAxis.setTickLabelFont(xfont);// 轴数值
		localCategoryAxis.setTickLabelPaint(Color.BLACK); // 字体颜色
		localCategoryAxis
				.setCategoryLabelPositions(CategoryLabelPositions.UP_45); // 横轴上的label斜显示
		localCategoryAxis.setCategoryLabelPositions(CategoryLabelPositions
				.createUpRotationLabelPositions(0.3926990816987241D));
		CategoryItemRenderer localCategoryItemRenderer = localCategoryPlot
				.getRenderer();

		localCategoryItemRenderer.setBaseItemLabelsVisible(true);
		BarRenderer localBarRenderer = (BarRenderer) localCategoryItemRenderer;
		localBarRenderer.setItemMargin(0.2D);
		localBarRenderer.setDrawBarOutline(false);
		localBarRenderer
				.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		localBarRenderer.setBaseItemLabelsVisible(true);
		localBarRenderer
				.setBasePositiveItemLabelPosition(new ItemLabelPosition(
						ItemLabelAnchor.CENTER, TextAnchor.CENTER));
		localBarRenderer
				.setBaseNegativeItemLabelPosition(new ItemLabelPosition(
						ItemLabelAnchor.CENTER, TextAnchor.CENTER));

		// 根据颜色方案设置颜色
		for (int i = 0; i < colorList.size(); i++) {
			localBarRenderer.setSeriesPaint(i, colorList.get(i));
		}

		// localJFreeChart.getLegend().setBorder(0.7, 0.7, 0.7, 0.7);//设置legend边框粗细
		return localJFreeChart;
	}

	/**
	 * 饼状图
	 * @param json 组成元素：dataProvider ：数据元，categoryField：类别域，valueField：值域，title：标题
	 * @return JFreeChart
	 */
	@SuppressWarnings("unchecked")
	public static JFreeChart createPieChart3D(JSONObject json) {
		DefaultPieDataset localDefaultCategoryDataset = new DefaultPieDataset();
		List data = json.getObject("dataProvider", List.class);
		String categoryField = json.getString("categoryField");
		String valueField = json.getString("valueField");
		String title = json.getString("title");
		JSONObject originColor = json.getJSONObject("originColor");

		if (data != null) {
			for (int i = 0; i < data.size(); i++) {
				Map m = (Map) data.get(i);
				localDefaultCategoryDataset.setValue((String) m.get(categoryField), Double.valueOf(m.get(valueField) + ""));
			}
		}

		JFreeChart localJFreeChart = ChartFactory.createPieChart(
				title, localDefaultCategoryDataset, true,
				true, true);
		// 字体设置
		configFont(localJFreeChart);
		// MultiplePiePlot localMultiplePiePlot = (MultiplePiePlot) localJFreeChart
		// .getPlot();
		// 基本设置
		PiePlot localPiePlot = (PiePlot) localJFreeChart.getPlot();
		localPiePlot.setBackgroundPaint(null);// 去除默认背景
		localPiePlot.setOutlinePaint(Color.white);// 背景外边框颜色
		// localJFreeChart.setTextAntiAlias(false);//去除字体边缘锯齿
		localPiePlot.setBaseSectionOutlinePaint(Color.white);// 分界线颜色
		localPiePlot.setStartAngle(90);// 开始角度
		localPiePlot.setShadowPaint(Color.white);// 去除阴影

		// 颜色方案
		if (data != null && originColor != null && originColor.size() > 0) {
			for (int i = 0; i < data.size(); i++) {
				Map m = (Map) data.get(i);
				if (originColor.get((Integer) m.get("typeID") + "") != null) {
					String color = originColor.get((Integer) m.get("typeID") + "").toString().substring(1);
					localPiePlot.setSectionPaint((String) m.get(categoryField), new Color(Integer.parseInt(color, 16)));
				}
			}
		}

		// 标签设置
		localPiePlot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {2}", NumberFormat.getNumberInstance(), new DecimalFormat("0.00%")));// 内容
		java.awt.Font font = new java.awt.Font("UniGB-UCS2-V",
				java.awt.Font.PLAIN, 12);// X轴
		localPiePlot.setLabelGap(0.02D);
		localPiePlot.setLabelFont(font);// 字体
		localPiePlot.setLabelBackgroundPaint(Color.white);// 背景色
		localPiePlot.setLabelOutlinePaint(Color.white);// 指示线颜色
		localPiePlot.setLabelShadowPaint(Color.white);// 阴影
		localPiePlot.setMaximumLabelWidth(0.18D);// 最小宽度
		// localPiePlot.setLabelLinkStyle(PieLabelLinkStyle.STANDARD);
		// localPiePlot.setOutlinePaint(Color.white);

		// 图例设置
		localPiePlot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator(
				"{0}:[{1}]"));// 图例内容
		LegendTitle legendTitle = localJFreeChart.getLegend();
		legendTitle.setBorder(0, 0, 0, 0);// 设置legend边框粗细
		legendTitle.setPosition(RectangleEdge.RIGHT);// 位置
		// java.awt.Font legendFont = new java.awt.Font("UniGB-UCS2-V",
		// java.awt.Font.PLAIN, 14);// 字体
		// legendTitle.setItemFont(legendFont);
		legendTitle.setItemPaint(Color.black);// 颜色
		legendTitle.setVerticalAlignment(VerticalAlignment.TOP);// 垂直居中
		legendTitle.setMargin(40, 0, 0, 0);// 偏移量
		return localJFreeChart;
	}

	/**
	 * 饼状图
	 * @param json 组成元素：dataProvider ：数据元，categoryField：类别域，valueField：值域，title：标题
	 * @return JFreeChart
	 */
	@SuppressWarnings("unchecked")
	public static JFreeChart createColorPieChart3D(JSONObject json) {
		DefaultCategoryDataset localDefaultCategoryDataset = new DefaultCategoryDataset();
		List data = json.getObject("dataProvider", List.class);
		String categoryField = json.getString("categoryField");
		String valueField = json.getString("valueField");
		String colorType = json.getString("colorType");
		String title = json.getString("title");
		String[] colorArr = { "#d15b47", "#ffb752", "#fee074", "#6fb3e0", "#9abc32", "#FF5800", "#cbb752", "#ebe074", "#efb3e0", "#eabc32", "#FFEFD5" };

		if (data != null) {
			int otherNum = 0;
			for (int i = 0; i < data.size(); i++) {
				Map m = (Map) data.get(i);
				if (i < 10) {
					localDefaultCategoryDataset.addValue(
							Double.valueOf(m.get(valueField) + ""),
							(String) m.get(categoryField), "");
				} else {
					otherNum += Integer.parseInt(m.get(valueField) + "");
				}
			}
			if (data.size() > 10) {
				localDefaultCategoryDataset.addValue(Double.valueOf(otherNum), "其他", "");
			}
		}

		JFreeChart localJFreeChart = ChartFactory.createMultiplePieChart(
				title, localDefaultCategoryDataset, TableOrder.BY_COLUMN, true,
				true, true);
		configFont(localJFreeChart);
		MultiplePiePlot localMultiplePiePlot = (MultiplePiePlot) localJFreeChart
				.getPlot();
		PiePlot localPiePlot = (PiePlot) localMultiplePiePlot.getPieChart()
				.getPlot();

		// 颜色方案
		if (data != null) {
			for (int i = 0; i < data.size(); i++) {
				if (i > 10) {
					break;
				}
				Map m = (Map) data.get(i);
				if ("warn".equals(colorType)) {
					// 严重级别的颜色另外处理
					if ("一般".equals((String) m.get(categoryField))) {
						String color = "#6FB3E0";
						localPiePlot.setSectionPaint((String) m.get(categoryField), new Color(Integer.parseInt(color.substring(1), 16)));
					} else if ("警告".equals((String) m.get(categoryField))) {
						String color = "#fee074";
						localPiePlot.setSectionPaint((String) m.get(categoryField), new Color(Integer.parseInt(color.substring(1), 16)));
					} else if ("严重".equals((String) m.get(categoryField))) {
						String color = "#FFB752";
						localPiePlot.setSectionPaint((String) m.get(categoryField), new Color(Integer.parseInt(color.substring(1), 16)));
					} else if ("极度严重".equals((String) m.get(categoryField))) {
						String color = "#D15B47";
						localPiePlot.setSectionPaint((String) m.get(categoryField), new Color(Integer.parseInt(color.substring(1), 16)));
					} else {
						String color = colorArr[i];
						localPiePlot.setSectionPaint((String) m.get(categoryField), new Color(Integer.parseInt(color.substring(1), 16)));
					}
				} else {
					if ("严重".equals((String) m.get(categoryField))) {
						String color = "#D15B47";
						localPiePlot.setSectionPaint((String) m.get(categoryField), new Color(Integer.parseInt(color.substring(1), 16)));
					} else if ("高级".equals((String) m.get(categoryField))) {
						String color = "#FFB752";
						localPiePlot.setSectionPaint((String) m.get(categoryField), new Color(Integer.parseInt(color.substring(1), 16)));
					} else if ("中级".equals((String) m.get(categoryField))) {
						String color = "#FEE074";
						localPiePlot.setSectionPaint((String) m.get(categoryField), new Color(Integer.parseInt(color.substring(1), 16)));
					} else if ("低级".equals((String) m.get(categoryField))) {
						String color = "#6FB3E0";
						localPiePlot.setSectionPaint((String) m.get(categoryField), new Color(Integer.parseInt(color.substring(1), 16)));
					} else if ("信息".equals((String) m.get(categoryField))) {
						String color = "#9ABC32";
						localPiePlot.setSectionPaint((String) m.get(categoryField), new Color(Integer.parseInt(color.substring(1), 16)));
					} else {
						String color = colorArr[i];
						localPiePlot.setSectionPaint((String) m.get(categoryField), new Color(Integer.parseInt(color.substring(1), 16)));
					}
				}
			}
		}

		localPiePlot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {2}({1})", NumberFormat.getNumberInstance(), new DecimalFormat("0.00%")));
		java.awt.Font font = new java.awt.Font("UniGB-UCS2-V",
				java.awt.Font.PLAIN, 12);// X轴
		localPiePlot.setLabelFont(font);
		localPiePlot.setMaximumLabelWidth(0.18D);
		return localJFreeChart;
	}

	/**
	 * 配置字体
	 * 
	 * @param chart
	 *            JFreeChart 对象
	 */
	public static void configFont(JFreeChart chart) {
		java.awt.Font kfont = new java.awt.Font("UniGB-UCS2-V",
				java.awt.Font.PLAIN, 12);// 底部
		java.awt.Font titleFont = new java.awt.Font("UniGB-UCS2-V", Font.BOLD,
				25); // 图片标题

		chart.setTitle(new TextTitle(chart.getTitle().getText(), titleFont)); // 图片标题

		if (chart.getLegend() != null) { // 底部
			chart.getLegend().setItemFont(kfont);
		}
	}

	/**
	 * 获取导出单元格
	 * @param text 单元格内容
	 * @param font  字体
	 * @param horizontalAlignment 水平位置  如：Cell.ALIGN_CENTER
	 */
	public static PdfPCell getExportCell(String text, Font font, int horizontalAlignment) {
		PdfPCell cell = new PdfPCell(new Paragraph(text, font));
		cell.setHorizontalAlignment(horizontalAlignment);
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
		cell.setMinimumHeight(25);
		cell.setColspan(2);
		cell.setBorder(0);
		cell.setBorderWidthTop(1);
		cell.setBorderWidthBottom(1);
		return cell;
	}

	/**
	 * 获取导出单元格
	 * @param text 单元格内容
	 * @param font  字体
	 * @param horizontalAlignment 水平位置  如：Cell.ALIGN_CENTER
	 * @throws BadElementException 
	 */
	public static Cell getCell(String text, Font font, int horizontalAlignment, int colspan) throws BadElementException {
		Cell cell = new Cell(new Paragraph(text, font));
		cell.setHorizontalAlignment(horizontalAlignment);
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
		cell.setColspan(colspan);
		cell.setBorder(1);
		cell.setBorderWidthTop(0.0001f);
		cell.setBorderWidthBottom(0.00001f);
		return cell;
	}

	/**
	 * 获取分割行
	 * @param title 标题
	 * @param font 字体
	 * @param colspan 
	 * @return Cell
	 * @throws BadElementException
	 */
	public static Cell getSplitCell(String title, Font font, int colspan) throws BadElementException {
		Cell cell = new Cell(new Paragraph(title, font));
		cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
		cell.setBorder(0);
		cell.setColspan(colspan);
		return cell;
	}

	/**
	 * 获取带border单元格
	 * @param title 内容
	 * @param font 字体
	 * @param colspan 列数
	 * @return Cell
	 * @throws BadElementException
	 */
	public static Cell getBorderCell(String title, Font font, int colspan, int align) throws BadElementException {
		Paragraph tdPara = new Paragraph(title, font);
		Cell bodyCell = new Cell(tdPara);
		bodyCell.setColspan(colspan);
		bodyCell.setHorizontalAlignment(align);// 横向距中
		// bodyCell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
		bodyCell.setBorder(1);
		return bodyCell;
	}

	@SuppressWarnings("unchecked")
	public static Map getColumColorField() {
		return columColorField;
	}

	@SuppressWarnings("unchecked")
	public static void setColumColorField(Map columColorField) {
		PDFExportUtils.columColorField = columColorField;
	}

	@SuppressWarnings("serial")
	static class CustomBarRenderer3D extends BarRenderer3D
	{
		public Paint getItemPaint(int paramInt1, int paramInt2)
		{
			CategoryDataset localCategoryDataset = getPlot().getDataset();
			Object obj = localCategoryDataset.getColumnKey(paramInt2);
			if (!CharUtil.isEmpty(obj)) {
				return (Color) getColumColorField().get(obj);
			}
			return (Paint) new Color(0);
		}
	}
}
