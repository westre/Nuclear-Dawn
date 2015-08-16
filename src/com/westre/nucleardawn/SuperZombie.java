package com.westre.nucleardawn;

import java.lang.reflect.Field;

import net.minecraft.server.EntityCow;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityPig;
import net.minecraft.server.EntitySheep;
import net.minecraft.server.EntityVillager;
import net.minecraft.server.EntityZombie;
import net.minecraft.server.PathfinderGoalBreakDoor;
import net.minecraft.server.PathfinderGoalFloat;
import net.minecraft.server.PathfinderGoalLookAtPlayer;
import net.minecraft.server.PathfinderGoalMeleeAttack;
import net.minecraft.server.PathfinderGoalMoveThroughVillage;
import net.minecraft.server.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.PathfinderGoalRandomLookaround;
import net.minecraft.server.PathfinderGoalRandomStroll;
import net.minecraft.server.World;

import org.bukkit.craftbukkit.util.UnsafeList;

public class SuperZombie extends EntityZombie {
	private float targetSpeed;

	@SuppressWarnings("rawtypes")
	public SuperZombie(World world) {
        super(world);
        this.bw = 0.3F;   //Change this to your liking. This is were you set the speed
        this.targetSpeed = 1.7F;
       
        try {
            Field gsa = net.minecraft.server.PathfinderGoalSelector.class.getDeclaredField("a");
            gsa.setAccessible(true);

            gsa.set(this.goalSelector, new UnsafeList());
            gsa.set(this.targetSelector, new UnsafeList());
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
 
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(1, new PathfinderGoalBreakDoor(this));
        this.goalSelector.a(2, new PathfinderGoalMeleeAttack(this, EntityHuman.class, (float) (this.bw * targetSpeed), false)); // this one to attack human, TARGET SPEED!
        this.goalSelector.a(3, new PathfinderGoalMeleeAttack(this, EntityVillager.class, (float) this.bw * targetSpeed, true));
        this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, EntityPig.class, (float) this.bw * targetSpeed, true));
        this.goalSelector.a(5, new PathfinderGoalMeleeAttack(this, EntityCow.class, (float) this.bw * targetSpeed, true));
        this.goalSelector.a(6, new PathfinderGoalMeleeAttack(this, EntitySheep.class, (float) this.bw * targetSpeed, true));
        this.goalSelector.a(7, new PathfinderGoalMoveTowardsRestriction(this, (float) this.bw));
        this.goalSelector.a(8, new PathfinderGoalMoveThroughVillage(this, (float) this.bw, false));
        this.goalSelector.a(9, new PathfinderGoalRandomStroll(this, (float) this.bw));
        this.goalSelector.a(10, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F)); // this one to look at human
        this.goalSelector.a(11, new PathfinderGoalRandomLookaround(this));
        
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, 8.0F, 0, true)); // this one to target human
        this.targetSelector.a(3, new PathfinderGoalNearestAttackableTarget(this, EntityVillager.class, 8.0F, 0, false));
        this.targetSelector.a(4, new PathfinderGoalNearestAttackableTarget(this, EntityPig.class, 8.0F, 0, false));
        this.targetSelector.a(5, new PathfinderGoalNearestAttackableTarget(this, EntityCow.class, 8.0F, 0, false));
        this.targetSelector.a(6, new PathfinderGoalNearestAttackableTarget(this, EntitySheep.class, 8.0F, 0, false));
    }
}
