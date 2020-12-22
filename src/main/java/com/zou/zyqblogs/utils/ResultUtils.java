package com.zou.zyqblogs.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ResultUtils implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int code; //请求状态
	private String msg; //返回信息
	private Object obj; //返回数据
	
	public ResultUtils(CodeEnum ce,Object obj) {
		this.code=ce.getCode();
		this.msg=ce.getMsg();
		this.obj=obj;
	}
}