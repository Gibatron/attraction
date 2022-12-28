package dev.mrturtle.attraction;

import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModBlockTags {
	public static final TagKey<Block> BOOSTER = TagKey.of(Registries.BLOCK.getKey(), new Identifier("attraction", "booster"));
	public static final TagKey<Block> BOOSTABLE = TagKey.of(Registries.BLOCK.getKey(), new Identifier("attraction", "boostable"));
	public static final TagKey<Block> MAGNETIC = TagKey.of(Registries.BLOCK.getKey(), new Identifier("attraction", "magnetic"));
}
