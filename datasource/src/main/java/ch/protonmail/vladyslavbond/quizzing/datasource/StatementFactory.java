package ch.protonmail.vladyslavbond.quizzing.datasource;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StatementFactory
{

    CallableStatement prepareCall(String sql) throws SQLException;

    PreparedStatement prepareStatement(String sql) throws SQLException;

}
