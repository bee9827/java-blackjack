package domain.state.finished;

import domain.card.Hand;
import domain.state.MatchResult;
import domain.state.State;
import java.util.function.Function;

public class Stay extends Finished {
    public Stay(Hand hand) {
        super(hand);
    }

    @Override
    public Function<Integer, Integer> earningRate(MatchResult matchResult) {
        if (MatchResult.WIN.equals(matchResult)) {
            return (n) -> n;
        }
        if (MatchResult.DRAW.equals(matchResult)) {
            return (n) -> 0;
        }
        if (MatchResult.LOSE.equals(matchResult)) {
            return (n) -> -n;
        }
        throw new IllegalStateException("Stay의 earningRate 잘못된 접근입니다.");
    }

    @Override
    public MatchResult judgeResult(State dealerState) {
        if (dealerState instanceof Bust
                || this.getScore().isHigher(dealerState.getScore())) {
            return MatchResult.WIN;
        }
        if (dealerState.getScore().isHigher(this.getScore())) {
            return MatchResult.LOSE;
        }
        return MatchResult.DRAW;
    }

}

