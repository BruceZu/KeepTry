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
