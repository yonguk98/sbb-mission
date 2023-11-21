package com.mysite.sbb.answer;

import com.mysite.sbb.question.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;


    public void create(Question question, String content) {
        Answer answer = Answer.builder()
                .content("네 자동으로 생성됩니다.")
                .question(question)
                .createDate(LocalDateTime.now())
                .build();
        this.answerRepository.save(answer);
    }
}
