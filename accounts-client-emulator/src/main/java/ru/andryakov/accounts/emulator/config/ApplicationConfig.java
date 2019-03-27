package ru.andryakov.accounts.emulator.config;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.andryakov.accounts.common.service.AccountService;
import ru.andryakov.accounts.emulator.data.IdGenerator;
import ru.andryakov.accounts.emulator.request.AddAmountRequest;
import ru.andryakov.accounts.emulator.request.GetAmountRequest;
import ru.andryakov.accounts.emulator.runner.EmulatorRunner;
import ru.andryakov.accounts.emulator.runner.RequestRunner;

@Configuration
public class ApplicationConfig {

    @Value("${accounts.client.emulator.numberOfThreadsAddAmount}")
    private int numberOfThreadsAddAmount;

    @Value("${accounts.client.emulator.numberOfThreadsGetAmount}")
    private int numberOfThreadsGetAmount;

    @Value("${accounts.client.emulator.ids}")
    private String ids;

    @Bean
    public IdGenerator idGenerator() {
        return IdGenerator.create(ids);
    }

    @Bean
    public RequestRunner addAmountRunner(AccountService accountService, IdGenerator idGenerator) {
        return new RequestRunner(
                new AddAmountRequest(accountService, idGenerator),
                numberOfThreadsAddAmount);
    }

    @Bean
    public RequestRunner getAmountRunner(AccountService accountService, IdGenerator idGenerator) {
        return new RequestRunner(
                new GetAmountRequest(accountService, idGenerator),
                numberOfThreadsGetAmount);
    }

    @Bean
    public EmulatorRunner emulatorRunner(List<RequestRunner> requestRunners) {
        return new EmulatorRunner(requestRunners);
    }
}
