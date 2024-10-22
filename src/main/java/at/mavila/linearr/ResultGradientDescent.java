package at.mavila.linearr;

import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.With;

@With
@Builder
public record ResultGradientDescent(List<BigDecimal> w, BigDecimal b, List<BigDecimal> costHistory) {
}
