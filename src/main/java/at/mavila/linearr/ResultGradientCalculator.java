package at.mavila.linearr;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.With;

@With
@Builder
public record ResultGradientCalculator(BigDecimal wValid, BigDecimal bValid) {
}
