/*
 *    Copyright 2012-2013 The Haohui Network Corporation
 */
package Common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * <pre>
 * Java 甯哥敤娴佸鐞嗗伐鍏� * </pre>
 * 
 * @project baidamei
 * @author cevencheng <cevencheng@gmail.com>
 * @create 2012-11-29 涓嬪崍5:17:32
 */
public class StreamTool {
	
	/**
	 * 灏嗘祦鍙﹀瓨涓烘枃浠�	 * 
	 * @param is
	 * @param outfile
	 */
	public void streamSaveAsFile(InputStream is, File outfile) {
		FileOutputStream fos = null;
		try {
			File file = outfile;
			fos = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			int len;
			while((len = is.read(buffer)) > 0){
				fos.write(buffer, 0, len);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
				is.close();
				fos.close();
			} catch (Exception e2) {
				e2.printStackTrace();
				throw new RuntimeException(e2);
			}
		}
	}
	/**
	 * Read an input stream into a string
	 * @param in
	 * @return
	 * @throws IOException
	 */
	static public String streamToString(InputStream in) throws IOException {
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = in.read(b)) != -1;) {
			out.append(new String(b, 0, n));
		}
		return out.toString();
	}
	public static byte[] stream2Byte(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int len = 0;
		byte[] b = new byte[1024];
		while ((len = is.read(b, 0, b.length)) != -1) {                     
		    baos.write(b, 0, len);
		}
		byte[] buffer =  baos.toByteArray();
		return buffer;
	}
	/** 
     * @鏂规硶鍔熻兘 InputStream 杞负 byte 
     * @param InputStream 
     * @return 瀛楄妭鏁扮粍 
     * @throws Exception 
     */  
    public static byte[] inputStream2Byte(InputStream inStream)  
            throws Exception {  
        // ByteArrayOutputStream outSteam = new ByteArrayOutputStream();  
        // byte[] buffer = new byte[1024];  
        // int len = -1;  
        // while ((len = inStream.read(buffer)) != -1) {  
        // outSteam.write(buffer, 0, len);  
        // }  
        // outSteam.close();  
        // inStream.close();  
        // return outSteam.toByteArray();  
        int count = 0;  
        while (count == 0) {  
            count = inStream.available();  
        }  
        byte[] b = new byte[count];  
        inStream.read(b);  
        return b;  
    }  
  
    /** 
     * @鏂规硶鍔熻兘 byte 杞负 InputStream 
     * @param 瀛楄妭鏁扮粍 
     * @return InputStream 
     * @throws Exception 
     */  
    public static InputStream byte2InputStream(byte[] b) throws Exception {  
        InputStream is = new ByteArrayInputStream(b);  
        return is;  
    }  
  
    /** 
     * @鍔熻兘 鐭暣鍨嬩笌瀛楄妭鐨勮浆鎹�
     * @param 鐭暣鍨�
     * @return 涓や綅鐨勫瓧鑺傛暟缁�
     */  
    public static byte[] shortToByte(short number) {  
        int temp = number;  
        byte[] b = new byte[2];  
        for (int i = 0; i < b.length; i++) {  
            b[i] = new Integer(temp & 0xff).byteValue();// 灏嗘渶浣庝綅淇濆瓨鍦ㄦ渶浣庝綅  
            temp = temp >> 8; // 鍚戝彸绉�浣� 
        }  
        return b;  
    }  
  
    /** 
     * @鍔熻兘 瀛楄妭鐨勮浆鎹笌鐭暣鍨�
     * @param 涓や綅鐨勫瓧鑺傛暟缁�
     * @return 鐭暣鍨�
     */  
    public static short byteToShort(byte[] b) {  
        short s = 0;  
        short s0 = (short) (b[0] & 0xff);// 鏈�綆浣� 
        short s1 = (short) (b[1] & 0xff);  
        s1 <<= 8;  
        s = (short) (s0 | s1);  
        return s;  
    }  
  
    /** 
     * @鏂规硶鍔熻兘 鏁村瀷涓庡瓧鑺傛暟缁勭殑杞崲 
     * @param 鏁村瀷 
     * @return 鍥涗綅鐨勫瓧鑺傛暟缁�
     */  
    public static byte[] intToByte(int i) {  
        byte[] bt = new byte[4];  
        bt[0] = (byte) (0xff & i);  
        bt[1] = (byte) ((0xff00 & i) >> 8);  
        bt[2] = (byte) ((0xff0000 & i) >> 16);  
        bt[3] = (byte) ((0xff000000 & i) >> 24);  
        return bt;  
    }  
  
    /** 
     * @鏂规硶鍔熻兘 瀛楄妭鏁扮粍鍜屾暣鍨嬬殑杞崲 
     * @param 瀛楄妭鏁扮粍 
     * @return 鏁村瀷 
     */  
    public static int bytesToInt(byte[] bytes) {  
        int num = bytes[0] & 0xFF;  
        num |= ((bytes[1] << 8) & 0xFF00);  
        num |= ((bytes[2] << 16) & 0xFF0000);  
        num |= ((bytes[3] << 24) & 0xFF000000);  
        return num;  
    }  
  
    /** 
     * @鏂规硶鍔熻兘 瀛楄妭鏁扮粍鍜岄暱鏁村瀷鐨勮浆鎹�
     * @param 瀛楄妭鏁扮粍 
     * @return 闀挎暣鍨�
     */  
    public static byte[] longToByte(long number) {  
        long temp = number;  
        byte[] b = new byte[8];  
        for (int i = 0; i < b.length; i++) {  
            b[i] = new Long(temp & 0xff).byteValue();  
            // 灏嗘渶浣庝綅淇濆瓨鍦ㄦ渶浣庝綅  
            temp = temp >> 8;  
            // 鍚戝彸绉�浣� 
        }  
        return b;  
    }  
  
    /** 
     * @鏂规硶鍔熻兘 瀛楄妭鏁扮粍鍜岄暱鏁村瀷鐨勮浆鎹�
     * @param 瀛楄妭鏁扮粍 
     * @return 闀挎暣鍨�
     */  
    public static long byteToLong(byte[] b) {  
        long s = 0;  
        long s0 = b[0] & 0xff;// 鏈�綆浣� 
        long s1 = b[1] & 0xff;  
        long s2 = b[2] & 0xff;  
        long s3 = b[3] & 0xff;  
        long s4 = b[4] & 0xff;// 鏈�綆浣� 
        long s5 = b[5] & 0xff;  
        long s6 = b[6] & 0xff;  
        long s7 = b[7] & 0xff; // s0涓嶅彉  
        s1 <<= 8;  
        s2 <<= 16;  
        s3 <<= 24;  
        s4 <<= 8 * 4;  
        s5 <<= 8 * 5;  
        s6 <<= 8 * 6;  
        s7 <<= 8 * 7;  
        s = s0 | s1 | s2 | s3 | s4 | s5 | s6 | s7;  
        return s;  
    }  
}
