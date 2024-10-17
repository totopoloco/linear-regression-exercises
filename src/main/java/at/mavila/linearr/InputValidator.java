package at.mavila.linearr;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class InputValidator {
  /**
   * Wrap the parameters.
   * Validate each parameter for non-null and non-empty, if not throw an IllegalArgumentException
   *
   * @param x list of x values
   * @param y list of y values
   * @param w weight
   * @param b bias
   * @return the valid parameters
   */
  public static Result wrapParameters(List<BigDecimal> x, List<BigDecimal> y, BigDecimal w, BigDecimal b) {
    //Validate each parameter for non-null and non-empty, if not throw an IllegalArgumentException
    validateArrayX(x);
    validateArrayY(y);
    //Validate that x and y have the same size
    checkArraySizes(x, y);

    return createResult(w, b);
  }

  /**
   * Validate w and b for non-null and return the valid values.
   *
   * @param w weight.
   * @param b bias.
   * @return the valid values.
   */
  private static Result createResult(BigDecimal w, BigDecimal b) {
    return Result.builder()
        .wValid(Objects.requireNonNull(w, "w is null"))
        .bValid(Objects.requireNonNull(b, "b is null"))
        .build();
  }

  private static void checkArraySizes(List<BigDecimal> x, List<BigDecimal> y) {
    if (x.size() != y.size()) {
      throw new IllegalArgumentException("x and y have different sizes");
    }
  }

  private static void validateArrayY(List<BigDecimal> y) {
    if (CollectionUtils.isEmpty(y)) {
      throw new IllegalArgumentException("y is empty");
    }
  }

  private static void validateArrayX(List<BigDecimal> x) {
    if (CollectionUtils.isEmpty(x)) {
      throw new IllegalArgumentException("x is empty");
    }
  }
}
