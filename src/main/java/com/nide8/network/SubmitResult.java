package com.nide8.network;

public class SubmitResult {
	//Gson转SubmitResult，获取提交后的返回结果
	String[] unknown;
	public SubmitResult(String[] unknown) {
		this.unknown = unknown;
	}
	public String[] getUnknown() {
		return unknown;
	}
	public void setUnknown(String[] unknown) {
		this.unknown = unknown;
	}
}
