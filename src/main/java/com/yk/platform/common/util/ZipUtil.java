package com.yk.platform.common.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;

import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class ZipUtil {
	static final int BUFFER = 8192;
	static Logger logger = Logger.getLogger(ZipUtil.class);

	/**
	 * unzip an archive
	 * 
	 * @param sourcePath
	 * @param destinationPath
	 * @param password
	 */
	public static String unzip(String sourcePath, String destinationPath, String password) {
		try {
			ZipFile zipFile = new ZipFile(sourcePath);
			if (zipFile.isEncrypted()) {
				zipFile.setPassword(password);
			}
			zipFile.extractAll(destinationPath);
			File file = zipFile.getFile();
			return file.getName();
		} catch (ZipException e) {
			logger.error("unzip file failed", e);
			return null;
		}
	}

	private static void compress(File file, ZipOutputStream out, String basedir) {
		/* 判断是目录还是文件 */
		if (file.isDirectory()) {
			compressDirectory(file, out, basedir);
		} else {
			compressFile(file, out, basedir);
		}
	}

	/**
	 * 对目录进行压缩
	 * 
	 * @param dir
	 * @param out
	 * @param basedir
	 */
	private static void compressDirectory(File dir, ZipOutputStream out, String basedir) {
		if (!dir.exists())
			return;

		File[] files = dir.listFiles();
		if (null != files && files.length > 0) {
			for (int i = 0; i < files.length; i++) {
				compress(files[i], out, basedir + dir.getName() + "/");
			}
		}
	}

	/**
	 * 对文件进行压缩
	 * 
	 * @param file
	 * @param out
	 * @param basedir
	 */
	private static void compressFile(File file, ZipOutputStream out, String basedir) {
		if (!file.exists()) {
			return;
		}
		try {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			ZipEntry entry = new ZipEntry(basedir + file.getName());
			out.putNextEntry(entry);
			int count;
			byte data[] = new byte[BUFFER];
			while ((count = bis.read(data, 0, BUFFER)) != -1) {
				out.write(data, 0, count);
			}
			bis.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void zip(String srcPathName, String zipFileName) {
		File file = new File(srcPathName);
		File zipFile = new File(zipFileName);
		if (!file.exists())
			throw new RuntimeException(srcPathName + "不存在");
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
			CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream, new CRC32());
			ZipOutputStream out = new ZipOutputStream(cos);
			out.setEncoding(System.getProperty("sun.jnu.encoding"));
			String basedir = "";
			compress(file, out, basedir);
			out.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
