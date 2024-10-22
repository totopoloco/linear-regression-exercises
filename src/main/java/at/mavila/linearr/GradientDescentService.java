package at.mavila.linearr;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor(onConstructor_ = @Autowired)
public class GradientDescentService {

  private final ComputeGradientLogisticService computeGradientLogisticService;
  private final ComputeCostLogisticService computeCostLogisticService;

  /**
   * Compute the gradient descent.
   *
   * @param x                  (ndarray (m,n)   : Data, m examples with n features
   * @param y                  (ndarray (m,))   : target values
   * @param wIn                (ndarray (n,)): Initial values of model parameters
   * @param bIn                (scalar)      : Initial values of model parameter
   * @param alpha              (float)      : Learning rate
   * @param numberOfIterations (scalar) : number of iterations to run gradient descent
   * @return w (ndarray (n,))   : Updated values of parameters
   * b (scalar)         : Updated value of parameter
   */
  public ResultGradientDescent compute(final List<List<BigDecimal>> x,
                                       final List<BigDecimal> y,
                                       final List<BigDecimal> wIn,
                                       final BigDecimal bIn,
                                       final BigDecimal alpha,
                                       final long numberOfIterations) {

    List<BigDecimal> jHistory = new ArrayList<>();
    //Clone wIn in w
    List<BigDecimal> w = new ArrayList<>(wIn);
    BigDecimal b = BigDecimal.valueOf(bIn.doubleValue());

    for (long i = 0; i < numberOfIterations; i++) {
      //Calculate the gradient and update the parameters
      ResultComputeGradient compute = computeGradientLogisticService.compute(x, y, w, b);
      //w is an array of n elements and djDw is an array of n elements
      //port this from Python to Java
      //w = w - alpha * djDw
      //b = b - alpha * djDb
      for (int j = 0; j < w.size(); j++) {
        w.set(j, w.get(j).subtract(alpha.multiply(compute.djDw().get(j))));
      }
      b = b.subtract(alpha.multiply(compute.djDb()));

      //Port this from Python to Java, I am not sure if this is needed in Java, but let's keep it for consistency
      /*
       # Save cost J at each iteration
        if i<100000:      # prevent resource exhaustion
            J_history.append( compute_cost_logistic(X, y, w, b) )
      * */
      if (i < 100000) {
        jHistory.add(this.computeCostLogisticService.compute(x, y, w, b));
      }

      // # Print cost every at intervals 10 times or as many iterations if < 10
      if (i % Math.ceil((double) numberOfIterations / 10L) == 0) {
        log.info(String.format("Iteration %4d: Cost %s", i, jHistory.getLast()));
      }

    }


    return ResultGradientDescent.builder().w(w).costHistory(jHistory).b(b).build();
  }

}
