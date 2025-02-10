package com.suyuri.ttodokproject.service.quiz;
import com.suyuri.ttodokproject.entity.quiz.QuizChoiceEntity;
import com.suyuri.ttodokproject.repository.quiz.QuizChoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizChoiceService {


    @Autowired
    private final QuizChoiceRepository quizChoiceRepository;

    public List<QuizChoiceEntity> getQuizChoiceByChoiceCode(String card) {
        List<QuizChoiceEntity> quizChoiceEntities = quizChoiceRepository.findByQuestionCode(card);

        return quizChoiceEntities;
    }
}