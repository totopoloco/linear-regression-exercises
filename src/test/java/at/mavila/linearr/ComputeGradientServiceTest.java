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
class ComputeGradientServiceTest {

  @Autowired
  private ComputeGradientService computeGradientService;

  @Test
  void whenGivenW2B1ParametersThenGetAResult() {
    //Given
    List<BigDecimal> x = List.of(BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3), BigDecimal.valueOf(4));
    List<BigDecimal> y = List.of(BigDecimal.valueOf(2), BigDecimal.valueOf(3), BigDecimal.valueOf(4), BigDecimal.valueOf(5));
    BigDecimal w = BigDecimal.valueOf(2);
    BigDecimal b = BigDecimal.valueOf(1);

    //When
    List<BigDecimal> result = this.computeGradientService.computeGradient(x, y, w, b);

    //Then
    assertThat(result).isNotNull().containsExactly(BigDecimal.valueOf(7.5), BigDecimal.valueOf(2.5));

  }

  @Test
  void whenGivenW21B1ParametersThenGetAResult() {
    //Given
    List<BigDecimal> x = List.of(BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3), BigDecimal.valueOf(4));
    List<BigDecimal> y = List.of(BigDecimal.valueOf(2), BigDecimal.valueOf(3), BigDecimal.valueOf(4), BigDecimal.valueOf(5));
    BigDecimal w = BigDecimal.valueOf(0.0019D);
    BigDecimal b = BigDecimal.valueOf(-1D);

    //When
    List<BigDecimal> result = this.computeGradientService.computeGradient(x, y, w, b);

    //Then
    assertThat(result).isNotNull().containsExactly(BigDecimal.valueOf(-12.48575), BigDecimal.valueOf(-4.49525));

  }

  @Test
  void whenGiven0sParametersThenGetAResult() {
    //Given
    List<BigDecimal> x = List.of(BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3), BigDecimal.valueOf(4));
    List<BigDecimal> y = List.of(BigDecimal.valueOf(2), BigDecimal.valueOf(3), BigDecimal.valueOf(4), BigDecimal.valueOf(5));
    BigDecimal w = BigDecimal.valueOf(0D);
    BigDecimal b = BigDecimal.valueOf(0D);

    //When
    List<BigDecimal> result = this.computeGradientService.computeGradient(x, y, w, b);

    //Then
    assertThat(result).isNotNull().containsExactly(new BigDecimal("-1E+1"), BigDecimal.valueOf(-3.5D));

  }

  @Test
  void whenGivenW4B3ParametersThenGetAResult() {
    //Given
    List<BigDecimal> x = List.of(BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3), BigDecimal.valueOf(4));
    List<BigDecimal> y = List.of(BigDecimal.valueOf(2), BigDecimal.valueOf(3), BigDecimal.valueOf(4), BigDecimal.valueOf(5));
    BigDecimal w = BigDecimal.valueOf(4);
    BigDecimal b = BigDecimal.valueOf(3);

    //When
    List<BigDecimal> result = this.computeGradientService.computeGradient(x, y, w, b);

    //Then
    assertThat(result).isNotNull().containsExactly(BigDecimal.valueOf(27.5), BigDecimal.valueOf(9.5));

  }

  //whenGivenNullParametersThenThrowIllegalArgumentException
  @Test
  void whenGivenNullParametersThenThrowIllegalArgumentException() {
    //Given
    List<BigDecimal> x = null;
    List<BigDecimal> y = null;
    BigDecimal w = null;
    BigDecimal b = null;

    //When
    //Then
    assertThatThrownBy(() -> {
      this.computeGradientService.computeGradient(x, y, w, b);
    }).isInstanceOf(IllegalArgumentException.class);

  }

  @Test
  void whenGivenEmptyListsThenThrowIllegalArgumentException() {
    //Given
    List<BigDecimal> x = List.of();
    List<BigDecimal> y = List.of();
    BigDecimal w = BigDecimal.valueOf(2);
    BigDecimal b = BigDecimal.valueOf(1);

    //When and Then
    assertThatThrownBy(() -> this.computeGradientService.computeGradient(x, y, w, b))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("x is empty");
  }

  @Test
  void whenGivenDifferentSizesThenThrowIllegalArgumentException() {
    //Given
    List<BigDecimal> x = List.of(BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3), BigDecimal.valueOf(4));
    List<BigDecimal> y = List.of(BigDecimal.valueOf(2), BigDecimal.valueOf(3), BigDecimal.valueOf(4));
    BigDecimal w = BigDecimal.valueOf(2);
    BigDecimal b = BigDecimal.valueOf(1);

    //When and Then
    assertThatThrownBy(() -> this.computeGradientService.computeGradient(x, y, w, b))
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
    assertThatThrownBy(() -> this.computeGradientService.computeGradient(x, y, w, b))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("w is null");
  }

  @Test
  void whenGivenWNullBNotNullParametersThenThrowIllegalArgumentException() {
    //Given
    List<BigDecimal> x = List.of(BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3), BigDecimal.valueOf(4));
    List<BigDecimal> y = List.of(BigDecimal.valueOf(2), BigDecimal.valueOf(3), BigDecimal.valueOf(4), BigDecimal.valueOf(5));
    BigDecimal w = null;
    BigDecimal b = BigDecimal.valueOf(1);

    //When and Then
    assertThatThrownBy(() -> this.computeGradientService.computeGradient(x, y, w, b))
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
    assertThatThrownBy(() -> this.computeGradientService.computeGradient(x, y, w, b))
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
    assertThatThrownBy(() -> this.computeGradientService.computeGradient(x, y, w, b))
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
    List<BigDecimal> result = this.computeGradientService.computeGradient(x, y, w, b);

    //Then
    assertThat(result).isNotNull().containsExactly(BigDecimal.valueOf(-27.5), BigDecimal.valueOf(-9.5));


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
    assertThatThrownBy(() -> this.computeGradientService.computeGradient(x, y, w, b))
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
    List<BigDecimal> result = computeGradientService.computeGradient(x, y, w, b);

    // Then
    assertThat(result).isNotNull();

    assertThat(result.size()).isEqualTo(2);

    assertThat(new BigDecimal("7.5000000000000000001")).isEqualTo(result.get(0));
    assertThat(new BigDecimal("2.5")).isEqualTo(result.get(1));
  }

  @Test
  void testComputeGradientWithComplexScale() {
    // Given
    List<BigDecimal> x = List.of(
        new BigDecimal("1.87384384939939939949"),
        new BigDecimal("2.67384743874783848377"),
        new BigDecimal("3.63466487377474873838"),
        new BigDecimal("4.46734132408094013489"));
    List<BigDecimal> y = List.of(
        new BigDecimal("2.74632613284783274837"),
        new BigDecimal("3.64328674783278478328"),
        new BigDecimal("4.84783487328748723878"),
        new BigDecimal("5.86743872478238787877"));
    BigDecimal w = new BigDecimal("2.87384738743874387438");
    BigDecimal b = new BigDecimal("1.73782374387219930838");

    // When
    List<BigDecimal> result = computeGradientService.computeGradient(x, y, w, b);

    // Then
    assertThat(result).isNotNull();

    assertThat(result.size()).isEqualTo(2);

    assertThat(new BigDecimal("22.304997738381273637")).isEqualTo(result.get(0));
    assertThat(new BigDecimal("6.5499271421945655007")).isEqualTo(result.get(1));
  }

  @Test
  void testComputeGradientWithEvenMoreComplexScale() {
    // Given
    List<BigDecimal> x = List.of(
        new BigDecimal("1.873843849399399399495435435143"),
        new BigDecimal("2.673847438747838483776546546254"),
        new BigDecimal("3.634664873774748738387465462455"),
        new BigDecimal("4.467341324080940134891514351344"));
    List<BigDecimal> y = List.of(
        new BigDecimal("2.746326132847832748371513454135"),
        new BigDecimal("3.643286747832784783286254654664"),
        new BigDecimal("4.847834873287487238781345134444"),
        new BigDecimal("5.867438724782387878776754654625"));
    BigDecimal w = new BigDecimal("2.867438724782387878776754654625");
    BigDecimal b = new BigDecimal("1.737823743872199308384324333333");

    // When
    List<BigDecimal> result = computeGradientService.computeGradient(x, y, w, b);

    // Then
    assertThat(result).isNotNull();

    assertThat(result.size()).isEqualTo(2);

    assertThat(new BigDecimal("22.234776935149942207")).isEqualTo(result.get(0));
    assertThat(new BigDecimal("6.5296602312213786817")).isEqualTo(result.get(1));
  }

  @Test
  void testComputeGradientWithExactScaleKillTrailingZeros() {
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
    BigDecimal w = new BigDecimal("2.00000000000000000001");
    BigDecimal b = new BigDecimal("1.00000000000000000001");

    // When
    List<BigDecimal> result = computeGradientService.computeGradient(x, y, w, b);

    // Then
    assertThat(result).isNotNull();
    assertThat(result.size()).isEqualTo(2);

    // Check that the results have the exact scale after stripping trailing zeros
    assertThat(result.get(0).scale()).isEqualTo(19); // 20 digits - 1 trailing zero
    assertThat(result.get(1).scale()).isEqualTo(19);  // No trailing zeros

    // Check that the results are exactly as expected
    assertThat(result.get(0)).isEqualTo(new BigDecimal("7.5000000000000000002"));
    assertThat(result.get(1)).isEqualTo(new BigDecimal("2.5000000000000000001"));

    // Additional checks to ensure trailing zeros are stripped
    assertThat(result.get(0).toPlainString()).isEqualTo("7.5000000000000000002");
    assertThat(result.get(1).toPlainString()).isEqualTo("2.5000000000000000001");
  }

  //Test using property
  @Property
  void whenGivenRandomValuesThenGetAResult(
      @ForAll @BigRange(min = "1", max = "10") final BigDecimal w,
      @ForAll @BigRange(min = "1", max = "10") final BigDecimal b) {

    //Given
    List<BigDecimal> x = List.of(BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3), BigDecimal.valueOf(4));
    List<BigDecimal> y = List.of(BigDecimal.valueOf(2), BigDecimal.valueOf(3), BigDecimal.valueOf(4), BigDecimal.valueOf(5));
    x.forEach(i -> log.info("x: {}", i));
    y.forEach(i -> log.info("y: {}", i));
    //When
    List<BigDecimal> result = this.computeGradientService.computeGradient(x, y, w, b);

    //Then
    log.info("w: {}, b: {}, result: {}", w, b, result);
    assertThat(result).isNotNull();
  }

}