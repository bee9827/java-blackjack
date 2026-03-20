package domain.state.finished;

import domain.card.Hand;
import domain.score.Score;
import domain.state.MatchResult;
import domain.state.State;
import java.util.function.Function;

public class Bust extends Finished {
    private static final Score BUST_SCORE_BOUND = new Score(21);

    public Bust(Hand hand) {
        super(hand);
    }

    public static boolean isBust(Hand hand) {
        return hand.calculateScore().isHigher(BUST_SCORE_BOUND);
    }

    @Override
    public Function<Integer, Integer> earningRate(MatchResult matchResult) {
        return (n) -> -n;
    }

    @Override
    public MatchResult judgeResult(State dealerState) {
        return MatchResult.LOSE;
    }
}
