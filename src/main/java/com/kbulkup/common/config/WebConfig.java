package com.kbulkup.common.config;

import com.kbulkup.common.config.RootConfig;
import com.kbulkup.common.config.ServletConfig;
import com.kbulkup.common.config.SecurityConfig;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;

public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

    // 파일 업로드 설정 (ServletConfig에서 가져옴)
    // 실제 경로와 크기는 ServletConfig의 @Value를 통해 주입되므로 여기서는 기본값 또는 임시값 설정
    // 또는 ServletConfig에서 MultipartConfigElement 빈을 직접 가져와 사용할 수도 있음
    // 여기서는 WebConfig에서 직접 설정하는 방식으로 진행
    private final String LOCATION = System.getProperty("java.io.tmpdir"); // 임시 디렉토리 사용
    private final long MAX_FILE_SIZE = 1024 * 1024 * 10L; // 10MB
    private final long MAX_REQUEST_SIZE = 1024 * 1024 * 20L; // 20MB
    private final int FILE_SIZE_THRESHOLD = 1024 * 1024 * 5; // 5MB

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{RootConfig.class, SecurityConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{ServletConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return new Filter[]{characterEncodingFilter};
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setInitParameter("throwExceptionIfNoHandlerFound", "true");
        MultipartConfigElement multipartConfig = new MultipartConfigElement(
                LOCATION,
                MAX_FILE_SIZE,
                MAX_REQUEST_SIZE,
                FILE_SIZE_THRESHOLD
        );
        registration.setMultipartConfig(multipartConfig);
    }
}
