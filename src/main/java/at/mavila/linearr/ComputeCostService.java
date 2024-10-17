package at.mavila.linearr;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
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

    Result result = InputValidator.wrapParameters(x, y, w, b);

    //Compute the cost
    BigDecimal divided = calculateTotalCost(x, y, result.wValid(), result.bValid()) /*BigDecimal*/
        .divide(BigDecimal.valueOf(2L * x.size()), new MathContext(Utils.PRECISION));

    return divided.stripTrailingZeros();
  }

  private static BigDecimal calculateTotalCost(final List<BigDecimal> x,
                                               final List<BigDecimal> y,
                                               final BigDecimal w,
                                               final BigDecimal b) {
    return x.stream().map(xi -> calculateSingleCost(x, y, xi, w, b)).reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  private static BigDecimal calculateSingleCost(final List<BigDecimal> x,
                                                final List<BigDecimal> y,
                                                final BigDecimal xi,
                                                final BigDecimal w,
                                                final BigDecimal b) {

    return ModelCreator.createModel(xi, w, b).subtract(y.get(x.indexOf(xi))).pow(2);

  }


}