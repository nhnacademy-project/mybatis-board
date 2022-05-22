package com.nhnacademy.jdbc.board.config;

import java.io.File;
import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    private static final int MAX_FILE_SIZE = 100 * 1024 * 1024;
    private final static String FS = File.separator;
    private final static String UPLOAD_PATH = System.getProperty("user.dir")
        + FS + "src" + FS + "main" + FS + "resources" + FS + "WEB-INF/upload" + FS;

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {com.nhnacademy.jdbc.board.config.RootConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }

    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();

        return new Filter[] {characterEncodingFilter, hiddenHttpMethodFilter};
    }

    @Override
    protected DispatcherServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
        DispatcherServlet dispatcherServlet =
            (DispatcherServlet) super.createDispatcherServlet(servletAppContext);
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
        return dispatcherServlet;
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        MultipartConfigElement
            multipartConfig =
            new MultipartConfigElement(UPLOAD_PATH, MAX_FILE_SIZE, MAX_FILE_SIZE, 0);
        registration.setMultipartConfig(multipartConfig);
    }
}
