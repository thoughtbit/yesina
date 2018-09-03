package com.lkmotion.yesincar.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * drds事务
 *
 * @author ZhuBin
 * @date 2018/8/31
 */
@Component
public class DrdsFlexibleTransaction extends DataSourceTransactionManager {

    private static final String BEGIN_TX_SQL = "SET drds_transaction_policy = 'XA'";

    @Autowired
    public DrdsFlexibleTransaction(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected void prepareTransactionalConnection(Connection con, TransactionDefinition definition) throws SQLException {
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate(BEGIN_TX_SQL);
        }
    }
}
