package com.cloth.clothshop.Management;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RequiredArgsConstructor @Configuration
public class Management_RepeatCode {

    public void managementPaging(Class form, Page paging, Model model ,HttpServletRequest request,
                                 String tagetServiceClass, String tagetServiceClassMethod) {

        int page = 0;

        if (request.getParameter("page") != null) {

            page = Integer.parseInt(request.getParameter("page"));
        }

        String option = request.getParameter("option");
        String keyword = request.getParameter("keyword");

        try {

            Class<?> serviceClass = Class.forName(tagetServiceClass);
            Constructor<?> constructor = serviceClass.getConstructor();
            Object serviceInstance = constructor.newInstance();

            Class<?>[] paramterType = new Class<?>[]{int.class, String.class, String.class};
            Method method = serviceClass.getDeclaredMethod(tagetServiceClassMethod, paramterType);

            Object[] arguments = new Object[]{page, option, keyword};
            paging = (Page<?>) method.invoke(serviceInstance, arguments);

            model.addAttribute("paging", paging);
            model.addAttribute("tagetForm", form);

            System.out.println("리팩트 코드로부터 넘어옴 페이징 토탈 페이지 ::::: " + paging.getTotalPages());
            System.out.println("리팩트 코드로부터 넘어옴 페이징 사이즈 ::::: " + paging.getSize());

        } catch (ClassNotFoundException | NoSuchMethodError | IllegalAccessError | InvocationTargetException |
                 NoSuchMethodException | InstantiationException | IllegalAccessException e) {

            e.printStackTrace();
        }

    }
}
