package com.cloth.clothshop.Management;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.*;
import org.springframework.ui.Model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@Configuration
public class Management_RepeatCode {

    private final ApplicationContext applicationContext;
    public Management_RepeatCode(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Getter
    public static class CustomPage<T> extends PageImpl<T> {

        private final Class<?> entityClass;

        public CustomPage(List<T> content, Pageable pageable, long total, Class<?> entityClass) {
            super(content, pageable, total);
            this.entityClass = entityClass;
        }
    }

    //참고 managementGetMemberList
    private Page<?> autoWritePageing(int page, String searchOption, String keyword,
                                   Class tagetRepositoryClassName) {

        String sortBenchmark = "id";
        Sort sort = Sort.by(sortBenchmark).ascending();
        Pageable pageable = PageRequest.of(page, 15, sort);

        try {

            Class<?>[] parameterType = new Class<?>[]{String.class, String.class, Pageable.class};
            Object[] arguments = new Object[]{searchOption, keyword, pageable};

            Object repositoryInstance = applicationContext.getBean(tagetRepositoryClassName.getName());
            Method method = repositoryInstance.getClass().getDeclaredMethod("findByOptionAndKeyword", parameterType);

            Page<?> autoPaging = (Page<?>) method.invoke(repositoryInstance, arguments);

            return autoPaging;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {

            throw new RuntimeException(e);
        }
    }

    public void autoManagementPaging() {

    }

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
            Class<?>[] parameterType = new Class<?>[]{int.class, String.class, String.class};
            Method method = serviceClass.getDeclaredMethod(tagetServiceClassMethod, parameterType);
            Object[] arguments = new Object[]{page, option, keyword};
            Page<?> paging = (Page<?>) method.invoke(serviceInstance, arguments);

            CustomPage<?> customPage = new CustomPage<>(paging.getContent(), paging.getPageable(), paging.getTotalElements(), tableEntityClass);

            model.addAttribute("paging", customPage);
            model.addAttribute("tagetForm", form);

        } catch (ClassNotFoundException | NoSuchMethodError | IllegalAccessError | InvocationTargetException |
                 NoSuchMethodException | IllegalAccessException e) {

            //수정할것
            throw new RuntimeException(e);
        }
    }
}