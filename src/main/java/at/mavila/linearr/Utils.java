package at.mavila.linearr;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

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

  /**
   * Convenient method to validate a list.
   *
   * @param list the list to validate.
   * @param name the name of the list.
   * @throws IllegalArgumentException if the list is null or empty.
   */
  public static void validateList(List<?> list, String name) {
    if (CollectionUtils.isEmpty(list)) {
      throw new IllegalArgumentException(name + " is null or empty");
    }
  }

  /**
   * Convenient method to validate an object.
   *
   * @param obj  the object to validate.
   * @param name the name of the object.
   * @throws IllegalArgumentException if the object is null.
   */
  public static void validateNotNull(Object obj, String name) {
    if (Objects.isNull(obj)) {
      throw new IllegalArgumentException(name + " is null");
    }
  }
}
