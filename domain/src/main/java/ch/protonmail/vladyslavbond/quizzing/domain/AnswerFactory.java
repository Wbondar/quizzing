package ch.protonmail.vladyslavbond.quizzing.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import ch.protonmail.vladyslavbond.quizzing.datasource.DataAccess;
import ch.protonmail.vladyslavbond.quizzing.datasource.DataAccessException;
import ch.protonmail.vladyslavbond.quizzing.datasource.DataAccessFactory;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;

public final class AnswerFactory 
extends SimpleFactory<Answer>
implements Factory<Answer> 
{
	AnswerFactory ( ) 
	{
	    super(Answer.class, new AnswerMapper ( ));
	}
	
	public Answer newInstance (Assessment assessment, Task task, String input)
	{
		Object[] arguments = {
		        ((NumericIdentificator<Assessment>)assessment.getId( )).longValue( )
		        ,((NumericIdentificator<Task>)task.getId( )).longValue( )
		        ,input
		};
		try
        {
            return this.getDataAccess( ).store("{CALL answer_create(?, ?, ?)}", arguments);
        } catch (DataAccessException e)
        {
           throw new AnswerFactoryException (e);
        }
	}
	
	public ScoredAnswer newInstance (OngoingAssessment assessment, Task task, String input, int reward)
	{
        Object[] arguments = {
                ((NumericIdentificator<Assessment>)assessment.getId( )).longValue( )
                ,((NumericIdentificator<Task>)task.getId( )).longValue( )
                ,input
                ,reward
        };
        try
        {
            return (ScoredAnswer)this.getDataAccess( ).store("{CALL answer_scored_create(?, ?, ?, ?)}", new ScoredAnswerMapper( ), arguments);
        } catch (DataAccessException e)
        {
           throw new AnswerFactoryException (e);
        }
	}
	
	@Override
	public Answer getInstance (Identificator<Answer> id) 
	{
		Object[] arguments = {((NumericIdentificator<Answer>)id).longValue( )};
		try
        {
            return this.getDataAccess( ).fetch("SELECT * FROM view_answer WHERE id = ?;", arguments);
        } catch (DataAccessException e)
        {
            throw new AnswerFactoryException (e);
        }
	}
	
	public Set<? extends Answer> getInstances (Identificator<Assessment> idOfAssessment)
	{
        Object[] arguments = {((NumericIdentificator<Assessment>)idOfAssessment).longValue( )};
        Set<Answer> answers = new HashSet<Answer> ( );
        try
        {
            answers.addAll(this.getDataAccess( ).fetchAll("SELECT * FROM view_answer WHERE assessment_id = ?", arguments));
        } catch (DataAccessException e)
        {
            throw new AnswerFactoryException (e);
        }
        return answers;
	}
}
