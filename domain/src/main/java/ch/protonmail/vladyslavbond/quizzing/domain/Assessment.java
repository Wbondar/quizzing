package ch.protonmail.vladyslavbond.quizzing.domain;

import java.util.Collections;
import java.util.Set;

import ch.protonmail.vladyslavbond.quizzing.util.Identifiable;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;

public class Assessment 
implements Identifiable<Assessment>
{
	public static Assessment EMPTY = new Assessment ( );
	
	private Assessment ( )
	{
		this.id      = (Identificator<Assessment>)NumericIdentificator.<Assessment>valueOf(0);
		this.student = Student.EMPTY;
		this.tasks   = Collections.<Task>emptySet( );
	}
	
	Assessment (Identificator<Assessment> id, Student student, Set<Task> tasks)
	{
		this.id      = id;
		this.student = student;
		this.tasks   = Collections.<Task>unmodifiableSet(tasks);
	}
	
	private final Student student;
	
	public final Student getStudent ( )
	{
		return this.student;
	}
	
	private final Set<Task> tasks;
	
	public final Set<Task> getTasks ( )
	{
		return this.tasks;
	}
	
	public final Set<? extends Answer> getAnswers ( )
	{
		return Factories.<AnswerFactory>getInstance(AnswerFactory.class).getInstances(this.id);
	}
	
	private final Identificator<Assessment> id;
	
	@Override
	public Identificator<Assessment> getId ( )
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
		if (o instanceof Assessment)
		{
			return o.hashCode( ) == this.hashCode( );
		}
		return false;
	}
	
	@Override
	public final int hashCode ( )
	{
		return this.id.hashCode( ) + this.student.hashCode( ) + this.tasks.hashCode( );
	}
}
