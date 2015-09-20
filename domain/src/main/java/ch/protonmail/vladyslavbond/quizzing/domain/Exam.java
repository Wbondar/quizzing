package ch.protonmail.vladyslavbond.quizzing.domain;

import java.util.Set;

public final class Exam 
implements Identifiable<Exam> 
{
	public final static Exam EMPTY = new Exam ( );
	
	private Exam ( )
	{
		this.id = new IntIdentificator<Exam> (0);
		this.title = "Title of the exam is missing.";
	}
	
	Exam (Identificator<Exam> id, String title)
	{
		this.id = id;
		this.title = title;
	}
	
	public Set<Pool> getPools ( )
	{
		return Factories.<PoolFactory>getInstance(PoolFactory.class).getInstances(this.id);
	}
	
	Set<Task> getTasks ( )
	{
		return Factories.<TaskFactory>getInstance(TaskFactory.class).getInstances(this);
	}
	
	public OngoingAssessment beginAssessmentFor (Student student)
	{
		return Factories.<AssessmentFactory>getInstance(AssessmentFactory.class).newInstance(student, this);
	}
	
	private final String title;
	
	public String getTitle ( )
	{
		return this.title;
	}
	
	private final Identificator<Exam> id;
	
	@Override
	public Identificator<Exam> getId ( ) 
	{
		return this.id;
	}
	
	@Override
	public boolean equals (Object o)
	{
		if (o == null)
		{
			return false;
		}
		if (o instanceof Exam)
		{
			return o.hashCode( ) == this.hashCode( );
		}
		return false;
	}
	
	@Override
	public int hashCode ( )
	{
		return this.id.hashCode( );
	}
}
