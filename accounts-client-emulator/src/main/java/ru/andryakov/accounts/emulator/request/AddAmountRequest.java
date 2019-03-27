package ru.andryakov.accounts.emulator.request;

import java.util.concurrent.ThreadLocalRandom;
import ru.andryakov.accounts.common.service.AccountService;
import ru.andryakov.accounts.emulator.data.IdGenerator;

public class AddAmountRequest implements Request {

    private static final int MIN_VALUE = -1024;
    private static final int MAX_VALUE = 1024;


    private final AccountService accountService;
    private final IdGenerator idGenerator;

    public AddAmountRequest(AccountService accountService,
            IdGenerator idGenerator) {
        this.accountService = accountService;
        this.idGenerator = idGenerator;
    }

    @Override
    public void send() {
        accountService.addAmount(idGenerator.getNextId(), getAmount());
    }

    private long getAmount() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        long value = 0;
        while (value == 0) {
            value = random.nextInt(MIN_VALUE, MAX_VALUE);
        }
        return value;
    }
}
