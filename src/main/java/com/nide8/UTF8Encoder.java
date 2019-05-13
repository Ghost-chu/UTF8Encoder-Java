package com.nide8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

public class UTF8Encoder extends Thread{
	static Thread executorThread;
	static Thread workerThread;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		//����ִ���߳�
		
		Executor.preInitThread();
		
		//���̵߳ȴ�����
		while (true) {
			String cmd =  br.readLine();
			if(!onCommand(cmd))
				return; //Quit program
		}
	}
	
	@SuppressWarnings("deprecation")
	public static boolean onCommand(String cmd) {
		UTF8Encoder.echo(">"+cmd);
		cmd.toLowerCase();
		switch (cmd) {
		case "stop":
			return false;
		case "list":
			UTF8Encoder.echo("Ok, still alive"); //���ⱻ������Ϊ�Ƿ���˹��˶�ɱ����
			break;
		case "nide8":
			UTF8Encoder.echo("Ok, still alive"); //���ⱻ������Ϊ�Ƿ���˹��˶�ɱ����
			break;
		case "confirm":
			if(workerThread!=null && workerThread.isAlive()) {
				UTF8Encoder.echo("����ת���߳�����ִ�У�����/killǿ����ֹת���߳�");
			}
			workerThread = new Worker();
			workerThread.run();
			break;
		case "kill":
			if(workerThread==null || (workerThread!=null && !workerThread.isAlive())) {
				UTF8Encoder.echo("ת���̲߳����ڻ���ִ�����");
			}
			workerThread.stop();
			UTF8Encoder.echo("ת���߳���ǿ��ֹͣ");
			break;
		case "printwhitelist":
			Executor.getFilenameExtension().printWhiteList();
			break;
		case "printblacklist":
			Executor.getFilenameExtension().printBlackList();
			break;
		case "printunknownlist":
			Executor.getFilenameExtension().printUnknownList();
			break;
		case "submit":
			Executor.getFilenameExtension().submitUnknownList();
			UTF8Encoder.echo("�������ύ");
			break;
		case "lbwnb":
			UTF8Encoder.echo("��������ȥ������");
			break;
		case "apex":
			UTF8Encoder.echo("Ӣ�۸��� ͸������ �ȶ������");
			break;
		case "rainbowsix":
			UTF8Encoder.echo("LMG Mounted and loaded");
			break;
		case "mc":
			UTF8Encoder.echo("��������������");
			break;
		case "cxk":
			UTF8Encoder.echo("ȫ���������� ��Һã�������ϰʱ�������ĸ�����ϰ�� ��������ϲ����������RAP������MUSIC ���� ����̫����");
			break;
		default:
			UTF8Encoder.echo("δ֪����");
			break;
		}
		return true;
	}
	public static void echo(String text) {
		String timeString = (new java.text.SimpleDateFormat("HH:mm:ss")).format(new Date());
		System.out.println("["+timeString+"] "+text);
	}
}
