package ch.protonmail.vladyslavbond.quizzing.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import ch.protonmail.vladyslavbond.quizzing.datasource.Mapper;
import ch.protonmail.vladyslavbond.quizzing.datasource.MapperException;
import ch.protonmail.vladyslavbond.quizzing.datasource.NativeMapper;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;

public final class ScoreMapper
extends NativeMapper<Score>
implements Mapper<Score>
{
    public ScoreMapper ( )
    {
        super(Score.class);
    }
    
    @Override
    public Score build ( ) throws ScoreMapperException, MapperException
    {
        try
        {
            FinishedAssessmentFactory assessmentFactory = Factories.<FinishedAssessmentFactory>getInstance(FinishedAssessmentFactory.class);
            Identificator<FinishedAssessment> idOfAssessment = NumericIdentificator.<FinishedAssessment>valueOf(this.<Long>get("assessment_id", Long.class));
            FinishedAssessment assessment = assessmentFactory.getInstance(idOfAssessment);
            Identificator<Score> id = NumericIdentificator.<Score>valueOf(this.<Long>get("id", Long.class));
            TaskFactory taskFactory = Factories.<TaskFactory>getInstance(TaskFactory.class);
            Identificator<Task> idOfTask = NumericIdentificator.<Task>valueOf(this.<Long>get("task_id", Long.class));
            Task task = taskFactory.getInstance(idOfTask);
            Set<Answer> answers = new HashSet<Answer> ( );
            for (Answer answer : assessment.getAnswers( ))
            {
                if (answer.getTask( ).getId( ).equals(idOfTask))
                {
                    answers.add(answer);
                }
            }
            answers = Collections.<Answer>unmodifiableSet(answers);
            return new Score (id, assessment.getStudent( ), task, answers, this.<Integer>get("reward", Integer.class));
        } catch (FactoryException e) {
            throw new ScoreMapperException (e);
        }
    }
}
