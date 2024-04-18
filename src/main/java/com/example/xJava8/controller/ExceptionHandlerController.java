package com.example.xJava8.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;

/**
 * Springにおける例外ハンドリングの一つ
 *
 * @ExceptionHander　を使用した例外ハンドリングController
 */
@Log4j2
@ControllerAdvice
public class ExceptionHandlerController {

    //　メソッドの引数に予期される型がないことを示す例外
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ModelAndView methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        ModelAndView mav = new ModelAndView("redirect:./home?error");
        return mav;
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ModelAndView emptyResultDataAccessException(EmptyResultDataAccessException e) {
        ModelAndView mav = new ModelAndView("redirect:./home?error");
        return mav;
    }
}
