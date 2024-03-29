package dev.mrturtle.attraction.mixin;

import dev.mrturtle.attraction.advancement.ModCriteria;
import dev.mrturtle.attraction.config.ConfigManager;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import static dev.mrturtle.attraction.Attraction.calculateMagnet;

@Mixin(Entity.class)
public abstract class EntityMixin {
	@Shadow
	World world;

	@Shadow
	private BlockPos blockPos;

	@Shadow
	private Vec3d velocity;

	@Inject(method = "tick", at = @At("HEAD"))
	public void magnetTick(CallbackInfo ci) {
		if (!world.isClient) {
			float magneticValue = getMagneticValue();
			if (magneticValue > 0) {
				Iterable<BlockPos> blocks = BlockPos.iterate(blockPos.down(8).south(8).west(8), blockPos.up(8).north(8).east(8));
				for (BlockPos block : blocks) {
					BlockState state = world.getBlockState(block);
					if (ConfigManager.config.isBlockMagnetic(state)) {
						Entity entity = (Entity) (Object) this;
						boolean wasPulled = calculateMagnet(block, state, entity, magneticValue);
						if (!wasPulled)
							continue;
						// Advancement triggers
						if (entity instanceof ServerPlayerEntity) {
							if (((ServerPlayerEntity) entity).isFallFlying()) {
								double speed = Math.abs(velocity.x) + Math.abs(velocity.y) + Math.abs(velocity.z);
								speed *= 20;
								if (speed >= 88) {
									ModCriteria.MAGNET_BOOST_ELYTRA.trigger((ServerPlayerEntity) entity);
								}
							}
						} else if (entity instanceof ItemEntity) {
							List<ServerPlayerEntity> nearbyPlayers = world.getEntitiesByClass(ServerPlayerEntity.class, new Box(block).expand(10, 10, 10), playerEntity -> true);
							for (ServerPlayerEntity player : nearbyPlayers) {
								ModCriteria.ITEM_ATTRACTED.trigger(player, ((ItemEntity) entity).getStack(), (ServerWorld) world, block);
							}
						}
					}
				}
			}
		}
	}

	private float getMagneticValue() {
		Entity entity = (Entity)(Object) this;
		// Magnets shouldn't affect flying players
		if (entity instanceof ServerPlayerEntity) {
			if (((ServerPlayerEntity) entity).getAbilities().flying)
				return 0;
		}
		// Magnetic armor
		if (entity instanceof LivingEntity) {
			int count = 0;
			for (ItemStack itemStack : entity.getArmorItems()) {
				if (ConfigManager.config.isItemMagnetic(itemStack))
					count += ConfigManager.config.getItemMagneticValue(itemStack);
			}
			if (count > 0)
				return count;
		}
		// Magnetic entity tag
		if (ConfigManager.config.isEntityMagnetic(entity)) {
			return (float) ConfigManager.config.getEntityMagneticValue(entity);
		}
		// Magnetic item on ground
		if (entity instanceof ItemEntity) {
			ItemStack stack = ((ItemEntity) entity).getStack();
			if (ConfigManager.config.isItemMagnetic(stack))
				return (float) ConfigManager.config.getItemMagneticValue(stack);
		}
		return 0;
	}
}
