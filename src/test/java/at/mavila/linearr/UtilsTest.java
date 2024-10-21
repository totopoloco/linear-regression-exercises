package at.mavila.linearr;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;

class UtilsTest {

  @Test
  void testGetBigDecimals_case1() {
    assertThat(Utils.getBigDecimals(BigDecimal.valueOf(1), BigDecimal.valueOf(2))).isEqualTo(
        new BigDecimal[] {BigDecimal.valueOf(1), BigDecimal.valueOf(2)});
  }

  @Test
  void testGetBigDecimals_case2() {
    assertThat(Utils.getBigDecimals(BigDecimal.valueOf(2), BigDecimal.valueOf(3))).isEqualTo(
        new BigDecimal[] {BigDecimal.valueOf(2), BigDecimal.valueOf(3)});
  }

  @Test
  void testGetBigDecimals_case3() {
    assertThat(Utils.getBigDecimals(BigDecimal.valueOf(3), BigDecimal.valueOf(4))).isEqualTo(
        new BigDecimal[] {BigDecimal.valueOf(3), BigDecimal.valueOf(4)});
  }

  @Test
  void testGetBigDecimals_case4() {
    assertThat(Utils.getBigDecimals(BigDecimal.valueOf(4), BigDecimal.valueOf(5))).isEqualTo(
        new BigDecimal[] {BigDecimal.valueOf(4), BigDecimal.valueOf(5)});
  }

  @Test
  void testGetBigDecimals_case5() {
    assertThat(Utils.getBigDecimals(BigDecimal.valueOf(5), BigDecimal.valueOf(6))).isEqualTo(
        new BigDecimal[] {BigDecimal.valueOf(5), BigDecimal.valueOf(6)});
  }

  @Test
  void testGetBigDecimals_case6() {
    assertThat(Utils.getBigDecimals(BigDecimal.valueOf(0), BigDecimal.valueOf(0))).isEqualTo(
        new BigDecimal[] {BigDecimal.valueOf(0), BigDecimal.valueOf(0)});
  }

  @Test
  void testValidateList_case1() {
    assertDoesNotThrow(() -> Utils.validateList(List.of(BigDecimal.valueOf(1), BigDecimal.valueOf(2)), "test"));
  }

  @Test
  void testValidateList_case2() {
    assertDoesNotThrow(() -> Utils.validateList(List.of(BigDecimal.valueOf(2), BigDecimal.valueOf(3)), "test"));
  }

  @Test
  void testValidateList_case3() {
    assertDoesNotThrow(() -> Utils.validateList(List.of(BigDecimal.valueOf(3), BigDecimal.valueOf(4)), "test"));
  }

  @Test
  void testValidateList_case4() {
    assertDoesNotThrow(() -> Utils.validateList(List.of(BigDecimal.valueOf(4), BigDecimal.valueOf(5)), "test"));
  }

  @Test
  void testValidateList_case5() {
    assertDoesNotThrow(() -> Utils.validateList(List.of(BigDecimal.valueOf(5), BigDecimal.valueOf(6)), "test"));
  }

  @Test
  void testValidateList_case6() {
    assertDoesNotThrow(() -> Utils.validateList(List.of(BigDecimal.valueOf(0), BigDecimal.valueOf(0)), "test"));
  }

  @Test
  void testValidateList_nullList() {
    assertThatThrownBy(() -> Utils.validateList(null, "test")).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void testValidateList_emptyList() {
    assertThatThrownBy(() -> Utils.validateList(List.of(), "test")).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void testValidateNotNull_case1() {
    assertDoesNotThrow(() -> Utils.validateNotNull(BigDecimal.valueOf(1), "test"));
  }

  @Test
  void testValidateNotNull_case2() {
    assertDoesNotThrow(() -> Utils.validateNotNull(BigDecimal.valueOf(2), "test"));
  }

  @Test
  void testValidateNull_case3() {
    assertThatThrownBy(() -> Utils.validateNotNull(null, "test")).isInstanceOf(IllegalArgumentException.class);
  }
}