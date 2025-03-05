package com.mehmetsukrukavak.quizapp.service;

import com.mehmetsukrukavak.quizapp.model.Question;
import com.mehmetsukrukavak.quizapp.model.QuestionWrapper;
import com.mehmetsukrukavak.quizapp.model.Quiz;
import com.mehmetsukrukavak.quizapp.model.Response;
import com.mehmetsukrukavak.quizapp.repository.QuestionRepository;
import com.mehmetsukrukavak.quizapp.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuestionRepository questionRepository;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        List<Question> questionList = questionRepository.findRandomQuestionsByCategory(category, numQ);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questionList);

        quizRepository.save(quiz);

        return new ResponseEntity<>("Quiz created",HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Optional<Quiz> quiz = quizRepository.findById(id);

        List<Question> questionList = quiz.get().getQuestions();
        List<QuestionWrapper> questionWrapperList = new ArrayList<>();

        for (Question q : questionList) {
            QuestionWrapper qw = new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
            questionWrapperList.add(qw);
        }

        return new ResponseEntity<>(questionWrapperList, HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        Quiz quiz = quizRepository.findById(id).get();

        List<Question> questionList = quiz.getQuestions();
        int right = 0;
        int index = 0;
        for (Response r : responses) {
                if (r.getResponse().equals(questionList.get(index).getRightAnswer()))
                    right++;
                index++;
        }
        return new ResponseEntity<>(right, HttpStatus.OK);
    }

    public ResponseEntity<String> createQuizWithoutCategory(int numQ, String title) {
        List<Question> questionList = questionRepository.findRandomQuestions(numQ);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questionList);

        quizRepository.save(quiz);

        return new ResponseEntity<>("Quiz created",HttpStatus.CREATED);
    }
}
