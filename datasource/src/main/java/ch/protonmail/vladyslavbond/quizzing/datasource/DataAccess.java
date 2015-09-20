package ch.protonmail.vladyslavbond.quizzing.datasource;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract class DataAccess<T>
extends Object
{
	private final StatementFactory           statementFactory;
    private final Class<T>                   typeOfEntities;
    private final Class<? extends Mapper<T>> typeOfDefaultMapper;

	DataAccess (StatementFactory statementFactory, Class<T> typeOfEntities, Class<? extends Mapper<T>> typeOfDefaultMapper)
	{
		this.statementFactory = statementFactory;
		this.typeOfEntities   = typeOfEntities;
		this.typeOfDefaultMapper     = typeOfDefaultMapper;
	}
	
	private final Mapper<T> getDefaultMapper ( ) 
	        throws DataAccessException
	{
        Mapper<T> mapper;
        try
        {
            mapper = typeOfDefaultMapper.newInstance( );
        } 
        catch (InstantiationException e1)
        {
            throw new DataAccessException ("Failed to instantiate mapper.", e1);
        } 
        catch (IllegalAccessException e1)
        {
            throw new DataAccessException ("Failed to instantiate mapper.", e1);
        }
        return mapper;
	}
	
	protected final CallableStatement prepareCall (String sql)
	throws SQLException
	{
		return statementFactory.prepareCall(sql);
	}

	protected final PreparedStatement prepareStatement (String sql)
	throws SQLException
	{
		return statementFactory.prepareStatement(sql);
	}
    
    public final boolean execute (String sql, Map<String, Object> labelToValue) 
    throws SQLException
    {
        CallableStatement statement = prepareCall(sql);
        for (String label : labelToValue.keySet( ))
        {
            statement.setObject(label, labelToValue.get(label));
        }
        return statement.execute( );
    }
    
    public final boolean execute (String sql, List<Object> values) 
    throws SQLException
    {
        CallableStatement statement = prepareCall(sql);
        int i = 1;
        for (Object value : values)
        {
            statement.setObject(i++, value);
        }
        return statement.execute( );
    }

	public final Collection<T> fetchAll (String sql, List<Object> values, Mapper<T> mapper)
	throws DataAccessException
	{
	    try
	    {
    		PreparedStatement statement = prepareStatement(sql);
    		int j = 1;
            for (Object o : values)
            {
                statement.setObject(j++, o);
            }
    		ResultSet resultSet = statement.executeQuery( );
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
                        throw new DataAccessException ("Failed to retrieve value.", e);
                    }
    		        Object argument = resultSet.getObject(i);
    		        mapper.set(label, parameterType, argument);
    		        i--;
    		    }
    		    entities.add(mapper.build( ));
    		}
    		return entities;
	    } catch (SQLException e) {
	        throw new DataAccessException ("Failed to fetch data from the database.", e);
	    }
	}
	
	public final Collection<T> fetchAll (String sql, List<Object> values) 
	        throws DataAccessException
	{
	    return this.fetchAll(sql, values, getDefaultMapper( ));
	}
	
	public final Collection<T> fetchAll (String sql) 
	        throws DataAccessException
	{
	    return fetchAll(sql, Collections.<Object>emptyList( ));
	}
	
	public final T fetch (String sql, List<Object> values) 
	        throws DataAccessException
	{
	    return fetchAll(sql, values).iterator().next();
	}
    
    public final T fetch (String sql) 
            throws DataAccessException
    {
        return fetch(sql, Collections.<Object>emptyList( ));
    }
}