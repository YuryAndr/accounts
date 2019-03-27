package ru.andryakov.accounts.server.dao;

import java.util.Optional;

public interface AccountDao {

    Optional<Long> getTotalAmount(int id);

    void addAmount(int id, long value);
}
