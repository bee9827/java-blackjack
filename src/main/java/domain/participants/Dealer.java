package domain.participants;

import domain.strategy.CasinoDealerHitStrategy;
import domain.strategy.HitStrategy;

public class Dealer extends Participant {
    private static final String NAME = "딜러";
    private static final HitStrategy DEFAULT_HIT_STRATEGY = new CasinoDealerHitStrategy();

    public Dealer() {
        super(NAME, DEFAULT_HIT_STRATEGY);
    }

    public Dealer(HitStrategy hitStrategy) {
        super(NAME, hitStrategy);
    }

}
