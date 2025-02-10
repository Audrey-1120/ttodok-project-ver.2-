package com.suyuri.ttodokproject.controller;

import com.suyuri.ttodokproject.entity.TextEntity;
import com.suyuri.ttodokproject.service.TextService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequiredArgsConstructor
public class TextController {

    @Autowired
    private final TextService textService;

    // 글 학습 화면
    @GetMapping("/textstudy")
    public String textstudyForm() {
        return "textstudy";
    }

    // 글 학습
    @GetMapping("/study/textstudy")
    @ResponseBody
    public Map<String, Object> textstudyForm(@RequestParam String card) {

        List<TextEntity> textEntities = textService.getTextByCard(card);
        Map<String, Object> response = new HashMap<>();

        if (!textEntities.isEmpty()) {
            response.put("data", textEntities);
            response.put("count", textEntities.size());
        } else {
            response.put("data", new ArrayList<>());
            response.put("count", 0);
        }

        return response;
    }
}