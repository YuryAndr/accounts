package ru.andryakov.accounts.server.dao;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import org.springframework.transaction.annotation.Transactional;

public class WriteInvalidateCacheAccountDaoWrapper implements AccountDao {

    private final AccountDao accountDaoWrapped;
    private final Cache<Integer, Optional<Long>> totalAmountsCache;


    public WriteInvalidateCacheAccountDaoWrapper(AccountDao accountDaoWrapped) {
        this.accountDaoWrapped = accountDaoWrapped;
        totalAmountsCache = CacheBuilder.newBuilder()
                .concurrencyLevel(64)
                .maximumSize(1000)
                .expireAfterWrite(1L, TimeUnit.HOURS)
                .build();
    }


    @Override
    public Optional<Long> getTotalAmount(int id) {
        try {
            return totalAmountsCache.get(id,
                    () -> accountDaoWrapped.getTotalAmount(id));
        } catch (ExecutionException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    @Transactional
    public void addAmount(int id, long value) {
        accountDaoWrapped.addAmount(id, value);
        totalAmountsCache.invalidate(id);
    }
}
