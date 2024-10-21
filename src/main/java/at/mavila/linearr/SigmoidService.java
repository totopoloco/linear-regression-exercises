package at.mavila.linearr;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class SigmoidService {

  /**
   * Calculate the sigmoid function for a given value z.
   * sigmoid(z) = 1 / (1 + e^(-z))
   * Usage:
   * <pre>
   * sigmoid(0) = 0.5
   * sigmoid(null) = IllegalArgumentException
   * </pre>
   *
   * @param z value for the sigmoid function
   * @return the result of the sigmoid function
   */
  public BigDecimal sigmoid(BigDecimal z) {
    //Validate input, if z is null, throw an IllegalArgumentException
    if (Objects.isNull(z)) {
      throw new IllegalArgumentException("z is null");
    }
    return applyFormula(z).stripTrailingZeros();
  }

  private static BigDecimal applyFormula(BigDecimal z) {
    return BigDecimal.ONE.divide(//Divide 1 by the lower part of the formula
        BigDecimal.ONE.add(BigDecimal.valueOf(Math.exp(z.negate().doubleValue()))), new MathContext(Utils.PRECISION)
    );
  }

}
