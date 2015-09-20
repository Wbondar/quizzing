package ch.protonmail.vladyslavbond.quizzing.controllers;

import ch.protonmail.vladyslavbond.quizzing.domain.*;

public final class Assessments 
extends Controller 
{
    public Result create (Member member, Exam exam) 
    {
        MemberFactory memberFactory = Factories.<MemberFactory>getInstance(MemberFactory.class);
        OngoingAssessment assessment = exam.beginAssessmentFor(memberFactory.getStudent(member));
        if (assessment == null || assessment.equals(OngoingAssessment.EMPTY))
        {
            return badRequest("Failure.");
        }
        return this.read(assessment);
    }
    
    public Result read (Assessment assessment) 
    {
        if (assessment == null || assessment.equals(Assessment.EMPTY))
        {
            return badRequest("Failure.");
        }
        return ok(assessment.getId( ).toString( ));
    }
    
    public Result read (OngoingAssessment assessment) 
    {
        return this.read((Assessment)assessment);
    }

    private Result update (OngoingAssessment assessment, Task task, String answer)
    {
        Answer result = assessment.provideAnswer(task, answer);
        if (result == null || result.equals(Answer.EMPTY))
        {
            return badRequest("Failure.");
        }
        return this.read(assessment);
    }
    
    public Result update (Assessment assessment, Task task, String answer) 
    {
        return this.update((OngoingAssessment)assessment, task, answer);
    }
    
    public Result destroy (Assessment assessment) 
    {
        AssessmentFactory assessmentFactory = Factories.<AssessmentFactory>getInstance(AssessmentFactory.class);
        boolean success = assessmentFactory.destroy(assessment);
        if (!success)
        {
            return badRequest("Failure.");
        }
        return ok("Success.");
    }
}
