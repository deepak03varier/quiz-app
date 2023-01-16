package com.projects.quizapp.entity.dialects;

import com.fasterxml.jackson.core.type.TypeReference;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.postgresql.jdbc.PgArray;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StringListUserType extends AbstractUserType {

    @Override
    public Object deepCopy(final Object originalValue) {
        if (Objects.isNull(originalValue) || !(originalValue instanceof List)) {
            for (final Object val: (List) originalValue) {
                if (!(val instanceof String)) {
                    return null;
                }
            }
        }
        return objectMapper.convertValue(originalValue, new TypeReference<List<String>>() {});
    }

    @Override
    public Object nullSafeGet(final ResultSet rs, final String[] names,
                              final SharedSessionContractImplementor session,
                              final Object owner)
            throws SQLException {
        final PgArray o = (PgArray) rs.getObject(names[0]);
        if (Objects.nonNull(o) && Objects.nonNull(o.getArray())) {
            return objectMapper.convertValue(o.getArray(), new TypeReference<List<String>>() {});
        }
        return new ArrayList<>();
    }

    @Override
    public void nullSafeSet(final PreparedStatement preparedStatement, final Object value,
                            final int i, final SharedSessionContractImplementor sessionImplementor)
            throws SQLException {
        if (Objects.isNull(value)) {
            preparedStatement.setNull(i, Types.ARRAY);
        } else {
            List<String> stringList;
            stringList = (List<String>) value;
            String[] stringArray = stringList.toArray(new String[stringList.size()]);
            Array array = sessionImplementor.connection().createArrayOf("text", stringArray);
            preparedStatement.setArray(i, array);
        }

    }
}
