package ch.protonmail.vladyslavbond.quizzing.controllers;

import ch.protonmail.vladyslavbond.quizzing.domain.*;

public final class Exams 
extends Controller 
{
	public Result create (String titleOfExam)
	{
		ExamFactory examFactory = Factories.<ExamFactory>getInstance(ExamFactory.class);
		Exam exam = examFactory.newInstance(titleOfExam);
		if (exam == null || exam.equals(Exam.EMPTY))
		{
			return badRequest("Failure.");
		}
		return this.read(exam);
	}
	
	public Result read (Exam exam)
	{
		if (exam == null || exam.equals(Exam.EMPTY))
		{
			return badRequest("Failure.");
		}
		return ok(exam.getTitle( ));
	}
	
	public Result updatePool (Exam exam, Pool pool, Integer quantityOfTasksToFetchFromPool)
	{
		ExamFactory examFactory = Factories.<ExamFactory>getInstance(ExamFactory.class);
		exam = examFactory.update(exam, pool, quantityOfTasksToFetchFromPool);
		if (exam == null || exam.equals(Exam.EMPTY))
		{
			return badRequest("Failure.");
		}
		return this.read(exam);
	}
	
	public Result updatePoolRemove (Exam exam, Pool pool)
	{
		ExamFactory examFactory = Factories.<ExamFactory>getInstance(ExamFactory.class);
		exam = examFactory.update(exam, pool);
		if (exam == null || exam.equals(Exam.EMPTY))
		{
			return badRequest("Failure.");
		}
		return this.read(exam);
	}
	
	public Result destroy (Exam exam)
	{
		ExamFactory examFactory = Factories.<ExamFactory>getInstance(ExamFactory.class);
		boolean success = examFactory.destroy(exam);
		if (!success)
		{
			return badRequest("Failure.");
		}
		return ok("Success.");
	}
}
