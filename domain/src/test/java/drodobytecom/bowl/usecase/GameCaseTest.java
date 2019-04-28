package drodobytecom.bowl.usecase;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;

import java.util.List;

import drodobytecom.bowl.entity.Frame;
import drodobytecom.bowl.entity.Score;

import static drodobytecom.bowl.usecase.PlayOneShotCase.Action;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

class GameCaseTest extends AbstractTest {

   @Test
   void startedGameInFrame1AndWith10Pins() {
      start((frame, attempt, pinsLeft) -> {
         assertThat(frame).isEqualTo(1);
         assertThat(attempt).isEqualTo(1);
         assertThat(pinsLeft).isEqualTo(10);
      });
   }

   @Test
   void negativePinsDownInvalid() {
      assertThatThrownBy(() ->
              play(shots(-1), mock(Action.class)));
   }

   @Test
   void elevenPinsDownInvalid() {
      assertThatThrownBy(() ->
              play(shots(11), mock(Action.class)));
   }

   @Test
   void shot1PinInFirstAttemptThen9PinsLeftInSecondAttempt() {
      Action action = mock(Action.class);

      play(shots(1), action);

      verify(action, only()).viewPlay(1, 2, 9);
   }

   @Test
   void shotMissedInFirstAttemptRemains10PinsInSecondAttempt() {
      Action action = mock(Action.class);

      play(shots(0), action);

      verify(action, only()).viewPlay(1, 2, 10);
   }

   @Test
   void strikeInFirstAttemptGoesToNextFrameWith10Pins() {
      Action action = mock(Action.class);

      play(shots(10), action);

      verify(action, only()).viewPlay(2, 1, 10);
   }

   @Test
   void playFirstFrameWithShots1And2GoesToSecondNewFrame() {
      Action action = mock(Action.class);

      play(shots(1, 2), action);

      verify(action, atLeastOnce()).viewPlay(2, 1, 10);
      verify(action, never()).viewScore(any());
   }

   @Test
   void spareInFirstFrameGoesToSecondFrame() {
      Action action = mock(Action.class);

      play(shots(1, 9), action);

      verify(action, atLeastOnce()).viewPlay(2, 1, 10);
      verify(action, never()).viewScore(any());
   }

   @Test
   void spareWithExcessPinsInvalid() {
      assertThatThrownBy(() ->
              play(shots(3, 9), mock(Action.class)));
   }

   @Test
   void tooMuchShotsInvalid() {
      assertThatThrownBy(() ->
              play(shots(10, 10, 10, 10, 10, 10, 10, 10, 10, 1, 1, 3), mock(Action.class)));
   }

   @Test
   void paradoxCatScoreExample1() {
      Action action = mock(Action.class);
      int[] shots = shots(1, 4, 4, 5, 6, 4, 5, 5, 10, 0, 1, 7, 3, 6, 4, 10, 2, 8, 6);

      play(shots, action);

      ArgumentCaptor<Score> arg = ArgumentCaptor.forClass(Score.class);
      InOrder inOrder = inOrder(action);
      inOrder.verify(action).viewPlay(1, 2, 9);
      inOrder.verify(action).viewPlay(2, 1, 10);
      inOrder.verify(action).viewPlay(2, 2, 6);
      inOrder.verify(action).viewPlay(3, 1, 10);
      inOrder.verify(action).viewPlay(3, 2, 4);
      inOrder.verify(action).viewPlay(4, 1, 10);
      inOrder.verify(action).viewPlay(4, 2, 5);
      inOrder.verify(action).viewPlay(5, 1, 10);
      inOrder.verify(action).viewPlay(6, 1, 10);
      inOrder.verify(action).viewPlay(6, 2, 10);
      inOrder.verify(action).viewPlay(7, 1, 10);
      inOrder.verify(action).viewPlay(7, 2, 3);
      inOrder.verify(action).viewPlay(8, 1, 10);
      inOrder.verify(action).viewPlay(8, 2, 4);
      inOrder.verify(action).viewPlay(9, 1, 10);
      inOrder.verify(action).viewPlay(10, 1, 10);
      inOrder.verify(action).viewPlay(10, 2, 8);
      inOrder.verify(action).viewPlay(10, 3, 10);
      inOrder.verify(action).viewScore(arg.capture());

      List<Frame> frames = arg.getValue().frames();
      assertThat(frames.get(0).score()).isEqualTo(5);
      assertThat(frames.get(1).score()).isEqualTo(14);
      assertThat(frames.get(2).score()).isEqualTo(29);
      assertThat(frames.get(3).score()).isEqualTo(49);
      assertThat(frames.get(4).score()).isEqualTo(60);
      assertThat(frames.get(5).score()).isEqualTo(61);
      assertThat(frames.get(6).score()).isEqualTo(77);
      assertThat(frames.get(7).score()).isEqualTo(97);
      assertThat(frames.get(8).score()).isEqualTo(117);
      assertThat(frames.get(9).score()).isEqualTo(133);
   }

   @Test
   void paradoxCatScoreExample2() {
      Action action = mock(Action.class);
      int[] shots = shots(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10);

      play(shots, action);

      ArgumentCaptor<Score> arg = ArgumentCaptor.forClass(Score.class);
      InOrder inOrder = inOrder(action);
      inOrder.verify(action).viewPlay(2, 1, 10);
      inOrder.verify(action).viewPlay(3, 1, 10);
      inOrder.verify(action).viewPlay(4, 1, 10);
      inOrder.verify(action).viewPlay(5, 1, 10);
      inOrder.verify(action).viewPlay(6, 1, 10);
      inOrder.verify(action).viewPlay(7, 1, 10);
      inOrder.verify(action).viewPlay(8, 1, 10);
      inOrder.verify(action).viewPlay(9, 1, 10);
      inOrder.verify(action).viewPlay(10, 1, 10);
      inOrder.verify(action).viewPlay(10, 2, 10);
      inOrder.verify(action).viewPlay(10, 3, 10);
      inOrder.verify(action).viewScore(arg.capture());

      List<Frame> frames = arg.getValue().frames();
      assertThat(frames.get(0).score()).isEqualTo(30);
      assertThat(frames.get(1).score()).isEqualTo(60);
      assertThat(frames.get(2).score()).isEqualTo(90);
      assertThat(frames.get(3).score()).isEqualTo(120);
      assertThat(frames.get(4).score()).isEqualTo(150);
      assertThat(frames.get(5).score()).isEqualTo(180);
      assertThat(frames.get(6).score()).isEqualTo(210);
      assertThat(frames.get(7).score()).isEqualTo(240);
      assertThat(frames.get(8).score()).isEqualTo(270);
      assertThat(frames.get(9).score()).isEqualTo(300);
   }
}
