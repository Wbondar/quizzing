package ch.protonmail.vladyslavbond.quizzing.domain;

public final class ExamFactory 
implements Factory<Exam> 
{
	ExamFactory ( ) {}
	
	public Exam newInstance (String titleOfExam)
	{
		// TODO
		return new Exam (new IntIdentificator<Exam> (0), titleOfExam);
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
