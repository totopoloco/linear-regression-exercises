package at.mavila.linearr;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.BigRange;
import net.jqwik.spring.JqwikSpringSupport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@JqwikSpringSupport
@SpringBootTest
@Slf4j
class ComputeCostServiceTest {

  @Autowired
  private ComputeCostService computeCostService;

  @Test
  void whenGivenW2B1ParametersThenGetAResult() {
    //Given
    List<BigDecimal> x = List.of(BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3), BigDecimal.valueOf(4));
    List<BigDecimal> y = List.of(BigDecimal.valueOf(2), BigDecimal.valueOf(3), BigDecimal.valueOf(4), BigDecimal.valueOf(5));
    BigDecimal w = BigDecimal.valueOf(2);
    BigDecimal b = BigDecimal.valueOf(1);

    //When
    BigDecimal result = this.computeCostService.computeCost(x, y, w, b);

    //Then
    assertThat(result).isNotNull().isEqualTo(BigDecimal.valueOf(3.75));

  }

  @Test
  void whenGivenW0B0ParametersThenGetAResult() {
    //Given
    List<BigDecimal> x = List.of(BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3), BigDecimal.valueOf(4));
    List<BigDecimal> y = List.of(BigDecimal.valueOf(2), BigDecimal.valueOf(3), BigDecimal.valueOf(4), BigDecimal.valueOf(5));
    BigDecimal w = BigDecimal.valueOf(0D);
    BigDecimal b = BigDecimal.valueOf(0D);

    //When
    BigDecimal result = this.computeCostService.computeCost(x, y, w, b);

    //Then
    assertThat(result).isNotNull().isEqualTo(BigDecimal.valueOf(6.75));

  }

  @Test
  void whenGivenLargeParametersThenGetAResult() {
    //Given
    List<BigDecimal> x = List.of(
        new BigDecimal("1.299288388489299443"),
        new BigDecimal("2.299288828990299443"),
        new BigDecimal("3.827727772825617783"),
        new BigDecimal("4.727727990928288928")
    );
    List<BigDecimal> y = List.of(
        new BigDecimal("2.827727772825617783"),
        new BigDecimal("3.728843726393294728"),
        new BigDecimal("4.922837823899328388"),
        new BigDecimal("5.272736282827872872")
    );
    BigDecimal w = BigDecimal.valueOf(9.8288383994904D);
    BigDecimal b = BigDecimal.valueOf(2.9277398999389D);

    //When
    BigDecimal result = this.computeCostService.computeCost(x, y, w, b);

    //Then
    assertThat(result).isNotNull().isEqualTo(new BigDecimal("482.11794395470745996"));

  }

  @Test
  void whenGivenVeryLargeParametersThenGetAResult() {
    //Given
    List<BigDecimal> x = List.of(
        new BigDecimal("1.299288388489299443299288388489299443"),
        new BigDecimal("2.299288828990299443299288388489299443"),
        new BigDecimal("3.827727772825617783299288388489299443"),
        new BigDecimal("4.727727990928288928299288388489299443")
    );
    List<BigDecimal> y = List.of(
        new BigDecimal("2.827727772825617783299288388489299443"),
        new BigDecimal("3.728843726393294728299288388489299443"),
        new BigDecimal("4.922837823899328388299288388489299443"),
        new BigDecimal("5.272736282827872872299288388489299443")
    );
    BigDecimal w = BigDecimal.valueOf(0.18288383994904299288388489299443D);
    BigDecimal b = BigDecimal.valueOf(0.19277398999389299288388489299443D);

    //When
    BigDecimal result = this.computeCostService.computeCost(x, y, w, b);

    //Then
    assertThat(result).isNotNull().isEqualTo(new BigDecimal("6.1830270852817061563"));

  }

  @Test
  void whenPreparedParametersForTrailingZerosThenGetAResult() {
    //Given
    List<BigDecimal> x = List.of(
        new BigDecimal("1.0000000000000000000000000000000000001"),
        new BigDecimal("2.0000000000000000000000000000000000001"),
        new BigDecimal("3.0000000000000000000000000000000000001"),
        new BigDecimal("4.0000000000000000000000000000000000001")
    );
    List<BigDecimal> y = List.of(
        new BigDecimal("2.0000000000000000000000000000000000001"),
        new BigDecimal("3.0000000000000000000000000000000000001"),
        new BigDecimal("4.0000000000000000000000000000000000001"),
        new BigDecimal("5.0000000000000000000000000000000000001")
    );
    BigDecimal w = BigDecimal.valueOf(0.9000000000000000000000000001D);
    BigDecimal b = BigDecimal.valueOf(0.10000000000000000000000000000000000000000000002D);

    //When
    BigDecimal result = this.computeCostService.computeCost(x, y, w, b);

    //Then
    assertThat(result).isNotNull().isEqualTo(new BigDecimal("0.6675"));

  }

  @Test
  void whenGivenW4B3ParametersThenGetAResult() {
    //Given
    List<BigDecimal> x = List.of(BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3), BigDecimal.valueOf(4));
    List<BigDecimal> y = List.of(BigDecimal.valueOf(2), BigDecimal.valueOf(3), BigDecimal.valueOf(4), BigDecimal.valueOf(5));
    BigDecimal w = BigDecimal.valueOf(4);
    BigDecimal b = BigDecimal.valueOf(3);

    //When
    BigDecimal result = this.computeCostService.computeCost(x, y, w, b);

    //Then
    assertThat(result).isNotNull().isEqualTo(BigDecimal.valueOf(50.75));

  }

  // Add more tests here
  //Test nulls
  @Test
  void whenGivenNullParametersThenThrowIllegalArgumentException() {
    //Given
    List<BigDecimal> x = null;
    List<BigDecimal> y = null;
    BigDecimal w = null;
    BigDecimal b = null;

    //When and Then
    assertThatThrownBy(() -> this.computeCostService.computeCost(x, y, w, b))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("x is empty");
  }

  //Test empty lists
  @Test
  void whenGivenEmptyListsThenThrowIllegalArgumentException() {
    //Given
    List<BigDecimal> x = List.of();
    List<BigDecimal> y = List.of();
    BigDecimal w = BigDecimal.valueOf(2);
    BigDecimal b = BigDecimal.valueOf(1);

    //When and Then
    assertThatThrownBy(() -> this.computeCostService.computeCost(x, y, w, b))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("x is empty");
  }

  //Test different sizes
  @Test
  void whenGivenDifferentSizesThenThrowIllegalArgumentException() {
    //Given
    List<BigDecimal> x = List.of(BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3), BigDecimal.valueOf(4));
    List<BigDecimal> y = List.of(BigDecimal.valueOf(2), BigDecimal.valueOf(3), BigDecimal.valueOf(4));
    BigDecimal w = BigDecimal.valueOf(2);
    BigDecimal b = BigDecimal.valueOf(1);

    //When and Then
    assertThatThrownBy(() -> this.computeCostService.computeCost(x, y, w, b))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("x and y have different sizes");
  }

  //Test when w is null and b is null but x and y are not null
  @Test
  void whenGivenWNullBNullParametersThenThrowIllegalArgumentException() {
    //Given
    List<BigDecimal> x = List.of(BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3), BigDecimal.valueOf(4));
    List<BigDecimal> y = List.of(BigDecimal.valueOf(2), BigDecimal.valueOf(3), BigDecimal.valueOf(4), BigDecimal.valueOf(5));
    BigDecimal w = null;
    BigDecimal b = null;

    //When and Then
    assertThatThrownBy(() -> this.computeCostService.computeCost(x, y, w, b))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("w is null");
  }

  //Test when w is null and b is not null but x and y are not null
  @Test
  void whenGivenWNullBNotNullParametersThenThrowIllegalArgumentException() {
    //Given
    List<BigDecimal> x = List.of(BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3), BigDecimal.valueOf(4));
    List<BigDecimal> y = List.of(BigDecimal.valueOf(2), BigDecimal.valueOf(3), BigDecimal.valueOf(4), BigDecimal.valueOf(5));
    BigDecimal w = null;
    BigDecimal b = BigDecimal.valueOf(1);

    //When and Then
    assertThatThrownBy(() -> this.computeCostService.computeCost(x, y, w, b))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("w is null");
  }

  //Test when w is not null and b is null but x and y are not null
  @Test
  void whenGivenWNotNullBNullParametersThenThrowIllegalArgumentException() {
    //Given
    List<BigDecimal> x = List.of(BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3), BigDecimal.valueOf(4));
    List<BigDecimal> y = List.of(BigDecimal.valueOf(2), BigDecimal.valueOf(3), BigDecimal.valueOf(4), BigDecimal.valueOf(5));
    BigDecimal w = BigDecimal.valueOf(2);
    BigDecimal b = null;

    //When and Then
    assertThatThrownBy(() -> this.computeCostService.computeCost(x, y, w, b))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("b is null");
  }

  //Test when one of the lists is null but everything else is not null
  @Test
  void whenGivenXNullParametersThenThrowIllegalArgumentException() {
    //Given
    List<BigDecimal> x = null;
    List<BigDecimal> y = List.of(BigDecimal.valueOf(2), BigDecimal.valueOf(3), BigDecimal.valueOf(4), BigDecimal.valueOf(5));
    BigDecimal w = BigDecimal.valueOf(2);
    BigDecimal b = BigDecimal.valueOf(1);

    //When and Then
    assertThatThrownBy(() -> this.computeCostService.computeCost(x, y, w, b))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("x is empty");
  }

  //Test when w and b are negative
  @Test
  void whenGivenNegativeWAndBParametersThenGetAResult() {
    //Given
    List<BigDecimal> x = List.of(BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3), BigDecimal.valueOf(4));
    List<BigDecimal> y = List.of(BigDecimal.valueOf(2), BigDecimal.valueOf(3), BigDecimal.valueOf(4), BigDecimal.valueOf(5));
    BigDecimal w = BigDecimal.valueOf(-2);
    BigDecimal b = BigDecimal.valueOf(-1);

    //When
    BigDecimal result = this.computeCostService.computeCost(x, y, w, b);

    //Then
    assertThat(result).isNotNull().isEqualTo(BigDecimal.valueOf(50.75));

  }

  //Test when the array y is empty and everything else is not null
  @Test
  void whenGivenYEmptyParametersThenThrowIllegalArgumentException() {
    //Given
    List<BigDecimal> x = List.of(BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3), BigDecimal.valueOf(4));
    List<BigDecimal> y = List.of();
    BigDecimal w = BigDecimal.valueOf(2);
    BigDecimal b = BigDecimal.valueOf(1);

    //When and Then
    assertThatThrownBy(() -> this.computeCostService.computeCost(x, y, w, b))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("y is empty");
  }

  @Test
  void testComputeGradientWithExactScale() {
    // Given
    List<BigDecimal> x = List.of(
        new BigDecimal("1.00000000000000000001"),
        new BigDecimal("2.00000000000000000002"),
        new BigDecimal("3.00000000000000000003"),
        new BigDecimal("4.00000000000000000004"));
    List<BigDecimal> y = List.of(
        new BigDecimal("2.00000000000000000002"),
        new BigDecimal("3.00000000000000000003"),
        new BigDecimal("4.00000000000000000004"),
        new BigDecimal("5.00000000000000000005"));
    BigDecimal w = BigDecimal.valueOf(2);
    BigDecimal b = BigDecimal.valueOf(1);

    // When
    BigDecimal result = this.computeCostService.computeCost(x, y, w, b);

    // Then
    assertThat(result).isNotNull();
    assertThat(new BigDecimal("3.7500000000000000001")).isEqualTo(result);
  }

  //Test using property
  @Property
  void whenGivenRandomValuesThenGetAResult(
      @ForAll @BigRange(min = "1", max = "10") BigDecimal w,
      @ForAll @BigRange(min = "1", max = "10") BigDecimal b) {

    //Given
    List<BigDecimal> x = List.of(BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3), BigDecimal.valueOf(4));
    List<BigDecimal> y = List.of(BigDecimal.valueOf(2), BigDecimal.valueOf(3), BigDecimal.valueOf(4), BigDecimal.valueOf(5));
    x.forEach(i -> log.info("x: {}", i));
    y.forEach(i -> log.info("y: {}", i));
    //When
    BigDecimal result = this.computeCostService.computeCost(x, y, w, b);

    //Then
    log.info("w: {}, b: {}, result: {}", w, b, result);
    assertThat(result).isNotNull();
  }
}