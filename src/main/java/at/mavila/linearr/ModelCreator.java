package at.mavila.linearr;

import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ModelCreator {

  /**
   * Create a model.
   * Apply the formula: w * i + b
   *
   * @param i      the input value
   * @param wValid the valid weight
   * @param bValid the valid bias
   * @return the model
   */
  public static BigDecimal createModel(BigDecimal i, BigDecimal wValid, BigDecimal bValid) {
    return wValid.multiply(i).add(bValid);
  }
}
