package ru.andryakov.accounts.emulator.request;

import ru.andryakov.accounts.common.service.AccountService;
import ru.andryakov.accounts.emulator.data.IdGenerator;

public class GetAmountRequest implements Request {

    private final IdGenerator idGenerator;
    private final AccountService accountService;

    public GetAmountRequest(AccountService accountService,
            IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
        this.accountService = accountService;
    }

    @Override
    public void send() {
        accountService.getAmount(idGenerator.getNextId());
    }
}
