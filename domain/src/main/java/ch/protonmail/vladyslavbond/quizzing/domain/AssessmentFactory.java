package ch.protonmail.vladyslavbond.quizzing.domain;

import java.util.HashSet;
import java.util.Set;

import ch.protonmail.vladyslavbond.quizzing.datasource.DataAccessException;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;

public final class AssessmentFactory 
extends SimpleFactory<Assessment>
implements Factory<Assessment> 
{
    AssessmentFactory ( ) 
    {
        super(Assessment.class, new AssessmentMapper ( ));
    }
	
	public OngoingAssessment newInstance (Student student, Exam exam)
	{
		Object[] arguments = {
		          ((NumericIdentificator<Member>)student.getId( )).longValue()
		        , ((NumericIdentificator<Exam>)exam.getId( )).longValue()
		};
		try
        {
            return (OngoingAssessment) this.getDataAccess( ).store("{CALL assessment_ongoing_create (?, ?)}", new OngoingAssessmentMapper ( ), arguments);
        } catch (DataAccessException e)
        {
            throw new AssessmentFactoryException (e);
        }
	}
	
	@Override
	public Assessment getInstance (Identificator<Assessment> id) 
	{
        Object[] arguments = {
                ((NumericIdentificator<Assessment>)id).longValue()
      };
      try
      {
          return this.getDataAccess( ).fetch("SELECT * FROM view_assessments WHERE id = ?;", arguments);
      } catch (DataAccessException e)
      {
          throw new AssessmentFactoryException (e);
      }
	}
	
	public Set<Assessment> getInstances (Identificator<Student> idOfStudent)
	{
        Object[] arguments = {
                ((NumericIdentificator<Student>)idOfStudent).longValue()
      };
      Set<Assessment> assessments = new HashSet<Assessment> ( );
      try
      {
          assessments.addAll(this.getDataAccess( ).fetchAll("SELECT * FROM view_assessments WHERE student_id = ?;", arguments));
      } catch (DataAccessException e)
      {
          throw new AssessmentFactoryException (e);
      }
      return assessments;
	}
	
	public boolean destroy (Assessment assessment)
	{
		// TODO
		return false;
	}
	
	public boolean destroy (OngoingAssessment assessment)
	{
		// TODO
		return false;
	}
}
