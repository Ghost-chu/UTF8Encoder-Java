package com.nide8.network;

public class PullResult {
	//GsonתPullResult����ȡ����������
	String[] white;
	String[] black;
	public PullResult(String[] white,String[] black) {
		
		this.white=white;
		this.black=black;
		
	}
	public String[] getWhite() {
		return white;
	}
	public String[] getBlack() {
		return black;
	}
	public void setBlack(String[] black) {
		this.black = black;
	}
	public void setWhite(String[] white) {
		this.white = white;
	}
}
