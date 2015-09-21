package ch.protonmail.vladyslavbond.quizzing.domain;

import ch.protonmail.vladyslavbond.quizzing.util.*;

public class Answer 
implements Identifiable<Answer>
{
	public static final Answer EMPTY = new Answer ( );

	private Answer ( )
	{
		this.id      = NumericIdentificator.<Answer>valueOf(0);
		this.task    = Task.EMPTY;
		this.student = Student.EMPTY;
		this.input   = "Input is missing.";
	}
	
	Answer (Identificator<Answer> id, Task task, Student student, String input)
	{
		this.id      = id;
		this.task    = task;
		this.student = student;
		this.input   = input;
	}
	
	Answer (int id, Task task, Student student, String input)
	{
		this(NumericIdentificator.<Answer>valueOf(id), task, student, input);
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
	
	private final String input;
	
	public final String getInput ( )
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
