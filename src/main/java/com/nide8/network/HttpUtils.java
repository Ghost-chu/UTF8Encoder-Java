package com.nide8.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import com.nide8.UTF8Encoder;

public class HttpUtils {

    /**
     * ��ָ��URL����GET����������
     * 
     * @param url
     *            ���������URL
     * @param param
     *            ����������������Ӧ���� name1=value1&name2=value2 ����ʽ��
     * @return URL ������Զ����Դ����Ӧ���
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
        	String urlNameString;
            if(param!=null)
            	urlNameString = url + "?" + param;
            else 
            	urlNameString = url;
            URL realUrl = new URL(urlNameString);
            // �򿪺�URL֮�������
            URLConnection connection = realUrl.openConnection();
            // ����ͨ�õ���������
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            StringBuffer buffer = new StringBuffer(); //UA����Ϊ����ͳ�����ݣ�����: �汾�š������ڵ��û�����Ŀ¼
            buffer.append("Public Build v1.0.0 # ");  //Ϊɶ��ô���� �Ҿ��� [root@localhost] �����ӳ�˧!
            buffer.append("[");                       //ʵ���ϻ���ֻ�а汾������....
            buffer.append(System.getProperty("user.name"));
            buffer.append("@");
            buffer.append(System.getProperty("user.home"));
            buffer.append("]");
            connection.setRequestProperty("user-agent",buffer.toString());
            // ����ʵ�ʵ�����
            connection.connect();
            // ��ȡ������Ӧͷ�ֶ�
            //Map<String, List<String>> map = connection.getHeaderFields();
            // �������е���Ӧͷ�ֶ�
//            for (String key : map.keySet()) {
//                UTF8Encoder.echo(key + "--->" + map.get(key));
//            }
            // ���� BufferedReader����������ȡURL����Ӧ
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            UTF8Encoder.echo("����GET��������쳣��" + e);
            e.printStackTrace();
        }
        // ʹ��finally�����ر�������
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
 
    /**
     * ��ָ�� URL ����POST����������
     * 
     * @param url
     *            ��������� URL
     * @param param
     *            ����������������Ӧ���� name1=value1&name2=value2 ����ʽ��
     * @param isproxy
     *               �Ƿ�ʹ�ô���ģʽ
     * @return ������Զ����Դ����Ӧ���
     */
    public static String sendPost(String url, String param) {
    	//UTF8Encoder.echo(param);
        OutputStreamWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn = null;
           
            conn = (HttpURLConnection) realUrl.openConnection();
            
            // �򿪺�URL֮�������
 
            // ����POST�������������������
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");    // POST����
 
            // ����ͨ�õ���������
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "nide8");
            //conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
 
            conn.connect();
 
            // ��ȡURLConnection�����Ӧ�������
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            // �����������
            out.write(param);
            // flush������Ļ���
            out.flush();
            // ����BufferedReader����������ȡURL����Ӧ
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            UTF8Encoder.echo("���� POST ��������쳣��"+e);
            e.printStackTrace();
        }
        //ʹ��finally�����ر��������������
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }
}
