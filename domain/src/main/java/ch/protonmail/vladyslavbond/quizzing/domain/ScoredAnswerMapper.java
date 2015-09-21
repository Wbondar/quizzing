package ch.protonmail.vladyslavbond.quizzing.domain;

class ScoredAnswerMapper 
extends AnswerMapper
{
    public ScoredAnswerMapper ( )
    {
        
    }
    
    @Override
    public ScoredAnswer build ( )
    {
        final Answer answer = super.build( );
        final ScoredAnswer scoredAnswer = new ScoredAnswer(answer, get("score", Integer.class));
        return scoredAnswer;
    }
}
