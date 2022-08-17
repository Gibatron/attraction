package dev.mrturtle.attraction.blocks.entity;

import dev.mrturtle.attraction.Attraction;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class LodestoneBlockEntity extends AbstractMagneticBlockEntity {
	public LodestoneBlockEntity(BlockPos pos, BlockState state) {
		super(Attraction.LODESTONE_BLOCK_ENTITY, pos, state);
	}
}
