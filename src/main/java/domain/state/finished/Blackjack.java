package domain.state.finished;

import domain.card.Hand;
import domain.score.Score;
import domain.state.MatchResult;
import domain.state.State;
import java.util.function.Function;

public class Blackjack extends Finished {
    private static final int HAND_SIZE = 2;
    private static final Score BLACKJACK_SCORE = new Score(21);
    private static final Function<Integer, Integer> RATE = (n) -> (n) * 15 / 10;

    public Blackjack(Hand hand) {
        super(hand);
    }

    public static boolean isBlackJack(Hand hand) {
        return hand.getSize() == HAND_SIZE && hand.calculateScore().isEqual(BLACKJACK_SCORE);
    }

    @Override
    public Function<Integer, Integer> earningRate(MatchResult matchResult) {
        if (MatchResult.LOSE.equals(matchResult)) {
            throw new IllegalStateException("Blackjack은 질수 없습니다.");
        }
        if (MatchResult.DRAW.equals(matchResult)) {
            return (n) -> 0;
        }
        return RATE;
    }

    @Override
    public MatchResult judgeResult(State dealerState) {
        if (dealerState instanceof Blackjack) {
            return MatchResult.DRAW;
        }
        return MatchResult.WIN;
    }
}
