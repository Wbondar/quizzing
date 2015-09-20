package ch.protonmail.vladyslavbond.quizzing.domain;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

abstract class SimpleFactory<T extends Identifiable<T>>
implements Factory<T> 
{
	SimpleFactory ( ) 
	{
		this(DatasourceConnectionFactory.INSTANCE);
	}

	private SimpleFactory (ConnectionFactory connectionFactory)
	{
		this.connectionFactory = connectionFactory;
	}

	private final ConnectionFactory connectionFactory;
	
	private final Connection getConnection ( )
	throws SQLException
	{
		return this.connectionFactory.getConnection( );
	}
	
	protected final CallableStatement prepareCall (String query)
	throws SQLException
	{
		try
		(
			Connection connection = this.getConnection( );
		)
		{
			return connection.prepareCall(query);
		} catch (SQLException e) {
			throw e;
		}
	}
	
	protected final PreparedStatement prepareStatement (String query)
	throws SQLException
	{
		try
		(
			Connection connection = this.getConnection( );
		)
		{
			return connection.prepareStatement(query);
		} catch (SQLException e) {
			throw e;
		}
	}
}
