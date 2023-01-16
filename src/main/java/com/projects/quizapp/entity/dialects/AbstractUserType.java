package com.projects.quizapp.entity.dialects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.quizapp.exception.exceptions.InternalServerException;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.SerializationException;
import org.hibernate.usertype.UserType;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;
import java.util.Objects;

public class AbstractUserType implements UserType {

    protected final ObjectMapper objectMapper =
            new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                              .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    @Override
    public void nullSafeSet(final PreparedStatement preparedStatement, final Object value,
                            final int i, final SharedSessionContractImplementor sessionImplementor)
            throws SQLException {
        if (Objects.isNull(value)) {
            preparedStatement.setNull(i, Types.OTHER);
        } else {
            try {
                preparedStatement.setObject(i, objectMapper.writeValueAsString(value), Types.OTHER);
            } catch (final JsonProcessingException e) {
                throw new InternalServerException("Failed to save in db!");
            }
        }
    }

    @Override
    public Object deepCopy(final Object originalValue) {
        return originalValue;
    }

    @Override
    public Object nullSafeGet(final ResultSet rs, final String[] names, final SharedSessionContractImplementor session,
                              final Object owner)
            throws HibernateException, SQLException {
        return new Object();
    }

    @Override
    public Serializable disassemble(final Object value) {
        final Object copy = deepCopy(value);
        if (copy instanceof Serializable) {
            return (Serializable) copy;
        }
        throw new SerializationException(
                String.format("Cannot serialize '%s', %s is not Serializable.", value, value.getClass()), null);
    }

    @Override
    public Object assemble(final Serializable cached, final Object o) {
        return deepCopy(cached);
    }

    @Override
    public Object replace(final Object original, final Object target, final Object owner) {
        return deepCopy(original);
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public int hashCode(final Object x) {
        if (Objects.isNull(x)) {
            return 0;
        }
        return x.hashCode();
    }

    @Override
    public boolean equals(final Object x, final Object y) {
        return ObjectUtils.nullSafeEquals(x, y);
    }

    @Override
    public Class<?> returnedClass() {
        return Map.class;
    }

    @Override
    public int[] sqlTypes() {
        return new int[] {Types.JAVA_OBJECT};
    }
}
