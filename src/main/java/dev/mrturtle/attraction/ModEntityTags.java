package dev.mrturtle.attraction;

import net.minecraft.entity.EntityType;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEntityTags {
	public static final TagKey<EntityType<?>> MAGNETIC = TagKey.of(Registry.ENTITY_TYPE_KEY, new Identifier("c", "magnetic"));
}
