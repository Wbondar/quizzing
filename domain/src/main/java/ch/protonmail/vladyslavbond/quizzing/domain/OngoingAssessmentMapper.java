package ch.protonmail.vladyslavbond.quizzing.domain;

class OngoingAssessmentMapper 
extends AssessmentMapper
{
    public OngoingAssessmentMapper ( )
    {
        super( );
    }
    
    @Override
    public OngoingAssessment build ( )
    {
        Assessment assessment = super.build( );
        return new OngoingAssessment (assessment);
    }
}
