package ua.com.javarush.quest.ogarkov.questdelta.repository;

import ua.com.javarush.quest.ogarkov.questdelta.entity.Answer;

import java.util.Collection;

public class AnswerRepository extends AbstractRepository<Answer> {

    public static class AnswerRepositoryHolder {
        public static final AnswerRepository HOLDER_INSTANCE = new AnswerRepository();
    }

    public static AnswerRepository getInstance() {
        return AnswerRepositoryHolder.HOLDER_INSTANCE;
    }

    private AnswerRepository() {
    }

    @Override

    public Collection<Answer> find(Answer pattern) {
        return super.find(
                pattern,
                Answer::getId,
                Answer::getQuestionId,
                Answer::getCorrect,
                Answer::getText
        );
    }
}
