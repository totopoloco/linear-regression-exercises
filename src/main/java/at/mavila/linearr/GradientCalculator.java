package at.mavila.linearr;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GradientCalculator {

  public static BigDecimal[] calculateGradients(final List<BigDecimal> x,
                                                final List<BigDecimal> y,
                                                final int m,
                                                final Result result,
                                                final BigDecimal[] initialCondition) {
    return IntStream.range(0, m)
        .mapToObj(i -> {
          final BigDecimal diff =
              getDifferenceObserverVsPredicted(
                  y,
                  i,
                  ModelCreator.createModel(x.get(i), result.wValid(), result.bValid()));
          return Utils.getBigDecimals(diff.multiply(x.get(i)), diff);
        })
        .reduce(
            initialCondition, (mya, myb) -> Utils.getBigDecimals(mya[0].add(myb[0]), mya[1].add(myb[1]))
        );
  }

  private static BigDecimal getDifferenceObserverVsPredicted(final List<BigDecimal> y, final int i, final BigDecimal fwb) {
    return fwb.subtract(y.get(i));
  }


}
