/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.cisco.xmp.model.framework.propertyaccessor;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import org.hibernate.HibernateException;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;

public class EnumUserType implements UserType, ParameterizedType {
    private Method valueOfMethod;
    private Method identifierMethod;
    private Class targetClass;
    private static final int[] SQL_TYPES = new int[] { 12 };
    private static final String DEFAULT_IDENTIFIER_METHOD_NAME = "getValue";

    public void setParameterValues(Properties parameters) {
        String targetClassName = parameters.getProperty("targetClass");

        try {
            this.targetClass = Thread.currentThread().getContextClassLoader().loadClass(targetClassName);
        } catch (ClassNotFoundException arg7) {
            throw new HibernateException("Class " + targetClassName + " not found ", arg7);
        }

        String valueOfMethodName = parameters.getProperty("valueOfMethod");

        try {
            if (valueOfMethodName != null) {
                try {
                    this.valueOfMethod = this.targetClass.getMethod(valueOfMethodName, new Class[] { Integer.TYPE });
                } catch (NoSuchMethodException arg6) {
                    this.valueOfMethod = this.targetClass.getMethod(valueOfMethodName, new Class[] { String.class });
                }
            }
        } catch (Exception arg8) {
            throw new HibernateException("Failed to obtain valueOf method", arg8);
        }

        String identifierMethodName = parameters.getProperty("identifierMethod", "getValue");

        try {
            this.identifierMethod = this.targetClass.getMethod(identifierMethodName, new Class[0]);
        } catch (Exception arg5) {
            throw new HibernateException("Failed to obtain identifier method", arg5);
        }
    }

    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    public Class returnedClass() {
        return this.targetClass;
    }

    public boolean equals(Object x, Object y) throws HibernateException {
        return x == y;
    }

    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {
        String value = rs.getString(names[0]);
        String tmpValue = null;
        boolean isSet = false;
        boolean isArray = false;
        if (!rs.wasNull()) {
            try {
                if (value.contains("S-")) {
                    tmpValue = value.replaceFirst("S-", "");
                    isSet = true;
                    value = tmpValue;
                } else if (value.contains("A-")) {
                    tmpValue = value.replaceFirst("A-", "");
                    isArray = true;
                    value = tmpValue;
                }

                StringTokenizer ex = new StringTokenizer(value, ":");
                if (isSet) {
                    LinkedHashSet list1 = new LinkedHashSet();

                    while (ex.hasMoreTokens()) {
                        list1.add(this.findMyEnum(ex.nextToken()));
                    }

                    return list1;
                } else if (!isArray) {
                    return this.findMyEnum(ex.nextToken());
                } else {
                    ArrayList list = new ArrayList();

                    while (ex.hasMoreElements()) {
                        list.add(this.findMyEnum(ex.nextToken()));
                    }

                    return list;
                }
            } catch (Exception arg9) {
                throw new HibernateException("Class " + this.targetClass.getName() + " not a valid PersistentEnum",
                        arg9);
            }
        } else {
            return null;
        }
    }

    private Object findMyEnum(Object value) throws HibernateException {
        try {
            if (this.valueOfMethod != null) {
                try {
                    return this.valueOfMethod.invoke(this.targetClass, new Object[] { new Integer(value.toString()) });
                } catch (IllegalArgumentException arg2) {
                    return this.valueOfMethod.invoke(this.targetClass, new Object[] { value.toString() });
                }
            } else {
                return Enum.valueOf(this.targetClass, value.toString());
            }
        } catch (Exception arg3) {
            throw new HibernateException(arg3.getMessage());
        }
    }

    public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, 12);
        } else {
            try {
                Iterator exc;
                String tmpVal;
                String a;
                if (value instanceof Set) {
                    exc = ((Set) value).iterator();
                    tmpVal = "S-";
                    a = null;

                    while (exc.hasNext()) {
                        a = "" + this.identifierMethod.invoke(exc.next(), new Object[0]);
                        tmpVal = tmpVal.concat(a);
                        if (exc.hasNext()) {
                            tmpVal = tmpVal.concat(":");
                        }
                    }

                    st.setString(index, tmpVal);
                } else if (value.getClass().isArray()) {
                    Enum[] arg8 = (Enum[]) ((Enum[]) value);
                    tmpVal = "A-";
                    a = null;

                    for (int i = 0; i < arg8.length; ++i) {
                        a = "" + this.identifierMethod.invoke(arg8[i], new Object[0]);
                        tmpVal = tmpVal.concat(a);
                        if (i < arg8.length - 1) {
                            tmpVal = tmpVal.concat(":");
                        }
                    }

                    st.setString(index, tmpVal);
                } else if (value instanceof List) {
                    exc = ((List) value).iterator();
                    tmpVal = "A-";
                    a = null;

                    while (exc.hasNext()) {
                        a = "" + this.identifierMethod.invoke(exc.next(), new Object[0]);
                        tmpVal = tmpVal.concat(a);
                        if (exc.hasNext()) {
                            tmpVal = tmpVal.concat(":");
                        }
                    }

                    st.setString(index, tmpVal);
                } else {
                    st.setString(index, "" + this.identifierMethod.invoke(value, new Object[0]));
                }
            } catch (Exception arg7) {
                throw new HibernateException(arg7);
            }
        }

    }

    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    public boolean isMutable() {
        return false;
    }

    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }
}