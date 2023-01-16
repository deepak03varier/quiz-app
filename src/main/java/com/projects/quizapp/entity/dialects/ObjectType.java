package com.projects.quizapp.entity.dialects;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.postgresql.util.PGobject;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class ObjectType extends AbstractUserType {

    @Override
    public Object deepCopy(final Object originalValue) {
        if (Objects.isNull(originalValue)) {
            return null;
        }
        return objectMapper.convertValue(originalValue, Object.class);
    }

    @Override
    public Object nullSafeGet(final ResultSet rs, final String[] names,
                              final SharedSessionContractImplementor session,
                              final Object owner)
            throws SQLException {
        final PGobject o = (PGobject) rs.getObject(names[0]);
        if (Objects.nonNull(o) && Objects.nonNull(o.getValue())) {
            try {
                return objectMapper.readValue(o.getValue(), Object.class);
            } catch (final IOException ignored) {
            }
        }
        return new Object();
    }
}
