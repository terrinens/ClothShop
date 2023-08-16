package com.cloth.clothshop.Management;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@RequiredArgsConstructor
@Configuration
public class Management_RepeatCode {

    @Getter
    public static class CustomPage<T> extends PageImpl<T> {

        private final Class<?> entityClass; // 추가된 부분

        public CustomPage(List<T> content, Pageable pageable, long total, Class<?> entityClass) {
            super(content, pageable, total);
            this.entityClass = entityClass;
        }
    }

    @Autowired
    private ApplicationContext applicationContext;

    public void managementPaging(Model model ,Class form, Class<?> tableEntityClass, HttpServletRequest request,
                                 String tagetServiceClass, String tagetServiceClassMethod) {

        int page = 0;

        if (request.getParameter("page") != null) {

            page = Integer.parseInt(request.getParameter("page"));
        }

        String option = request.getParameter("option");
        String keyword = request.getParameter("keyword");

        try {

            Class<?> serviceClass = Class.forName(tagetServiceClass);
            Object serviceInstance = applicationContext.getBean(serviceClass);
            Class<?>[] paramterType = new Class<?>[]{int.class, String.class, String.class};
            Method method = serviceClass.getDeclaredMethod(tagetServiceClassMethod, paramterType);
            Object[] arguments = new Object[]{page, option, keyword};
            Page<?> paging = (Page<?>) method.invoke(serviceInstance, arguments);

            CustomPage<?> customPage = new CustomPage<>(paging.getContent(), paging.getPageable(), paging.getTotalElements(), tableEntityClass);

            model.addAttribute("paging", customPage);
            model.addAttribute("tagetForm", form);

        } catch (ClassNotFoundException | NoSuchMethodError | IllegalAccessError | InvocationTargetException |
                 NoSuchMethodException | IllegalAccessException e) {

            e.printStackTrace();
        }
    }
}