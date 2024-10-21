package at.mavila.linearr;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;

class InputValidatorTest {

  @Test
  void validateSizeArrays() {
    //Assert that exception WAS not thrown
    assertDoesNotThrow(() -> InputValidator.validateSizeArrays(List.of(), List.of()));
  }

  @Test
  void validateNullSizeArrays() {
    //Assert that exception WAS not thrown
    assertDoesNotThrow(() -> InputValidator.validateSizeArrays(null, null));
  }

  @Test
  void validateSizeArraysSame() {
    //Assert that exception WAS not thrown
    assertDoesNotThrow(() -> InputValidator.validateSizeArrays(List.of(BigDecimal.TEN), List.of(BigDecimal.TWO)));
  }

  @Test
  void validateSizeArraysDifferent() {
    //Assert that exception WAS not thrown
    assertThatThrownBy(
        () -> InputValidator.validateSizeArrays(List.of(BigDecimal.TEN), List.of(BigDecimal.TWO, BigDecimal.ONE))).isInstanceOf(
        IllegalArgumentException.class);
  }

  //Add two more tests
  //one where the first array is null and the second is not
  //one where the first array is not null and the second is null
  @Test
  void validateSizeArraysNullFirst() {
    //Assert that exception WAS not thrown
    assertDoesNotThrow(() -> InputValidator.validateSizeArrays(null, List.of(BigDecimal.TWO, BigDecimal.ONE)));
  }

  @Test
  void validateSizeArraysNullSecond() {
    //Assert that exception WAS not thrown
    assertDoesNotThrow(
        () -> InputValidator.validateSizeArrays(List.of(BigDecimal.TEN), null));

  }
}