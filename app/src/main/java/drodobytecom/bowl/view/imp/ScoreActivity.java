package drodobytecom.bowl.view.imp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import drodobytecom.bowl.App;
import drodobytecom.bowl.R;
import drodobytecom.bowl.presenter.ScorePresenter;
import drodobytecom.bowl.usecase.model.Score;
import drodobytecom.bowl.view.ScoreView;

import static android.view.View.VISIBLE;

public class ScoreActivity extends AppCompatActivity implements ScoreView {

   private Listener listener;
   private Drawable spare;
   private Drawable strike;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_score);

      spare = getResources().getDrawable(R.drawable.spare_border);
      strike = getResources().getDrawable(R.drawable.strike_border);

      listener = new ScorePresenter(((App) getApplication()).service());
      listener.shown(this);
   }

   @Override
   public void show(Score score) {
      getSupportActionBar().setTitle(R.string.title_score);
      ViewGroup view = findViewById(R.id.score);
      for (int frame = 0; frame < view.getChildCount(); frame++)
         setFrame(score, view, frame);
   }

   private void setFrame(Score score, ViewGroup view, int f) {
      View frameView = view.getChildAt(f);
      TextView firstAttempt = frameView.findViewById(R.id.firstAttempt);
      TextView secondAttempt = frameView.findViewById(R.id.secondAttempt);
      TextView thirdAttempt = frameView.findViewById(R.id.thirdAttempt);
      TextView frameScore = frameView.findViewById(R.id.frame_score);

      Score.Frame frame = score.frames.get(f);

      boolean isStrike = frame.pinsDownFirstAttempt == 10;
      boolean isSpare = frame.pinsDownSecondAttempt + frame.pinsDownFirstAttempt == 10;
      boolean isLastAttemptVisible = f == 9 && frame.pinsDownThirdAttempt != 0;

      if (isSpare)
         secondAttempt.setBackground(spare);
      else if (!isStrike)
         secondAttempt.setText(frame.pinsDownSecondAttempt + "");

      if (isStrike)
         secondAttempt.setBackground(strike);
      else
         firstAttempt.setText(frame.pinsDownFirstAttempt + "");

      if (isLastAttemptVisible) {
         thirdAttempt.setVisibility(VISIBLE);
         thirdAttempt.setText(frame.pinsDownThirdAttempt + "");
      }

      frameScore.setText(frame.score + "");
   }
}
