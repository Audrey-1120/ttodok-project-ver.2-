package com.suyuri.ttodokproject.service.quiz;

import com.suyuri.ttodokproject.entity.quiz.QuizQuestionEntity;
import com.suyuri.ttodokproject.repository.quiz.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    public List<QuizQuestionEntity> getQuizQuestionByQuizCode(String card) {
        List<QuizQuestionEntity> quizQuestionEntities = quizRepository.findByQuizCode(card);
        return quizQuestionEntities;
    }

}