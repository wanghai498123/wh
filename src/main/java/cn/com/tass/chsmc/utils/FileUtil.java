package cn.com.tass.chsmc.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.AndFileFilter;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.NotFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.log4j.Logger;

/**
 * 标题: 文件操作工作类
 * <p>
 * 描述: 文件操作辅助工具类
 * <p>
 * 版权: Copyright (c) 2014
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author jnta
 * @created 2014年8月29日 下午12:59:32
 * @version 1.0
 */
public class FileUtil {
	private static final Logger logger = Logger.getLogger(FileUtil.class);

	/**
	 * 上传文件
	 * 
	 * @param file
	 *            待上传文件
	 * @param destFolder
	 *            目标文件夹，会生成 目标文件夹/日期/文件名
	 * @param fileName
	 *            文件名
	 * @return 文件路径
	 * @throws IOException
	 */
	public static String upload(File file, String destFolder, String fileName) throws IOException {
		if (file != null) {
			File folder = new File(destFolder);
			if (!(folder.exists()))
				folder.mkdir();
			File target = new File(folder, fileName);
			FileUtils.copyFile(file, target);
			return folder + File.separator + fileName;
		}
		return null;
	}

	/**
	 * 创建文件
	 * 
	 * @param content
	 *            要写入的内容
	 * @param fileDir
	 *            要生成的目标文件 路径\文件名
	 * @return 创建成功或失败
	 * @throws IOException
	 */
	public static boolean createFile(String content, String fileDir) throws IOException {
		logger.info("CREATE FILE" + fileDir);
		File localFile = new File(fileDir);
		FileUtils.writeStringToFile(localFile, content, "UTF-8");
		return true;
	}

	/**
	 * 删除文件或文件夹
	 * 
	 * @param path
	 *            路径
	 * @return
	 * @throws IOException
	 */
	public static boolean deleteFile(String path) throws IOException {
		File targetFile = new File(path);
		if (targetFile.isDirectory()) {
			FileUtils.deleteDirectory(targetFile);
			return true;
		}
		if (targetFile.isFile()) {
			targetFile.delete();
			return true;
		}
		logger.info(path + " not exist!");
		return false;
	}

	/**
	 * 复制文件或文件夹
	 * 
	 * @param stcFilePath
	 *            源文件路径
	 * @param destFolder
	 *            目标文件夹
	 * @throws IOException
	 */
	public static void copyFile(String stcFilePath, String destFolder) throws IOException {
		logger.info("Source File:" + stcFilePath);
		logger.info("Target file:" + destFolder);
		File localFile1 = new File(stcFilePath);
		File localFile2 = new File(destFolder);
		if (localFile1.isDirectory())
			FileUtils.copyDirectoryToDirectory(localFile1, localFile2);
		else if (localFile1.isFile())
			FileUtils.copyFileToDirectory(localFile1, localFile2);
	}

	/**
	 * 判断文件夹是否存在
	 * 
	 * @param folderPath
	 * @return
	 */
	public static boolean isFolderExist(String folderPath) {
		File file = new File(folderPath);
		return (file.isDirectory());
	}

	/**
	 * 创建文件夹
	 * 
	 * @param folderPath
	 */
	public static void createFolder(String folderPath) {
		File localFile = new File(folderPath);
		if (!(localFile.exists()))
			localFile.mkdir();
	}

	/**
	 * 创建文件夹
	 * 
	 * @param destDirName
	 *            文件夹名称
	 * @param isForce
	 *            强制创建标识 如果存在则先删除，再创建
	 * @return
	 * @throws IOException
	 */
	public static boolean createDir(String destDirName, boolean isForce) throws IOException {
		File dir = new File(destDirName);
		if (dir.exists()) {
			if (isForce) {
				deleteFile(destDirName);
				dir.mkdir();
				return true;
			}
			return false;
		}
		if (dir.mkdirs()) {
			logger.info("创建目录" + destDirName + "成功！");
			return true;
		}
		logger.info("创建目录" + destDirName + "失败！");
		return false;
	}

	/**
	 * 判断文件是否攒在
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean isFileExist(String fileName) {
		logger.info("isFIleExist" + fileName);
		if ((fileName == null) || (fileName.length() == 0))
			return false;
		File localFile = new File(fileName);
		return ((localFile.exists()) && (!(localFile.isDirectory())));
	}

	/**
	 * 列举某个目录下某种类型文件
	 * 
	 * @param folder
	 * @param suffix
	 * @return
	 */
	public static String[] listFilebySuffix(String folder, String suffix) {
		logger.info("listFilebySuffix" + folder);
		SuffixFileFilter fileFilter1 = new SuffixFileFilter(suffix);
		NotFileFilter fileFilter2 = new NotFileFilter(DirectoryFileFilter.INSTANCE);
		AndFileFilter fileNameFilter = new AndFileFilter(fileFilter1, fileFilter2);
		return new File(folder).list(fileNameFilter);
	}

	/**
	 * 采用UTF-8编码方式读取文件内容
	 * 
	 * @param fileName
	 * @return
	 */
	public static String readFileByLines(String fileName) {

		return readFileByLines(fileName, "UTF-8");
	}

	/**
	 * 根据指定编码格式读取文件内容
	 * 
	 * @param fileName
	 *            文件路径
	 * @param encode
	 *            编码格式
	 * @return
	 */
	public static String readFileByLines(String fileName, String encode) {
		logger.info("readFileByLines" + fileName);
		File file = new File(fileName);
		BufferedReader bReader = null;
		String tempStr = null;
		String result = "";
		try {
			bReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), encode));
			// 一次读入一行，直到读入null为文件结束
			while ((tempStr = bReader.readLine()) != null)
				result = result + tempStr;
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		} finally {
			if (bReader != null)
				try {
					bReader.close();
				} catch (IOException e) {
				}
		}
	}

	/**
	 * 获取文件大小
	 * 
	 * @param file
	 *            待计算文件
	 * @return 文件大小
	 * @throws Exception
	 */
	public static long getFileSize(File file) throws Exception {
		long size = 0;
		if (file.exists()) {
			FileInputStream fileInputStream = null;
			fileInputStream = new FileInputStream(file);
			size = fileInputStream.available();
			fileInputStream.close();
		}
		return size;
	}

	/**
	 * 读文件
	 * 
	 * @param fileName
	 * @return 文件内容
	 */
	public static String readFile(String fileName) {

		String outPut = "";
		File file = new File(fileName);
		if (file.exists())
			if (file.isFile()) {
				try {

					@SuppressWarnings("resource")
					BufferedReader input = new BufferedReader(new FileReader(file));
					StringBuffer buffer = new StringBuffer();
					String text;
					while ((text = input.readLine()) != null) {
						buffer.append(text);
					}
					outPut = buffer.toString();
				} catch (IOException localIOException) {
					System.err.println("File Error!");
				}
			} else if (file.isDirectory()) {
				String[] dir = file.list();
				outPut = outPut + "Directory contents:/n";
				for (int i = 0; i < dir.length; ++i)
					outPut = outPut + dir[i] + "/n";
			} else
				System.err.println("Does not exist!");
		return outPut;
	}

	/**
	 * File对象转换为Byte数组
	 * 
	 * @param file
	 * @return
	 */
	public static byte[] getBytesFromFile(File file) {
		if (file == null)
			return null;
		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int i;
			while ((i = fileInputStream.read(b)) != -1) {

				outputStream.write(b, 0, i);
			}
			fileInputStream.close();
			outputStream.close();
			return outputStream.toByteArray();
		} catch (IOException e) {
		}
		return null;
	}

	/**
	 * 创建临时文件夹
	 * 
	 * @param realPath
	 * @return
	 */
	public static String createTempFile(String realPath) {
		String tempPath = realPath + "/temp";

		// 如果temp文件夹不存在，则创建
		File tempFile = new File(tempPath);
		if (!(tempFile.exists()))
			tempFile.mkdirs();


		// 上传临时文件夹目录
		String uploadpath = tempPath + "/" + System.currentTimeMillis();
		File uploadFile = new File(uploadpath);
		if (!(uploadFile.exists()))
			uploadFile.mkdirs();
		return uploadpath;
	}

	public static void write(String path, String content) {
		String str1 = new String();
		String str2 = new String();
		try {
			File localFile = new File(path);
			if (localFile.exists())
				logger.info(path + "文件存在");
			else if (localFile.createNewFile())
				logger.info("文件创建成功！");
			else
				logger.info("文件创建失败！");

			BufferedReader input = new BufferedReader(new FileReader(localFile));
			while ((str1 = input.readLine()) != null)
				str2 = str2 + str1 + "\n";
			input.close();
			str2 = str2 + content;

			BufferedWriter ouput = new BufferedWriter(new FileWriter(localFile));
			ouput.write(str2);
			ouput.close();
		} catch (Exception localException) {
			localException.printStackTrace();
		}
	}

	/**
	 * 获取文件的字节值
	 * 
	 * @param file
	 *            上传文件
	 * @return
	 * @throws IOException
	 */
	public static byte[] getFileByte(File file) throws IOException {
		int length = (int) file.length();
		byte[] buffer = new byte[length];
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			fis.read(buffer);
		} finally {
			if (null != fis)
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return buffer;
	}
}