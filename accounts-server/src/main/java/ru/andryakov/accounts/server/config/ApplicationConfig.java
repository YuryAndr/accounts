package ru.andryakov.accounts.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.andryakov.accounts.server.dao.AccountDao;
import ru.andryakov.accounts.server.dao.AccountDaoImpl;
import ru.andryakov.accounts.server.dao.WriteInvalidateCacheAccountDaoWrapper;

@Configuration
public class ApplicationConfig {

    @Bean
    public AccountDao accountDao(JdbcTemplate jdbcTemplate) {
        return new WriteInvalidateCacheAccountDaoWrapper(
                new AccountDaoImpl(jdbcTemplate));
    }
}
