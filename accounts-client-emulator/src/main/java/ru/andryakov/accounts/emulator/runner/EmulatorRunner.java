package ru.andryakov.accounts.emulator.runner;

import java.util.List;

public class EmulatorRunner {

    private final List<RequestRunner> requestRunners;

    public EmulatorRunner(List<RequestRunner> requestRunners) {
        this.requestRunners = requestRunners;
    }

    public void run() {
        requestRunners.forEach(RequestRunner::runRequest);
    }
}
