package com.mysite.sbb.controller;

import com.mysite.sbb.form.CalcForm;
import com.mysite.sbb.validator.CalcValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ValidationController {

    // * 커스텀 유효성 검사기
    // ! DI
    @Autowired
    CalcValidator calcValidator;

    // ! 커스텀 유효성 검사기 등록
    @InitBinder("calcForm")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(calcValidator);
    }

    // * form-backing bean 초기화
    @ModelAttribute
    public CalcForm setUpForm() {
        return new CalcForm();
    }

    // * 입력 화면 표시
    @GetMapping("/show")
    public String showView() {
        return "entry";
    }

    // * 확인 화면 표시 : Form 클래스 이용
    @PostMapping("/calc")
    public String confirmView(@Validated CalcForm form, BindingResult bindingResult, Model model) {
        // ! 입력 체크에서 에러 발생한 경우 -> 입력 화면으로 이동
        if (bindingResult.hasErrors()) return "entry";

        // ! 더하기
        Integer result = form.getLeftNum() + form.getRightNum();

        // ! Model에 저장
        model.addAttribute("result", result);

        // ! 확인 화면으로
        return "confirm";
    }
}
