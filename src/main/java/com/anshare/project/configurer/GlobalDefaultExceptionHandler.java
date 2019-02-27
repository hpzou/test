package com.anshare.project.configurer;

import com.anshare.project.core.ProjectConstant;
import com.anshare.project.core.ResultCore.Result;
import com.anshare.project.core.ResultCore.ResultGenerator;
import com.anshare.project.core.Util.AccessException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class GlobalDefaultExceptionHandler {

	/**
	 * 处理 AccessException 类型的异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler(AccessException.class)
	public Result accessExceptionHandler(Exception e) {
		e.printStackTrace();
		return ResultGenerator.genFailCodeResult(ProjectConstant.ACCESS_INVALID,e.getMessage());
	}

	/**
	 * 处理 Exception 类型的异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public Result defaultExceptionHandler(Exception e) {
		e.printStackTrace();
		return ResultGenerator.genFailCodeResult(ProjectConstant.SERVICE_INVALID,e.getMessage());
	}

	/**
	 * 处理 Service RuntimeException 类型的异常
	 */
	@ExceptionHandler(RuntimeException.class)
	public Result defaultServiceExceptionHandler(Exception e){
		e.printStackTrace();
		return ResultGenerator.genFailCodeResult(ProjectConstant.SERVICE_INVALID,e.getMessage());
	}
}
