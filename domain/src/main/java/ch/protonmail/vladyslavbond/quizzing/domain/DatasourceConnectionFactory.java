package ch.protonmail.vladyslavbond.quizzing.domain;

import java.sql.*;

enum DatasourceConnectionFactory
implements ConnectionFactory
{
	INSTANCE;

	private DatasourceConnectionFactory ( )
	{

	}

	@Override
	public final Connection getConnection ( )
	throws SQLException
	{
		// TODO
		return null;
	}
}