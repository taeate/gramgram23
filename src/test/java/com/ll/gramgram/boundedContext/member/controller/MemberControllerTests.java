package com.ll.gramgram.boundedContext.member.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest // 스프링부트 관련 컴포넌트 테스트할 때 붙여야함. ioc 컨테이너 작동시킴
@AutoConfigureMockMvc // http 요청, 응답 테스트할때
@Transactional // 실제로 테스트에서 발생한 DB 작업이 영구적으로 적용되지 않도록할때 , test = 트랜잭션 => 자동 롤백
@ActiveProfiles("test")  // application-test.yml 활성화시킨다
public class MemberControllerTests {
    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("회원가입 폼")
    void t001() throws Exception {
        //WHEN > mvc 를 통해 get 요청으로 수행
        ResultActions resultActions = mvc
                .perform(get("/member/join"))
                .andDo(print()); //확인용

        //THEN
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(MemberController.class))
                .andExpect(handler().methodName("showJoin"));


    }

}
