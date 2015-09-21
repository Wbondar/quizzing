package ch.protonmail.vladyslavbond.quizzing.datasource;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public interface StatementFactory
{
    CallableStatement prepareCall(String sql) throws SQLException;
    
    CallableStatement prepareCall(String sql, Object... arguments) throws SQLException;
    
    CallableStatement prepareCall(String sql, Map<String, Object> labelToValue) throws SQLException;

    PreparedStatement prepareStatement(String sql) throws SQLException;

    PreparedStatement prepareStatement(String sql, Object... arguments) throws SQLException;

    boolean execute (String sql) throws SQLException;

    boolean execute (String sql, Object... arguments) throws SQLException;
    
    boolean execute (String sql, Map<String, Object> labelToValue) throws SQLException;
}
