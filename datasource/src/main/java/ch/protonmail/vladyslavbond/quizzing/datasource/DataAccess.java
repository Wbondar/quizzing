package ch.protonmail.vladyslavbond.quizzing.datasource;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

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
	        throws SQLException
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
            throws SQLException
    {
        return this.processResultSet(resultSet, this.getDefaultMapper( ));
    }

    public final Collection<T> fetchAll (String sql, NativeMapper<T> mapper, Object... values)
    throws DataAccessException
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
	        throws DataAccessException
	{
	    return this.fetchAll(sql, getDefaultMapper( ), values);
	}
	
	public final Collection<T> fetchAll (String sql) 
	        throws DataAccessException
	{
	    return fetchAll(sql, new Object[0]);
	}
	
	public final T fetch (String sql, Object... values) 
	        throws DataAccessException
	{
	    return fetchAll(sql, values).iterator().next();
	}
    
    public final T fetch (String sql) 
            throws DataAccessException
    {
        return fetch(sql, new Object[0]);
    }

    public final Collection<T> storeAll (String sql, Object... arguments) 
            throws DataAccessException
    {
         try
         {
             CallableStatement statement = statementFactory.prepareCall(sql, arguments);
             return processResultSet(statement.executeQuery( ));
         } catch (SQLException e) {
             throw new DataAccessException ("Failed to store data in the database.", e);
         }
    }

    public final T store (String sql, Object... arguments) 
            throws DataAccessException
    {
         return this.storeAll(sql, arguments).iterator( ).next( );
    }
}