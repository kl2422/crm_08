package com.shsxt.base;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.shsxt.controller.BaseController;
import com.shsxt.exception.ParamException;

/**
 * 全局异常处理
 * @author TW
 *
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends BaseController {
	
	@ExceptionHandler(value = ParamException.class)
	public ResultInfo handlerParamException(ParamException paramException) {
		return failure(paramException);
	}
	
	@ExceptionHandler(value = Exception.class)
	public ResultInfo handlerException(Exception exception) {
		return failure(exception.getMessage());
	}
	
//	@ExceptionHandler(value = IllegalArgumentException.class)
//	public ResultInfo handlerIllegalArgumentExceptionException(IllegalArgumentException exception) {
//		return failure(exception.getMessage());
//	}
	
	@ExceptionHandler(value = {IllegalAccessException.class, IllegalArgumentException.class})
	public ResultInfo handlerIllegalException(Exception exception) {
		if (exception instanceof IllegalAccessException) {
			return failure(((IllegalAccessException)exception).getMessage());
		}
		if (exception instanceof IllegalArgumentException) {
			IllegalArgumentException e = (IllegalArgumentException)exception;
			return failure(e.getMessage());
		}
		return failure("未知异常");
	}
	
}
