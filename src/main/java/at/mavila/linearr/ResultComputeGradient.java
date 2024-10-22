package at.mavila.linearr;

import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.With;

@With
@Builder
public record ResultComputeGradient(BigDecimal djDb, List<BigDecimal> djDw) {
}
