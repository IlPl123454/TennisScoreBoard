import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.plenkovii.dto.MatchAppDto;
import org.plenkovii.dto.MatchScoreAppDto;
import org.plenkovii.entity.Player;
import org.plenkovii.service.MatchScoreCalculationService;

public class ScoreCalculationTest {
    private MatchScoreCalculationService matchScoreCalculationService;
    private MatchAppDto matchAppDto;

    private final String PLAYER1_WINS_POINT = "1";
    private final String PLAYER2_WINS_POINT = "2";

    @BeforeEach
    void setUp() {
        matchScoreCalculationService = new MatchScoreCalculationService();
        matchAppDto = new MatchAppDto(new Player(), new Player());
    }


    @ParameterizedTest
    @DisplayName("Тест добавления выигранного очка первому игроку (от 0 до 40)")
    @CsvSource({
            "LOVE, FIFTEEN",
            "FIFTEEN, THIRTY",
            "THIRTY, FORTY"
    })
    void ShouldUpdatePlayer1Score(MatchScoreAppDto.Point current, MatchScoreAppDto.Point expected) {
        matchAppDto.getScore().getPlayer1Score().setPoint(current);
        matchScoreCalculationService.addPointToWinner(matchAppDto, PLAYER1_WINS_POINT);

        Assertions.assertEquals(expected, matchAppDto.getScore().getPlayer1Score().getPoint());
        Assertions.assertEquals(MatchScoreAppDto.Point.LOVE, matchAppDto.getScore().getPlayer2Score().getPoint());
    }

    @ParameterizedTest
    @DisplayName("Тест добавления выигранного очка второму игроку (от 0 до 40)")
    @CsvSource({
            "LOVE, FIFTEEN",
            "FIFTEEN, THIRTY",
            "THIRTY, FORTY"
    })
    void ShouldUpdatePlayer2Score(MatchScoreAppDto.Point current, MatchScoreAppDto.Point expected) {
        matchAppDto.getScore().getPlayer2Score().setPoint(current);

        matchScoreCalculationService.addPointToWinner(matchAppDto, PLAYER2_WINS_POINT);

        Assertions.assertEquals(expected, matchAppDto.getScore().getPlayer2Score().getPoint());
        Assertions.assertEquals(MatchScoreAppDto.Point.LOVE, matchAppDto.getScore().getPlayer1Score().getPoint());
    }

    @ParameterizedTest
    @CsvSource({"LOVE", "FIFTEEN", "THIRTY"})
    @DisplayName("Когда у игрока 1 40 очков, он выигравыет розыгрыш и должен взять гейм")
    void ShouldUpdatePlayer1Game(MatchScoreAppDto.Point player2Point) {
        matchAppDto.getScore().getPlayer1Score().setPoint(MatchScoreAppDto.Point.FORTY);
        matchAppDto.getScore().getPlayer2Score().setPoint(player2Point);

        matchScoreCalculationService.addPointToWinner(matchAppDto, PLAYER1_WINS_POINT);

        Assertions.assertEquals(MatchScoreAppDto.Game.ONE, matchAppDto.getScore().getPlayer1Score().getGame());

        Assertions.assertEquals(MatchScoreAppDto.Point.LOVE, matchAppDto.getScore().getPlayer1Score().getPoint());
        Assertions.assertEquals(MatchScoreAppDto.Point.LOVE, matchAppDto.getScore().getPlayer2Score().getPoint());
    }

    @ParameterizedTest
    @CsvSource({"LOVE", "FIFTEEN", "THIRTY"})
    @DisplayName("Когда у игрока 2 40 очков, он выигравыет розыгрыш и должен взять гейм")
    void ShouldUpdatePlayer2Game(MatchScoreAppDto.Point player1Point) {
        matchAppDto.getScore().getPlayer1Score().setPoint(player1Point);
        matchAppDto.getScore().getPlayer2Score().setPoint(MatchScoreAppDto.Point.FORTY);

        matchScoreCalculationService.addPointToWinner(matchAppDto, PLAYER2_WINS_POINT);

        Assertions.assertEquals(MatchScoreAppDto.Game.ONE, matchAppDto.getScore().getPlayer2Score().getGame());

        Assertions.assertEquals(MatchScoreAppDto.Point.LOVE, matchAppDto.getScore().getPlayer1Score().getPoint());
        Assertions.assertEquals(MatchScoreAppDto.Point.LOVE, matchAppDto.getScore().getPlayer2Score().getPoint());
    }

    @Test
    @DisplayName("При счете 40-40 гейм не заканчивается, состояние ADV игроку 1")
    void ShouldUpdatePlayer1ScoreToAdvantage() {

        matchAppDto.getScore().getPlayer1Score().setPoint(MatchScoreAppDto.Point.FORTY);
        matchAppDto.getScore().getPlayer2Score().setPoint(MatchScoreAppDto.Point.FORTY);

        matchScoreCalculationService.addPointToWinner(matchAppDto, PLAYER1_WINS_POINT);

        Assertions.assertEquals(MatchScoreAppDto.Point.ADVANTAGE, matchAppDto.getScore().getPlayer1Score().getPoint());
        Assertions.assertEquals(MatchScoreAppDto.Point.BLANK, matchAppDto.getScore().getPlayer2Score().getPoint());
    }

    @Test
    @DisplayName("При счете 40-40 гейм не заканчивается, состояние ADV игроку 2")
    void ShouldUpdatePlayer2ScoreToAdvantage() {
        matchAppDto.getScore().getPlayer1Score().setPoint(MatchScoreAppDto.Point.FORTY);
        matchAppDto.getScore().getPlayer2Score().setPoint(MatchScoreAppDto.Point.FORTY);

        matchScoreCalculationService.addPointToWinner(matchAppDto, PLAYER2_WINS_POINT);

        Assertions.assertEquals(MatchScoreAppDto.Point.ADVANTAGE, matchAppDto.getScore().getPlayer2Score().getPoint());
        Assertions.assertEquals(MatchScoreAppDto.Point.BLANK, matchAppDto.getScore().getPlayer1Score().getPoint());
    }

    @Test
    @DisplayName("Когда у игрока 1 ADV и он выигрывает очко, он забирает гейм")
    void Player1ShouldWinGameWhenHaveAdvantage() {
        matchAppDto.getScore().getPlayer1Score().setPoint(MatchScoreAppDto.Point.ADVANTAGE);
        matchAppDto.getScore().getPlayer2Score().setPoint(MatchScoreAppDto.Point.BLANK);

        matchScoreCalculationService.addPointToWinner(matchAppDto, PLAYER1_WINS_POINT);

        Assertions.assertEquals(MatchScoreAppDto.Game.ONE, matchAppDto.getScore().getPlayer1Score().getGame());
    }

    @Test
    @DisplayName("Когда у игрока 1 ADV и он проигрывает очко, счет становится равным")
    void ShouldGetDeuceWhenLoseAdvantage() {
        matchAppDto.getScore().getPlayer1Score().setPoint(MatchScoreAppDto.Point.ADVANTAGE);
        matchAppDto.getScore().getPlayer2Score().setPoint(MatchScoreAppDto.Point.BLANK);

        matchScoreCalculationService.addPointToWinner(matchAppDto, PLAYER2_WINS_POINT);

        Assertions.assertEquals(MatchScoreAppDto.Point.DEUCE, matchAppDto.getScore().getPlayer1Score().getPoint());
        Assertions.assertEquals(MatchScoreAppDto.Point.DEUCE, matchAppDto.getScore().getPlayer2Score().getPoint());
    }

    @Test
    @DisplayName("При выигрыше 6 геймов, выигрывается сет")
    void Player1ShouldWinSetWhenWin6Games() {
        matchAppDto.getScore().getPlayer1Score().setGame(MatchScoreAppDto.Game.FIVE);
        matchAppDto.getScore().getPlayer1Score().setPoint(MatchScoreAppDto.Point.FORTY);

        matchScoreCalculationService.addPointToWinner(matchAppDto, PLAYER1_WINS_POINT);

        Assertions.assertEquals(MatchScoreAppDto.Set.ONE, matchAppDto.getScore().getPlayer1Score().getSet());
    }

    @Test
    @DisplayName("При выигрыши 6-го гейма, когда счет 5-5, сет не заканчивается, еще гейм")
    void ShouldWinSevenGameIfSecondPlayerWonFiveGames() {
        matchAppDto.getScore().getPlayer1Score().setGame(MatchScoreAppDto.Game.FIVE);
        matchAppDto.getScore().getPlayer2Score().setGame(MatchScoreAppDto.Game.FIVE);

        matchAppDto.getScore().getPlayer1Score().setPoint(MatchScoreAppDto.Point.FORTY);

        matchScoreCalculationService.addPointToWinner(matchAppDto, PLAYER1_WINS_POINT);

        Assertions.assertEquals(MatchScoreAppDto.Game.SIX, matchAppDto.getScore().getPlayer1Score().getGame());
        Assertions.assertEquals(MatchScoreAppDto.Set.ZERO, matchAppDto.getScore().getPlayer1Score().getSet());
    }


    @Test
    @DisplayName("При счете 6-6 в геймах начинается тайбрейк, вместо обычного гейма")
    void ShouldStartTieBreak() {
        matchAppDto.getScore().getPlayer1Score().setGame(MatchScoreAppDto.Game.SIX);
        matchAppDto.getScore().getPlayer2Score().setGame(MatchScoreAppDto.Game.SIX);

        matchScoreCalculationService.addPointToWinner(matchAppDto, PLAYER1_WINS_POINT);

        Assertions.assertTrue(matchScoreCalculationService.isTieBreak());
    }

    @Test
    @DisplayName("Надо набрать 7 очков в тайбрейке чтобы выиграть сет")
    void ShouldWinTieBreakWhenHave7Points() {
        matchAppDto.getScore().getPlayer1Score().setGame(MatchScoreAppDto.Game.SIX);
        matchAppDto.getScore().getPlayer2Score().setGame(MatchScoreAppDto.Game.SIX);

        matchAppDto.getScore().getPlayer1Score().setTieBreakPoint(6);

        matchScoreCalculationService.addPointToWinner(matchAppDto, PLAYER1_WINS_POINT);

        Assertions.assertEquals(MatchScoreAppDto.Set.ONE, matchAppDto.getScore().getPlayer1Score().getSet());
    }

    @ParameterizedTest
    @DisplayName("Должна быть разница в 2 очка для выигрыша в тай-брейке")
    @CsvSource({
            "6, 5",
            "11, 10",
            "99, 98"
    })
    void ShouldHave2TieBreakPointAdvantageToWinTieBreak(int Player1TieBreakPoint, int Player2TieBreakPoint) {
        matchAppDto.getScore().getPlayer1Score().setGame(MatchScoreAppDto.Game.SIX);
        matchAppDto.getScore().getPlayer2Score().setGame(MatchScoreAppDto.Game.SIX);

        matchAppDto.getScore().getPlayer1Score().setTieBreakPoint(Player1TieBreakPoint);
        matchAppDto.getScore().getPlayer2Score().setTieBreakPoint(Player2TieBreakPoint);

        matchScoreCalculationService.addPointToWinner(matchAppDto, PLAYER1_WINS_POINT);

        Assertions.assertEquals(MatchScoreAppDto.Set.ONE, matchAppDto.getScore().getPlayer1Score().getSet());
    }

}