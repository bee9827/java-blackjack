package domain.strategy;

import domain.score.Score;

public interface HitStrategy {
    boolean canHit(Score score);
}
