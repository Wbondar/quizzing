package ch.protonmail.vladyslavbond.quizzing.domain;

import java.util.Set;

import ch.protonmail.vladyslavbond.quizzing.datasource.MapperException;
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
    public FinishedAssessment build ( ) throws FinishedAssessmentMapperException, MapperException
    {
        Identificator<FinishedAssessment> id = NumericIdentificator.<FinishedAssessment>valueOf(get("id", Long.class));
        StudentFactory studentFactory = Factories.<StudentFactory>getInstance(StudentFactory.class);
        Identificator<Student> idOfStudent = NumericIdentificator.<Student>valueOf(get("student_id", Long.class));
        Student student;
        try
        {
            student = studentFactory.getInstance(idOfStudent);
        } catch (StudentFactoryException e)
        {
           throw new FinishedAssessmentMapperException (e);
        }
        TaskFactory taskFactory = Factories.<TaskFactory>getInstance(TaskFactory.class);
        Set<Task> tasks;
        try
        {
            tasks = taskFactory.getFinishedAssessmentInstances(id);
        } catch (TaskFactoryException e)
        {
            throw new FinishedAssessmentMapperException ("Failed to get tasks of the assessment.", e);
        }
        Set<Answer> answers =  Factories.<AnswerFactory>getInstance(AnswerFactory.class).getFinishedAssessmentInstances(id);
        FinishedAssessment assessment = new FinishedAssessment (id, student, tasks, answers);
        return assessment;
    }

}
