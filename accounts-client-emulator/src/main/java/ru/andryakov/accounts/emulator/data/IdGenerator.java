package ru.andryakov.accounts.emulator.data;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.util.Assert;

public class IdGenerator {

    private static final String ID_NEGATIVE_ERROR_MESSAGE = "id can not be negative";
    private List<Integer> ids;

    private IdGenerator(List<Integer> ids) {
        this.ids = ids;
    }

    public static IdGenerator create(String idsRange) {
        if (idsRange.matches("[-]?\\d+[.][.][-]?\\d+")) {
            String[] range = idsRange.split("[.][.]");
            int low = Integer.parseInt(range[0]);
            int high = Integer.parseInt(range[1]);
            Assert.isTrue(low >= 0, ID_NEGATIVE_ERROR_MESSAGE);
            Assert.isTrue(high >= 0, ID_NEGATIVE_ERROR_MESSAGE);
            if (high < low) {
                //swap
                low = high ^ low;
                high = low ^ high;
                low = low ^ high;
            }
            return new IdGenerator(IntStream
                    .range(low, high + 1).boxed().collect(toList()));
        }
        return new IdGenerator(Arrays
                .stream(idsRange.split(","))
                .map(String::trim)
                .map(idString -> {
                    int id = Integer.parseInt(idString);
                    Assert.isTrue(id >= 0, ID_NEGATIVE_ERROR_MESSAGE);
                    return id;
                })
                .collect(toList()));
    }

    public int getNextId() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return ids.get(random.nextInt(0, ids.size()));
    }
}
