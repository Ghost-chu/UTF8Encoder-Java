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
		//Ϊnide8.com���ƴ˹��ߣ�����Ϊ���������ļ���
		UTF8Encoder.echo("UTF-8ת���� by Ghost_chu");
		UTF8Encoder.echo("���Եȣ����ڳ�ʼ��ɨ��...");
		root = new File(".");
		UTF8Encoder.echo("�����ù���Ŀ¼Ϊ��"+root.toString());
		bak_root = new File(".","ת�뱸���ļ���");
		if(!bak_root.exists())
			bak_root.mkdirs();
		UTF8Encoder.echo("�����ñ���Ŀ¼Ϊ��"+bak_root.toString());
		UTF8Encoder.echo("���ڴ��ƶ˷�������ȡ����...");
		filenameExtension = new FilenameExtension();
		if(!filenameExtension.successGetDataFromServer()) {
				UTF8Encoder.echo("���ݳ�ʼ��ʧ�ܣ���ֹͣ������");
				return;
		}
		UTF8Encoder.echo("����ʼ��ɨ�蹤��...");
		fileScanner = new FileScanner();
		UTF8Encoder.echo("�뷢������confirm�Կ�ʼ�ļ�ת�����");
	}
	public static FilenameExtension getFilenameExtension() {
		return filenameExtension;
	}
	public static FileScanner getFileScanner() {
		return fileScanner;
	}
}
