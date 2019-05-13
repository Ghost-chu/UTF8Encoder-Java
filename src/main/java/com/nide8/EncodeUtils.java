package com.nide8;

import lombok.Cleanup;
import lombok.NonNull;

import java.io.*;
import java.util.BitSet;

/**
* ���빤���࣬��Ҫ����ʶ��UTF8��UTF8 BOM��GBK
* Created by dongp on 2018/1/9.
*/
public class EncodeUtils {
   private static int BYTE_SIZE = 8;
   public static String CODE_UTF8 = "UTF-8";
   public static String CODE_UTF8_BOM = "UTF-8_BOM";
   public static String CODE_GBK = "GBK";

   /**
    * ͨ���ļ�ȫ���ƻ�ȡ���뼯����
    *
    * @param fullFileName
    * @param ignoreBom
    * @return
    * @throws Exception
    */
   public static String getEncode(String fullFileName, boolean ignoreBom) throws Exception {
       //log.debug("fullFileName ; {}", fullFileName);
       BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fullFileName));
       String result = getEncode(bis, ignoreBom);
       try {bis.close();} catch (Exception ignore) {}
       return result;
   }

   /**
    * ͨ���ļ���������ȡ���뼯���ƣ��ļ�������Ϊδ��
    *
    * @param bis
    * @return
    * @throws Exception
    */
   public static String getEncode(@NonNull BufferedInputStream bis, boolean ignoreBom) throws Exception  {
       bis.mark(0);

       String encodeType = "";
       byte[] head = new byte[3];
       bis.read(head);
       if (head[0] == -1 && head[1] == -2) {
           encodeType = "UTF-16";
       } else if (head[0] == -2 && head[1] == -1) {
           encodeType = "Unicode";
       } else if (head[0] == -17 && head[1] == -69 && head[2] == -65) { //��BOM
           if (ignoreBom) {
               encodeType = CODE_UTF8;
           } else {
               encodeType = CODE_UTF8_BOM;
           }
       } else if ("Unicode".equals(encodeType)) {
           encodeType = "UTF-16";
       } else if (isUTF8(bis)) {
           encodeType = CODE_UTF8;
       } else {
           encodeType = CODE_GBK;
       }
       //log.info("result encode type : " + encodeType);
       return encodeType;
   }

   /**
    * �Ƿ�����BOM��UTF8��ʽ�����жϳ��泡����ֻ������BOM UTF8��GBK
    *
    * @param bis
    * @return
    */
   private static boolean isUTF8(@NonNull BufferedInputStream bis) throws Exception {
       bis.reset();

       //��ȡ��һ���ֽ�
       int code = bis.read();
       do {
           BitSet bitSet = convert2BitSet(code);
           //�ж��Ƿ�Ϊ���ֽ�
           if (bitSet.get(0)) {//���ֽ�ʱ���ٶ�ȡN���ֽ�
               if (!checkMultiByte(bis, bitSet)) {//δ���ͨ��,ֱ�ӷ���
                   return false;
               }
           } else {
               //���ֽ�ʱʲô�����������ٴζ�ȡ�ֽ�
           }
           code = bis.read();
       } while (code != -1);
       return true;
   }

   /**
    * �����ֽڣ��ж��Ƿ�Ϊutf8���Ѿ���ȡ��һ���ֽ�
    *
    * @param bis
    * @param bitSet
    * @return
    */
   private static boolean checkMultiByte(@NonNull BufferedInputStream bis, @NonNull BitSet bitSet) throws Exception {
       int count = getCountOfSequential(bitSet);
       byte[] bytes = new byte[count - 1];//�Ѿ���ȡ��һ���ֽڣ������ٶ�ȡ
       bis.read(bytes);
       for (byte b : bytes) {
           if (!checkUtf8Byte(b)) {
               return false;
           }
       }
       return true;
   }

   /**
    * ��ⵥ�ֽڣ��ж��Ƿ�Ϊutf8
    *
    * @param b
    * @return
    */
   private static boolean checkUtf8Byte(byte b) throws Exception {
       BitSet bitSet = convert2BitSet(b);
       return bitSet.get(0) && !bitSet.get(1);
   }

   /**
    * ���bitSet�дӿ�ʼ�ж��ٸ�������1
    *
    * @param bitSet
    * @return
    */
   private static int getCountOfSequential(@NonNull BitSet bitSet) {
       int count = 0;
       for (int i = 0; i < BYTE_SIZE; i++) {
           if (bitSet.get(i)) {
               count++;
           } else {
               break;
           }
       }
       return count;
   }


   /**
    * ������תΪBitSet
    *
    * @param code
    * @return
    */
   private static BitSet convert2BitSet(int code) {
       BitSet bitSet = new BitSet(BYTE_SIZE);

       for (int i = 0; i < BYTE_SIZE; i++) {
           int tmp3 = code >> (BYTE_SIZE - i - 1);
           int tmp2 = 0x1 & tmp3;
           if (tmp2 == 1) {
               bitSet.set(i);
           }
       }
       return bitSet;
   }
   
   public static boolean isEmptyFile(File wantedFile) {
	   if(wantedFile.length()==0)
		   return true;
	   return false;
   }
   public static boolean isEmptyFile(String wantedPath) {
	   File wantedFile = new File(wantedPath);
	   if(wantedFile.length()==0)
		   return true;
	   return false;
   }

   /**
    * ��һָ��������ļ�ת��Ϊ��һ������ļ�
    *
    * @param oldFullFileName
    * @param oldCharsetName
    * @param newFullFileName
    * @param newCharsetName
    */
   public static void convert(File oldFile, String oldCharsetName, File newFile, String newCharsetName) throws Exception {
       //log.info("the old file name is : {}, The oldCharsetName is : {}", oldFullFileName, oldCharsetName);
       //log.info("the new file name is : {}, The newCharsetName is : {}", newFullFileName, newCharsetName);
	   boolean isBOM = false;
	   if(oldCharsetName.equals("UTF-8_BOM")) {
		   isBOM = true;
		   oldCharsetName = "UTF-8"; //��ƭ��ȡ�������ǻ����ж�����д���
	   }
	   StringBuffer content = new StringBuffer();
       @Cleanup
       BufferedReader bin = new BufferedReader(new InputStreamReader(new FileInputStream(oldFile), oldCharsetName));
       String line;
       while ((line = bin.readLine()) != null) {
    	   if(isBOM) {
    		   char[] bomChars = line.toCharArray();
    		   char[] noneBomchar = new char[bomChars.length - 1];
    		   line = String.valueOf(noneBomchar);
    		   UTF8Encoder.echo("����ת�� >> ��⵽UTF8-BOMͷ���ѽ��д���");
    		   isBOM=false;//reset
    	   }
           content.append(line);
           content.append(System.getProperty("line.separator"));
       }
       @Cleanup
       Writer out = new OutputStreamWriter(new FileOutputStream(newFile), newCharsetName);
       out.write(content.toString());
       try {bin.close();} catch (Exception ignore) {}
       try {out.close();} catch (Exception ignore) {}
   }

}