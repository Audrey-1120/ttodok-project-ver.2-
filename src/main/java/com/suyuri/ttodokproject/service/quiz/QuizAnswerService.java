package com.suyuri.ttodokproject.service.quiz;


import com.suyuri.ttodokproject.entity.quiz.QuizAnswerEntity;
import com.suyuri.ttodokproject.entity.quiz.QuizChoiceEntity;
import com.suyuri.ttodokproject.repository.quiz.QuizAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuizAnswerService {

    @Autowired
    private final QuizAnswerRepository quizAnswerRepository;

    //퀴즈  - 해설
    public List<QuizAnswerEntity> getQuizAnswerByQuestionCode(String questionCode) {
        List<QuizAnswerEntity> quizAnswerEntities = quizAnswerRepository.findByQuestionCode(questionCode);
        return quizAnswerEntities;
    }

}