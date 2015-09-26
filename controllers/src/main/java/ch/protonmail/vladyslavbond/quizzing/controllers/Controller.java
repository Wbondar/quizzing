package ch.protonmail.vladyslavbond.quizzing.controllers;

import ch.protonmail.vladyslavbond.quizzing.domain.ExamFactory;
import ch.protonmail.vladyslavbond.quizzing.domain.Factories;
import ch.protonmail.vladyslavbond.quizzing.domain.FinishedAssessmentFactory;
import ch.protonmail.vladyslavbond.quizzing.domain.InstructorFactory;
import ch.protonmail.vladyslavbond.quizzing.domain.OngoingAssessmentFactory;
import ch.protonmail.vladyslavbond.quizzing.domain.PoolFactory;
import ch.protonmail.vladyslavbond.quizzing.domain.StudentFactory;
import ch.protonmail.vladyslavbond.quizzing.domain.TaskFactory;

abstract class Controller 
{
    protected final PoolFactory getPoolFactory ( )
    {
        return Factories.<PoolFactory>getInstance(PoolFactory.class);
    }
    
    protected final TaskFactory getTaskFactory ( )
    {
        return Factories.<TaskFactory>getInstance(TaskFactory.class);
    }
    
    protected final InstructorFactory getInstructorFactory ( )
    {
        return Factories.<InstructorFactory>getInstance(InstructorFactory.class);
    }

    protected final OngoingAssessmentFactory getOngoingAssessmentFactory()
    {
        return Factories.<OngoingAssessmentFactory>getInstance(OngoingAssessmentFactory.class);
    }

    protected final FinishedAssessmentFactory getFinishedAssessmentFactory()
    {
        return Factories.<FinishedAssessmentFactory>getInstance(FinishedAssessmentFactory.class);
    }

    protected final ExamFactory getExamFactory()
    {
        return Factories.<ExamFactory>getInstance(ExamFactory.class);
    }

    protected final StudentFactory getStudentFactory()
    {
        return Factories.<StudentFactory>getInstance(StudentFactory.class);
    }
}