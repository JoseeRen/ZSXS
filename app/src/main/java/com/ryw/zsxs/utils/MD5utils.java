/*
 * Create on 2017-6-8 下午4:11
 * FileName: MD5utils.java
 * Author: Ren Yaowei
 * Blog: http://www.renyaowei.top
 * Email renyaowei@foxmail.com
 */

package com.ryw.zsxs.utils;

import java.security.MessageDigest;


public class MD5utils {
    public static String encode(String psd){
		//psd=psd+"akgmahajdhaj";加盐
    	
    	//得到MD5加密算法的对象
    	try {
			MessageDigest digest = MessageDigest.getInstance("md5");
		    //将密码的字节加密为字节数组
			byte[] result = digest.digest(psd.getBytes());
			StringBuilder sb=new StringBuilder();
			
			for(byte b:result){
				int number=b&0xff;
				//将上面int类型的数转为16进制
				String numberStr=Integer.toHexString(number);
				if(numberStr.length()==1){
					sb.append("0");
				}
				sb.append(numberStr);
			}
			return sb.toString();
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	return null;
    	
    }
}
