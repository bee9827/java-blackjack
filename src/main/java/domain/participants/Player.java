package domain.participants;

import domain.bet.Betting;
import domain.strategy.HitStrategy;
import domain.strategy.UntilBustHitStrategy;

public class Player extends Participant {
    private static final HitStrategy DEFAULT_HIT_STRATEGY = new UntilBustHitStrategy();

    protected final Betting betting;

    public Player(String name, Betting betting, HitStrategy hitStrategy) {
        super(name, hitStrategy);
        this.betting = betting;
    }

    public Player(String name, Betting betting) {
        super(name, DEFAULT_HIT_STRATEGY);
        this.betting = betting;
    }

    public Integer getProfit(Participant dealer) {
        return state.getProfit(dealer.getState(), betting.amount());
    }
}
