package dev.mrturtle.attraction.blocks;

import dev.mrturtle.attraction.Attraction;
import dev.mrturtle.attraction.advancement.ModCriteria;
import dev.mrturtle.attraction.blocks.entity.LodestoneBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class LodestoneBlock extends BlockWithEntity {

	public LodestoneBlock(AbstractBlock.Settings settings) {
		super(settings);
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		// With inheriting from BlockWithEntity this defaults to INVISIBLE, so we need to change that!
		return BlockRenderType.MODEL;
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new LodestoneBlockEntity(pos, state);
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return checkType(type, Attraction.LODESTONE_BLOCK_ENTITY, LodestoneBlockEntity::tick);
	}

	public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
		if (!world.isClient) {
			BlockState updated = world.getBlockState(fromPos);
			boolean charged = false;
			boolean fromLodestone = false;
			if (updated.getBlock() instanceof LightningRodBlock) {
				if (updated.get(LightningRodBlock.POWERED)) {
					charged = true;
				}
			} else if (updated.getBlock() instanceof ChargedLodestoneBlock) {
				if (updated.get(ChargedLodestoneBlock.LIT)) {
					charged = true;
					fromLodestone = true;
				}
			}
			if (charged) {
				world.setBlockState(pos, Attraction.CHARGED_LODESTONE_BLOCK.getDefaultState().with(ChargedLodestoneBlock.LIT, true), Block.NOTIFY_ALL);
				// Advancement trigger
				List<ServerPlayerEntity> nearbyPlayers = world.getEntitiesByClass(ServerPlayerEntity.class, new Box(pos).expand(10, 10, 10), playerEntity -> true);
				for (ServerPlayerEntity player : nearbyPlayers) {
					ModCriteria.STRIKE_LODESTONE.trigger(player, 1);
					if (fromLodestone) {
						ModCriteria.STRIKE_LODESTONE.trigger(player, 2);
					}
				}
			}
		}
	}
}
