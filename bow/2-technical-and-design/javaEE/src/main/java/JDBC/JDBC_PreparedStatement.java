//  Copyright 2016 The Sawdust Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

package JDBC;

import java.sql.*;

/**
 * <pre>
 * same query and access path will be used if you pass a different parameter  e.g.
 *      "Standard Charted" or "HSBC".
 *
 * ResultSet returned by prepared statement execution is of "TYPE_FORWARD_ONLY"
 * but can be customized by using overloaded method of prepareStatement().
 *
 * https://docs.oracle.com/javase/tutorial/jdbc/basics/storedprocedures.html
 * Parameter Mode IN: Formal parameter cannot be assigned a value in the stored procedure?
 */
public class JDBC_PreparedStatement {
    public static void main(String args[]) {
        try (
                Connection con = DriverManager.getConnection(
                        "mysql:\\localhost:1520",
                        "root",
                        "root");

        ) {
            String queryDrop =
                    "DROP PROCEDURE IF EXISTS SHOW_SUPPLIERS";

            String createProcedure =
                    "create procedure SHOW_SUPPLIERS() " +
                            "begin " +
                            "select SUPPLIERS.SUP_NAME, " +
                            "COFFEES.COF_NAME " +
                            "from SUPPLIERS, COFFEES " +
                            "where SUPPLIERS.SUP_ID = " +
                            "COFFEES.SUP_ID " +
                            "order by SUP_NAME; " +
                            "end";
            // sql statement without parameter
            Statement stmtDrop = con.createStatement();
            stmtDrop.execute(queryDrop);
            Statement stmt = con.createStatement();
            stmt.executeUpdate(createProcedure);


            // sql statement
            String sql = "select distinct loan_type from loan where bank=?";

            PreparedStatement preStatement = con.prepareStatement(sql);
            preStatement.setString(1, "Citibank");

            ResultSet result = preStatement.executeQuery();
            while (result.next()) {
                System.out.println("Loan Type: " + result.getString("loan_type"));
            }

            // procedure
            CallableStatement cs = null;
            cs = con.prepareCall("{call SHOW_SUPPLIERS}");
            ResultSet rs = cs.executeQuery();

            // procedure with parameter
            createProcedure =
                    "create procedure GET_SUPPLIER_OF_COFFEE(" +
                            "IN coffeeName varchar(32), " +
                            "OUT supplierName varchar(40)) " +
                            "begin " +
                            "select SUPPLIERS.SUP_NAME into " +
                            "supplierName " +
                            "from SUPPLIERS, COFFEES " +
                            "where SUPPLIERS.SUP_ID = " +
                            "COFFEES.SUP_ID " +
                            "and coffeeName = COFFEES.COF_NAME; " +
                            "select supplierName; " +
                            "end";

            // .....

            cs =  con.prepareCall("{call GET_SUPPLIER_OF_COFFEE(?, ?)}");
            cs.setString(1, "coffeeNameArg");
            cs.registerOutParameter(2, Types.VARCHAR);
            cs.executeQuery();

            String supplierName = cs.getString(2);
        } catch (Exception e) {
        }
    }
}
