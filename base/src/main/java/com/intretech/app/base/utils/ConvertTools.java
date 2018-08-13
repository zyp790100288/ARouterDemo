package com.intretech.app.base.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.DecimalFormat;

public class ConvertTools {
	public int macStringToInt(String macString) {
		String macTemp;
		macTemp = getLow4BytesOfMac(macString);
		byte[] macBytes = hexStringToBytes(macTemp);
		return byteToInt(macBytes);
	}

	public String getLow4BytesOfMac(String macString) {
		macString = macString.substring(6);
		macString = macString.replace("-", "");
		return macString;
	}

	/*
	 * Convert byte[] to hex
	 * 
	 * @param src byte[] data
	 * 
	 * @return hex string
	 */
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	public static String oneByteToHexString(byte oneByte) {
		StringBuilder stringBuilder = new StringBuilder("");
		int v = oneByte & 0xFF;
		String hv = Integer.toHexString(v);
		if (hv.length() < 2) {
			stringBuilder.append(0);
		}
		stringBuilder.append(hv);
		return stringBuilder.toString();
	}

	/**
	 * Convert hex string to byte[]
	 * 
	 * @param hexString
	 *            the hex string
	 * @return byte[]
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	/**
	 * Convert char to byte
	 * 
	 * @param c
	 *            char
	 * @return byte
	 */
	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	/**
	 * 转换short为byte
	 * 
	 * @param number
	 * @return
	 */
	public static byte[] shortToByte(short number) {
		int temp = number;
		byte[] b = new byte[2];
		for (int i = 0; i < b.length; i++) {
			b[i] = new Integer(temp & 0xff).byteValue();
			temp = temp >> 8;
		}
		return b;
	}

	/**
	 * 转换byte为short
	 * 
	 * @param b
	 * @return
	 */
	public static short byteToShort(byte[] b) {
		if (b == null)
			return 0;

		short s = 0;
		short s0 = (short) (b[0] & 0xff);// ���λ
		short s1 = (short) (b[1] & 0xff);
		s1 <<= 8;
		s = (short) (s0 | s1);
		return s;
	}

	 /**
	  * 将short数组转化为byte数组
	  *  	高位在前，低位在后
	  * @param dataType
	  * @param data
	  * @return
	  */
	 public static  byte[] shortArrayToByteArray(byte dataType,short[] data ){
		 int x=0;
		 byte[] byteData=new byte[data.length*2+1];
		 byteData[x]=dataType; 
		 for (int i = 0; i < data.length; i++) {

			 byte[] bs=shortToByte(data[i]);
			 for (int j = 0; j < bs.length; j++) {
				x++;
				byteData[x] = bs[bs.length-1-j];
			}
		}
		 
		return byteData;
		 
	 }
	 
	 /**
	  * 将short数组转化为byte数组
	  *  	高位在前，低位在后
	  * @param data
	  * @return
	  */
	 public static  byte[] shortArrayToByteArray(short[] data ){
		 int x=0;
		 byte[] byteData=new byte[data.length*2];
		
		 for (int i = 0; i < data.length; i++) {

			 byte[] bs=shortToByte(data[i]);
			 for (int j = 0; j < bs.length; j++) {
			
				byteData[x] = bs[bs.length-1-j];	
				x++;
			}
		}
		 
		return byteData;
		 
	 }
	 
	 /**
	  * 将byte数组转化为short数据
	  * 	高位在前，低位在后的情况
	  * @param data
	  * @return
	  */
	 public static short[] byteArrayToShortArray(byte[] data){
		 short[] newData =new short[data.length/2];
		 for (int i = 0; i < data.length; i++) {
			if(i%2==1){
				byte[] tempArray = {data[i],data[i-1]};
				newData[(i-1)/2] = byteToShort(tempArray);
			}
		}
		return newData;
		 
	 }
	 
	 
	/** 转换long为byte **/
	public static byte[] longToByte(long number) {
		long temp = number;
		byte[] b = new byte[8];
		for (int i = 0; i < b.length; i++) {
			b[i] = new Long(temp & 0xff).byteValue();
			temp = temp >> 8; 
		}
		return b;
	}

	/** 转换byte为long **/
	public static long byteToLong(byte[] b) {
		long s = 0;
		long s0 = b[0] & 0xff;// ���λ
		long s1 = b[1] & 0xff;
		long s2 = b[2] & 0xff;
		long s3 = b[3] & 0xff;
		long s4 = b[4] & 0xff;// ���λ
		long s5 = b[5] & 0xff;
		long s6 = b[6] & 0xff;
		long s7 = b[7] & 0xff;

		// s0����
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

	/**
	 * 转换int为byte
	 * 
	 * @param number
	 * @return
	 */
	public static byte[] intToByte(int number) {
		int temp = number;
		byte[] b = new byte[4];
		for (int i = 0; i < b.length; i++) {
			b[i] = new Integer(temp & 0xff).byteValue();//
			temp = temp >> 8; // ������8λ
		}
		return b;
	}

	/**
	 * 转换byte为int
	 * 
	 * @param b
	 * @return
	 */
	public static int byteToInt(byte[] b) {
		int s = 0;
		int s0 = b[0] & 0xff;//
		int s1 = b[1] & 0xff;
		int s2 = b[2] & 0xff;
		int s3 = b[3] & 0xff;
		s3 <<= 24;
		s2 <<= 16;
		s1 <<= 8;
		s = s0 | s1 | s2 | s3;
		return s;
	}

	/** 转换字符串为数字 */
	public static int strToInt(String value, int defaultValue) {
		try {
			return Integer.valueOf(value);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/** 转换字符串为数字 */
	public static long strToInt(String value, long defaultValue) {
		try {
			return Long.valueOf(value);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/** 转换16进制字符串为数字 */
	public static int hexToInt(String value, int defaultValue) {
		try {
			return Integer.parseInt(value, 16);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/** 转换16进制字符串为数字 */
	public static long hexToInt(String value, long defaultValue) {
		try {
			return Long.parseLong(value, 16);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/** 转换字符串为数字 */
	public static float strToFloat(String value, float defaultValue) {
		try {
			return Float.valueOf(value);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/** 转换字符串为数字 */
	public static double strToDouble(String value, double defaultValue) {
		try {
			return Double.valueOf(value);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/** 通过四舍五入的方式转换float为int **/
	public static int floatToInt(float value) {
		if (value > 0) {
			return (int) (value + 0.5);
		} else if (value < 0) {
			return (int) (value - 0.5);
		} else {
			return 0;
		}
	}
	/**
	 * 将float转成byte[]
	 * @param val
	 * @return byte[]
	 */
	public static byte[] float2ByteArray(float val) {
		return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putFloat(val).array();
	}




	/**
	 * 将byte[]转成float
	 * @param data
	 * @return float
	 */
	public static float byteArray2Float(byte[] data) {
		if (data == null || data.length < 4) {
			return -1234.0f;
		}
		return ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN).getFloat();
	}

	/**
	 *
	 * <pre>
	 * 将int转换为32位byte.
	 * 实际上每个8位byte只存储了一个0或1的数字
	 * 比较浪费.
	 * </pre>
	 *
	 * @param num
	 * @return
	 */
	public static byte[] intToByte32(int num) {
		byte[] arr = new byte[32];
		for (int i = 31; i >= 0; i--) {
			// &1 也可以改为num&0x01,表示取最地位数字.
			arr[i] = (byte) (num & 1);
			// 右移一位.
			num >>= 1;
		}
		return arr;
	}

	/**
	 * 
	 * @param byteForConvert
	 * @param convertFormat
	 *            "#0.0" 表示保留一位小数 "#0.00"表示保留两位小数
	 * @return String convResult
	 *                ("字节单位数值或格式有误");
	 */
	public static String setByteUnitConvert(long byteForConvert,
                                            String convertFormat) {
		float convNum = 0;
		String unit = "B";
		float convByte = byteForConvert;
		DecimalFormat df = new DecimalFormat(convertFormat);
		// 字节单位换算
		if (convByte >= 1024 * 1024) {
			// 1MB
			convNum = convByte / 1024 / 1024;
			unit = "MB";
		} else if (convByte >= 1024) {
			// 1KB
			convNum = convByte / 1024;
			unit = "KB";
		} else if (convByte >= 0) {
			// 1B
			convNum = convByte;
			unit = "B";
		} else {

		}
		return df.format(convNum) + unit;
	}

	//10进制转16进制
	public static String IntToHex(int n){
		char[] ch = new char[20];
		int nIndex = 0;
		while ( true ){
			int m = n/16;
			int k = n%16;
			if ( k == 15 )
				ch[nIndex] = 'F';
			else if ( k == 14 )
				ch[nIndex] = 'E';
			else if ( k == 13 )
				ch[nIndex] = 'D';
			else if ( k == 12 )
				ch[nIndex] = 'C';
			else if ( k == 11 )
				ch[nIndex] = 'B';
			else if ( k == 10 )
				ch[nIndex] = 'A';
			else
				ch[nIndex] = (char)('0' + k);
			nIndex++;
			if ( m == 0 )
				break;
			n = m;
		}
		StringBuffer sb = new StringBuffer();
		sb.append(ch, 0, nIndex);
		sb.reverse();
		String strHex = new String("");
		strHex += sb.toString();
		return strHex;
	}

	//16进制转10进制
	public static int HexToInt(String strHex){
		int nResult = 0;
		if ( !IsHex(strHex) )
			return nResult;
		String str = strHex.toUpperCase();
		if ( str.length() > 2 ){
			if ( str.charAt(0) == '0' && str.charAt(1) == 'X' ){
				str = str.substring(2);
			}
		}
		int nLen = str.length();
		for ( int i=0; i<nLen; ++i ){
			char ch = str.charAt(nLen-i-1);
			try {
				nResult += (GetHex(ch)*GetPower(16, i));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return nResult;
	}

	//计算16进制对应的数值
	public static int GetHex(char ch) throws Exception {
		if ( ch>='0' && ch<='9' )
			return (int)(ch-'0');
		if ( ch>='a' && ch<='f' )
			return (int)(ch-'a'+10);
		if ( ch>='A' && ch<='F' )
			return (int)(ch-'A'+10);
		throw new Exception("error param");
	}

	//计算幂
	public static int GetPower(int nValue, int nCount) throws Exception {
		if ( nCount <0 )
			throw new Exception("nCount can't small than 1!");
		if ( nCount == 0 )
			return 1;
		int nSum = 1;
		for ( int i=0; i<nCount; ++i ){
			nSum = nSum*nValue;
		}
		return nSum;
	}
	//判断是否是16进制数
	public static boolean IsHex(String strHex){
		int i = 0;
		if ( strHex.length() > 2 ){
			if ( strHex.charAt(0) == '0' && (strHex.charAt(1) == 'X' || strHex.charAt(1) == 'x') ){
				i = 2;
			}
		}
		for ( ; i<strHex.length(); ++i ){
			char ch = strHex.charAt(i);
			if ( (ch>='0' && ch<='9') || (ch>='A' && ch<='F') || (ch>='a' && ch<='f') )
				continue;
			return false;
		}
		return true;
	}

}
