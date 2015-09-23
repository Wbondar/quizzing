package ch.protonmail.vladyslavbond.quizzing.domain;

import java.util.HashSet;
import java.util.Set;

import ch.protonmail.vladyslavbond.quizzing.datasource.DataAccessException;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;

public final class FinishedAssessmentFactory 
extends SimpleFactory<FinishedAssessment>
implements Factory<FinishedAssessment> 
{
    FinishedAssessmentFactory ( ) 
    {
        super(FinishedAssessment.class, new FinishedAssessmentMapper ( ));
    }
	
	@Override
	public FinishedAssessment getInstance (Identificator<FinishedAssessment> id) 
	{
        Object[] arguments = {
                ((NumericIdentificator<FinishedAssessment>)id).longValue()
      };
      try
      {
          return this.getDataAccess( ).fetch("SELECT * FROM view_finished_assessments WHERE id = ?;", arguments);
      } catch (DataAccessException e)
      {
          throw new AssessmentFactoryException (e);
      }
	}
	
	public Set<FinishedAssessment> getInstances (Identificator<Student> idOfStudent)
	{
        Object[] arguments = {
                ((NumericIdentificator<Student>)idOfStudent).longValue()
      };
      Set<FinishedAssessment> assessments = new HashSet<FinishedAssessment> ( );
      try
      {
          assessments.addAll(this.getDataAccess( ).fetchAll("SELECT * FROM view_finished_assessments WHERE student_id = ?;", arguments));
      } catch (DataAccessException e)
      {
          throw new AssessmentFactoryException (e);
      }
      return assessments;
	}
	
	public boolean destroy (FinishedAssessment assessment)
	{
		// TODO
		return false;
	}
}
