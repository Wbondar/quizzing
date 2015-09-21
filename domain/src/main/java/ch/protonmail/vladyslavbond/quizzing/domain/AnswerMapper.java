package ch.protonmail.vladyslavbond.quizzing.domain;

import ch.protonmail.vladyslavbond.quizzing.datasource.Mapper;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;

class AnswerMapper
extends Mapper<Answer>
{
    public AnswerMapper ( )
    {
        super(Answer.class);
    }
    
    @Override
    public Answer build ( )
    {
        final Long id = get("id", Long.class);
        final TaskFactory taskFactory = Factories.<TaskFactory>getInstance(TaskFactory.class);
        final Identificator<Task> idOfTask = NumericIdentificator.<Task>valueOf(get("task_id", Long.class));
        final Task task = taskFactory.getInstance(idOfTask);
        final MemberFactory memberFactory = Factories.<MemberFactory>getInstance(MemberFactory.class);
        final Identificator<Member> idOfStudent = NumericIdentificator.<Member>valueOf(get("member_id", Long.class));
        final Student student = memberFactory.getStudent(idOfStudent);
        final String input = get("input", String.class);
        return new Answer (NumericIdentificator.<Answer>valueOf(id), task, student, input);
    }
}
