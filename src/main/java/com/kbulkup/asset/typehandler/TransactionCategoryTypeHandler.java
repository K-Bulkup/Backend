package com.kbulkup.asset.typehandler;

import com.kbulkup.asset.domain.TransactionCategory;
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
    public void setNonNullParameter(PreparedStatement ps, int i, TransactionCategory category, JdbcType jdbcType) throws SQLException {
        ps.setString(i, category.toDbValue());  // 💡 핵심
    }

    @Override
    public TransactionCategory getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return TransactionCategory.fromDbValue(rs.getString(columnName));
    }

    @Override
    public TransactionCategory getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return TransactionCategory.fromDbValue(rs.getString(columnIndex));
    }

    @Override
    public TransactionCategory getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return TransactionCategory.fromDbValue(cs.getString(columnIndex));
    }
}
