package ua.com.javarush.quest.ogarkov.questdelta.controller.quest;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.com.javarush.quest.ogarkov.questdelta.dto.*;
import ua.com.javarush.quest.ogarkov.questdelta.service.*;
import ua.com.javarush.quest.ogarkov.questdelta.settings.Go;
import ua.com.javarush.quest.ogarkov.questdelta.settings.Setting;
import ua.com.javarush.quest.ogarkov.questdelta.util.Jsp;
import ua.com.javarush.quest.ogarkov.questdelta.util.Parser;

import java.io.IOException;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@MultipartConfig(fileSizeThreshold = 1 << 20)
@WebServlet(Go.EDIT_QUEST_CONTENT)
public class EditQuestContentServlet extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 7582798421846485830L;
    private final QuestService questService = QuestService.INSTANCE;
    private final QuestionService questionService = QuestionService.INSTANCE;
    private final AnswerService answerService = AnswerService.INSTANCE;
    private final EditorService editorService = EditorService.INSTANCE;
    private final UserService userService = UserService.INSTANCE;
    private final Setting S = Setting.get();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FormData formData = FormData.of(req);
        long userId = Parser.userId(req);
        UserDto user = userService.get(userId).orElseThrow();
        Optional<QuestDto> optQuest = questService.get(formData.getId());
        if (optQuest.isPresent() && editorService.checkRights(optQuest.get(), user)) {
            QuestDto quest = optQuest.get();
            long questionId = formData.getLong(S.paramQuestionId);
            QuestionDto question = questionService.get(questionId).orElse(questionService.get(quest.getFirstQuestionId()).orElseThrow());
            req.setAttribute(S.attrQuest, quest);
            req.setAttribute(S.attrQuestion, question);
            req.setAttribute(S.attrAnswers, getAnswers(question));
            Jsp.forward(req, resp, S.jspEditQuestContent);
        } else Jsp.redirect(req, resp, Go.QUESTS);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        FormData formData = FormData.of(req);
        Long questId = formData.getId();
        Optional<QuestDto> optQuest = questService.get(questId);
        if (optQuest.isPresent()) {
            QuestDto quest = optQuest.get();
            long questionId = formData.getLong(S.paramQuestionId);
            QuestionDto question = questionService.get(questionId).orElse(questionService.get(quest.getFirstQuestionId()).orElseThrow());
            int questionIndex = quest.getQuestions().indexOf(question);
            //----------- Create question -----------//
            if (isExist(req, S.paramQuestionCreate)) {
                questionService.createEmpty(formData.getId());
                questionIndex = quest.getQuestions().size() - 1;
                Jsp.redirect(req, resp, editorService.getEditPath(questId, questionIndex));
                return;

                //----------- Update question -----------//
            } else if (isExist(req, S.paramQuestionUpdate)) {
                questionService.update(formData, req.getPart(S.inputImage));

                //----------- Delete question -----------//
            } else if (isExist(req, S.paramQuestionDelete)) {
                editorService.deleteNonFirstQuestion(formData);
                //----------- Create answer -----------//
            } else if (isExist(req, S.paramAnswerCreate)) {
                answerService.create(formData);

                //----------- Delete answer -----------//
            } else if (isExist(req, S.paramAnswerDelete)) {
                answerService.delete(formData);

                //----------- Delete quest -----------//
            } else if (isExist(req, S.paramQuestDelete)) {
                questService.delete(formData);
                Jsp.redirect(req, resp, Go.EDIT_QUESTS);
                return;
            }
            Jsp.redirect(req, resp, editorService.getEditPath(questId, questionIndex));
        } else Jsp.redirect(req, resp, Go.QUESTS);
    }

    private boolean isExist(HttpServletRequest req, String param) {
        return req.getParameter(param) != null;
    }

    private List<Map.Entry<AnswerDto, QuestionDto>> getAnswers(QuestionDto question) {
        List<Map.Entry<AnswerDto, QuestionDto>> answers = new ArrayList<>();
        for (AnswerDto answer : question.getAnswers()) {
            QuestionDto nextQuestion = questionService.get(answer.getNextQuestionId()).orElseThrow();
            answers.add(Map.entry(answer, nextQuestion));
        }
        return answers;
    }
}
