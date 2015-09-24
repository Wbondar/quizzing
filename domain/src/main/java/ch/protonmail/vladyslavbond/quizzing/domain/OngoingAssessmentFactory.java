package ch.protonmail.vladyslavbond.quizzing.domain;

import ch.protonmail.vladyslavbond.quizzing.datasource.DataAccessException;
import ch.protonmail.vladyslavbond.quizzing.datasource.MapperException;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;

public final class OngoingAssessmentFactory 
extends SimpleFactory<OngoingAssessment>
{
    public OngoingAssessmentFactory ( )
    {
        super(OngoingAssessment.class, new OngoingAssessmentMapper ( ));
    }
    
    public OngoingAssessment newInstance (Student student, Exam exam) throws AssessmentFactoryException
    {
        Object[] arguments = {
                  ((NumericIdentificator<Student>)student.getId( )).longValue()
                , ((NumericIdentificator<Exam>)exam.getId( )).longValue()
        };
        try
        {
            return (OngoingAssessment) this.getDataAccess( ).store("{CALL assessment_ongoing_create (?, ?)}", new OngoingAssessmentMapper ( ), arguments);
        } catch (MapperException | DataAccessException e)
        {
            throw new AssessmentFactoryException (e);
        }
    }
    
    @Override
    public OngoingAssessment getInstance(Identificator<OngoingAssessment> id) throws AssessmentFactoryException
    {
        Object[] arguments = {
                ((NumericIdentificator<OngoingAssessment>)id).longValue()
      };
      try
      {
          return this.getDataAccess( ).fetch("SELECT * FROM view_ongoing_assessments WHERE id = ?;", arguments);
      } catch (DataAccessException | MapperException e)
      {
          throw new AssessmentFactoryException (e);
      }
    }

    public boolean destroy(OngoingAssessment ongoingAssessment) throws AssessmentFactoryException
    {
        Object[] arguments = {
                ((NumericIdentificator<OngoingAssessment>)ongoingAssessment.getId( )).longValue()
      };
      try
      {
          return this.getDataAccess( ).store("{CALL ongoing_assessment_destroy(?)}", arguments) != null;
      } catch (DataAccessException | MapperException e)
      {
          throw new AssessmentFactoryException (e);
      }
    }

}
