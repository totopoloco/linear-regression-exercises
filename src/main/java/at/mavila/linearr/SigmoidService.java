package at.mavila.linearr;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class SigmoidService {

  public BigDecimal sigmoid(BigDecimal z) {
    //Validate input, if z is null, throw an IllegalArgumentException
    if (Objects.isNull(z)) {
      throw new IllegalArgumentException("z is null");
    }

    //First calculate lower part of the formula
    //1 + e^(-z)
    final BigDecimal zNegated = z.negate();
    //Calculate e^(-z)
    final BigDecimal eToTheMinusZ = BigDecimal.valueOf(Math.exp(zNegated.doubleValue()));
    //Calculate 1 + e^(-z)
    final BigDecimal lower = BigDecimal.ONE.add(eToTheMinusZ);

    //Divide 1 by the lower part of the formula
    final BigDecimal divided = BigDecimal.ONE.divide(lower, new MathContext(Utils.PRECISION));
    return divided.stripTrailingZeros();
  }

}
