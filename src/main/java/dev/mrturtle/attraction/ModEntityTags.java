package dev.mrturtle.attraction;

import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModEntityTags {
	public static final TagKey<EntityType<?>> MAGNETIC = TagKey.of(Registries.ENTITY_TYPE.getKey(), new Identifier("attraction", "magnetic"));
}
