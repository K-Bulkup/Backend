package com.kbulkup.asset.typehandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class JsonToMapTypeHandler extends BaseTypeHandler<Map<String, BigDecimal>> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Map<String, BigDecimal> parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, toJson(parameter));
    }

    @Override
    public Map<String, BigDecimal> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return toMap(rs.getString(columnName));
    }

    @Override
    public Map<String, BigDecimal> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return toMap(rs.getString(columnIndex));
    }

    @Override
    public Map<String, BigDecimal> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return toMap(cs.getString(columnIndex));
    }

    private String toJson(Map<String, BigDecimal> map) {
        try {
            return objectMapper.writeValueAsString(map);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, BigDecimal> toMap(String json) {
        try {
            if (json == null || json.isEmpty()) return new HashMap<>();
            return objectMapper.readValue(json, objectMapper.getTypeFactory()
                    .constructMapType(Map.class, String.class, BigDecimal.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
