package drodobytecom.bowl.view.imp;

import android.content.Context;
import android.support.annotation.AnimRes;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

import java.util.ArrayList;
import java.util.List;

import drodobytecom.bowl.R;

import static android.view.animation.AnimationUtils.loadAnimation;

/**
 * UI Helper class
 */
class UI {

   static void fadeAndResetPins(ViewGroup pins) {
      fadeOut(pins, () -> {
         resetPins(pins);
         fadeIn(pins, () -> {
         });
      });
   }

   static void fadeIn(ViewGroup pins, Runnable onEnd) {
      pins.startAnimation(anim(pins.getContext(), R.anim.fade_in, onEnd));
   }

   static void fadeOut(ViewGroup pins, Runnable onEnd) {
      pins.startAnimation(anim(pins.getContext(), R.anim.fade_out, onEnd));
   }

   static void resetPins(ViewGroup pins) {
      Animation up = loadAnimation(pins.getContext(), R.anim.pin_up);
      for (View pin : list(pins)) {
         pin.setAnimation(up);
         pin.setClickable(true);
      }
   }

   static void pinsDown(ViewGroup pins, int from, int to, Runnable onEnd) {
      Animation down = anim(pins.getContext(), R.anim.pin_down, onEnd);
      for (View pin : list(pins).subList(from, to)) {
         pin.startAnimation(down);
         pin.setClickable(false);
      }
   }

   private static List<View> list(ViewGroup group) {
      ArrayList<View> views = new ArrayList<>(group.getChildCount());
      for (int i = 0; i < group.getChildCount(); i++)
         views.add(group.getChildAt(i));
      return views;
   }

   private static Animation anim(Context context, @AnimRes int id, Runnable onEnd) {
      Animation animation = loadAnimation(context, id);
      animation.setAnimationListener(new OnEnd(onEnd));
      return animation;
   }

   private static class OnEnd implements AnimationListener {
      private Runnable runnable;

      OnEnd(Runnable runnable) {
         this.runnable = runnable;
      }

      @Override
      public void onAnimationStart(Animation animation) {
      }

      @Override
      public void onAnimationEnd(Animation animation) {
         runnable.run();
      }

      @Override
      public void onAnimationRepeat(Animation animation) {
      }
   }
}
