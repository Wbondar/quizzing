package ch.protonmail.vladyslavbond.quizzing.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import ch.protonmail.vladyslavbond.quizzing.datasource.DataAccessException;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;

public final class ScoreFactory 
extends SimpleFactory<Score>
{
    public ScoreFactory( )
    {
        super(Score.class, new ScoreMapper ( ));
    }

    public Score newInstance (OngoingAssessment assessment, Task task, int reward)
    {
        Object[] arguments = {
                ((NumericIdentificator<OngoingAssessment>)assessment.getId( )).longValue( )
                ,((NumericIdentificator<Task>)task.getId( )).longValue( )
                ,reward
        };
        try
        {
            return this.getDataAccess( ).store("{CALL score_create(?, ?, ?)}", arguments);
        } catch (DataAccessException e)
        {
           throw new ScoreFactoryException (e);
        }
    }

    @Override
    public Score getInstance(Identificator<Score> id)
    {
        Object[] arguments = {
                ((NumericIdentificator<Score>)id).longValue( )
        };
        try
        {
            return this.getDataAccess( ).fetch("SELECT * FROM view_score WHERE id = ?;", arguments);
        } catch (DataAccessException e)
        {
           throw new ScoreFactoryException (e);
        }
    }

    public Set<Score> getInstances (Identificator<FinishedAssessment> id)
    {
        Object[] arguments = {
                ((NumericIdentificator<FinishedAssessment>)id).longValue( )
        };
        Set<Score> scores = new HashSet<Score> ( );
        try
        {
            scores.addAll(this.getDataAccess( ).fetchAll("SELECT * FROM view_score WHERE assessment_id = ?;", arguments));
            return scores;
        } catch (DataAccessException e)
        {
           throw new ScoreFactoryException (e);
        } finally {
            return scores;
        }
    }
}