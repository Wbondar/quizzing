package ch.protonmail.vladyslavbond.quizzing.datasource;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

public class DataAccess<T>
extends Object
{
	private final StatementFactory statementFactory;
    private final Class<T>         typeOfEntities;
    private final Mapper<T>        defaultMapper;

	DataAccess (StatementFactory statementFactory, Class<T> typeOfEntities, Mapper<T> defaultMapper)
	{
		this.statementFactory = statementFactory;
		this.typeOfEntities   = typeOfEntities;
		this.defaultMapper    = defaultMapper;
	}
	
	private final Mapper<T> getDefaultMapper ( ) 
	{
        return defaultMapper;
	}
	
	private final Collection<T> processResultSet (ResultSet resultSet, Mapper<T> mapper) 
	        throws SQLException, MapperException
    {
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        ArrayList<T> entities = new ArrayList<T> ( );
        while (resultSet.next( ))
        {
            int i = resultSetMetaData.getColumnCount();
            while (i > 0)
            {
                String label = resultSetMetaData.getColumnLabel(i);
                Class<?> parameterType;
                try
                {
                    parameterType = Class.forName(resultSetMetaData.getColumnClassName(i));
                } 
                catch (ClassNotFoundException e)
                {
                    throw new SQLException ("Failed to retrieve value from column.", e);
                }
                Object argument = resultSet.getObject(i);
                mapper.set(label, parameterType, argument);
                i--;
            }
            entities.add(mapper.getType( ).cast(mapper.build( )));
            mapper.clear( );
        }
        return entities;
    }

    private final Collection<T> processResultSet(ResultSet resultSet) 
            throws SQLException, MapperException
    {
        return this.processResultSet(resultSet, this.getDefaultMapper( ));
    }

    public final Collection<T> fetchAll (String sql, Mapper<T> mapper, Object... values)
    throws DataAccessException, MapperException
    {
        try
        {
            PreparedStatement statement = statementFactory.prepareStatement(sql, values);
            return processResultSet(statement.executeQuery( ), mapper);
        } catch (SQLException e) {
            throw new DataAccessException ("Failed to fetch data from the database.", e);
        }
    }

    public final Collection<T> fetchAll (String sql, Object... values) 
	        throws DataAccessException, MapperException
	{
	    return this.fetchAll(sql, getDefaultMapper( ), values);
	}
	
	private final Collection<T> fetchAll (String sql) 
	        throws DataAccessException, MapperException
	{
	    return fetchAll(sql, new Object[0]);
	}
	
	public final T fetch (String sql, Object... values) 
	        throws DataAccessException, MapperException
	{
	    try
	    {
	        return fetchAll(sql, values).iterator().next();
	    } catch (NoSuchElementException e) {
	        return null;
	    }
	}
    
    private final T fetch (String sql) 
            throws DataAccessException, MapperException
    {
        return fetch(sql, new Object[0]);
    }
    
    private final int resolveType (Object o)
    {
        if (o instanceof Long)
        {
            return Types.BIGINT;
        }
        if (o instanceof Integer)
        {
            return Types.INTEGER;
        }
        if (o instanceof String)
        {
            return Types.VARCHAR;
        }
        return Types.JAVA_OBJECT;
    }

    public final Collection<T> storeAll (String sql, Mapper<T> mapper, Object... arguments) 
            throws DataAccessException, MapperException
    {
         try
         {
             CallableStatement statement = statementFactory.prepareCall(sql, arguments);
             return processResultSet(statement.executeQuery( ));
         } catch (SQLException e) {
             throw new DataAccessException ("Failed to store data in the database.", e);
         }
    }
    
    public final Collection<T> storeAll (String sql, Object...arguments) 
            throws DataAccessException, MapperException
    {
        return this.storeAll(sql, getDefaultMapper( ), arguments);
    }

    public final T store (String sql, Object... arguments) 
            throws DataAccessException, MapperException
    {
        try
        {
            return this.storeAll(sql, arguments).iterator( ).next( );   
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}