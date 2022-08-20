package dev.mrturtle.attraction.blocks.entity;

import dev.mrturtle.attraction.Attraction;
import dev.mrturtle.attraction.ModBlockTags;
import dev.mrturtle.attraction.blocks.ChargedLodestoneBlock;
import net.minecraft.block.BlockState;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class ChargedLodestoneBlockEntity extends AbstractMagneticBlockEntity {
	public ChargedLodestoneBlockEntity(BlockPos pos, BlockState state) {
		super(Attraction.CHARGED_LODESTONE_BLOCK_ENTITY, pos, state);
	}

	public static void tick(World world, BlockPos pos, BlockState state, AbstractMagneticBlockEntity be) {
		if (!world.isClient) {
			Direction[] directions = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
			int boosters = 0;
			for (Direction direction : directions) {
				if (world.getBlockState(pos.add(direction.getVector())).isIn(ModBlockTags.LODESTONE_BOOSTER)) {
					boosters += 1;
				}
			}
			double strength = be.strength;
			be.strength = state.get(ChargedLodestoneBlock.POWERED) ? -1 - boosters * 0.5 : 1 + boosters * 0.5;
			if (Math.signum(strength) != Math.signum(be.strength)) {
				world.playSound(null, pos, Attraction.CHARGED_LODESTONE_INVERT, SoundCategory.BLOCKS, 1.0F, 1.0F);
			}
			AbstractMagneticBlockEntity.tick(world, pos, state, be);
		}
	}
}
