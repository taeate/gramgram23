package com.ll.gramgram.boundedContext.member.controller;

import com.ll.gramgram.boundedContext.member.entity.Member;
import com.ll.gramgram.boundedContext.member.service.MemberService;
import com.ll.gramgram.standard.ut.Ut.Ut;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PreAuthorize("isAnonymous()")
    @GetMapping("/join")
    public String showJoin(){
        return "usr/member/join";
    }

    @Getter
    @AllArgsConstructor
    public static class JoinForm {
        @NotBlank
        @Size(min = 4, max = 30)
        private final String username;
        @NotBlank
        @Size(min = 4, max = 30)
        private final String password;
    }
    @PreAuthorize("isAnonymous()")
    @PostMapping("/join")
    public String join(@Valid JoinForm joinForm) {
        memberService.join(joinForm.getUsername(), joinForm.getPassword());

        return "redirect:/member/login?msg=" + Ut.url.encode("회원가입이 완료되었습니다.\n로그인 후 이용해주세요.");
    }

    @PreAuthorize("isAnonymous()") // 로그인 안한사람만 들어올수있음
    @GetMapping("/login")
    public String showLogin(){
        return "usr/member/login";
    }

    @PreAuthorize("isAuthenticated()") // 로그인 한사람만 들어올수있음
    @GetMapping("/me")
    public String showMe(Principal principal, Model model){
        Member loginedMember = memberService.findByUsername(principal.getName()).orElseThrow();
        model.addAttribute("loginedMember", loginedMember);
        return "usr/member/me";
    }
}
