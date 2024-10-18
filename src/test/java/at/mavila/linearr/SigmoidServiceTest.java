package at.mavila.linearr;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
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
class SigmoidServiceTest {


  @Autowired
  private SigmoidService sigmoidService;

  @Property
  void sigmoid(@ForAll @BigRange(min = "1", max = "10") BigDecimal z) {
    log.info("z: {}", z);
    BigDecimal sigmoid = this.sigmoidService.sigmoid(z);
    assertThat(sigmoid).isNotNull().isBetween(BigDecimal.ZERO, BigDecimal.ONE);
    log.info("sigmoid: {}", sigmoid);
  }

  //Test when z is null
  @Test
  void sigmoidNull() {
    assertThatThrownBy(() -> this.sigmoidService.sigmoid(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("z is null");
  }

  @Test
  void sigmoidZero() {
    BigDecimal z = BigDecimal.ZERO;
    BigDecimal sigmoid = this.sigmoidService.sigmoid(z);
    assertThat(sigmoid).isNotNull().isEqualTo(BigDecimal.valueOf(0.5));
  }

  @Test
  void sigmoidOne() {
    BigDecimal z = BigDecimal.ONE;
    BigDecimal sigmoid = this.sigmoidService.sigmoid(z);
    assertThat(sigmoid).isNotNull().isBetween(BigDecimal.valueOf(0.7), BigDecimal.valueOf(0.8));
  }

  @Test
  void sigmoidTrailingZeros() {
    BigDecimal z = new BigDecimal("0.00000000004000000000000000001");
    BigDecimal sigmoid = this.sigmoidService.sigmoid(z);
    assertThat(sigmoid).isNotNull().isBetween(BigDecimal.ZERO, BigDecimal.ONE);
  }

  @Test
  void sigmoidFractionTrailingZeros() {
    BigDecimal z = new BigDecimal("0.00000000000000000000000000001");
    BigDecimal sigmoid = this.sigmoidService.sigmoid(z);
    assertThat(sigmoid).isNotNull().isBetween(BigDecimal.ZERO, BigDecimal.ONE);
  }

  @Test
  void sigmoidPrecision() {
    BigDecimal z = new BigDecimal("1.2345678901234567890123456789");
    BigDecimal sigmoid = this.sigmoidService.sigmoid(z);
    assertThat(sigmoid).isNotNull();
    assertThat(sigmoid.precision()).isEqualTo(20); // Ensure the precision is exactly 20
  }

  @Test
  void sigmoidWithTrailingZerosInInput() {
    // Given
    BigDecimal z = new BigDecimal("1.0000000000000000000000000000");

    // When
    BigDecimal sigmoid = this.sigmoidService.sigmoid(z);

    // Then
    assertThat(sigmoid).isNotNull();
    assertThat(sigmoid.stripTrailingZeros()).isEqualTo(sigmoid); // Ensure trailing zeros are stripped
    assertThat(sigmoid.scale()).isLessThanOrEqualTo(20); // Ensure the scale is correct
  }

  @Test
  void sigmoidWithTrailingZerosInInputII() {
    // Given
    BigDecimal z = new BigDecimal("1.0000000000320000000000000000001");

    // When
    BigDecimal sigmoid = this.sigmoidService.sigmoid(z);

    // Then
    assertThat(sigmoid).isNotNull();
    assertThat(sigmoid.stripTrailingZeros()).isEqualTo(sigmoid); // Ensure trailing zeros are stripped
    assertThat(sigmoid.scale()).isLessThanOrEqualTo(20); // Ensure the scale is correct
  }


  @Test
  void sigmoidWhen50ThenTrailingZeros() {
    // Given
    BigDecimal z = new BigDecimal("50");

    // When
    BigDecimal sigmoid = this.sigmoidService.sigmoid(z);

    // Then
    assertThat(sigmoid).isNotNull();
    assertThat(sigmoid.stripTrailingZeros()).isEqualTo(sigmoid); // Ensure trailing zeros are stripped
    assertThat(sigmoid.scale()).isLessThanOrEqualTo(20); // Ensure the scale is correct
    assertThat(sigmoid).isEqualTo(BigDecimal.ONE);
  }

  @Test
  void sigmoidMinusOne() {
    BigDecimal z = BigDecimal.ONE.negate();
    BigDecimal sigmoid = this.sigmoidService.sigmoid(z);
    assertThat(sigmoid).isNotNull().isBetween(BigDecimal.valueOf(0.2), BigDecimal.valueOf(0.3));
  }
}