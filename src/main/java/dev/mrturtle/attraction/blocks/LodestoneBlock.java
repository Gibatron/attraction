package dev.mrturtle.attraction.blocks;

import dev.mrturtle.attraction.Attraction;
import dev.mrturtle.attraction.blocks.entity.LodestoneBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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
			if (updated.getBlock() instanceof LightningRodBlock) {
				if (updated.get(LightningRodBlock.POWERED)) {
					world.setBlockState(pos, Attraction.CHARGED_LODESTONE_BLOCK.getDefaultState().with(ChargedLodestoneBlock.LIT, true), Block.NOTIFY_ALL);
				}
			} else if (updated.getBlock() instanceof ChargedLodestoneBlock) {
				if (updated.get(ChargedLodestoneBlock.LIT)) {
					world.setBlockState(pos, Attraction.CHARGED_LODESTONE_BLOCK.getDefaultState().with(ChargedLodestoneBlock.LIT, true), Block.NOTIFY_ALL);
				}
			}
		}
	}
}
