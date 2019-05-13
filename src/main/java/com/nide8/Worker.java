package com.nide8;

public class Worker extends Thread {
	public void run() {
		executeThread();
	}
	public static void executeThread() {
		UTF8Encoder.echo("扫描已启动，您的文件将会自动创建备份");
		Executor.fileScanner.run();
		//扫描结束，上传数据
		UTF8Encoder.echo("正在上报扫描过程中出现的未知拓展名，以改善未来转码体验...");
		Executor.filenameExtension.uploadExtReport();
		UTF8Encoder.echo("扫描已结束，请切换您的核心");
	}
}
