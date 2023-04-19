package dev.mrturtle.attraction.blocks;

import dev.mrturtle.attraction.Attraction;
import dev.mrturtle.attraction.advancement.ModCriteria;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ChargedLodestoneBlock extends Block {
	public static final BooleanProperty POWERED = BooleanProperty.of("powered");
	public static final BooleanProperty LIT = BooleanProperty.of("lit");

	public ChargedLodestoneBlock(AbstractBlock.Settings settings) {
		super(settings);
		setDefaultState(getStateManager().getDefaultState().with(POWERED, false).with(LIT, false));
	}

	@Override
	public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
		if (oldState.isOf(Blocks.LODESTONE)) {
			if (world.getBlockState(pos.down()).isOf(Blocks.LODESTONE)) {
				world.setBlockState(pos.down(), Attraction.CHARGED_LODESTONE_BLOCK.getDefaultState().with(ChargedLodestoneBlock.LIT, true), Block.NOTIFY_ALL);
				// Advancement trigger
				List<ServerPlayerEntity> nearbyPlayers = world.getEntitiesByClass(ServerPlayerEntity.class, new Box(pos).expand(10, 10, 10), playerEntity -> true);
				for (ServerPlayerEntity player : nearbyPlayers) {
					ModCriteria.STRIKE_LODESTONE.trigger(player, 2);
				}
			}
		}
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(POWERED);
		builder.add(LIT);
	}

	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
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
				world.playSound(null, pos, Attraction.CHARGED_LODESTONE_INVERT, SoundCategory.BLOCKS, 1.0F, 1.0F);
			}
		}
	}

	@Nullable
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(POWERED, ctx.getWorld().isReceivingRedstonePower(ctx.getBlockPos()));
	}
}
