package at.mavila.linearr;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GradientCalculator {

  /**
   * Calculate the gradients.
   *
   * @param x                list of x values
   * @param y                list of y values
   * @param m                number of samples
   * @param result           wrapped parameters
   * @param initialCondition initial condition, wrapped in an array of BigDecimals with 0 and 0.
   * @return the gradients for w and b
   */
  public static BigDecimal[] calculateGradients(final List<BigDecimal> x,
                                                final List<BigDecimal> y,
                                                final int m,
                                                final Result result,
                                                final BigDecimal[] initialCondition) {
    return IntStream.range(0, m)
        .mapToObj(i -> {
          final BigDecimal diff =
              getDifferenceObservedVsPredicted(
                  y,
                  i,
                  ModelCreator.createModel(x.get(i), result.wValid(), result.bValid()));
          return Utils.getBigDecimals(diff.multiply(x.get(i)), diff);
        })
        .reduce(
            initialCondition, (mya, myb) -> Utils.getBigDecimals(mya[0].add(myb[0]), mya[1].add(myb[1]))
        );
  }

  private static BigDecimal getDifferenceObservedVsPredicted(final List<BigDecimal> y, final int i, final BigDecimal fwb) {
    return fwb.subtract(y.get(i));
  }
}
