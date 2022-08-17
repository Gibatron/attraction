package dev.mrturtle.attraction.blocks.entity;

import dev.mrturtle.attraction.Attraction;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class NetheriteBlockBlockEntity extends AbstractMagneticBlockEntity {
	public NetheriteBlockBlockEntity(BlockPos pos, BlockState state) {
		super(Attraction.NETHERITE_BLOCK_BLOCK_ENTITY, pos, state);
		strength = 9f;
	}
}
