package com.nide8;

import java.io.File;

public class Executor extends Thread {
	static boolean isDebugging = false;
	static File root;
	static File bak_root;
	static FilenameExtension filenameExtension;
	static FileScanner fileScanner;
	
	public void run() {
		preInitThread();
	}
	public static void preInitThread() {
		//为nide8.com定制此工具，可作为服务器核心加载
		UTF8Encoder.echo("UTF-8转码器 by Ghost_chu");
		UTF8Encoder.echo("请稍等，正在初始化扫描...");
		root = new File(".");
		UTF8Encoder.echo("已设置工作目录为："+root.toString());
		bak_root = new File(".","转码备份文件夹");
		if(!bak_root.exists())
			bak_root.mkdirs();
		UTF8Encoder.echo("已设置备份目录为："+bak_root.toString());
		UTF8Encoder.echo("正在从云端服务器拉取数据...");
		filenameExtension = new FilenameExtension();
		if(!filenameExtension.successGetDataFromServer()) {
				UTF8Encoder.echo("数据初始化失败，请停止服务器");
				return;
		}
		UTF8Encoder.echo("正初始化扫描工具...");
		fileScanner = new FileScanner();
		UTF8Encoder.echo("请发送命令confirm以开始文件转码操作");
	}
	public static FilenameExtension getFilenameExtension() {
		return filenameExtension;
	}
	public static FileScanner getFileScanner() {
		return fileScanner;
	}
}
