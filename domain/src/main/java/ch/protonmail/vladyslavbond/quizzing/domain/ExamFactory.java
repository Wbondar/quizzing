package ch.protonmail.vladyslavbond.quizzing.domain;

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
	
	public Exam newInstance (String titleOfExam)
	{
		// TODO
		return new Exam (NumericIdentificator.<Exam>valueOf(0), titleOfExam);
	}
	
	@Override
	public Exam getInstance (Identificator<Exam> id) 
	{
		// TODO
		return Exam.EMPTY;
	}
	
	public Exam update (Exam exam, Pool pool, Integer quantity)
	{
		// TODO
		return Exam.EMPTY;
	}
	
	public Exam update (Exam exam, Pool pool)
	{
		// TODO
		return Exam.EMPTY;
	}

	public boolean destroy (Exam exam) 
	{
		// TODO Auto-generated method stub
		return false;
	}
}
