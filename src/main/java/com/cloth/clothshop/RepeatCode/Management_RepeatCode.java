package com.cloth.clothshop.RepeatCode;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.prefs.BackingStoreException;

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
    private Page<?> autoWriteManagementPaging(int page, String searchOption, String keyword,
                                              String targetRepositoryClassName, String sortBenchmark) {

        Sort sort = Sort.by(sortBenchmark).ascending();
        Pageable pageable = PageRequest.of(page, 15, sort);

        try {

            Class<?>[] parameterType = new Class<?>[]{String.class, String.class, Pageable.class};
            Object[] arguments = new Object[]{searchOption, keyword, pageable};

            Object repositoryInstance = applicationContext.getBean(targetRepositoryClassName);
            Method method = repositoryInstance.getClass().getDeclaredMethod("findByOptionAndKeyword", parameterType);

            //ex : Page<Member> memberPage = mRepository.findByOptionAndKeyword(searchOption, keyword, pageable);
            Page<?> autoPaging = (Page<?>) method.invoke(repositoryInstance, arguments);

            return autoPaging;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {

            //수정할것 최소 무슨 문제인지 알아볼수 있게 분리하자.
            throw new RuntimeException(e);
        }
    }

    public void autoManagementPaging(Model model, String targetFormClassName, Class<?> tableEntityClass,
                                     String targetRepositoryClassName, String sortBenchmark, String[] pathVariable) {

        int page = 0;
        String keyword = null;
        String option = null;

        //?page=0&keyword=a&option=all
        for (int i = 0; i <= pathVariable.length; i++) {
            switch (pathVariable[i]) {
                case "page" -> page = Integer.parseInt(pathVariable[i]);
                case "keyword" -> keyword = pathVariable[i];
                case "option" -> option = pathVariable[i];
            }
        }

        Page<?> autoPage = autoWriteManagementPaging(page, option, keyword, targetRepositoryClassName, sortBenchmark);
        CustomPage<?> customPage = new CustomPage<>(autoPage.getContent(), autoPage.getPageable(), autoPage.getTotalElements(), tableEntityClass);

        model.addAttribute("paging", customPage);
        model.addAttribute("targetForm", targetFormClassName);
    }

    public void managementPaging(Model model, Class form, Class<?> tableEntityClass, HttpServletRequest request,
                                 String targetServiceClass, String targetServiceClassMethod) {

        int page = 0;

        if (request.getParameter("page") != null) {

            page = Integer.parseInt(request.getParameter("page"));
        }

        String option = request.getParameter("option");
        String keyword = request.getParameter("keyword");

        try {

            Class<?> serviceClass = Class.forName(targetServiceClass);
            Object serviceInstance = applicationContext.getBean(serviceClass);
            Class<?>[] parameterType = new Class<?>[]{int.class, String.class, String.class};
            Method method = serviceClass.getDeclaredMethod(targetServiceClassMethod, parameterType);
            Object[] arguments = new Object[]{page, option, keyword};
            Page<?> paging = (Page<?>) method.invoke(serviceInstance, arguments);

            CustomPage<?> customPage = new CustomPage<>(paging.getContent(), paging.getPageable(), paging.getTotalElements(), tableEntityClass);

            model.addAttribute("paging", customPage);
            model.addAttribute("tagetForm", form);

        } catch (ClassNotFoundException | NoSuchMethodError | IllegalAccessError | InvocationTargetException |
                 NoSuchMethodException | IllegalAccessException e) {

            //수정할것 최소 무슨 문제인지 알아볼수 있게 분리하자.
            throw new RuntimeException(e);
        }
    }
}