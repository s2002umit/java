package com.mkyong.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SNMPTimeoutMigration {

    private static Logger LOG = LoggerFactory.getLogger(SNMPTimeoutMigration.class);

    public static void updateDeviceSNMPTimeout(Connection conn) throws Exception {
        LOG.debug("Entered updateDeviceSNMPTimeout");
        String tableToUpdate = "credential";
        try {
            if (doesTableExist(conn, tableToUpdate)) {

                final String getSNMPTimeoutsSql = "SELECT group_id,c_value FROM credential where c_name like 'SNMP_TIMEOUT'";

                PreparedStatement pst = conn.prepareStatement(getSNMPTimeoutsSql);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    // Retrieve by column name
                    String group_id = rs.getString("group_id");
                    String c_value = rs.getString("c_value");

                    LOG.info("group_id: " + group_id);
                    LOG.info("c_value: " + c_value);

                    String snmpTimeoutValue = getDecryptedValue(c_value);
                    if (snmpTimeoutValue != null) {
                        int snmpTimeout = Integer.parseInt(snmpTimeoutValue);
                        LOG.info("snmpTimeout in sec" + snmpTimeout / 1000);
                        if (snmpTimeout / 1000 < 1) {
                            LOG.info("snmp timeout is less then 1 sec . So needs to be updated");
                            updateSNMPTimeout(conn, group_id, getEncryptedValue(String.valueOf(snmpTimeout * 1000)));
                        }
                    }

                }

            } else {
                LOG.error("Table {} AND/OR {} does not exist", tableToUpdate);
            }
        } catch (SQLException e) {
            LOG.error("Failed to update entries in {} ", tableToUpdate, e);
            throw new Exception(e);
        }
        LOG.debug("Completed updateDeviceSNMPTimeout");
    }

    public static boolean doesTableExist(Connection conn, String tableName) throws SQLException {
        LOG.info("Checking if {} table exists", tableName);
        final String checkTableExistsSql = "SELECT EXISTS (SELECT 1 FROM  INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = ? )";
        PreparedStatement pst = conn.prepareStatement(checkTableExistsSql);
        pst.setString(1, tableName);
        ResultSet rs = pst.executeQuery();
        rs.next();
        return rs.getBoolean(1);

    }

    public static void updateSNMPTimeout(Connection conn, String group_id, String snmpTimeoutValue) throws Exception {
        LOG.info("Entered updateSNMPTimeout");
        try {
            final String updateQuery = "UPDATE  credential SET c_value = '" + snmpTimeoutValue
                    + "' WHERE credential.group_id = " + group_id + "and credential.c_name ='SNMP_TIMEOUT'";
            PreparedStatement ps = conn.prepareStatement(updateQuery);
            int rows = ps.executeUpdate();
            LOG.info("{} group id updated with snmp timeout {} ", group_id, snmpTimeoutValue);

        } catch (SQLException e) {
            LOG.error("Failed to update entries in {} ", group_id, e);
            throw new Exception(e);
        }
        LOG.debug("Completed updateSNMPTimeout");
    }

    public static String getDecryptedValue(String c_value) {
        String decryptValue = null;
        try {
            decryptValue = ThreeDesEncrypt.decrypt(c_value);
            LOG.info("decrypt value is" + decryptValue);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.info("Error while decrypt value");
        }
        return decryptValue;
    }

    private static String getEncryptedValue(String value) {
        String encryptValue = null;
        try {
            String salt = "ads7asds";
            StringBuffer saltedValue = new StringBuffer(salt).append(value);
            encryptValue = ThreeDesEncrypt.encrypt(saltedValue.toString());
            LOG.info("encryptValue is " + encryptValue);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.info("Error while encrypt value");
        }
        return encryptValue;
    }

}
