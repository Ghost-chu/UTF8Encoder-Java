package com.nide8;

public class Worker extends Thread {
	public void run() {
		executeThread();
	}
	public static void executeThread() {
		UTF8Encoder.echo("ɨ���������������ļ������Զ���������");
		Executor.fileScanner.run();
		//ɨ��������ϴ�����
		UTF8Encoder.echo("�����ϱ�ɨ������г��ֵ�δ֪��չ�����Ը���δ��ת������...");
		Executor.filenameExtension.uploadExtReport();
		UTF8Encoder.echo("ɨ���ѽ��������л����ĺ���");
	}
}
