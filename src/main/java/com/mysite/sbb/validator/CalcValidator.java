package com.mysite.sbb.validator;

import com.mysite.sbb.form.CalcForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CalcValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        // ! 인수로 전달 받은 Form이 입력 체크 대상인 지 확인
        return CalcForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        // * 커스텀 유효성 검사 로직 구현
        // ! 대상 Form 취득
        CalcForm form = (CalcForm) target;

        // ! 값이 입력되어 있는지 판단
        if (form.getLeftNum() != null && form.getRightNum() != null) {
            // ! 왼쪽 입력값이 홀수, 오른쪽 입력값이 짝수가 아닌 경우 (에러 발생 대상)
            if (!((form.getLeftNum() % 2 == 1) && (form.getRightNum() % 2 == 0))) {
                // ! 에러 메시지 키 지정
                errors.reject("com.mysite.sbb.validator.CalcValidator.message");
            }
        }
    }
}
