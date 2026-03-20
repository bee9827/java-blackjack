package dto;

import domain.bet.Betting;
import domain.participants.Participant;
import domain.participants.Player;
import domain.strategy.HitStrategy;

public record PlayerCreateDto(
        String name,
        Betting betting
) {

    public Participant toDefaultStrategyPlayer(HitStrategy hitStrategy) {
        return new Player(name, betting, hitStrategy);
    }
}
