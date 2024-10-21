package at.mavila.linearr;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import lombok.AllArgsConstructor;
import org.apache.commons.math3.analysis.function.Log;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
public class ComputeCostLogisticService {

  private final SigmoidService sigmoidService;

  /**
   * Compute the cost for the logistic regression.
   *
   * @param x (ndarray (m,n)): Data, m examples with n features.
   * @param y (ndarray (m,)) : target values.
   * @param w (ndarray (n,)) : model parameters (weights).
   * @param b b (scalar)       : model parameter (bias).
   * @return the cost for the logistic regression.
   */
  BigDecimal compute(List<List<BigDecimal>> x, List<BigDecimal> y, List<BigDecimal> w, BigDecimal b) {

    performParameterValidations(x, y, w, b);

    final int m = x.size();
    final AtomicReference<BigDecimal> cost = new AtomicReference<>(BigDecimal.ZERO);
    for (int n = 0; n < m; n++) {
      //Calculate the input for the sigmoid function
      //it is the dot product of X[i} with w plus b
      //Take the element from the list x at index n
      final List<BigDecimal> xN = x.get(n);
      //Calculate the dot product of xN and w
      final BigDecimal zInput = dotProduct(xN, w).add(b);
      final BigDecimal fWbInput = this.sigmoidService.sigmoid(zInput);

      //Calculate the cost for the logistic regression and accumulate it
      //cost +=  -y[i]*np.log(f_wb_i) - (1-y[i])*np.log(1-f_wb_i)
      final BigDecimal yNegated = y.get(n).negate();
      //Calculate log(f_wb_i)
      final Log log = new Log();

      final BigDecimal yMultiplied = yNegated.multiply(BigDecimal.valueOf(log.value(fWbInput.doubleValue())));
      final BigDecimal add = BigDecimal.ONE.add(yNegated).multiply(BigDecimal.valueOf(log.value(1D - fWbInput.doubleValue())));

      //Cost is equal to the rest of yMultiplied and add
      cost.set(cost.get().add(yMultiplied.subtract(add)));
    }

    return cost.get().divide(BigDecimal.valueOf(m), new MathContext(Utils.PRECISION)).stripTrailingZeros();
  }

  private static void performParameterValidations(List<List<BigDecimal>> x,
                                                  List<BigDecimal> y,
                                                  List<BigDecimal> w,
                                                  BigDecimal b) {
    Utils.validateList(x, "x");
    Utils.validateList(y, "y");
    Utils.validateList(w, "w");
    Utils.validateNotNull(b, "b");
  }


  private BigDecimal dotProduct(final List<BigDecimal> xN, final List<BigDecimal> w) {
    return BigDecimal.valueOf(calculateDotProduct(xN, w));
  }

  private static double calculateDotProduct(List<BigDecimal> xN, List<BigDecimal> w) {
    return (new ArrayRealVector(
        xN.stream()
            .mapToDouble(BigDecimal::doubleValue)
            .toArray())
    ).dotProduct(
        new ArrayRealVector(
            w.stream()
                .mapToDouble(BigDecimal::doubleValue)
                .toArray())
    );
  }
}
