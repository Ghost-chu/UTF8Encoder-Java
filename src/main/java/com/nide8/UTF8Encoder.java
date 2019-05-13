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
		//启动执行线程
		
		Executor.preInitThread();
		
		//主线程等待命令
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
			UTF8Encoder.echo("Ok, still alive"); //避免被面板服认为是服务端关了而杀进程
			break;
		case "nide8":
			UTF8Encoder.echo("Ok, still alive"); //避免被面板服认为是服务端关了而杀进程
			break;
		case "confirm":
			if(workerThread!=null && workerThread.isAlive()) {
				UTF8Encoder.echo("已有转码线程正在执行，输入/kill强制终止转码线程");
			}
			workerThread = new Worker();
			workerThread.run();
			break;
		case "kill":
			if(workerThread==null || (workerThread!=null && !workerThread.isAlive())) {
				UTF8Encoder.echo("转码线程不存在或已执行完毕");
			}
			workerThread.stop();
			UTF8Encoder.echo("转码线程已强制停止");
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
			UTF8Encoder.echo("数据已提交");
			break;
		case "lbwnb":
			UTF8Encoder.echo("请勿消费去世主播");
			break;
		case "apex":
			UTF8Encoder.echo("英雄辅助 透视自瞄 稳定不封号");
			break;
		case "rainbowsix":
			UTF8Encoder.echo("LMG Mounted and loaded");
			break;
		case "mc":
			UTF8Encoder.echo("你想来个火柴盒吗");
			break;
		case "cxk":
			UTF8Encoder.echo("全民制作人们 大家好，我是练习时长两年半的个人练习生 蔡徐坤，喜欢唱、跳、RAP、篮球，MUSIC —— 鸡你太美！");
			break;
		default:
			UTF8Encoder.echo("未知命令");
			break;
		}
		return true;
	}
	public static void echo(String text) {
		String timeString = (new java.text.SimpleDateFormat("HH:mm:ss")).format(new Date());
		System.out.println("["+timeString+"] "+text);
	}
}
