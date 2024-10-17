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