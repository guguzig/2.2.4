import org.example.TipService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BaseTest {

    private TipService tipService;
    private final BigDecimal tolerance = BigDecimal.valueOf(0.01);

    @BeforeEach
    void setUp() {
        tipService = new TipService();
    }

    @DisplayName("Test rounding tips")
    @ParameterizedTest(name = "Test rounding tips: amount={0}, expected={1}")
    @CsvSource({
            "500, 550",   // 10% tip when amount < 100
            "1000, 1050", // 5% tip when amount >= 1000
            "1500, 1575", // 5% tip when amount >= 1000
            "800, 880",   // 10% tip when amount < 1000
            "1200, 1260", // 5% tip when amount >= 1000
            "2000, 2100"  // 5% tip when amount >= 1000
    })
    void testRoundTips(BigDecimal amount, BigDecimal expected) {
        BigDecimal result = tipService.roundTips(amount);
        BigDecimal difference = result.subtract(expected).abs();
        assertTrue(BigDecimal.ZERO.compareTo(difference) <= 0, "Expected: " + expected + ", Actual: " + result);
        assertTrue(difference.compareTo(tolerance) <= 0, "Tolerance exceeded: " + difference);
    }
}
