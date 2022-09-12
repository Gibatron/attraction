package dev.mrturtle.attraction.mixin;

import dev.mrturtle.attraction.ModBlockTags;
import dev.mrturtle.attraction.ModEntityTags;
import dev.mrturtle.attraction.advancement.ModCriteria;
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
			if (getType().isIn(ModEntityTags.MAGNETIC)) {
				BlockPos blockPos = this.getBlockPos();
				Iterable<BlockPos> blocks = BlockPos.iterate(blockPos.down(8).south(8).west(8), blockPos.up(8).north(8).east(8));
				for (BlockPos block : blocks) {
					BlockState state = world.getBlockState(block);
					if (state.isIn(ModBlockTags.MAGNETIC)) {
						boolean wasPulled = calculateMagnet(block, state, this, 1);
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
