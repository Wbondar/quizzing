package ch.protonmail.vladyslavbond.quizzing.datasource;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

final class NativeStatementFactory
implements StatementFactory
{
    private final ConnectionFactory connectionFactory;

    NativeStatementFactory (ConnectionFactory connectionFactory)
    {
        this.connectionFactory = connectionFactory;
    }
    
    @Override
    public CallableStatement prepareCall(String sql) throws SQLException
    {
        return connectionFactory.getConnection( ).prepareCall(sql);
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException
    {
        return connectionFactory.getConnection( ).prepareStatement(sql);
    }
}
