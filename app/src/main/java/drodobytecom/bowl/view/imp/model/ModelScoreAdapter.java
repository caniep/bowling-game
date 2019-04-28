package drodobytecom.bowl.view.imp.model;

import java.util.ArrayList;
import java.util.List;

import drodobytecom.bowl.entity.Frame;
import drodobytecom.bowl.entity.Score;
import drodobytecom.bowl.util.Adapter;

public class ModelScoreAdapter implements Adapter<Score, ModelScore> {

   @Override
   public ModelScore adapt(Score score) {
      ModelScore model = new ModelScore();
      model.frames = adapt(score.frames());
      return model;
   }

   private List<ModelScore.Frame> adapt(List<Frame> frames) {
      ArrayList<ModelScore.Frame> models = new ArrayList<>(frames.size());
      for (Frame frame : frames)
         models.add(adapt(frame));
      return models;
   }

   private ModelScore.Frame adapt(Frame frame) {
      ModelScore.Frame model = new ModelScore.Frame();
      model.firstHit = frame.firstHit();
      model.secondHit = frame.secondHit();
      model.thirdHit = frame.thirdHit();
      model.score = frame.score();
      return model;
   }
}
