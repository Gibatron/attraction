package dev.mrturtle.attraction.mixin;

import dev.mrturtle.attraction.advancement.ModCriteria;
import dev.mrturtle.attraction.config.ConfigManager;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static dev.mrturtle.attraction.Attraction.calculateMagnet;

@Mixin(AbstractMinecartEntity.class)
public abstract class AbstractMinecartEntityMixin extends Entity {

	public AbstractMinecartEntityMixin(EntityType<?> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "tick", at = @At("HEAD"))
	public void magnetTick(CallbackInfo ci) {
		if (!this.world.isClient) {
			if (ConfigManager.config.isEntityMagnetic(this)) {
				BlockPos blockPos = this.getBlockPos();
				Iterable<BlockPos> blocks = BlockPos.iterate(blockPos.down(8).south(8).west(8), blockPos.up(8).north(8).east(8));
				for (BlockPos block : blocks) {
					BlockState state = world.getBlockState(block);
					if (ConfigManager.config.isBlockMagnetic(state)) {
						boolean wasPulled = calculateMagnet(block, state, this, (float) ConfigManager.config.getEntityMagneticValue(this));
						if (!wasPulled)
							continue;
						// Advancement trigger
						if (hasPassengers()) {
							for (Entity passenger : getPassengerList()) {
								if (passenger instanceof ServerPlayerEntity) {
									ModCriteria.RIDE_ATTRACTED.trigger((ServerPlayerEntity) passenger);
								}
							}
						}
					}
				}
			}
		}
	}
}
