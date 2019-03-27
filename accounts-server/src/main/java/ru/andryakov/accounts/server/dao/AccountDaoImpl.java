package ru.andryakov.accounts.server.dao;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class AccountDaoImpl implements AccountDao {

    private static final String SELECT_AMOUNT = "SELECT amount FROM accounts WHERE id = ?";
    private static final String ADD_AMOUNT = "INSERT INTO accounts(id, amount) VALUES(?, ?) "
            + "ON CONFLICT(id) "
            + "DO UPDATE SET amount = accounts.amount + excluded.amount";

    private JdbcTemplate jdbcTemplate;

    public AccountDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Long> getTotalAmount(int id) {
            return Optional.ofNullable(
                    jdbcTemplate.query(SELECT_AMOUNT,
                            resultSet -> {
                                if (resultSet.next()) {
                                    long amount = resultSet.getLong("amount");
                                    return resultSet.wasNull() ? null : amount;
                                }
                                return null;
                            }, id));
    }

    @Override
    public void addAmount(int id, long value) {
        jdbcTemplate.update(ADD_AMOUNT, id, value);
    }
}
