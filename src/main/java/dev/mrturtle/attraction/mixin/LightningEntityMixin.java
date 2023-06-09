package dev.mrturtle.attraction.mixin;

import dev.mrturtle.attraction.Attraction;
import dev.mrturtle.attraction.advancement.ModCriteria;
import dev.mrturtle.attraction.blocks.ChargedLodestoneBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(LightningEntity.class)
public abstract class LightningEntityMixin extends Entity {
	@Shadow
	private int ambientTick;

	@Shadow
	protected abstract BlockPos getAffectedBlockPos();

	public LightningEntityMixin(EntityType<?> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "tick", at = @At(value = "HEAD"))
	private void strikeLodestone(CallbackInfo ci) {
		World world = getWorld();
		if (ambientTick == 2 && !world.isClient()) {
			BlockPos blockPos = getAffectedBlockPos().down();
			BlockState blockState = world.getBlockState(blockPos);
			if (blockState.isOf(Blocks.LODESTONE)) {
				world.setBlockState(blockPos, Attraction.CHARGED_LODESTONE_BLOCK.getDefaultState().with(ChargedLodestoneBlock.LIT, true), Block.NOTIFY_ALL);
				// Advancement trigger
				List<ServerPlayerEntity> nearbyPlayers = world.getEntitiesByClass(ServerPlayerEntity.class, new Box(blockPos).expand(10, 10, 10), playerEntity -> true);
				for (ServerPlayerEntity player : nearbyPlayers) {
					ModCriteria.STRIKE_LODESTONE.trigger(player, 1);
				}
			}
		}
	}
}
