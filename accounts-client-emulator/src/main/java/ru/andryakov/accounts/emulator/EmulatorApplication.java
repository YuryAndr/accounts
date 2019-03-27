package ru.andryakov.accounts.emulator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import ru.andryakov.accounts.common.service.AccountService;
import ru.andryakov.accounts.emulator.runner.EmulatorRunner;

@SpringBootApplication
public class EmulatorApplication {

    public static final int N_THREADS = 100;

    public static void main(String[] args) throws InterruptedException {
        EmulatorRunner emulatorRunner = SpringApplication
                .run(EmulatorApplication.class, args).getBean(EmulatorRunner.class);
        emulatorRunner.run();
    }
}
