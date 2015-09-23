package ch.protonmail.vladyslavbond.quizzing.controllers;

import ch.protonmail.vladyslavbond.quizzing.domain.*;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;
import ch.protonmail.vladyslavbond.quizzing.domain.Assessment;

public final class Assessments 
extends Controller 
{   
    public Assessment retrieve (Long idOfAssessment) 
    {
        Assessment assessment = getFinishedAssessmentFactory( ).getInstance(NumericIdentificator.<FinishedAssessment>valueOf(idOfAssessment));
        if (assessment == null || assessment.equals(FinishedAssessment.EMPTY) || assessment.equals(OngoingAssessment.EMPTY))
        {
            assessment = getOngoingAssessmentFactory( ).getInstance(NumericIdentificator.<OngoingAssessment>valueOf(idOfAssessment));
        }
        if (assessment == null || assessment.equals(FinishedAssessment.EMPTY) || assessment.equals(OngoingAssessment.EMPTY))
        {
            return FinishedAssessment.EMPTY;
        }
        return assessment;
    }

    private OngoingAssessment update (OngoingAssessment assessment, Task task, String answer)
    {
        Answer result = assessment.provideAnswer(task.getId( ), answer);
        if (result == null || result.equals(Answer.EMPTY))
        {
            return OngoingAssessment.EMPTY;
        }
        return assessment;
    }
    
    public OngoingAssessment update (Assessment assessment, Task task, String answer) 
    {
        return this.update((OngoingAssessment)assessment, task, answer);
    }

    public OngoingAssessment create (Identificator<Student> idOfStudent, Identificator<Exam> idOfExam)
    {
        return this.create(getStudentFactory( ).getInstance(idOfStudent), getExamFactory( ).getInstance(idOfExam));
    }

    private OngoingAssessment create(Student student, Exam exam)
    {
        OngoingAssessment assessment = getOngoingAssessmentFactory( ).newInstance(student, exam);
        if (assessment == null || assessment.equals(OngoingAssessment.EMPTY))
        {
            return OngoingAssessment.EMPTY;
        }
        return assessment;
    }

    public OngoingAssessment update(Long idOfAssessment, Long idOfTask, String input)
    {
        return this.update(NumericIdentificator.<OngoingAssessment>valueOf(idOfAssessment), NumericIdentificator.<Task>valueOf(idOfTask), input);
    }

    private OngoingAssessment update(Identificator<OngoingAssessment> id, Identificator<Task> idOfTask, String input)
    {
        return this.update(getOngoingAssessmentFactory( ).getInstance(id), getTaskFactory( ).getInstance(idOfTask), input);
    }

    private TaskFactory getTaskFactory()
    {
        return Factories.<TaskFactory>getInstance(TaskFactory.class);
    }

    public boolean destroy(Long idOfAssessment)
    {
        return this.destroy(NumericIdentificator.<FinishedAssessment>valueOf(idOfAssessment));
    }

    private boolean destroy(Identificator<FinishedAssessment> id)
    {
        return this.destroy(getFinishedAssessmentFactory().getInstance(id));
    }
    
    public boolean destroy (FinishedAssessment assessment) 
    {
        FinishedAssessmentFactory assessmentFactory = Factories.<FinishedAssessmentFactory>getInstance(FinishedAssessmentFactory.class);
        return assessmentFactory.destroy(assessment);
    }

    public boolean finish(Long idOfAssessment)
    {
        return this.finish(NumericIdentificator.<OngoingAssessment>valueOf(idOfAssessment));
    }

    private boolean finish(Identificator<OngoingAssessment> id)
    {
        return this.finish (getOngoingAssessmentFactory( ).getInstance(id));
    }

    private boolean finish (OngoingAssessment instance)
    {
        instance.finish( );
        return true;
    }

    private OngoingAssessmentFactory getOngoingAssessmentFactory()
    {
        return Factories.<OngoingAssessmentFactory>getInstance(OngoingAssessmentFactory.class);
    }

    private FinishedAssessmentFactory getFinishedAssessmentFactory()
    {
        return Factories.<FinishedAssessmentFactory>getInstance(FinishedAssessmentFactory.class);
    }

    private ExamFactory getExamFactory()
    {
        return Factories.<ExamFactory>getInstance(ExamFactory.class);
    }

    private StudentFactory getStudentFactory()
    {
        return Factories.<StudentFactory>getInstance(StudentFactory.class);
    }
}
