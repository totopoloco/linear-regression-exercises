package at.mavila.linearr;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.With;

@With
@Builder
public record Result(BigDecimal wValid, BigDecimal bValid) {
}
