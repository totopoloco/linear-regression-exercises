package at.mavila.linearr;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class ComputeGradientLogisticServiceTest {

  @Autowired
  private ComputeGradientLogisticService computeGradientLogisticService;

  @Test
  void whenUsingParameterFromTrainingThenResult() {

//    X_tmp = np.array([[0.5, 1.5], [1,1], [1.5, 0.5], [3, 0.5], [2, 2], [1, 2.5]])
//    y_tmp = np.array([0, 0, 0, 1, 1, 1])
//    w_tmp = np.array([2.,3.])
//    b_tmp = 1.

    List<List<BigDecimal>> x = List.of(
        List.of(BigDecimal.valueOf(0.5D), BigDecimal.valueOf(1.5D)),
        List.of(BigDecimal.valueOf(1D), BigDecimal.valueOf(1D)),
        List.of(BigDecimal.valueOf(1.5D), BigDecimal.valueOf(0.5D)),
        List.of(BigDecimal.valueOf(3D), BigDecimal.valueOf(0.5D)),
        List.of(BigDecimal.valueOf(2D), BigDecimal.valueOf(2D)),
        List.of(BigDecimal.valueOf(1D), BigDecimal.valueOf(2.5D))
    );

    List<BigDecimal> y = List.of(
        BigDecimal.valueOf(0D),
        BigDecimal.valueOf(0D),
        BigDecimal.valueOf(0D),
        BigDecimal.valueOf(1D),
        BigDecimal.valueOf(1D),
        BigDecimal.valueOf(1D));

    List<BigDecimal> w = List.of(BigDecimal.valueOf(2D), BigDecimal.valueOf(3D));

    BigDecimal b = BigDecimal.valueOf(1D);

    ResultComputeGradient resultComputeGradient = this.computeGradientLogisticService.compute(x, y, w, b);

    assertThat(resultComputeGradient).isNotNull();
    assertThat(resultComputeGradient.djDw().get(0)).isNotNull()
        .isCloseTo(new BigDecimal("0.498333393278696"), Percentage.withPercentage(0.0001));
    assertThat(resultComputeGradient.djDw().get(1)).isNotNull()
        .isCloseTo(new BigDecimal("0.49883942983996693"), Percentage.withPercentage(0.0001));

    assertThat(resultComputeGradient.djDb()).isNotNull()
        .isCloseTo(new BigDecimal("0.49861806546328574"), Percentage.withPercentage(0.0001));


  }
}