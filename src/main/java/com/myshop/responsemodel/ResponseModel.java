package com.myshop.responsemodel;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;

import lombok.Getter;
/**
 * @author galip
 * 
 */
@Getter
public class ResponseModel<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private boolean isError = false;
	private String errorCode = null;
	private String errorMessage = null;
	private T data = null;
	
	public ResponseModel(T data) {
		super();
		this.isError = false;
		this.data = data;
	}
	
	public ResponseModel(Exception ex) {
		super();
		this.isError = true;
		if(ex instanceof UndeclaredThrowableException) {
			InvocationTargetException e = (InvocationTargetException) ex;
			this.errorMessage = e.getTargetException().getMessage();
		}
		this.errorMessage = ex.getMessage();
	}
}