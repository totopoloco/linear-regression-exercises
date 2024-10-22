package at.mavila.linearr;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GradientDescentServiceTest {

  @Autowired
  private GradientDescentService gradientDescentService;

  @Test
  void whenUsingParameterFromTrainingThenResult() {

    //w_tmp: [0. 0.]
    //b_tmp: 0
    //alpha: 0.1
    //num_iters: 10000
    //X_tmp: [[0.5, 1.5], [1, 1], [1.5, 0.5], [3, 0.5], [2, 2], [1, 2.5]]
    //y_tmp: [0, 0, 0, 1, 1, 1]

    List<BigDecimal> w = List.of(BigDecimal.ZERO, BigDecimal.ZERO);
    BigDecimal b = BigDecimal.ZERO;
    BigDecimal alpha = new BigDecimal("0.1");
    long num_iters = 10000L;
    List<List<BigDecimal>> x = List.of(
        List.of(new BigDecimal("0.5"), new BigDecimal("1.5")),
        List.of(new BigDecimal("1"), new BigDecimal("1")),
        List.of(new BigDecimal("1.5"), new BigDecimal("0.5")),
        List.of(new BigDecimal("3"), new BigDecimal("0.5")),
        List.of(new BigDecimal("2"), new BigDecimal("2")),
        List.of(new BigDecimal("1"), new BigDecimal("2.5"))
    );
    List<BigDecimal> y =
        List.of(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE);


    ResultGradientDescent compute = this.gradientDescentService.compute(x, y, w, b, alpha, num_iters);
    assertThat(compute).isNotNull();
  }
}