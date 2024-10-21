package at.mavila.linearr;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class ComputeCostLogisticServiceTest {

  @Autowired
  private ComputeCostLogisticService computeCostLogisticService;

  @Test
  void whenExampleFromCourseWithBM3ThenResult() {
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

    List<BigDecimal> w = List.of(BigDecimal.valueOf(1D), BigDecimal.valueOf(1D));
    BigDecimal b = BigDecimal.valueOf(-3D);

    BigDecimal compute = this.computeCostLogisticService.compute(x, y, w, b);

    assertThat(compute).isNotNull().isCloseTo(new BigDecimal("0.3668667864055175"), Percentage.withPercentage(0.0001));


  }

  @Test
  void whenExampleFromCourseWithBM4ThenResult() {
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

    List<BigDecimal> w = List.of(BigDecimal.valueOf(1D), BigDecimal.valueOf(1D));
    BigDecimal b = BigDecimal.valueOf(-4D);

    BigDecimal compute = this.computeCostLogisticService.compute(x, y, w, b);

    assertThat(compute).isNotNull().isCloseTo(new BigDecimal("0.5036808636748461"), Percentage.withPercentage(0.0001));


  }

  @Test
  void whenEmptyDataThenArithmeticException() {
    List<List<BigDecimal>> x = List.of();
    List<BigDecimal> y = List.of();
    List<BigDecimal> w = List.of(BigDecimal.valueOf(1D), BigDecimal.valueOf(1D));
    BigDecimal b = BigDecimal.valueOf(-3D);


    assertThatThrownBy(() -> this.computeCostLogisticService.compute(x, y, w, b))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void whenNullDataThenThrowException() {
    List<BigDecimal> w = List.of(BigDecimal.valueOf(1D), BigDecimal.valueOf(1D));
    BigDecimal b = BigDecimal.valueOf(-3D);

    assertThatThrownBy(() -> this.computeCostLogisticService.compute(null, null, w, b))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void whenDifferentVectorSizesThenThrowException() {
    List<List<BigDecimal>> x = List.of(
        List.of(BigDecimal.valueOf(0.5D), BigDecimal.valueOf(1.5D)),
        List.of(BigDecimal.valueOf(1D), BigDecimal.valueOf(1D))
    );

    List<BigDecimal> y = List.of(
        BigDecimal.valueOf(0D),
        BigDecimal.valueOf(0D),
        BigDecimal.valueOf(0D)
    );

    List<BigDecimal> w = List.of(BigDecimal.valueOf(1D), BigDecimal.valueOf(1D));
    BigDecimal b = BigDecimal.valueOf(-3D);

    BigDecimal compute = this.computeCostLogisticService.compute(x, y, w, b);

    assertThat(compute).isNotNull();
  }

  @Test
  void whenNegativeWeightsThenResult() {
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

    List<BigDecimal> w = List.of(BigDecimal.valueOf(-1D), BigDecimal.valueOf(-1D));
    BigDecimal b = BigDecimal.valueOf(3D);

    BigDecimal compute = this.computeCostLogisticService.compute(x, y, w, b);

    assertThat(compute).isNotNull().isCloseTo(new BigDecimal("1.2002001197388507667"), Percentage.withPercentage(0.0001));
  }

}