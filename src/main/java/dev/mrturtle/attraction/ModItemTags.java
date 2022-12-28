package dev.mrturtle.attraction;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModItemTags {
	public static final TagKey<Item> MAGNETIC = TagKey.of(Registries.ITEM.getKey(), new Identifier("attraction", "magnetic"));
}
