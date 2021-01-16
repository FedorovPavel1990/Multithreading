package fifteenthreads;

import java.math.BigDecimal;
import java.util.Random;

public class TestProcess {

    public static void process() {
        BigDecimal n = new BigDecimal(1);
        for (int j = 0; j < 100000; j++) {
            n = n.multiply(new BigDecimal(new Random().nextInt()));
        }
    }

}