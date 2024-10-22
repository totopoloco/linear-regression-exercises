package at.mavila.linearr;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
public class ComputeGradientLogisticService {

  private final SigmoidService sigmoidService;

  /**
   * X (ndarray (m,n): Data, m examples with n features
   * y (ndarray (m,)): target values
   * w (ndarray (n,)): model parameters  (wieghts)
   * b (scalar)      : model parameter (bias)
   *
   * @param x (ndarray (m,n))
   * @param y (ndarray (m,))
   * @param w (ndarray (n,))
   * @param b (scalar)
   * @return dj_dw (ndarray (n,)): The gradient of the cost w.r.t. the parameters w.
   * dj_db (scalar)      : The gradient of the cost w.r.t. the parameter b.
   */
  public ResultComputeGradient compute(final List<List<BigDecimal>> x,
                                       final List<BigDecimal> y,
                                       final List<BigDecimal> w,
                                       final BigDecimal b) {
    int m = x.size();

    List<BigDecimal> djDw = new ArrayList<>(m);
    //Initialize the djDw list with zeros
    for (int i = 0; i < m; i++) {
      djDw.add(BigDecimal.ZERO);
    }

    final AtomicReference<BigDecimal> djDb = new AtomicReference<>(BigDecimal.ZERO);

    for (int i = 0; i < m; i++) {
      final double dotProduct = Utils.calculateDotProduct(x.get(i), w);
      final BigDecimal fWbi = this.sigmoidService.sigmoid(BigDecimal.valueOf(dotProduct).add(b));
      final BigDecimal erri = fWbi.subtract(y.get(i));

      for (int j = 0; j < w.size(); j++) {
        final BigDecimal dj_dwj = djDw.get(j).add(erri.multiply(x.get(i).get(j)));
        //Update the value of djDw at index j
        djDw.set(j, dj_dwj);
      }
      djDb.set(djDb.get().add(erri));

    }
    djDw.replaceAll(dj -> dj.divide(BigDecimal.valueOf(m), new MathContext(Utils.PRECISION)).stripTrailingZeros());
    //There are zeros in the djDw list, so we need to remove them
    djDw.removeIf(BigDecimal.ZERO::equals);
    djDb.set(djDb.get().divide(BigDecimal.valueOf(m), new MathContext(Utils.PRECISION)).stripTrailingZeros());

    return new ResultComputeGradient(djDb.get(), djDw);

  }
}