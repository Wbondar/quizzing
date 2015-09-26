package ch.protonmail.vladyslavbond.quizzing.domain;

import java.util.HashSet;
import java.util.Set;

import ch.protonmail.vladyslavbond.quizzing.datasource.DataAccessException;
import ch.protonmail.vladyslavbond.quizzing.datasource.MapperException;
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
	
	public Answer newInstance (OngoingAssessment assessment, Task task, String input) throws AnswerFactoryException
	{
		Object[] arguments = {
		        ((NumericIdentificator<OngoingAssessment>)assessment.getId( )).longValue( )
		        ,((NumericIdentificator<Task>)task.getId( )).longValue( )
		        ,input
		};
		try
        {
            return this.getDataAccess( ).store("{CALL answer_create(?, ?, ?)}", arguments);
        } catch (MapperException | DataAccessException e)
        {
           throw new AnswerFactoryException (e);
        }
	}
	
	@Override
	public Answer getInstance (Identificator<Answer> id) throws AnswerFactoryException 
	{
		Object[] arguments = {((NumericIdentificator<Answer>)id).longValue( )};
		try
        {
            return this.getDataAccess( ).fetch("SELECT * FROM view_answers WHERE id = ?;", arguments);
        } catch (MapperException | DataAccessException e)
        {
            throw new AnswerFactoryException (e);
        }
	}
	
	private Set<Answer> getInstances (long id) 
	        throws AnswerFactoryException
	{
        Set<Answer> answers = new HashSet<Answer> ( );
        try
        {
            answers.addAll(this.getDataAccess( ).fetchAll("SELECT * FROM view_answers WHERE assessment_id = ?;", id));
            return answers;
        } catch (MapperException | DataAccessException e)
        {
            throw new AnswerFactoryException (e);
        }
	}

    public Set<Answer> getOngoingAssessmentInstances(Identificator<OngoingAssessment> id) 
            throws AnswerFactoryException
    {
        return this.getInstances(((NumericIdentificator<OngoingAssessment>)id).longValue( ));
    }

    public Set<Answer> getFinishedAssessmentInstances(Identificator<FinishedAssessment> id) 
            throws AnswerFactoryException
    {
        return this.getInstances(((NumericIdentificator<FinishedAssessment>)id).longValue( ));
    }

    public Set<Answer> getInstances(FinishedAssessment finishedAssessment) throws AnswerFactoryException
    {
        return getFinishedAssessmentInstances(finishedAssessment.getId( ));
    }

    public Set<Answer> getInstances(OngoingAssessment ongoingAssessment) throws AnswerFactoryException
    {
        return getOngoingAssessmentInstances(ongoingAssessment.getId( ));
    }
}
