package com.suyuri.ttodokproject.controller.member;

import com.suyuri.ttodokproject.dto.MemberDTO;
import com.suyuri.ttodokproject.entity.MemberEntity;
import com.suyuri.ttodokproject.entity.TdWordEntity;
import com.suyuri.ttodokproject.service.MemberService;
import com.suyuri.ttodokproject.service.TdWordService;
import com.suyuri.ttodokproject.service.point.PointService;
import jakarta.servlet.http.HttpServletRequest;
import com.suyuri.ttodokproject.dto.MemberDTO;
import com.suyuri.ttodokproject.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final PointService pointService;

    private final TdWordService tdWordService;

    // 메인페이지 화면
    @GetMapping("/main")
    public String mainPage() {
        return "main_ver2";
    }

    // 회원가입 화면
    @GetMapping("/member/join")
    public String joinForm() {
        return "join";
    }

    // 로그인 화면
    @GetMapping("/member/login")
    public String loginForm() {
        return "login";
    }

    // 메인 페이지와 마이페이지 화면 출력 요청
    @GetMapping("/main_ver2")
    public String mainPage(HttpSession session, Model model) {
        String loginId = (String) session.getAttribute("loginId");

        List<String> allProductNames = pointService.getAllProductNames();
        List<Integer> allProductPoints = pointService.getAllProductPoints();

        TdWordEntity randomWord = tdWordService.getRandomWord();

        if (loginId != null) {
            String userNick = memberService.getNickName(loginId);
            int memberPoint = memberService.getMemberPoint(loginId);

            model.addAttribute("userNick", userNick);
            model.addAttribute("memberPoint", memberPoint);
            model.addAttribute("productName", allProductNames);
            model.addAttribute("productPoint", allProductPoints);
            model.addAttribute("tdwordList", List.of(randomWord));

        }
        return "main_ver2";
    }

    // 마이페이지 화면
    @GetMapping("/myPage")
    public String myPage(HttpSession session, Model model) {
        String loginId = (String) session.getAttribute("loginId");

        if (loginId != null) {
            String userNick = memberService.getNickName(loginId);
            int memberPoint = memberService.getMemberPoint(loginId);

            model.addAttribute("userNick", userNick);
            model.addAttribute("memberPoint", memberPoint);
        }
        return "myPage";
    }

    // 회원가입
    @PostMapping("/member/join")
    public String save(@ModelAttribute MemberDTO memberDTO) {

        LocalDate birthDate = LocalDate.of(memberDTO.getYear(), memberDTO.getMonth(), memberDTO.getDay());
        memberDTO.setBirthDate(birthDate);
        memberService.save(memberDTO);
        return "endjoin";
    }

    // 로그인
    @PostMapping("/member/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session, Model model) {

        Map<String, String> loginResult = memberService.login(memberDTO);

        if (loginResult.get("status").equals("success")) {
            session.setAttribute("loginId", memberDTO.getMemberId());
            return "redirect:/main_ver2";

        } else {
            String errorMessage = loginResult.get("message");
            if (errorMessage != null) {
                model.addAttribute("errorMessage", errorMessage);
            }
            return "login";
        }
    }

    //아이디 중복확인
    @PostMapping("/member/id-check")
    public @ResponseBody String idCheck(@RequestParam("memberId") String memberId) {
        return memberService.idCheck(memberId);
    }


    //패스워드 중복및 유효성 검사
    @PostMapping("/member/pw-check")
    public @ResponseBody String passwordCheck(@RequestParam("memberPw") String memberPw) {
        return memberService.passwordCheck(memberPw);
    }

    // 회원탈퇴
    @DeleteMapping("/members/delete")
    public ResponseEntity<String> deleteMember(HttpSession session) {
        String loginId = (String) session.getAttribute("loginId");

        if (loginId != null) {
            boolean success = memberService.deleteMember(loginId);

            if (success) {
                session.invalidate();
                return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("회원 탈퇴에 실패했습니다.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("로그인된 사용자만 회원 탈퇴가 가능합니다.");
        }
    }

    //로그아웃
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/member/login";
    }
}