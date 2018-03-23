/******************************************************************************
 * Copyright (c) 2009, 2010 by Cisco Systems, Inc. All rights reserved.
 * 
 * This software contains proprietary information which shall not be reproduced
 * or transferred to other documents and shall not be disclosed to others or
 * used for manufacturing or any other purpose without prior permission of Cisco
 * Systems.
 * 
 *****************************************************************************/
package com.javatpoint.mypackage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

/**
 * This user type will convert a byte[] to blob and back again. Hibernate has
 * built-in support for mapping byte[] to VARBINARY using type="binary".
 * Hibernate also support mapping java.sql.Blob to BLOB using type="blob".
 * However, it's sometimes useful to read the whole blob into memory and deal
 * with it as a byte array. This is what this user type does.
 * 
 * Its Java type will be byte[]; Mapping looks like: 
 * <property name="byteArray" column="BYTEARRAY"
 *           type="com.cisco.xmp.model.usertypes.BinaryBlobType"/>
 * 
 */
public class BinaryToBlobUserType implements UserType {

    /**
     * {@inheritDoc}
     */
    // @Override
    public int[] sqlTypes() {
        return new int[] { Types.BLOB };
    }

    /**
     * {@inheritDoc}
     */
    // @Override
    public Class returnedClass() {
        return byte[].class;
    }

    /**
     * {@inheritDoc}
     */
    // @Override
    public boolean equals(Object x, Object y) {
        return (x == y) || (x != null && y != null && java.util.Arrays.equals((byte[]) x, (byte[]) y));
    }

    /**
     * {@inheritDoc}
     */
    // @Override
    public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {
        Blob blob = rs.getBlob(names[0]);
        return (blob != null) ? blob.getBytes(1, (int) blob.length()) : null;
    }

    /**
     * {@inheritDoc}
     */
    // @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException {
        // if (DatabaseUtils.isPostgresDb(st.getConnection().getMetaData())) {
        // if (value == null) {
        // st.setNull(index, Types.BLOB);
        // } else {
        // st.setBlob(index, Hibernate.createBlob((byte[]) value));
        // }
        // } else {
        // if (value == null) {
        // st.setBinaryStream(index, null, 0); // there was AbstractMethod
        // // error without argument
        // // "0"
        // } else {
        // st.setBinaryStream(index, new ByteArrayInputStream((byte[]) value),
        // ((byte[]) value).length);
        // }
        // }
    }

    /**
     * {@inheritDoc}
     */
    // @Override
    public Object deepCopy(Object value) {
        if (value == null) {
            return null;
        }
        byte[] bytes = (byte[]) value;
        byte[] result = new byte[bytes.length];
        System.arraycopy(bytes, 0, result, 0, bytes.length);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    // @Override
    public boolean isMutable() {
        return true;
    }

    /** {@inheritDoc} */
    // @Override
    public Object assemble(Serializable arg0, Object arg1) throws HibernateException {
        return arg0;
    }

    /** {@inheritDoc} */
    // @Override
    public Serializable disassemble(Object arg0) throws HibernateException {
        return (Serializable) arg0;
    }

    /** {@inheritDoc} */
    // @Override
    public int hashCode(Object arg0) throws HibernateException {
        return arg0.hashCode();
    }

    /** {@inheritDoc} */
    // @Override
    public Object replace(Object arg0, Object arg1, Object arg2) throws HibernateException {
        return arg0;
    }

    private void _handleNonOracleBlob(PreparedStatement st, Object value, int index) throws HibernateException {
        ObjectOutputStream oos = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(value);
            oos.flush();
            is = new ByteArrayInputStream(baos.toByteArray());
            st.setBinaryStream(index, is, is.available());
        } catch (Exception e) {
            throw new HibernateException(e);
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException ioe) {
                    // ok
                }
            }
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException ioe) {
                    // ok
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ioe) {
                    // ok;
                }
            }
        }
    }

}
