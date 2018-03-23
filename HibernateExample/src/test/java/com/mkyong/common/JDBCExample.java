package com.mkyong.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCExample {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://172.19.190.38:5432/campus";

    // Database credentials
    static final String USER = "apic_em_user";
    static final String PASS = "-/Tx7j3PSN";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            // STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            // STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            SNMPTimeoutMigration.updateDeviceSNMPTimeout(conn);

            // // STEP 4: Execute a query
            // System.out.println("Creating statement...");
            // stmt = conn.createStatement();
            // String sql;
            // sql = "SELECT company_id, companyname FROM company";
            // ResultSet rs = stmt.executeQuery(sql);
            //
            // // STEP 5: Extract data from result set
            // while (rs.next()) {
            // // Retrieve by column name
            // int id = rs.getInt("company_id");
            // String first = rs.getString("companyname");
            //
            // // Display values
            // System.out.print("ID: " + id);
            // System.out.print(", First: " + first);
            // }
            // // STEP 6: Clean-up environment
            // rs.close();
            // stmt.close();
            conn.close();
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }// end finally try
        }// end try
        System.out.println("Goodbye!");
    }// end main
}