package ch.protonmail.vladyslavbond.quizzing.domain;

import java.sql.Connection;
import java.sql.SQLException;

interface ConnectionFactory
{
	public abstract Connection getConnection ( )
	throws SQLException;
}