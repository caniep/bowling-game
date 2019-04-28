package drodobytecom.bowl.view.imp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.Gson;

import drodobytecom.bowl.App;
import drodobytecom.bowl.R;
import drodobytecom.bowl.presenter.PlayPresenter;
import drodobytecom.bowl.view.PlayView;
import drodobytecom.bowl.view.imp.model.ModelScore;

import static java.lang.Integer.parseInt;

public class PlayActivity extends AppCompatActivity implements PlayView {

   private Listener listener;
   private int pinsLeft;
   private ViewGroup pins;
   private String firstAttempt;
   private String secondAttempt;
   private String thirdAttempt;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_play);

      setSupportActionBar(findViewById(R.id.toolbar));

      pins = findViewById(R.id.pins);
      firstAttempt = getString(R.string.first_attempt);
      secondAttempt = getString(R.string.second_attempt);
      thirdAttempt = getString(R.string.third_attempt);

      App app = (App) getApplication();
      listener = new PlayPresenter(app.gameService(), app.dataService());
      listener.started(this);
   }

   @Override
   public void showPlay(int frame, int attempt, int pinsLeft) {
      this.pinsLeft = pinsLeft;
      String attemptString = attempt == 1 ? firstAttempt : attempt == 2 ? secondAttempt : thirdAttempt;
      getSupportActionBar().setTitle(getString(R.string.title_frame, frame + "", attemptString));
      if (attempt == 1 || ((frame == 10) && (pinsLeft == 10)))
         UI.fadeAndResetPins(pins);
   }

   @Override
   public void showScore(ModelScore score) {
      Intent intent = new Intent(this, ScoreActivity.class);
      intent.putExtra("score", new Gson().toJson(score));
      startActivity(intent);
   }

   public void pinClicked(View view) {
      if (view.getId() == R.id.none)
         listener.pinsDownSelected(this, 0);
      else {
         int from = 10 - pinsLeft;
         int to = parseInt(((Button) view).getText().toString());
         int pinsDown = to - from;
         UI.pinsDown(findViewById(R.id.pins), from, to,
                 () -> listener.pinsDownSelected(this, pinsDown));
      }
   }
}
