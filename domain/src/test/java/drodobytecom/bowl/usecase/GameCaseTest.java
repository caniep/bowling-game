package drodobytecom.bowl.usecase;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import drodobytecom.bowl.usecase.model.Score;

import static drodobytecom.bowl.usecase.PlayOneShotCase.Action;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class GameCaseTest extends AbstractTest {

   @Test
   void startedGameInFrame1AndWith10Pins() {
      startGame(frame -> {
         assertThat(frame.number()).isEqualTo(1);
         assertThat(frame.pinsLeft()).isEqualTo(10);
      });
   }

   @Test
   void negativePinsDownInvalid() {
      assertThatThrownBy(() ->
              startGame(f ->
                      playOneShot(-1, mock(Action.class))));
   }

   @Test
   void elevenPinsDownInvalid() {
      assertThatThrownBy(() ->
              startGame(f ->
                      playOneShot(11, mock(Action.class))));
   }

   @Test
   void shot1PinInFirstAttemptThen9PinsLeftInSecondAttempt() {
      Action action = mock(Action.class);

      startGame(f ->
              playOneShot(1, action));

      verify(action).viewPlay(1, 2, 9);
   }

   @Test
   void shotMissedInFirstAttemptRemains10PinsInSecondAttempt() {
      Action action = mock(Action.class);

      startGame(f ->
              playOneShot(0, action));

      verify(action).viewPlay(1, 2, 10);
   }

   @Test
   void strikeInFirstAttemptGoesToNextFrameWith10Pins() {
      Action action = mock(Action.class);

      startGame(f ->
              playOneShot(10, action));

      verify(action).viewPlay(2, 1, 10);
   }

   @Test
   void playFirstFrameWithShots1And2GoesToSecondNewFrame() {
      Action action = mock(Action.class);

      startGame(f ->
              playShots(shots(1, 2), action));

      verify(action).viewPlay(2, 1, 10);
   }

   @Test
   void spareInFirstFrameGoesToSecondFrame() {
      Action action = mock(Action.class);

      startGame(f ->
              playShots(shots(1, 9), action));

      verify(action).viewPlay(2, 1, 10);
   }

   @Test
   void invalidSpareWithExcessPins() {
      assertThatThrownBy(() -> {
         startGame(f ->
                 playShots(shots(3, 9), mock(Action.class)));
      });
   }

   @Test
   void paradoxCatScoreExample1() {
      ComputeScoreCase.Action action = Mockito.mock(ComputeScoreCase.Action.class);
      int[] shots = shots(1, 4, 4, 5, 6, 4, 5, 5, 10, 0, 1, 7, 3, 6, 4, 10, 2, 8, 6);

      startGame(f ->
              computeScore(shots, action));

      ArgumentCaptor<Score> arg = ArgumentCaptor.forClass(Score.class);
      verify(action).show(arg.capture());

      Score score = arg.getValue();
      assertThat(score.frames.get(0).score).isEqualTo(5);
      assertThat(score.frames.get(1).score).isEqualTo(14);
      assertThat(score.frames.get(2).score).isEqualTo(29);
      assertThat(score.frames.get(3).score).isEqualTo(49);
      assertThat(score.frames.get(4).score).isEqualTo(60);
      assertThat(score.frames.get(5).score).isEqualTo(61);
      assertThat(score.frames.get(6).score).isEqualTo(77);
      assertThat(score.frames.get(7).score).isEqualTo(97);
      assertThat(score.frames.get(8).score).isEqualTo(117);
      assertThat(score.frames.get(9).score).isEqualTo(133);
   }

   @Test
   void paradoxCatScoreExample2() {
      ComputeScoreCase.Action action = Mockito.mock(ComputeScoreCase.Action.class);
      int[] shots = shots(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10);

      startGame(f ->
              computeScore(shots, action));

      ArgumentCaptor<Score> arg = ArgumentCaptor.forClass(Score.class);
      verify(action).show(arg.capture());

      Score score = arg.getValue();
      assertThat(score.frames.get(0).score).isEqualTo(30);
      assertThat(score.frames.get(1).score).isEqualTo(60);
      assertThat(score.frames.get(2).score).isEqualTo(90);
      assertThat(score.frames.get(3).score).isEqualTo(120);
      assertThat(score.frames.get(4).score).isEqualTo(150);
      assertThat(score.frames.get(5).score).isEqualTo(180);
      assertThat(score.frames.get(6).score).isEqualTo(210);
      assertThat(score.frames.get(7).score).isEqualTo(240);
      assertThat(score.frames.get(8).score).isEqualTo(270);
      assertThat(score.frames.get(9).score).isEqualTo(300);
   }
}
