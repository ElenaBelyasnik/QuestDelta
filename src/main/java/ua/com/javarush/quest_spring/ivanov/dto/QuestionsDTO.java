package ua.com.javarush.quest_spring.ivanov.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter
@Setter
public class QuestionsDTO {
    private int id;
    private String text;
    private int[] answerIds;

    @Override
    public String toString() {
        return "QuestionsDTO{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", answerIds=" + Arrays.toString(answerIds) +
                '}';
    }
}
