package dev.mrturtle.attraction;

import dev.mrturtle.attraction.advancement.ModCriteria;
import dev.mrturtle.attraction.blocks.ChargedLodestoneBlock;
import dev.mrturtle.attraction.blocks.entity.ChargedLodestoneBlockEntity;
import dev.mrturtle.attraction.blocks.entity.LodestoneBlockEntity;
import dev.mrturtle.attraction.blocks.entity.NetheriteBlockBlockEntity;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.block.entity.api.QuiltBlockEntityTypeBuilder;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;
import org.quiltmc.qsl.lifecycle.api.event.ServerTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Attraction implements ModInitializer {
	/*
	How Attractive: Witness a magnetic block attract an item
	Being Pulled Along: Be in a minecart while it's being attracted by a magnetic block
	Supercharged: Strike a lodestone with lightning
	Superdupercharged: Strike 5 lodestones with lightning at the same time (HIDDEN)
	Opposites Attract: Have a netherite block attract an iron block
	Where we're going we don't need roads: Use magnetic blocks to propel yourself to Xm/s while flying with an elytra (HIDDEN)
	*/
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod name as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("Attraction");

	public static final ChargedLodestoneBlock CHARGED_LODESTONE_BLOCK = new ChargedLodestoneBlock(QuiltBlockSettings.of(Material.REPAIR_STATION).luminance((state) -> state.get(Properties.LIT) ? 15 : 5).requiresTool().strength(3.5F).sounds(BlockSoundGroup.LODESTONE));

	public static final BlockEntityType<LodestoneBlockEntity> LODESTONE_BLOCK_ENTITY = Registry.register(
			Registry.BLOCK_ENTITY_TYPE,
			new Identifier("attraction", "lodestone_block_entity"),
			QuiltBlockEntityTypeBuilder.create(LodestoneBlockEntity::new, Blocks.LODESTONE).build()
	);

	public static final BlockEntityType<ChargedLodestoneBlockEntity> CHARGED_LODESTONE_BLOCK_ENTITY = Registry.register(
			Registry.BLOCK_ENTITY_TYPE,
			new Identifier("attraction", "charged_lodestone_block_entity"),
			QuiltBlockEntityTypeBuilder.create(ChargedLodestoneBlockEntity::new, CHARGED_LODESTONE_BLOCK).build()
	);

	public static final BlockEntityType<NetheriteBlockBlockEntity> NETHERITE_BLOCK_BLOCK_ENTITY = Registry.register(
			Registry.BLOCK_ENTITY_TYPE,
			new Identifier("attraction", "netherite_block_block_entity"),
			QuiltBlockEntityTypeBuilder.create(NetheriteBlockBlockEntity::new, Blocks.NETHERITE_BLOCK).build()
	);

	public static final Identifier CHARGED_LODESTONE_INVERT_ID = new Identifier("attraction:charged_lodestone_invert");

	public static SoundEvent CHARGED_LODESTONE_INVERT = new SoundEvent(CHARGED_LODESTONE_INVERT_ID);

	@Override
	public void onInitialize(ModContainer mod) {
		Registry.register(Registry.BLOCK, new Identifier("attraction", "charged_lodestone"), CHARGED_LODESTONE_BLOCK);
		Registry.register(Registry.ITEM, new Identifier("attraction", "charged_lodestone"), new BlockItem(CHARGED_LODESTONE_BLOCK, new Item.Settings().group(ItemGroup.DECORATIONS)));
		Registry.register(Registry.SOUND_EVENT, CHARGED_LODESTONE_INVERT_ID, CHARGED_LODESTONE_INVERT);
		ModCriteria.register();
	}
}
