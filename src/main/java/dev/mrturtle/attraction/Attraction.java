package dev.mrturtle.attraction;

import dev.mrturtle.attraction.advancement.ModCriteria;
import dev.mrturtle.attraction.blocks.ChargedLodestoneBlock;
import dev.mrturtle.attraction.compat.GravityAPICompat;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.entity.Entity;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class Attraction implements ModInitializer {

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod name as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("Attraction");

	public static final ChargedLodestoneBlock CHARGED_LODESTONE_BLOCK = new ChargedLodestoneBlock(FabricBlockSettings.of(Material.METAL).luminance((state) -> state.get(Properties.LIT) ? 15 : 5).requiresTool().strength(3.5F).sounds(BlockSoundGroup.LODESTONE));

	public static final Identifier CHARGED_LODESTONE_INVERT_ID = new Identifier("attraction:charged_lodestone_invert");

	public static SoundEvent CHARGED_LODESTONE_INVERT = SoundEvent.of(CHARGED_LODESTONE_INVERT_ID);

	public static boolean useGravityAPI = false;

	@Override
	public void onInitialize() {
		if (FabricLoader.getInstance().isModLoaded("gravity_api")) {
			useGravityAPI = true;
		}
		Registry.register(Registries.BLOCK, new Identifier("attraction", "charged_lodestone"), CHARGED_LODESTONE_BLOCK);
		BlockItem lodestoneItem = new BlockItem(CHARGED_LODESTONE_BLOCK, new Item.Settings());
		Registry.register(Registries.ITEM, new Identifier("attraction", "charged_lodestone"), lodestoneItem);
		Registry.register(Registries.SOUND_EVENT, CHARGED_LODESTONE_INVERT_ID, CHARGED_LODESTONE_INVERT);
		ModCriteria.register();
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(entries -> entries.add(lodestoneItem));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> entries.addAfter(Blocks.LODESTONE, lodestoneItem));
	}

	public static boolean calculateMagnet(BlockPos block, BlockState state, Entity entity, float magneticValue) {
		Vec3d center = Vec3d.ofCenter(block);
		double distance = center.distanceTo(entity.getPos());
		double magnetStrength = 1;
		// Needs changed to config system
		// Optional<Double> baseValue = MAGNETIC_BLOCK.get(state.getBlock());
		Optional<Double> baseValue = Optional.empty();
		if (baseValue.isPresent())
			magnetStrength = baseValue.get();
		if (state.isIn(ModBlockTags.BOOSTABLE)) {
			Direction[] directions = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST, Direction.UP, Direction.DOWN};
			int boosters = 0;
			for (Direction direction : directions) {
				if (entity.world.getBlockState(block.add(direction.getVector())).isIn(ModBlockTags.BOOSTER)) {
					boosters += 1;
				}
			}
			magnetStrength = magnetStrength + boosters * 0.5;
		}
		if (state.isOf(Attraction.CHARGED_LODESTONE_BLOCK))
			if (state.get(ChargedLodestoneBlock.POWERED))
				magnetStrength *= -1;
		if (distance < 7 && (distance >= 1 || magnetStrength < 1)) {
			Vec3d vec = center.subtract(entity.getPos());
			vec = vec.normalize().multiply((magnetStrength * magneticValue * 0.01f) * (7 - distance));
			if (useGravityAPI) {
				vec = GravityAPICompat.checkForGravity(vec, entity);
			}
			entity.addVelocity(vec.x, vec.y, vec.z);
			entity.velocityModified = true;
			return true;
		}
		return false;
	}
}
