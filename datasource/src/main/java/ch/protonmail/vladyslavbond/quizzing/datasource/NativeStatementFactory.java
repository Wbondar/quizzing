package ch.protonmail.vladyslavbond.quizzing.datasource;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

enum NativeStatementFactory
implements StatementFactory
{
    INSTANCE ( );
    
    private final ConnectionFactory connectionFactory;
    
    NativeStatementFactory ( )
    {
        this(NativeConnectionFactory.INSTANCE);
    }

    NativeStatementFactory (ConnectionFactory connectionFactory)
    {
        this.connectionFactory = connectionFactory;
    }
    
    private final Connection getConnection ( )
    throws SQLException
    {
        return this.connectionFactory.getConnection( );
    }
    
    private final PreparedStatement populateStatement (PreparedStatement statement, Object... arguments) 
            throws SQLException
    {
        int i = 1;
        for (Object o : arguments)
        {
            statement.setObject(i++, o);
        }
        return statement;
    }
    
    private final CallableStatement populateStatement (CallableStatement statement, Map<String, Object> labelToValue) 
            throws SQLException
    {
        for (String label : labelToValue.keySet( ))
        {
            statement.setObject(label, labelToValue.get(label));
        }
        return statement;
    }
    
    @Override
    public CallableStatement prepareCall(String sql) throws SQLException
    {
        return getConnection( ).prepareCall(sql);
    }
    
    @Override
    public CallableStatement prepareCall (String sql, Object... values)
    throws SQLException
    {
        return (CallableStatement)this.populateStatement(prepareCall(sql), values);
    }

    @Override
    public CallableStatement prepareCall(String sql, Map<String, Object> labelToValue) 
            throws SQLException
    {
        return this.populateStatement(this.prepareCall(sql), labelToValue);
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException
    {
        return getConnection( ).prepareStatement(sql);
    }

    public PreparedStatement prepareStatement (String sql, Object... values)
    throws SQLException
    {
        return this.populateStatement(prepareStatement(sql), values);
    }
    
    @Override
    public boolean execute (String sql) 
    throws SQLException
    {
        Connection connection = getConnection( );
        return connection.createStatement().execute(sql);
    }

    @Override
    public boolean execute(String sql, Object... arguments) throws SQLException
    {
        return this.prepareStatement(sql, arguments).execute( );
    }

    @Override
    public boolean execute(String sql, Map<String, Object> labelToValue)
            throws SQLException
    {
        return this.prepareCall(sql, labelToValue).execute( );
    }
}
