package ch.protonmail.vladyslavbond.quizzing.domain;

import java.util.Set;

import ch.protonmail.vladyslavbond.quizzing.datasource.NativeMapper;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;

class FinishedAssessmentMapper 
extends NativeMapper<FinishedAssessment>
{
    public FinishedAssessmentMapper ( )
    {
        super(FinishedAssessment.class);
    }
    
    @Override
    public FinishedAssessment build ( )
    {
        Identificator<FinishedAssessment> id = NumericIdentificator.<FinishedAssessment>valueOf(get("id", Long.class));
        StudentFactory studentFactory = Factories.<StudentFactory>getInstance(StudentFactory.class);
        Identificator<Student> idOfStudent = NumericIdentificator.<Student>valueOf(get("student_id", Long.class));
        Student student = studentFactory.getInstance(idOfStudent);
        TaskFactory taskFactory = Factories.<TaskFactory>getInstance(TaskFactory.class);
        Set<Task> tasks = taskFactory.getFinishedAssessmentInstances(id);
        Set<Answer> answers =  Factories.<AnswerFactory>getInstance(AnswerFactory.class).getFinishedAssessmentInstances(id);
        FinishedAssessment assessment = new FinishedAssessment (id, student, tasks, answers);
        return assessment;
    }

}
