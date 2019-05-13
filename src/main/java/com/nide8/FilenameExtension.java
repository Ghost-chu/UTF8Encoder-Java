package com.nide8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.nide8.network.HttpUtils;
import com.nide8.network.PullResult;
import com.nide8.network.SubmitUnknown;

public class FilenameExtension {
	final static String[] builtInWhitelist = {"txt","log","cfg","ini","lang","json","properties","yml","toml","err","csv","sk"};
	final static String[] builtInBlacklist = {"db","dat","mca","ini","lang","json","properties","yml","toml","err","csv","sk"};
	List<String> whiteList= new ArrayList<String>();
	List<String> blackList= new ArrayList<String>();
	List<String> unknownList = new ArrayList<String>();
	boolean successGetData = false;
	final static String nide8URL = "https://gl.xn--gfs727n.com:233/transform/";
	public FilenameExtension() {
		//从这里拉取服务器数据
//		whiteList=Arrays.asList(builtInWhitelist);
//		blackList=Arrays.asList(builtInBlacklist);
		Gson gson = new Gson();
		//从麦块科技服务器拉取数据
		String originData = HttpUtils.sendGet(nide8URL, null);
		PullResult pullResult = null;
		try {
			pullResult = gson.fromJson(originData, PullResult.class);
		}catch (JsonSyntaxException e) {
			UTF8Encoder.echo("服务器错误，请联系麦块科技客服："+e.getMessage());
			UTF8Encoder.echo("服务器错误，服务器返回数据："+originData);
			return;
		}
		if(pullResult==null) {
			UTF8Encoder.echo("服务器错误，请联系麦块科技客服");
			UTF8Encoder.echo("服务器错误，服务器返回数据："+originData);
			return;
		}
		if(pullResult.getBlack()==null) {
			UTF8Encoder.echo("服务器错误，请联系麦块科技客服");
			UTF8Encoder.echo("服务器错误，服务器返回数据："+originData);
			return;
		}
		if(pullResult.getWhite()==null) {
			UTF8Encoder.echo("服务器错误，请联系麦块科技客服");
			UTF8Encoder.echo("服务器错误，服务器返回数据："+originData);
			return;
		}
		successGetData = true;
		whiteList = Arrays.asList(pullResult.getWhite());
		blackList = Arrays.asList(pullResult.getBlack());
		
		return;
		
	}
	
	public void printWhiteList() {
		StringBuffer builder = new StringBuffer();
		builder.append("支持的文件类型：");
		for (String string : whiteList) {
			builder.append(string+",");
		}
		UTF8Encoder.echo(builder.toString());
	}
	public void printBlackList() {
		StringBuffer builder = new StringBuffer();
		builder.append("排除的文件类型：");
		for (String string : blackList) {
			builder.append(string+",");
		}
		UTF8Encoder.echo(builder.toString());
	}
	public void printUnknownList() {
		StringBuffer builder = new StringBuffer();
		builder.append("未知的文件类型：");
		for (String string : unknownList) {
			builder.append(string+",");
		}
		UTF8Encoder.echo(builder.toString());
	}
	
	public boolean successGetDataFromServer() {
		return successGetData;
	}
	
	public boolean shouldConvert(String extension) {
		if(extension==null)
			return false;
		extension=extension.toLowerCase();
		if(whiteList.contains(extension))
			return true;
		if(blackList.contains(extension))
			return false;
		if(!unknownList.contains(extension))
			unknownList.add(extension);
		return false;
	}
	public boolean submitUnknownList() {
		//从这里提交服务器数据
		uploadExtReport();
		return true;
	}
	public void uploadExtReport() {
		Gson gson = new Gson();
		HttpUtils.sendPost(nide8URL, gson.toJson(new SubmitUnknown(unknownList.toArray(new String[0]))));
	}
}
