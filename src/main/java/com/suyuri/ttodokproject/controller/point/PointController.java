package com.suyuri.ttodokproject.controller.point;

import com.suyuri.ttodokproject.dto.MemberDTO;
import com.suyuri.ttodokproject.service.MemberService;
import com.suyuri.ttodokproject.service.point.PointService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;

    private final MemberService memberService;

    //포인트상점 페이지 출력
    @GetMapping("/pointshop")
    public String pointshop(HttpSession session, Model model) {

        String loginId = (String) session.getAttribute("loginId");
        List<String> allProductNames = pointService.getAllProductNames();
        List<Integer> allProductPoints = pointService.getAllProductPoints();

        if (loginId != null) {
            int memberPoint = memberService.getMemberPoint(loginId);
            model.addAttribute("memberPoint", memberPoint);
            model.addAttribute("productName", allProductNames);
            model.addAttribute("productPoint", allProductPoints);
        }
        return "pointshop_home";
    }

    //포인트상점 - 상품 구매
    @PostMapping("/pointshop/buyproduct")
    public @ResponseBody Map<String, Object> buyproduct(HttpSession session) {

        Map<String, Object> response = new HashMap<>();

        String loginId = (String) session.getAttribute("loginId");
        int memberPoint = memberService.getMemberPoint(loginId);

        response.put("loginId", loginId);
        response.put("memberPoint", memberPoint);

        return response;
    }


    // 상품 구매 - 잔여 포인트 갱신
    @PostMapping("/pointshop/productupdatepoint")
    public String pointhomeupdatepoint(@RequestParam String loginId, @RequestParam int point) {

        MemberDTO memberDTO = new MemberDTO(loginId, point);
        memberService.updateProductMemberPoint(memberDTO);
        return "pointshop_home";
    }
}