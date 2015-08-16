package com.westre.nucleardawn;

import java.lang.reflect.Field;

import org.bukkit.craftbukkit.util.UnsafeList;

import net.minecraft.server.EntityCow;
import net.minecraft.server.EntityCreeper;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityOcelot;
import net.minecraft.server.EntityPig;
import net.minecraft.server.EntitySheep;
import net.minecraft.server.EntitySkeleton;
import net.minecraft.server.EntityVillager;
import net.minecraft.server.EntityZombie;
import net.minecraft.server.PathfinderGoalAvoidPlayer;
import net.minecraft.server.PathfinderGoalBreakDoor;
import net.minecraft.server.PathfinderGoalFloat;
import net.minecraft.server.PathfinderGoalHurtByTarget;
import net.minecraft.server.PathfinderGoalLookAtPlayer;
import net.minecraft.server.PathfinderGoalMeleeAttack;
import net.minecraft.server.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.PathfinderGoalRandomLookaround;
import net.minecraft.server.PathfinderGoalRandomStroll;
import net.minecraft.server.PathfinderGoalSwell;
import net.minecraft.server.World;

public class SuperCreeper extends EntityCreeper {
	
	private float targetSpeed;

	@SuppressWarnings("rawtypes")
	public SuperCreeper(World world) {
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
 
        this.goalSelector.a(1, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, new PathfinderGoalSwell(this));
        this.goalSelector.a(3, new PathfinderGoalAvoidPlayer(this, EntityOcelot.class, 6.0F, 0.25F, 0.3F));
        this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, EntityHuman.class, (float) this.bw * targetSpeed, true));
        this.goalSelector.a(5, new PathfinderGoalMeleeAttack(this, EntityVillager.class, (float) this.bw * targetSpeed, true));
        this.goalSelector.a(6, new PathfinderGoalMeleeAttack(this, EntityPig.class, (float) this.bw * targetSpeed, true));
        this.goalSelector.a(7, new PathfinderGoalMeleeAttack(this, EntityCow.class, (float) this.bw * targetSpeed, true));
        this.goalSelector.a(8, new PathfinderGoalMeleeAttack(this, EntitySheep.class, (float) this.bw * targetSpeed, true));
        this.goalSelector.a(9, new PathfinderGoalMeleeAttack(this, EntityZombie.class, (float) this.bw * targetSpeed, true));
        this.goalSelector.a(10, new PathfinderGoalMeleeAttack(this, SuperZombie.class, (float) this.bw * targetSpeed, true));
        this.goalSelector.a(11, new PathfinderGoalMeleeAttack(this, EntitySkeleton.class, (float) this.bw * targetSpeed, true));
        this.goalSelector.a(12, new PathfinderGoalRandomStroll(this, 0.2F));
        this.goalSelector.a(13, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(14, new PathfinderGoalRandomLookaround(this));
        this.goalSelector.a(15, new PathfinderGoalBreakDoor(this));
        this.targetSelector.a(1, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, 16.0F, 0, true));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityVillager.class, 8.0F, 0, false));
        this.targetSelector.a(3, new PathfinderGoalNearestAttackableTarget(this, EntityPig.class, 8.0F, 0, false));
        this.targetSelector.a(4, new PathfinderGoalNearestAttackableTarget(this, EntityCow.class, 8.0F, 0, false));
        this.targetSelector.a(5, new PathfinderGoalNearestAttackableTarget(this, EntitySheep.class, 8.0F, 0, false));
        this.targetSelector.a(6, new PathfinderGoalNearestAttackableTarget(this, EntityZombie.class, 8.0F, 0, false));
        this.targetSelector.a(7, new PathfinderGoalNearestAttackableTarget(this, SuperZombie.class, 8.0F, 0, false));
        this.targetSelector.a(8, new PathfinderGoalNearestAttackableTarget(this, EntitySkeleton.class, 8.0F, 0, false));
        this.targetSelector.a(9, new PathfinderGoalHurtByTarget(this, false));
	}
}
