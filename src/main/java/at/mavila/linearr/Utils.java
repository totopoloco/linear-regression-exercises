package at.mavila.linearr;

import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Utils {

  /**
   * Precision for the calculations.
   */
  public static final int PRECISION = 20;

  /**
   * Convenient method to get an array of BigDecimals.
   *
   * @param value0 the first value.
   * @param value1 the second value.
   * @return the array of BigDecimals.
   */
  public static BigDecimal[] getBigDecimals(BigDecimal value0, BigDecimal value1) {
    return new BigDecimal[] {value0, value1};
  }
}
