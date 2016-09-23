package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <pre>
 * same query and access path will be used if you pass a different parameter  e.g.
 *      "Standard Charted" or "HSBC".
 *
 * ResultSet returned by prepared statement execution is of "TYPE_FORWARD_ONLY"
 * but can be customized by using overloaded method of prepareStatement().
 */
public class JDBC_PreparedStatement {
    public static void main(String args[]) throws SQLException {
        Connection con = DriverManager.getConnection("mysql:\\localhost:1520", "root", "root");
        PreparedStatement preStatement = con.prepareStatement("select distinct loan_type from loan where bank=?");
        preStatement.setString(1, "Citibank");

        ResultSet result = preStatement.executeQuery();
        while (result.next()) {
            System.out.println("Loan Type: " + result.getString("loan_type"));
        }
    }
}
