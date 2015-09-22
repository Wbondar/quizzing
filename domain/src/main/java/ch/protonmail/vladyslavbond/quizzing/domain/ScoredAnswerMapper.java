package ch.protonmail.vladyslavbond.quizzing.domain;

import ch.protonmail.vladyslavbond.quizzing.datasource.Mapper;
import ch.protonmail.vladyslavbond.quizzing.datasource.NativeMapper;

class ScoredAnswerMapper 
extends NativeMapper<ScoredAnswer>
implements Mapper<ScoredAnswer>
{
    private final transient AnswerMapper answerMapper;

    public ScoredAnswerMapper ( )
    {
        super(ScoredAnswer.class);
        this.answerMapper = new AnswerMapper ( );
    }
    
    @Override
    public ScoredAnswer build ( )
    {
        final Answer answer = answerMapper.build( );
        final ScoredAnswer scoredAnswer = new ScoredAnswer(answer, get("score", Integer.class));
        return scoredAnswer;
    }
}
