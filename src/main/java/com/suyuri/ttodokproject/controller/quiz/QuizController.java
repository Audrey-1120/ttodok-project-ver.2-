package com.suyuri.ttodokproject.controller.quiz;

import com.suyuri.ttodokproject.dto.MemberDTO;
import com.suyuri.ttodokproject.entity.quiz.QuizQuestionEntity;
import com.suyuri.ttodokproject.service.MemberService;
import com.suyuri.ttodokproject.service.quiz.QuizService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class QuizController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private MemberService memberService;


    // 퀴즈 - 퀴즈 메인 화면
    @GetMapping("/quizhome")
    public String quizhomeform() {
        return "quizhome";
    }

    // 퀴즈 - 퀴즈1 화면
    @GetMapping("/quiz1")
    public String quiz1form() {
        return "quiz1";
    }

    // 퀴즈 - 퀴즈2 화면
    @GetMapping("/quiz2")
    public String quiz2form() {
        return "quiz2";
    }

    // 퀴즈 - 퀴즈3 화면
    @GetMapping("/quiz3")
    public String quiz3form() {
        return "quiz3";
    }

    // 퀴즈 - 퀴즈4 화면
    @GetMapping("/quiz4")
    public String quiz4form() {
        return "quiz4";
    }

    // 퀴즈 - 문제 및 지문 조회
    @GetMapping("/study/quizQuestion")
    @ResponseBody
    public Map<String, Object> quizQuestionForm(@RequestParam String card) {

        List<QuizQuestionEntity> quizQuestionEntities = quizService.getQuizQuestionByQuizCode(card);
        Map<String, Object> response = new HashMap<>();

        if (!quizQuestionEntities.isEmpty()) {
            response.put("data", quizQuestionEntities);
        } else {
            response.put("data", new ArrayList<>());
        }
        return response;
    }


    //퀴즈 - 포인트 100점 부여
    @PostMapping("/quiz4/pointadd")
    public @ResponseBody Map<String, Object> pointadd(HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        String loginId = (String) session.getAttribute("loginId");
        int memberPoint = memberService.getMemberPoint(loginId);

        response.put("loginId", loginId);
        response.put("memberPoint", memberPoint);

        return response;
    }

    //퀴즈 - 포인트 갱신
    @PostMapping("/quiz4/updatepoint")
    public String updatePoint(@RequestParam String loginId, @RequestParam int point) {

        MemberDTO memberDTO = new MemberDTO(loginId, point);
        memberService.updateMemberPoint(memberDTO);
        return "quiz4";
    }






}