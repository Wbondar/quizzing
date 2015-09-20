package ch.protonmail.vladyslavbond.quizzing.domain;

import java.util.Set;

public final class PoolFactory 
extends SimpleFactory<Pool>
implements Factory<Pool> 
{
	PoolFactory ( ) {}
	
	@Override
	public Pool getInstance (Identificator<Pool> id) 
	{
		// TODO
		return Pool.EMPTY;
	}
	
	public Set<Pool> getInstances (Identificator<Exam> idOfExam)
	{
		// TODO
		return java.util.Collections.<Pool>emptySet( );
	}

	public Pool newInstance(String titleOfPool) 
	{
		// TODO Auto-generated method stub
		return new Pool (new IntIdentificator<Pool> (0), titleOfPool);
	}

	public Pool update(Pool pool, String titleOfPool) 
	{
		// TODO Auto-generated method stub
		return new Pool (pool.getId( ), titleOfPool);
	}

	public Pool update(Pool pool, Task task, boolean b) 
	{
		// TODO Auto-generated method stub
		return Pool.EMPTY;
	}

	public boolean destroy(Pool pool) 
	{
		// TODO Auto-generated method stub
		return false;
	}
}
