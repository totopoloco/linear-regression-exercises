package at.mavila.linearr;

import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Utils {

  public static BigDecimal[] getBigDecimals(BigDecimal value0, BigDecimal value1) {
    return new BigDecimal[] {value0, value1};
  }
}
