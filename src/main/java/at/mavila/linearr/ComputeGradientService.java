package at.mavila.linearr;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ComputeGradientService {

  /**
   * Compute the gradient.
   * Apply the formula: djdwi = fwb-y[i]*x[i] and djdbi = fwb-y[i]
   * Accumulate the partial derivatives for w and b.
   * Divide the accumulated partial derivatives by the number of samples.
   * Return the partial derivatives.
   * <p>
   * Usage:
   * <pre>
   * computeGradient([1, 2, 3, 4], [2, 3, 4, 5], 2, 1) = [7.5, 2.5]
   * computeGradient([1, 2, 3, 4], [2, 3, 4, 5], 4, 3) = [25.5, 6.75]
   * </pre>
   *
   * @param x list of x values
   * @param y list of y values
   * @param w weight
   * @param b bias
   * @return the gradient
   */
  public List<BigDecimal> computeGradient(List<BigDecimal> x, List<BigDecimal> y, BigDecimal w, BigDecimal b) {

    //Use the input validator to validate the input
    final Result result = InputValidator.wrapParameters(x, y, w, b);
    final int m = x.size();

    BigDecimal djdw = BigDecimal.ZERO;
    BigDecimal djdb = BigDecimal.ZERO;

    for (int i = 0; i < m; i++) {

      BigDecimal fwb = ModelCreator.createModel(x.get(i), result.wValid(), result.bValid());
      //Apply the formula for djdwi = fwb-y[i]*x[i]
      BigDecimal djdwi = fwb.subtract(y.get(i)).multiply(x.get(i));
      //Apply the formula for djdwi = fwb-y[i]
      BigDecimal djdbi = fwb.subtract(y.get(i));
      //Accumulate the partial derivatives for w and b
      djdw = djdw.add(djdwi);
      djdb = djdb.add(djdbi);

    }

    //Divide the accumulated partial derivatives by the number of samples
    final BigDecimal djdwDivideM = djdw.divide(BigDecimal.valueOf(m), 20, RoundingMode.UNNECESSARY).stripTrailingZeros();
    final BigDecimal djdbDivideM = djdb.divide(BigDecimal.valueOf(m), 20, RoundingMode.UNNECESSARY).stripTrailingZeros();

    //Return the partial derivatives
    return List.of(djdwDivideM, djdbDivideM);

  }

}
