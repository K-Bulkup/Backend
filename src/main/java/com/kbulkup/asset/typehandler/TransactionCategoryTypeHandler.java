package com.kbulkup.asset.typehandler;

import com.kbulkup.asset.domain.TransactionCategory;
import com.kbulkup.common.exception.DatabaseAccessException;
import com.kbulkup.common.response.ResponseCode;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@MappedTypes(TransactionCategory.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class TransactionCategoryTypeHandler extends BaseTypeHandler<TransactionCategory> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, TransactionCategory category, JdbcType jdbcType)  {
        try {
            ps.setString(i, category.toDbValue());  // 💡 핵심
        } catch (SQLException e) {
            throw new DatabaseAccessException(ResponseCode.INVALID_SQL_QUERY);
        }
    }

    @Override
    public TransactionCategory getNullableResult(ResultSet rs, String columnName) {
        try {
            return TransactionCategory.fromDbValue(rs.getString(columnName));
        } catch (SQLException e) {
            throw new DatabaseAccessException(ResponseCode.INVALID_SQL_QUERY);
        }
    }

    @Override
    public TransactionCategory getNullableResult(ResultSet rs, int columnIndex) {
        try {
            return TransactionCategory.fromDbValue(rs.getString(columnIndex));
        } catch (SQLException e) {
            throw new DatabaseAccessException(ResponseCode.INVALID_SQL_QUERY);
        }
    }

    @Override
    public TransactionCategory getNullableResult(CallableStatement cs, int columnIndex) {
        try {
            return TransactionCategory.fromDbValue(cs.getString(columnIndex));
        } catch (Exception e) {
            throw new DatabaseAccessException(ResponseCode.INVALID_SQL_QUERY);
        }
    }
}
