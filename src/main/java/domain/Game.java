package domain;

import domain.card.Hand;
import domain.deck.Deck;
import domain.deck.maker.DeckMaker;
import domain.participants.Dealer;
import domain.participants.Participant;
import domain.participants.Player;
import domain.state.generator.FinishedStateGenerator;
import domain.strategy.HitStrategy;
import dto.PlayerCreateDto;
import java.util.List;

public class Game {
    private final Deck deck;
    private final Participant dealer;
    private final List<Participant> players;
    private final List<FinishedStateGenerator> finishedStateGenerators;

    private Game(Deck deck,
                 Participant dealer,
                 List<Participant> players,
                 List<FinishedStateGenerator> finishedStateGenerators) {
        this.deck = deck;
        this.dealer = dealer;
        this.players = players;
        this.finishedStateGenerators = finishedStateGenerators;
    }

    public static Game makeGame(DeckMaker deckMaker,
                                HitStrategy dealerStrategy,
                                HitStrategy playerHitStrategy,
                                List<PlayerCreateDto> playersDto,
                                List<FinishedStateGenerator> generators) {
        Deck deck = Deck.createFromDeckMaker(deckMaker);
        Participant dealer = new Dealer(dealerStrategy);
        List<Participant> players = playersDto.stream()
                .map(playerCreateDto ->
                        (Participant) new Player(playerCreateDto.name(), playerCreateDto.betting(), playerHitStrategy))
                .toList();
        return new Game(deck, dealer, players, generators);
    }

    public void start() {
        dealer.startState(finishedStateGenerators, Hand.createFromDeck(deck));
        players.forEach(player -> player.startState(finishedStateGenerators, Hand.createFromDeck(deck)));
    }

    public Participant getDealer() {
        return dealer;
    }

    public List<Participant> getPlayers() {
        return players;
    }

    public Deck getDeck() {
        return deck;
    }
}
