package at.mavila.linearr;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

@Service
public class ComputeCostService {

  /**
   * Compute the cost of the linear regression model.
   * Apply the formula: 1/2m * sum((w * x + b - y)^2)
   * where m is the number of data points, w is the weight, b is the bias, x is the list of x values and y is the list of y values.
   * <pre>
   * Usage:
   * computeCost([1, 2, 3, 4], [2, 3, 4, 5], 2, 1) = 3.75
   * computeCost([1, 2, 3, 4], [2, 3, 4, 5], 4, 3) = 50.75
   * computeCost(null, null, null, null) = IllegalArgumentException
   * </pre>
   *
   * @param x list of x values
   * @param y list of y values
   * @param w weight
   * @param b bias
   * @return the cost of the linear regression model
   */
  public BigDecimal computeCost(List<BigDecimal> x, List<BigDecimal> y, BigDecimal w, BigDecimal b) {

    Result result = wrapParameters(x, y, w, b);

    //Compute the cost
    return calculateTotalCost(x, y, result.wValid(), result.bValid())
        .divide(BigDecimal.valueOf(2L * x.size()), 20, RoundingMode.UNNECESSARY)
        .stripTrailingZeros();
  }

  private static Result wrapParameters(List<BigDecimal> x, List<BigDecimal> y, BigDecimal w, BigDecimal b) {
    //Validate each parameter for non-null and non-empty, if not throw an IllegalArgumentException
    if (CollectionUtils.isEmpty(x)) {
      throw new IllegalArgumentException("x is empty");
    }
    if (CollectionUtils.isEmpty(y)) {
      throw new IllegalArgumentException("y is empty");
    }
    //Validate that x and y have the same size
    if (x.size() != y.size()) {
      throw new IllegalArgumentException("x and y have different sizes");
    }

    //Validate w and b for non-null and return the valid values
    return new Result(Objects.requireNonNull(w, "w is null"), Objects.requireNonNull(b, "b is null"));
  }

  private record Result(BigDecimal wValid, BigDecimal bValid) {
  }

  private static BigDecimal calculateTotalCost(List<BigDecimal> x, List<BigDecimal> y, BigDecimal wValid, BigDecimal bValid) {
    return x.stream().map(i -> calculateSingleCost(x, y, i, wValid, bValid)).reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  private static BigDecimal calculateSingleCost(List<BigDecimal> x, List<BigDecimal> y, BigDecimal i, BigDecimal wValid,
                                                BigDecimal bValid) {
    return wValid.multiply(i).add(bValid).subtract(y.get(x.indexOf(i))).pow(2);
  }


}
