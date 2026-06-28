package de.slikey.effectlib.effect;

import de.slikey.effectlib.Effect;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.EffectType;
import de.slikey.effectlib.util.RandomUtils;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

public class OriginalEffect extends Effect {
    /**
     * Particle to display
     */
    public Particle particle = Particle.FLAME;

    public double radius = 0.8;

    public OriginalEffect(EffectManager effectManager) {
        super(effectManager);
        type = EffectType.REPEATING;
        particleCount = 10;
        period = 1;
    }

    @Override
    public void onRun() {
        Location location = getLocation().clone();
        for (int i = 0; i < particleCount; i++) {
            Vector v = RandomUtils.getRandomVector();
            location.add(v);
            display(particle, location);
            location.subtract(v);
        }
    }

    @Override
    public Effect clone() {
        Effect effect = super.clone();
        OriginalEffect originalEffect = (OriginalEffect) effect;
        originalEffect.particle = this.particle;
        originalEffect.radius = this.radius;
        return effect;
    }
}
