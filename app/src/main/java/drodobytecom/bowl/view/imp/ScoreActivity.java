package drodobytecom.bowl.view.imp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import drodobytecom.bowl.App;
import drodobytecom.bowl.R;
import drodobytecom.bowl.presenter.ScorePresenter;
import drodobytecom.bowl.view.ScoreView;
import drodobytecom.bowl.view.imp.model.ModelScore;

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

      App app = (App) getApplication();
      listener = new ScorePresenter(app.gameService(), app.dataService());
      listener.shown(this, getScore());
   }

   @Override
   public void show(ModelScore score) {
      getSupportActionBar().setTitle(R.string.title_score);
      ViewGroup view = findViewById(R.id.score);
      for (int frame = 0; frame < view.getChildCount(); frame++)
         setFrame(score, view, frame);
   }

   private ModelScore getScore() {
      return new Gson().fromJson(getIntent().getExtras().getString("score"), ModelScore.class);
   }

   private void setFrame(ModelScore score, ViewGroup view, int f) {
      View frameView = view.getChildAt(f);
      TextView firstAttempt = frameView.findViewById(R.id.firstHit);
      TextView secondAttempt = frameView.findViewById(R.id.secondHit);
      TextView thirdAttempt = frameView.findViewById(R.id.thirdHit);
      TextView frameScore = frameView.findViewById(R.id.frame_score);

      ModelScore.Frame frame = score.frames.get(f);

      boolean isStrike = frame.firstHit == 10;
      boolean isSpare = frame.secondHit + frame.firstHit == 10;
      boolean isLastAttemptVisible = f == 9 && frame.thirdHit != 0;

      if (isSpare)
         secondAttempt.setBackground(spare);
      else if (!isStrike)
         secondAttempt.setText(frame.secondHit + "");

      if (isStrike)
         secondAttempt.setBackground(strike);
      else
         firstAttempt.setText(frame.firstHit + "");

      if (isLastAttemptVisible) {
         thirdAttempt.setVisibility(VISIBLE);
         thirdAttempt.setText(frame.thirdHit + "");
      }

      frameScore.setText(frame.score + "");
   }
}
