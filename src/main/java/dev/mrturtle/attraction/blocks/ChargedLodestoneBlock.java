package dev.mrturtle.attraction.blocks;

import dev.mrturtle.attraction.Attraction;
import dev.mrturtle.attraction.blocks.entity.ChargedLodestoneBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ChargedLodestoneBlock extends BlockWithEntity {
	public static final BooleanProperty POWERED = BooleanProperty.of("powered");
	public static final BooleanProperty LIT = BooleanProperty.of("lit");

	public ChargedLodestoneBlock(AbstractBlock.Settings settings) {
		super(settings);
		setDefaultState(getStateManager().getDefaultState().with(POWERED, false).with(LIT, false));
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		// With inheriting from BlockWithEntity this defaults to INVISIBLE, so we need to change that!
		return BlockRenderType.MODEL;
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new ChargedLodestoneBlockEntity(pos, state);
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return checkType(type, Attraction.CHARGED_LODESTONE_BLOCK_ENTITY, ChargedLodestoneBlockEntity::tick);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(POWERED);
		builder.add(LIT);
	}

	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, RandomGenerator random) {
		if (state.get(POWERED) && !world.isReceivingRedstonePower(pos)) {
			world.setBlockState(pos, state.cycle(POWERED), Block.NOTIFY_LISTENERS);
		}
	}

	public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
		if (!world.isClient) {
			boolean bl = state.get(POWERED);
			BlockState updated = world.getBlockState(fromPos);
			if (updated.getBlock() instanceof LightningRodBlock) {
				if (!updated.get(LightningRodBlock.POWERED) && state.get(LIT)) {
					world.setBlockState(pos, Attraction.CHARGED_LODESTONE_BLOCK.getDefaultState().with(ChargedLodestoneBlock.LIT, false), Block.NOTIFY_ALL);
				}
			} else if (updated.getBlock() instanceof ChargedLodestoneBlock) {
				if (!updated.get(ChargedLodestoneBlock.LIT) && state.get(LIT)) {
					world.setBlockState(pos, Attraction.CHARGED_LODESTONE_BLOCK.getDefaultState().with(ChargedLodestoneBlock.LIT, false), Block.NOTIFY_ALL);
				}
			}
			if (bl != world.isReceivingRedstonePower(pos)) {
				if (bl) {
					world.scheduleBlockTick(pos, this, 4);
				} else {
					world.setBlockState(pos, state.cycle(POWERED), Block.NOTIFY_LISTENERS);
				}
			}
		}
	}

	@Nullable
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(POWERED, ctx.getWorld().isReceivingRedstonePower(ctx.getBlockPos()));
	}
}
