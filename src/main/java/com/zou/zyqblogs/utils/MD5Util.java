package com.zou.zyqblogs.utils;

import java.security.MessageDigest;
import java.util.Arrays;

public class MD5Util {
	/***
	 * MD5加密 生成32位md5码
	 * 
	 * @param 待加密字符串
	 * @return 返回32位md5码
	 */
	public static String md5Encode(String inStr) throws Exception {
		//MessageDigest 类为应用程序提供信息摘要算法的功能，如 MD5 或 SHA 算法。
		MessageDigest md5 = null;
		try {
			//返回实现指定摘要算法的 MessageDigest 对象。algorithm - 所请求算法的名称
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}

		//将传递过来的字符串，转为UTF-8字符集的byte数组
		byte[] byteArray = inStr.getBytes("UTF-8");
//		System.out.println("byte==="+Arrays.toString(byteArray));
		//通过执行诸如填充之类的最终操作完成哈希计算。在调用此方法之后，摘要被重置。
		byte[] md5Bytes = md5.digest(byteArray);
//		System.out.println("md5==="+Arrays.toString(md5Bytes));
		//创建StringBuffer对象（增强类）
		StringBuffer hexValue = new StringBuffer();
		//循环md5Bytes，使其每一个字节
		// 跟0xff(是16进制的表达方式，F是15；十进制为：255，二进制为：1111 1111)进行&位运算
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
//			System.out.println("位运算结果===="+val+"==="+Integer.toHexString(val));
			if (val < 16) {
				hexValue.append("0");
			}
			//将运算完的结果转为16进制字符串
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();//返回
	}


}