package at.mavila.linearr;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
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

    final List<BigDecimal> jHistory = new ArrayList<>();
    //Clone wIn in w
    final List<BigDecimal> w = new ArrayList<>(wIn);
    final AtomicReference<BigDecimal> b = new AtomicReference<>(BigDecimal.valueOf(bIn.doubleValue()));

    for (long i = 0; i < numberOfIterations; i++) {
      //Calculate the gradient and update the parameters
      ResultComputeGradient resultComputeGradient = computeGradientLogisticService.compute(x, y, w, b.get());
      //w is an array of n elements and djDw is an array of n elements
      //port this from Python to Java
      //w = w - alpha * djDw
      //b = b - alpha * djDb
      calculateW(alpha, w, resultComputeGradient);
      b.set(calculateB(alpha, b.get(), resultComputeGradient));

      //Port this from Python to Java, I am not sure if this is needed in Java, but let's keep it for consistency
      /*
       # Save cost J at each iteration
        if i<100000:      # prevent resource exhaustion
            J_history.append( compute_cost_logistic(X, y, w, b) )
      * */
      storeInHistory(x, y, i, jHistory, w, b);

      // # Print cost every at intervals 10 times or as many iterations if < 10
      logProgress((double) numberOfIterations, i, jHistory);

    }


    return ResultGradientDescent.builder().w(w).costHistory(jHistory).b(b.get()).build();
  }

  private static void logProgress(double numberOfIterations, long i, List<BigDecimal> jHistory) {
    if (i % Math.ceil(numberOfIterations / 10L) == 0) {
      log.info(String.format("Iteration %4d: Cost %s", i, jHistory.getLast()));
    }
  }

  private void storeInHistory(List<List<BigDecimal>> x, List<BigDecimal> y, long i, List<BigDecimal> jHistory, List<BigDecimal> w,
                              AtomicReference<BigDecimal> b) {
    if (i < 100000) {
      jHistory.add(this.computeCostLogisticService.compute(x, y, w, b.get()));
    }
  }

  private static void calculateW(BigDecimal alpha, List<BigDecimal> w, ResultComputeGradient compute) {
    for (int j = 0; j < w.size(); j++) {
      w.set(j, w.get(j).subtract(alpha.multiply(compute.djDw().get(j))));
    }
  }

  private static BigDecimal calculateB(BigDecimal alpha, BigDecimal b, ResultComputeGradient compute) {
    return b.subtract(alpha.multiply(compute.djDb()));
  }

}
