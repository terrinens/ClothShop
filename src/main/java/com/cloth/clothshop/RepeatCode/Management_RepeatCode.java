package com.cloth.clothshop.RepeatCode;

import com.cloth.clothshop.Management.ManagementMemberForm;
import com.cloth.clothshop.Member.Member;
import com.cloth.clothshop.Member.MemberRepository;
import lombok.Getter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings("CallToPrintStackTrace")
@Configuration
public class Management_RepeatCode {

    private final ApplicationContext applicationContext;

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository mRepository;

    public Management_RepeatCode(ApplicationContext applicationContext, PasswordEncoder passwordEncoder, MemberRepository memberRepository) {
        this.applicationContext = applicationContext;
        this.passwordEncoder = passwordEncoder;
        this.mRepository = memberRepository;
    }

    @Getter
    public static class CustomPage<T> extends PageImpl<T> {

        private final Class<?> entityClass;

        public CustomPage(List<T> content, Pageable pageable, long total, Class<?> entityClass) {
            super(content, pageable, total);
            this.entityClass = entityClass;
        }
    }

    /**
     * QueryDsl로 커스텀한 메소드 이름 <strong>findByOptionAndKeyword</stroing>에서만 작동이 가능합니다. <br>
     * <h4>ex : Page<T> findByOptionAndKeyword(String searchOption, String searchKeyword, {@link Pageable} pageable);</h4>
     * 모든것이 올바르게 변수가 지정되었다면 자동으로 해당되는 레파지토리에서 메소드를 찾아 사용하여 페이징을 진행합니다.
     * @author DongChuel Kim
     * @param targetRCN 사용할 레파지토리 클래스 이름을 넣습니다. <br>
     *                  ex : String targetRCN = EntityRepository.class.getName(); <br>
     * @param sortBenchmark 엔티티 테이블에서 정렬에 기준이될 이름을 넣습니다. <br>
     * @param requestParamArray 파라메터값을 배열화해서 넣습니다. 하지만 page, option, keyword값만 가능합니다. <br>
     * @return Page<T> autoPaging <br> model.option <br> model.keyword <br>
     */

    @SuppressWarnings("unchecked")
    public <T> Page<T> autoWritePaging(Model model, String targetRCN, String sortBenchmark, Object[] requestParamArray) {

        int page = 0;
        String searchOption = null;
        String searchKeyword = null;

        for (int i = 0; i < requestParamArray.length; i++) {
            if (i == 0) {
                if (requestParamArray[i] != null) {
                    page = Integer.parseInt(requestParamArray[i].toString());
                }
            } else if (i == 1) {

                if (requestParamArray[i] != null) {
                    searchOption = requestParamArray[i].toString();
                }
            } else if (i == 2) {
                if (requestParamArray[i] != null) {
                    searchKeyword = requestParamArray[i].toString();
                }
            }
        }

        Sort sort = Sort.by(sortBenchmark).ascending();
        Pageable pageable = PageRequest.of(page, 15, sort);

        try {
            Class<?>[] parameterType = new Class<?>[]{String.class, String.class, Pageable.class};
            Object[] arguments = new Object[]{searchOption, searchKeyword, pageable};
            Object repositoryInstance = applicationContext.getBean(Class.forName(targetRCN));
            Method method = repositoryInstance.getClass().getDeclaredMethod("findByOptionAndKeyword", parameterType);

            Page<T> autoPaging;
            Object result = method.invoke(repositoryInstance, arguments);
            if (result instanceof Page<?>) {
                autoPaging = (Page<T>) result;
                model.addAttribute("option", searchOption);
                model.addAttribute("keyword", searchKeyword);
                return autoPaging;
            } else {
                throw new RuntimeException("Unexpected result type :::: ");
            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("No such method found :::: ", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Illegal access :::: ", e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Invocation target exception :::: ", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class not found :::: ", e);
        }
    }



    public void encoderPwdModify(Map<String, Object> formData, String originPwd) {
        String formDataPWD = formData.get("pwd").toString();
        String formDataModifyPWD = formData.get("pwdModify").toString();

            String id = formData.get("id").toString();
            String name = formData.get("name").toString();
            String role = formData.get("role").toString();
            String address = formData.get("address").toString();
            String tel = formData.get("tel").toString();

            if (formDataModifyPWD.length() >= 4) {
                try {
                    String encodePwd = passwordEncoder.encode(formDataModifyPWD);
                    mRepository.managementModify(id, encodePwd, name, role, address, tel);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (formDataPWD.equals(originPwd)){
                try {
                    mRepository.managementModifyNotPWD(id, name, role, address, tel);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    public void encoderPwdModify(Optional<Member> optional, ManagementMemberForm mmForm) {
        String formDataPWD = mmForm.getPwd();

        if (optional.isPresent()) {
            String originPwd = optional.get().getPwd();
            String id = mmForm.getId();
            String name = mmForm.getName();
            String role = mmForm.getRole();
            String address = mmForm.getAddress();
            String tel = mmForm.getTel();

            if (passwordEncoder.matches(formDataPWD, originPwd)) {
                mRepository.managementModifyNotPWD(id, name, role, address, tel);
            } else {
                mRepository.managementModify(id, formDataPWD, name, role, address, tel);
            }
        }
    }
}