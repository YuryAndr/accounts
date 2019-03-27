package ru.andryakov.accounts.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.andryakov.accounts.common.service.AccountService;

@SpringBootApplication
@EnableTransactionManagement
public class AccountsServerApplication {

    public static void main(String[] args) {
        SpringApplication
                .run(AccountsServerApplication.class, args);
    }
}
