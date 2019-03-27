package ru.andryakov.accounts.server.service;

import com.codahale.metrics.MetricRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.andryakov.accounts.common.exception.AccountServiceException;
import ru.andryakov.accounts.common.service.AccountService;
import ru.andryakov.accounts.server.dao.AccountDao;

@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);
    public static final String ID_NULL_ERROR_MESSAGE = "Id can not be null";

    @Autowired
    private AccountDao accountDao;

    @Override
    public Long getAmount(Integer id) {
        Assert.notNull(id, ID_NULL_ERROR_MESSAGE);
        try {
            return accountDao.getTotalAmount(id).orElse(0L);
        } catch (Exception e) {
            LOGGER.error("Error during getAmount", e);
            throw new AccountServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void addAmount(Integer id, Long value) {
        Assert.notNull(id, ID_NULL_ERROR_MESSAGE);
        Assert.notNull(value, "Value can not be null");
        Assert.isTrue(value != 0L, "Value can not be zero");
        Assert.isTrue(Math.abs(value) < Integer.MAX_VALUE, "Too large absolute value");
        try {
            accountDao.addAmount(id, value);
        } catch (Exception e) {
            LOGGER.error("Error during addAmount", e);
            throw new AccountServiceException(e.getMessage(), e);
        }
    }
}
