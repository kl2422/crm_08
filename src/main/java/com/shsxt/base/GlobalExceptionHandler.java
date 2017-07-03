package com.shsxt.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.shsxt.exception.ParamException;
import com.shsxt.exception.UnAuthPermissionException;

/**
 * 全局异常处理
 * @author TW
 *
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends BaseController {
	
//	@Autowired
//	private LogService logService;
	
	private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(value = ParamException.class)
	public ResultInfo handlerParamException(ParamException paramException) {
		logger.error("参数异常：{}", paramException);
//		logService.addLog(log); // 错误日志收集
		return failure(paramException);
	}
	
	@ExceptionHandler(value = UnAuthPermissionException.class)
	public ResultInfo handlerUnAuthPermissionException(UnAuthPermissionException exception) {
		logger.error("异常：{}", exception);
		return failure(exception.getPermissionCode(), exception.getMessage());
	}
	
	@ExceptionHandler(value = Exception.class)
	public ResultInfo handlerException(Exception exception) {
		logger.error("异常：{}", exception);
		return failure(exception.getMessage());
	}
	
//	@ExceptionHandler(value = IllegalArgumentException.class)
//	public ResultInfo handlerIllegalArgumentExceptionException(IllegalArgumentException exception) {
//		return failure(exception.getMessage());
//	}
	
	@ExceptionHandler(value = {IllegalAccessException.class, IllegalArgumentException.class})
	public ResultInfo handlerIllegalException(Exception exception) {
		logger.error("异常：{}", exception);
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
