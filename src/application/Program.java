package application;

import db.DB;
import db.DbException;
import db.DbIntegrityException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Program {
    public static void main(String[] args) {
        Connection conn = null;
        Statement statement = null;
        try {
            conn = DB.getConnection();
            conn.setAutoCommit(false);
            statement = conn.createStatement();

            int rows1 = statement.executeUpdate(
                    "UPDATE seller " +
                            "SET BaseSalary = 2090 " +
                            "WHERE DepartmentId = 1");

//            int x = 1;
//            if (x < 2) {
//                throw new SQLException("Fake Error");
//            }
            int rows2 = statement.executeUpdate(
                    "UPDATE seller " +
                            "SET BaseSalary = 3090 " +
                            "WHERE DepartmentId = 2");

            conn.commit();
            System.out.println("rows1: " + rows1);
            System.out.println("rows2: " + rows2);

        } catch (SQLException e) {
            try {
                conn.rollback();
                throw new DbException("Trasaction rolled back! caused by: " + e.getMessage());
            } catch (SQLException ex) {
                throw new DbException("Error trying to rollback! Caused by:" + ex.getMessage());
            }
        } finally {
            DB.closeStatement(statement);
            DB.closeConnection();
        }
    }
}
