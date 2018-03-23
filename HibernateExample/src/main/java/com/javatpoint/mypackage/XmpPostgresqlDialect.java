/******************************************************************************
 * Copyright (c) 2014 by Cisco Systems, Inc. All rights reserved.
 * 
 * This software contains proprietary information which shall not be reproduced
 * or transferred to other documents and shall not be disclosed to others or
 * used for manufacturing or any other purpose without prior permission of Cisco
 * Systems.
 * 
 *****************************************************************************/

package com.javatpoint.mypackage;

import java.sql.Types;

import org.hibernate.dialect.PostgreSQLDialect;

/**
 * A customized dialect for postgresql
 *
 * @author jasmbhat
 */
public class XmpPostgresqlDialect extends PostgreSQLDialect {

    public XmpPostgresqlDialect() {
        super();
        //blob should be mapped to bytea rather than oid.
        registerColumnType( Types.BLOB, "bytea" );
    }

}
