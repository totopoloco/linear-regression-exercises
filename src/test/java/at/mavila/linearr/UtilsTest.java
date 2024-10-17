package at.mavila.linearr;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;


class UtilsTest {


  @Test
  void testGetBigDecimals() {
    // Test case 1
    assertThat(Utils.getBigDecimals(BigDecimal.valueOf(1), BigDecimal.valueOf(2)))
        .isEqualTo(new BigDecimal[] {BigDecimal.valueOf(1), BigDecimal.valueOf(2)});
    // Test case 2
    assertThat(Utils.getBigDecimals(BigDecimal.valueOf(2), BigDecimal.valueOf(3)))
        .isEqualTo(new BigDecimal[] {BigDecimal.valueOf(2), BigDecimal.valueOf(3)});
    // Test case 3
    assertThat(Utils.getBigDecimals(BigDecimal.valueOf(3), BigDecimal.valueOf(4)))
        .isEqualTo(new BigDecimal[] {BigDecimal.valueOf(3), BigDecimal.valueOf(4)});
    // Test case 4
    assertThat(Utils.getBigDecimals(BigDecimal.valueOf(4), BigDecimal.valueOf(5)))
        .isEqualTo(new BigDecimal[] {BigDecimal.valueOf(4), BigDecimal.valueOf(5)});
    // Test case 5
    assertThat(Utils.getBigDecimals(BigDecimal.valueOf(5), BigDecimal.valueOf(6)))
        .isEqualTo(new BigDecimal[] {BigDecimal.valueOf(5), BigDecimal.valueOf(6)});
    // Test case 6
    assertThat(Utils.getBigDecimals(BigDecimal.valueOf(0), BigDecimal.valueOf(0)))
        .isEqualTo(new BigDecimal[] {BigDecimal.valueOf(0), BigDecimal.valueOf(0)});
  }
}