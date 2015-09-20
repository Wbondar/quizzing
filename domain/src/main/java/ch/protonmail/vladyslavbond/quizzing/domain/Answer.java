package ch.protonmail.vladyslavbond.quizzing.domain;

import java.util.Collection;
import java.util.Collections;

public class Answer 
implements Identifiable<Answer>
{
	public static final Answer EMPTY = new Answer ( );

	private Answer ( )
	{
		this.id = new IntIdentificator<Answer> (0);
		this.task = Task.EMPTY;
		this.student = Student.EMPTY;
		this.input = Collections.<String>emptyList( );
	}

	/**
	 * TODO: Input has to be single string.
	 * Multiple choice tasks will have multiple answers 
	 * instead of single answer containing 
	 * multiple inputs in a single answer.
	 */

	Answer (Identificator<Answer> id, Task task, Student student, Collection<String> input)
	{
		this.id      = id;
		this.task    = task;
		this.student = student;
		this.input   = Collections.<String>unmodifiableCollection(input);
	}
	
	Answer (int id, Task task, Student student, Collection<String> input)
	{
		this(new IntIdentificator<Answer> (id), task, student, input);
	}
	
	private final Task task;
	
	public final Task getTask ( )
	{
		return this.task;
	}
	
	private final Student student;
	
	public final Student getStudent ( )
	{
		return this.student;
	}
	
	private final Collection<String> input;
	
	public final Collection<String> getInput ( )
	{
		return this.input;
	}
	
	private final Identificator<Answer> id;
	
	@Override
	public final Identificator<Answer> getId ( )
	{
		return this.id;
	}
	
	@Override
	public final boolean equals (Object o)
	{
		if (o == null)
		{
			return false;
		}
		if (o instanceof Answer)
		{
			return o.hashCode( ) == this.hashCode( );
		}
		return false;
	}
	
	@Override
	public int hashCode ( )
	{
		return this.id.hashCode( ) + this.task.hashCode( ) + this.student.hashCode( ) + this.input.hashCode( );
	}
}
