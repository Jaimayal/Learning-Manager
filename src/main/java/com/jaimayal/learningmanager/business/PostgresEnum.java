package com.jaimayal.learningmanager.business;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.EnumType;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class PostgresEnum extends EnumType {
    public void nullSafeSet(
            PreparedStatement st,
            Object value,
            int index,
            SharedSessionContractImplementor session)
            throws HibernateException, SQLException {
        if (value == null) {
            st.setObject(index, Types.OTHER);
        } else {
            st.setObject(index, ((Enum) value), Types.OTHER);
        }
    }
}
