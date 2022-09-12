package dev.mrturtle.attraction;

import net.minecraft.block.Block;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlockTags {
	public static final TagKey<Block> BOOSTER = TagKey.of(Registry.BLOCK_KEY, new Identifier("attraction", "booster"));
	public static final TagKey<Block> BOOSTABLE = TagKey.of(Registry.BLOCK_KEY, new Identifier("attraction", "boostable"));
	public static final TagKey<Block> MAGNETIC = TagKey.of(Registry.BLOCK_KEY, new Identifier("attraction", "magnetic"));
}
