package ch.protonmail.vladyslavbond.quizzing.domain;

import ch.protonmail.vladyslavbond.quizzing.datasource.DataAccessException;
import ch.protonmail.vladyslavbond.quizzing.datasource.MapperException;
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
	
	private Exam newInstance (Identificator<Instructor> idOfInstructor, String titleOfExam)
	        throws ExamFactoryException
	{
      Object[] arguments = {
                  ((NumericIdentificator<Instructor>)idOfInstructor).intValue()
                , titleOfExam
      };
      try
      {
          return this.getDataAccess( ).store("{CALL exam_create(?, ?)}", arguments);
      } catch (MapperException | DataAccessException e)
      {
          throw new ExamFactoryException (e);
      }
	}
	
	public Exam newInstance (Instructor instructor, String titleOfExam) 
	        throws ExamFactoryException
	{
	    return this.newInstance(instructor.getId( ), titleOfExam);
	}
	
	@Override
	public Exam getInstance (Identificator<Exam> id) 
	        throws ExamFactoryException 
	{
      Object[] arguments = {
                  ((NumericIdentificator<Exam>)id).intValue()
      };
      try
      {
          return this.getDataAccess( ).fetch("SELECT * FROM view_exams WHERE id = ?;", arguments);
      } catch (MapperException | DataAccessException e)
      {
          throw new ExamFactoryException (e);
      }
	}
	
	private Exam update(Identificator<Instructor> idOfInstructor, Identificator<Exam> idOfExam, Identificator<Pool> idOfPool, Integer quantity) 
            throws ExamFactoryException
    {
          Object[] arguments = {
                   idOfInstructor.toNumber().intValue()
                  ,((NumericIdentificator<Exam>)idOfExam).intValue()
                  ,((NumericIdentificator<Pool>)idOfPool).intValue()
                  ,quantity
          };
          try
          {
              if (quantity < 1)
              {
                  return this.getDataAccess( ).store("{CALL exam_update_pool_remove (?, ?, ?, ?);", arguments);
              } else {
                  return this.getDataAccess( ).store("{CALL exam_update_pool_add (?, ?, ?, ?)}", arguments);   
              }
          } catch (MapperException | DataAccessException e)
          {
              throw new ExamFactoryException (e);
          }
    }
    
    public Exam update (Instructor instructor, Exam exam, Pool pool, Integer quantity) 
            throws ExamFactoryException
    {
        return this.update(instructor.getId( ), exam.getId( ), pool.getId( ), quantity);
    }

    private Exam update(Identificator<Instructor> idOfInstructor, Identificator<Exam> idOfExam, Identificator<Pool> idOfPool) 
            throws ExamFactoryException
    {
          Object[] arguments = {
                  idOfInstructor.toNumber( ).intValue( )
                  ,((NumericIdentificator<Exam>)idOfExam).intValue()
                  ,((NumericIdentificator<Pool>)idOfPool).intValue()
          };
          try
          {
              return this.getDataAccess( ).store("{CALL exam_update_pool_remove (?, ?, ?, ?)}", arguments);
          } catch (MapperException | DataAccessException e)
          {
              throw new ExamFactoryException (e);
          }
    }

    public Exam update (Instructor instructor, Exam exam, Pool pool) 
            throws ExamFactoryException
	{
		return this.update(instructor.getId( ), exam.getId( ), pool.getId( ));
	}
    
    private boolean destroy (Identificator<Instructor> idOfInstructor, Identificator<Exam> idOfExam)
    {
        // TODO Implement deletion of exams from database.
        return false;
    }

    public boolean destroy (Instructor instructor, Exam exam) 
	{
		return this.destroy(instructor.getId( ), exam.getId( ));
	}
}
