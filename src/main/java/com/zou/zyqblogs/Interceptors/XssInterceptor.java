package com.zou.zyqblogs.Interceptors;

import com.zou.zyqblogs.filter.XssHttpServletRequestWrapper;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.Map;

public class XssInterceptor implements HandlerInterceptor {

}