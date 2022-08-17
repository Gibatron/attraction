package dev.mrturtle.attraction.blocks;

import dev.mrturtle.attraction.Attraction;
import dev.mrturtle.attraction.blocks.entity.NetheriteBlockBlockEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NetheriteBlockBlock extends BlockWithEntity {

	public NetheriteBlockBlock(AbstractBlock.Settings settings) {
		super(settings);
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		// With inheriting from BlockWithEntity this defaults to INVISIBLE, so we need to change that!
		return BlockRenderType.MODEL;
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new NetheriteBlockBlockEntity(pos, state);
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return checkType(type, Attraction.NETHERITE_BLOCK_BLOCK_ENTITY, NetheriteBlockBlockEntity::tick);
	}
}
