package de.slikey.effectlib.effect;

import de.slikey.effectlib.Effect;
import de.slikey.effectlib.EffectType;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.util.CustomSound;

public class SoundEffect extends Effect {

    /**
     * Sound effect to play
     * Format: <soundName>,<volume>,<pitch>,<range>
     */
    public CustomSound sound;

    public float soundVolume = 100;
    public float soundPitch = 1;

    public SoundEffect(EffectManager effectManager) {
        super(effectManager);
        type = EffectType.INSTANT;
        asynchronous = false;
        period = 1;
        iterations = 1;
    }

    @Override
    public void onRun() {
      if (sound == null) return;
          if (this.getTargetPlayer() != null) {
              this.getTargetPlayer().playSound(getLocation(), sound.getSound(), soundVolume, soundPitch);
          } else {
              sound.play(effectManager.getOwningPlugin(), getLocation());
          }
      }
  }
