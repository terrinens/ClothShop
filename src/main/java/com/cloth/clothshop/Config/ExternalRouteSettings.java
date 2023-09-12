package com.cloth.clothshop.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.CacheControl;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Configuration
public class ExternalRouteSettings {
    @Value("${pimgsave.dir}")
    private String imgPath;
    private final String os = System.getProperty("os.name").toLowerCase();

    @Profile("localImgDir")
    @Configuration
    public class LocalExternalRouteSettings implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            if (os.startsWith("win")) {
                registry.addResourceHandler("/pimg/**")
                        .addResourceLocations("file:///" + imgPath);
            } else if (os.startsWith("linux")) {
                registry.addResourceHandler("/pimg/**")
                        .addResourceLocations("file:///" + imgPath);
            }
        }
    }

    @Profile("prodImgDir")
    @Configuration
    public class ProdExternalRouteSettings implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            if (os.startsWith("win")) {
                registry.addResourceHandler("/pimg/**")
                        .addResourceLocations("classpath:///" + imgPath)
                        .setCacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES));
                System.out.println(" { 외부 폴더 설정에서 알림. 환경 - 윈도우" + " }");
                System.out.println("외부 폴더 설정에서 최종 결과값 도출? { " + "classpath:///" + imgPath + "}");
            } else {
                String linuxRoot = "/" + imgPath.replace("C:\\", "").toLowerCase().trim();
                linuxRoot = linuxRoot.replace("\\", "/");
                linuxRoot = linuxRoot.substring(0, linuxRoot.lastIndexOf("/"));
                registry.addResourceHandler("/pimg/**")
                        /*.addResourceLocations("file:///clothshopimg/")*/
                        .addResourceLocations("file:///"+linuxRoot)
                        .setCacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES));
                System.out.println(" { 외부 폴더 설정에서 알림. 환경 - 리눅스 혹은 기타 환경" + " }");
                System.out.println("외부 폴더 설정에서 최종 결과값 도출? { " + "file:///" + linuxRoot + " }");
            }

        }
    }
}
