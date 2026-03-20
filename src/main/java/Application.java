import controller.BlackjackController;
import domain.deck.maker.DeckMaker;
import domain.deck.maker.OneRandomDeckMaker;
import domain.state.generator.BlackjackGenerator;
import domain.state.generator.BustGenerator;
import domain.state.generator.FinishedStateGenerator;
import domain.strategy.CasinoDealerHitStrategy;
import domain.strategy.HitStrategy;
import domain.strategy.UntilBustHitStrategy;
import java.util.List;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView(System.in);
        OutputView outputView = new OutputView(System.out);
        DeckMaker deckMaker = new OneRandomDeckMaker();
        BlackjackController controller = new BlackjackController(inputView, outputView);
        HitStrategy playerHitStrategy = new UntilBustHitStrategy();
        HitStrategy dealerHitStrategy = new CasinoDealerHitStrategy();
        List<FinishedStateGenerator> finishedStateGenerators = List.of(new BustGenerator(), new BlackjackGenerator());

        controller.start(deckMaker, dealerHitStrategy, playerHitStrategy, finishedStateGenerators);
    }
}
