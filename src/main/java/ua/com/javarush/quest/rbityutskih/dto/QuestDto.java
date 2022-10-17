package ua.com.javarush.quest.rbityutskih.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;

@Data
@Builder(builderMethodName = "with")
public class QuestDto {
    Long id;
    Long authorId;
    String name;
    String text;
    Long startQuestionId;
    Collection<QuestionDto> questions;
}
