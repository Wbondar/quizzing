package ch.protonmail.vladyslavbond.quizzing.domain;

import ch.protonmail.vladyslavbond.quizzing.datasource.DataAccessException;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;

public final class ExamFactory 
extends SimpleFactory<Exam>
implements Factory<Exam> 
{
    ExamFactory ( ) 
    {
        super(Exam.class, new ExamMapper ( ));
    }
	
	public Exam newInstance (Identificator<Instructor> idOfInstructor, String titleOfExam)
	{
      Object[] arguments = {
                  ((NumericIdentificator<Instructor>)idOfInstructor).longValue()
                , titleOfExam
      };
      try
      {
          return this.getDataAccess( ).store("{CALL exam_create(?, ?)}", arguments);
      } catch (DataAccessException e)
      {
          throw new ExamFactoryException (e);
      } finally {
          return Exam.EMPTY;
      }
	}
	
	@Override
	public Exam getInstance (Identificator<Exam> id) 
	{
      Object[] arguments = {
                  ((NumericIdentificator<Exam>)id).longValue()
      };
      try
      {
          return this.getDataAccess( ).fetch("SELECT * FROM view_exams WHERE id = ?;", arguments);
      } catch (DataAccessException e)
      {
          throw new ExamFactoryException (e);
      } finally {
          return Exam.EMPTY;
      }
	}
	
	public Exam update (Exam exam, Pool pool, Integer quantity)
	{
		return this.update(exam.getId( ), pool.getId( ), quantity);
	}
	
	public Exam update(Identificator<Exam> idOfExam, Identificator<Pool> idOfPool,
            Integer quantity)
    {
      Object[] arguments = {
              ((NumericIdentificator<Exam>)idOfExam).longValue()
              ,((NumericIdentificator<Pool>)idOfPool).longValue()
              ,quantity
      };
      try
      {
          return this.getDataAccess( ).store("{CALL exam_update_pool_add (?, ?, ?)}", arguments);
      } catch (DataAccessException e)
      {
          throw new ExamFactoryException (e);
      } finally {
          return Exam.EMPTY;
      }
    }

    public Exam update (Exam exam, Pool pool)
	{
		return this.update(exam.getId( ), pool.getId( ));
	}

	public Exam update(Identificator<Exam> idOfExam, Identificator<Pool> idOfPool)
    {
	      Object[] arguments = {
	              ((NumericIdentificator<Exam>)idOfExam).longValue()
	              ,((NumericIdentificator<Pool>)idOfPool).longValue()
	      };
	      try
	      {
	          return this.getDataAccess( ).store("{CALL exam_update_pool_remove (?, ?)}", arguments);
	      } catch (DataAccessException e)
	      {
	          throw new ExamFactoryException (e);
	      } finally {
	          return Exam.EMPTY;
	      }
    }

    public boolean destroy (Exam exam) 
	{
		// TODO Auto-generated method stub
		return false;
	}
}
