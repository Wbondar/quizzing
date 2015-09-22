package ch.protonmail.vladyslavbond.quizzing.domain;

import ch.protonmail.vladyslavbond.quizzing.datasource.DataAccess;
import ch.protonmail.vladyslavbond.quizzing.datasource.DataAccessFactory;
import ch.protonmail.vladyslavbond.quizzing.datasource.NativeMapper;
import ch.protonmail.vladyslavbond.quizzing.util.Identifiable;

abstract class SimpleFactory<T extends Identifiable<T>>
implements Factory<T> 
{
    private final DataAccess<T> dataAccess;
    
	protected SimpleFactory (Class<T> type, NativeMapper<T> mapper) 
	{
	    this.dataAccess = DataAccessFactory.<T>getInstance(type, mapper);
	}
	
	protected final DataAccess<T> getDataAccess ( )
	{
	    return this.dataAccess;
	}
}
