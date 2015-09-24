package ch.protonmail.vladyslavbond.quizzing.controllers;

import ch.protonmail.vladyslavbond.quizzing.domain.*;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;

public final class Assessments 
extends Controller 
{   
    public Assessment retrieve (Long idOfAssessment) throws AssessmentsControllerException 
    {
        try
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
        } catch (AssessmentFactoryException e) {
            throw new AssessmentsControllerException (e);
        }
    }

    private OngoingAssessment update (OngoingAssessment assessment, Task task, String answer) throws AssessmentsControllerException
    {
        try
        {
            Answer result = assessment.provideAnswer(task.getId( ), answer);
            if (result == null || result.equals(Answer.EMPTY))
            {
                return OngoingAssessment.EMPTY;
            }
            return assessment;   
        } catch (AnswerFactoryException | TaskFactoryException e) {
            throw new AssessmentsControllerException (e);
        }
    }
    
    public OngoingAssessment update (Assessment assessment, Task task, String answer) throws AssessmentsControllerException
    {
        return this.update((OngoingAssessment)assessment, task, answer);
    }

    public OngoingAssessment create (Identificator<Student> idOfStudent, Identificator<Exam> idOfExam) throws AssessmentsControllerException
    {
        try
        {
            return this.create(getStudentFactory( ).getInstance(idOfStudent), getExamFactory( ).getInstance(idOfExam));
        } catch (StudentFactoryException e)
        {
            throw new AssessmentsControllerException (e);
        }
    }

    private OngoingAssessment create(Student student, Exam exam) throws AssessmentsControllerException
    {
        try
        {
            OngoingAssessment assessment = getOngoingAssessmentFactory( ).newInstance(student, exam);
            if (assessment == null || assessment.equals(OngoingAssessment.EMPTY))
            {
                return OngoingAssessment.EMPTY;
            }
            return assessment;
        } catch (AssessmentFactoryException e)
        {
            throw new AssessmentsControllerException (e);
        }
    }

    public OngoingAssessment update(Long idOfAssessment, Long idOfTask, String input) throws AssessmentsControllerException
    {
        return this.update(NumericIdentificator.<OngoingAssessment>valueOf(idOfAssessment), NumericIdentificator.<Task>valueOf(idOfTask), input);
    }

    private OngoingAssessment update(Identificator<OngoingAssessment> id, Identificator<Task> idOfTask, String input) throws AssessmentsControllerException
    {
        try
        {
            return this.update(getOngoingAssessmentFactory( ).getInstance(id), getTaskFactory( ).getInstance(idOfTask), input);
        } catch (AssessmentFactoryException e)
        {
            throw new AssessmentsControllerException (e);
        } catch (TaskFactoryException e)
        {
            throw new AssessmentsControllerException (e);
        }
    }

    private TaskFactory getTaskFactory()
    {
        return Factories.<TaskFactory>getInstance(TaskFactory.class);
    }

    public boolean destroy(Long idOfAssessment) throws AssessmentsControllerException
    {
        return this.destroy(NumericIdentificator.<FinishedAssessment>valueOf(idOfAssessment));
    }

    private boolean destroy(Identificator<FinishedAssessment> id) throws AssessmentsControllerException
    {
        try
        {
            return this.destroy(getFinishedAssessmentFactory().getInstance(id));
        } catch (AssessmentFactoryException e)
        {
            throw new AssessmentsControllerException (e);
        }
    }
    
    public boolean destroy (FinishedAssessment assessment) 
    {
        FinishedAssessmentFactory assessmentFactory = Factories.<FinishedAssessmentFactory>getInstance(FinishedAssessmentFactory.class);
        return assessmentFactory.destroy(assessment);
    }

    public boolean finish(Long idOfAssessment) throws AssessmentsControllerException
    {
        return this.finish(NumericIdentificator.<OngoingAssessment>valueOf(idOfAssessment));
    }

    private boolean finish(Identificator<OngoingAssessment> id) throws AssessmentsControllerException
    {
        try
        {
            return this.finish (getOngoingAssessmentFactory( ).getInstance(id));
        } catch (AssessmentFactoryException e)
        {
            throw new AssessmentsControllerException (e);
        }
    }

    private boolean finish (OngoingAssessment instance) throws AssessmentsControllerException
    {
        try
        {
            instance.finish( );
        } catch (FactoryException e)
        {
            throw new AssessmentsControllerException (e);
        }
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
