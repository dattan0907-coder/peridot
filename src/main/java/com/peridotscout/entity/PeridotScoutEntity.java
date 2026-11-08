package com.peridotscout.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class PeridotScoutEntity extends Monster implements GeoEntity {
    // Animation names, exactly as written in peridot_scout.animation.json
    private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("animation.peridot_scout.idle");
    private static final RawAnimation WALK = RawAnimation.begin().thenLoop("animation.peridot_scout.walk");
    private static final RawAnimation RUN = RawAnimation.begin().thenLoop("animation.peridot_scout.run");
    private static final RawAnimation SWIM = RawAnimation.begin().thenLoop("animation.peridot_scout.swim");
    private static final RawAnimation DEATH = RawAnimation.begin().thenPlayAndHold("animation.peridot_scout.death");
    private static final RawAnimation ATTACK_LEFT = RawAnimation.begin().thenPlay("animation.peridot_scout.attack_left");
    private static final RawAnimation ATTACK_RIGHT = RawAnimation.begin().thenPlay("animation.peridot_scout.attack_right");
    private static final RawAnimation TAIL_WHIP = RawAnimation.begin().thenPlay("animation.peridot_scout.tail_whip");
    private static final RawAnimation ROAR = RawAnimation.begin().thenPlay("animation.peridot_scout.roar");
    private static final RawAnimation SCAN = RawAnimation.begin().thenPlay("animation.peridot_scout.scan");

    /** Death animation is 3 s long = 60 ticks. */
    private static final int DEATH_TICKS = 60;

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public PeridotScoutEntity(EntityType<? extends PeridotScoutEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 24.0)
                .add(Attributes.MOVEMENT_SPEED, 0.32)
                .add(Attributes.ATTACK_DAMAGE, 4.0)
                .add(Attributes.FOLLOW_RANGE, 28.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.1);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2, false));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 10.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public void tick() {
        super.tick();
        // Occasionally play the scanner animation while standing still with no target.
        if (!this.level().isClientSide && this.isAlive() && this.getTarget() == null
                && this.getDeltaMovement().horizontalDistanceSqr() < 1.0E-4
                && this.getRandom().nextInt(600) == 0) {
            this.triggerAnim("action", "scan");
        }
    }

    @Override
    public void setTarget(LivingEntity target) {
        boolean newTarget = target != null && this.getTarget() == null;
        super.setTarget(target);
        if (newTarget && !this.level().isClientSide) {
            this.triggerAnim("action", "roar");
            this.level().playSound(null, this.blockPosition(), SoundEvents.RAVAGER_ROAR,
                    SoundSource.HOSTILE, 1.0F, 1.2F);
        }
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean hit = super.doHurtTarget(target);
        if (hit && !this.level().isClientSide) {
            int roll = this.getRandom().nextInt(5);
            if (roll == 0) {
                this.triggerAnim("action", "tail_whip");
            } else {
                this.triggerAnim("action", roll % 2 == 0 ? "attack_left" : "attack_right");
            }
        }
        return hit;
    }

    /** Keep the corpse around long enough for the 3 s death animation. */
    @Override
    protected void tickDeath() {
        this.deathTime++;
        if (this.deathTime >= DEATH_TICKS && !this.level().isClientSide() && !this.isRemoved()) {
            this.level().broadcastEntityEvent(this, (byte) 60);
            this.remove(Entity.RemovalReason.KILLED);
        }
    }

    // ---- sounds (vanilla ravager placeholders) ----
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.RAVAGER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.RAVAGER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.RAVAGER_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.RAVAGER_STEP, 0.15F, 1.0F);
    }

    // ---- GeckoLib ----
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "main", 5, this::mainPredicate));
        controllers.add(new AnimationController<>(this, "action", 0, state -> PlayState.STOP)
                .triggerableAnim("attack_left", ATTACK_LEFT)
                .triggerableAnim("attack_right", ATTACK_RIGHT)
                .triggerableAnim("tail_whip", TAIL_WHIP)
                .triggerableAnim("roar", ROAR)
                .triggerableAnim("scan", SCAN));
    }

    private PlayState mainPredicate(AnimationState<PeridotScoutEntity> state) {
        if (this.isDeadOrDying()) {
            return state.setAndContinue(DEATH);
        }
        if (this.isInWater()) {
            return state.setAndContinue(SWIM);
        }
        if (state.isMoving()) {
            return state.setAndContinue(this.isAggressive() ? RUN : WALK);
        }
        return state.setAndContinue(IDLE);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
