package com.zou.zyqblogs.utils;

public enum CodeEnum {
	SUCCED(0,"查询成功"),BEDEFEATED(1,"查询失败")
	,VERIFY(2,"验证码错误"),USERPASS(3,"账号或密码错误")
	,OLDPASS(4,"旧密码错误"),COVERICON(5,"请上传封面图")
	,LOCKED(6,"该用户已被锁定，请联系管理员解除");
	
	private int code;
	private String msg;
	
	private CodeEnum(int code,String msg) {
		this.code=code;
		this.msg=msg;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public String getMsg() {
		return this.msg;
	}
}