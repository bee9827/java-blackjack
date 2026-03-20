package domain.state;

import static org.assertj.core.api.Assertions.assertThat;

import domain.TestFixture;
import domain.card.vo.Rank;
import domain.state.finished.Blackjack;
import domain.state.finished.Bust;
import domain.state.finished.Finished;
import domain.state.finished.Stay;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class FinishedTest {

    public static Stream<Arguments> earningRate() {
        return Stream.of(
                Arguments.of(new Stay(TestFixture.createHandByRank(List.of(Rank.KING, Rank.EIGHT))), MatchResult.WIN,
                        10000,
                        10000),
                Arguments.of(new Stay(TestFixture.createHandByRank(List.of(Rank.TWO, Rank.EIGHT))), MatchResult.DRAW,
                        10000,
                        0),
                Arguments.of(new Stay(TestFixture.createHandByRank(List.of(Rank.TWO, Rank.EIGHT))), MatchResult.LOSE,
                        10000,
                        -10000),

                Arguments.of(new Bust(TestFixture.createHandByRank(List.of(Rank.TWO, Rank.EIGHT))), MatchResult.WIN,
                        10000,
                        -10000),
                Arguments.of(new Bust(TestFixture.createHandByRank(List.of(Rank.TWO, Rank.EIGHT))), MatchResult.DRAW,
                        10000,
                        -10000),
                Arguments.of(new Bust(TestFixture.createHandByRank(List.of(Rank.TWO, Rank.EIGHT))), MatchResult.LOSE,
                        10000,
                        -10000),

                Arguments.of(new Blackjack(TestFixture.createHandByRank(List.of(Rank.TWO, Rank.EIGHT))),
                        MatchResult.WIN,
                        10000, 15000),
                Arguments.of(new Blackjack(TestFixture.createHandByRank(List.of(Rank.TWO, Rank.EIGHT))),
                        MatchResult.DRAW,
                        10000, 0)
        );
    }

    public static Stream<Arguments> judgeResult() {
        return Stream.of(
                Arguments.of(
                        new Blackjack(TestFixture.createHandByRank(List.of(Rank.KING, Rank.ACE))),
                        new Stay(TestFixture.createHandByRank(List.of(Rank.KING, Rank.KING))),
                        MatchResult.WIN),
                Arguments.of(
                        new Blackjack(TestFixture.createHandByRank(List.of(Rank.KING, Rank.ACE))),
                        new Bust(TestFixture.createHandByRank(List.of(Rank.KING, Rank.KING))),
                        MatchResult.WIN),
                Arguments.of(
                        new Blackjack(TestFixture.createHandByRank(List.of(Rank.KING, Rank.ACE))),
                        new Blackjack(TestFixture.createHandByRank(List.of(Rank.KING, Rank.ACE))),
                        MatchResult.DRAW),

                Arguments.of(
                        new Stay(TestFixture.createHandByRank(List.of(Rank.KING, Rank.KING))),
                        new Stay(TestFixture.createHandByRank(List.of(Rank.KING, Rank.NINE))),
                        MatchResult.WIN),
                Arguments.of(
                        new Stay(TestFixture.createHandByRank(List.of(Rank.KING, Rank.KING))),
                        new Stay(TestFixture.createHandByRank(List.of(Rank.KING, Rank.KING))),
                        MatchResult.DRAW),
                Arguments.of(
                        new Stay(TestFixture.createHandByRank(List.of(Rank.KING, Rank.NINE))),
                        new Stay(TestFixture.createHandByRank(List.of(Rank.KING, Rank.KING))),
                        MatchResult.LOSE),
                Arguments.of(
                        new Stay(TestFixture.createHandByRank(List.of(Rank.KING, Rank.ACE))),
                        new Bust(TestFixture.createHandByRank(List.of(Rank.KING, Rank.KING))),
                        MatchResult.WIN),
                Arguments.of(
                        new Stay(TestFixture.createHandByRank(List.of(Rank.KING, Rank.KING))),
                        new Blackjack(TestFixture.createHandByRank(List.of(Rank.KING, Rank.ACE))),
                        MatchResult.LOSE),

                Arguments.of(
                        new Blackjack(TestFixture.createHandByRank(List.of(Rank.KING, Rank.ACE))),
                        new Stay(TestFixture.createHandByRank(List.of(Rank.KING, Rank.KING))),
                        MatchResult.WIN),
                Arguments.of(
                        new Blackjack(TestFixture.createHandByRank(List.of(Rank.KING, Rank.ACE))),
                        new Bust(TestFixture.createHandByRank(List.of(Rank.KING, Rank.KING))),
                        MatchResult.WIN),
                Arguments.of(
                        new Blackjack(TestFixture.createHandByRank(List.of(Rank.KING, Rank.ACE))),
                        new Blackjack(TestFixture.createHandByRank(List.of(Rank.KING, Rank.ACE))),
                        MatchResult.DRAW)
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("earningRate(Result, Integer): 결과에 따라 수익률을 반환한다.")
    void earningRate(Finished state, MatchResult matchResult, Integer betCost, Integer expected) {
        Integer earnCost = state.earningRate(matchResult).apply(betCost);
        assertThat(earnCost).isEqualTo(expected);
    }

    @ParameterizedTest
    @DisplayName("getResult(): 승패를 반환한다.")
    @MethodSource
    void judgeResult(Finished playerState, State dealerState, MatchResult expected) {
        assertThat(playerState.judgeResult(dealerState)).isEqualTo(expected);
    }
}
