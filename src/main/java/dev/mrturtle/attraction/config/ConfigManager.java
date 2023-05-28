package dev.mrturtle.attraction.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.mrturtle.attraction.Attraction;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;

public class ConfigManager {
	private static final Path configPath = FabricLoader.getInstance().getConfigDir().resolve("attraction.json");

	public static Config config;

	public static double defaultStrength = 1.0;
	public static double defaultFullStrength = 4.0;

	public static void loadConfig() {
		try {
			File configFile = configPath.toFile();
			if (configFile.exists()) {
				Gson gson = new Gson();
				FileReader reader = new FileReader(configFile);
				config = gson.fromJson(reader, Config.class);
				reader.close();
			} else {
				createDefaultConfig();
			}
		} catch (Exception e) {
			Attraction.LOGGER.error("Something went wrong while loading config file!");
			e.printStackTrace();
		}
	}

	public static void createDefaultConfig() {
		File configFile = configPath.toFile();
		try {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			FileWriter writer = new FileWriter(configFile);
			config = getDefaultConfig();
			writer.write(gson.toJson(config));
			writer.close();
		} catch (Exception e) {
			Attraction.LOGGER.error("Something went wrong while saving config file!");
			e.printStackTrace();
		}
	}

	public static Config getDefaultConfig() {
		Config config = new Config();
		// This feels like a terrible way to do this, please send help.
		//// Default blocks
		config.magneticBlocks.put(Registries.BLOCK.getId(Blocks.LODESTONE).toString(), defaultStrength);
		config.magneticBlocks.put("attraction:charged_lodestone", defaultStrength);
		config.magneticBlocks.put(Registries.BLOCK.getId(Blocks.NETHERITE_BLOCK).toString(), 9.0);
		//// Default entities
		config.magneticEntities.put(Registries.ENTITY_TYPE.getId(EntityType.IRON_GOLEM).toString(), defaultFullStrength);
		// Minecarts
		config.magneticEntities.put(Registries.ENTITY_TYPE.getId(EntityType.MINECART).toString(), defaultFullStrength);
		config.magneticEntities.put(Registries.ENTITY_TYPE.getId(EntityType.CHEST_MINECART).toString(), defaultFullStrength);
		config.magneticEntities.put(Registries.ENTITY_TYPE.getId(EntityType.FURNACE_MINECART).toString(), defaultFullStrength);
		config.magneticEntities.put(Registries.ENTITY_TYPE.getId(EntityType.HOPPER_MINECART).toString(), defaultFullStrength);
		config.magneticEntities.put(Registries.ENTITY_TYPE.getId(EntityType.SPAWNER_MINECART).toString(), defaultFullStrength);
		config.magneticEntities.put(Registries.ENTITY_TYPE.getId(EntityType.COMMAND_BLOCK_MINECART).toString(), defaultFullStrength);
		config.magneticEntities.put(Registries.ENTITY_TYPE.getId(EntityType.TNT_MINECART).toString(), defaultFullStrength);
		//// Default items
		// Rails
		config.magneticItems.put(Registries.ITEM.getId(Items.RAIL).toString(), defaultStrength);
		config.magneticItems.put(Registries.ITEM.getId(Items.ACTIVATOR_RAIL).toString(), defaultStrength);
		config.magneticItems.put(Registries.ITEM.getId(Items.DETECTOR_RAIL).toString(), defaultStrength);
		config.magneticItems.put(Registries.ITEM.getId(Items.POWERED_RAIL).toString(), defaultStrength);
		// Compasses
		config.magneticItems.put(Registries.ITEM.getId(Items.COMPASS).toString(), defaultStrength);
		config.magneticItems.put(Registries.ITEM.getId(Items.RECOVERY_COMPASS).toString(), defaultStrength);
		// Iron armor
		config.magneticItems.put(Registries.ITEM.getId(Items.IRON_HELMET).toString(), defaultStrength);
		config.magneticItems.put(Registries.ITEM.getId(Items.IRON_CHESTPLATE).toString(), defaultStrength);
		config.magneticItems.put(Registries.ITEM.getId(Items.IRON_LEGGINGS).toString(), defaultStrength);
		config.magneticItems.put(Registries.ITEM.getId(Items.IRON_BOOTS).toString(), defaultStrength);
		// Netherite armor
		config.magneticItems.put(Registries.ITEM.getId(Items.NETHERITE_HELMET).toString(), defaultStrength);
		config.magneticItems.put(Registries.ITEM.getId(Items.NETHERITE_CHESTPLATE).toString(), defaultStrength);
		config.magneticItems.put(Registries.ITEM.getId(Items.NETHERITE_LEGGINGS).toString(), defaultStrength);
		config.magneticItems.put(Registries.ITEM.getId(Items.NETHERITE_BOOTS).toString(), defaultStrength);
		// Iron blocks
		config.magneticItems.put(Registries.ITEM.getId(Items.IRON_ORE).toString(), defaultStrength);
		config.magneticItems.put(Registries.ITEM.getId(Items.DEEPSLATE_IRON_ORE).toString(), defaultStrength);
		config.magneticItems.put(Registries.ITEM.getId(Items.RAW_IRON).toString(), defaultStrength);
		config.magneticItems.put(Registries.ITEM.getId(Items.RAW_IRON_BLOCK).toString(), defaultStrength);
		config.magneticItems.put(Registries.ITEM.getId(Items.IRON_BLOCK).toString(), defaultStrength);
		// Netherite blocks
		config.magneticItems.put(Registries.ITEM.getId(Items.NETHERITE_BLOCK).toString(), defaultStrength);
		config.magneticItems.put(Registries.ITEM.getId(Items.ANCIENT_DEBRIS).toString(), defaultStrength);
		// Iron tools
		config.magneticItems.put(Registries.ITEM.getId(Items.IRON_SWORD).toString(), defaultStrength);
		config.magneticItems.put(Registries.ITEM.getId(Items.IRON_PICKAXE).toString(), defaultStrength);
		config.magneticItems.put(Registries.ITEM.getId(Items.IRON_AXE).toString(), defaultStrength);
		config.magneticItems.put(Registries.ITEM.getId(Items.IRON_SHOVEL).toString(), defaultStrength);
		config.magneticItems.put(Registries.ITEM.getId(Items.IRON_HOE).toString(), defaultStrength);
		config.magneticItems.put(Registries.ITEM.getId(Items.SHEARS).toString(), defaultStrength);
		config.magneticItems.put(Registries.ITEM.getId(Items.FLINT_AND_STEEL).toString(), defaultStrength);
		config.magneticItems.put(Registries.ITEM.getId(Items.SHIELD).toString(), defaultStrength);
		// Netherite tools
		config.magneticItems.put(Registries.ITEM.getId(Items.NETHERITE_SWORD).toString(), defaultStrength);
		config.magneticItems.put(Registries.ITEM.getId(Items.NETHERITE_PICKAXE).toString(), defaultStrength);
		config.magneticItems.put(Registries.ITEM.getId(Items.NETHERITE_AXE).toString(), defaultStrength);
		config.magneticItems.put(Registries.ITEM.getId(Items.NETHERITE_SHOVEL).toString(), defaultStrength);
		config.magneticItems.put(Registries.ITEM.getId(Items.NETHERITE_HOE).toString(), defaultStrength);
		// Minecarts
		config.magneticItems.put(Registries.ITEM.getId(Items.MINECART).toString(), defaultStrength);
		config.magneticItems.put(Registries.ITEM.getId(Items.CHEST_MINECART).toString(), defaultStrength);
		config.magneticItems.put(Registries.ITEM.getId(Items.FURNACE_MINECART).toString(), defaultStrength);
		config.magneticItems.put(Registries.ITEM.getId(Items.HOPPER_MINECART).toString(), defaultStrength);
		config.magneticItems.put(Registries.ITEM.getId(Items.TNT_MINECART).toString(), defaultStrength);
		config.magneticItems.put(Registries.ITEM.getId(Items.COMMAND_BLOCK_MINECART).toString(), defaultStrength);
		// Lodestones
		config.magneticItems.put(Registries.ITEM.getId(Items.LODESTONE).toString(), defaultStrength);
		config.magneticItems.put("attraction:charged_lodestone", defaultStrength);
		// Ingots
		config.magneticItems.put(Registries.ITEM.getId(Items.IRON_INGOT).toString(), defaultStrength);
		config.magneticItems.put(Registries.ITEM.getId(Items.NETHERITE_INGOT).toString(), defaultStrength);
		config.magneticItems.put(Registries.ITEM.getId(Items.IRON_NUGGET).toString(), defaultStrength);
		// Anvils
		config.magneticItems.put(Registries.ITEM.getId(Items.ANVIL).toString(), defaultStrength);
		config.magneticItems.put(Registries.ITEM.getId(Items.CHIPPED_ANVIL).toString(), defaultStrength);
		config.magneticItems.put(Registries.ITEM.getId(Items.DAMAGED_ANVIL).toString(), defaultStrength);
		// Doors
		config.magneticItems.put(Registries.ITEM.getId(Items.IRON_DOOR).toString(), defaultStrength);
		config.magneticItems.put(Registries.ITEM.getId(Items.IRON_TRAPDOOR).toString(), defaultStrength);
		// Other iron things
		config.magneticItems.put(Registries.ITEM.getId(Items.HOPPER).toString(), defaultStrength);
		config.magneticItems.put(Registries.ITEM.getId(Items.CHAIN).toString(), defaultStrength);
		config.magneticItems.put(Registries.ITEM.getId(Items.CAULDRON).toString(), defaultStrength);
		config.magneticItems.put(Registries.ITEM.getId(Items.IRON_BARS).toString(), defaultStrength);
		config.magneticItems.put(Registries.ITEM.getId(Items.HEAVY_WEIGHTED_PRESSURE_PLATE).toString(), defaultStrength);
		config.magneticItems.put(Registries.ITEM.getId(Items.LANTERN).toString(), defaultStrength);
		config.magneticItems.put(Registries.ITEM.getId(Items.SOUL_LANTERN).toString(), defaultStrength);
		//// Default magnet boosters
		config.magnetBoosters.put(Registries.BLOCK.getId(Blocks.COPPER_BLOCK).toString(), defaultStrength);
		config.magnetBoosters.put(Registries.BLOCK.getId(Blocks.EXPOSED_COPPER).toString(), defaultStrength);
		config.magnetBoosters.put(Registries.BLOCK.getId(Blocks.WEATHERED_COPPER).toString(), defaultStrength);
		config.magnetBoosters.put(Registries.BLOCK.getId(Blocks.OXIDIZED_COPPER).toString(), defaultStrength);
		config.magnetBoosters.put(Registries.BLOCK.getId(Blocks.CUT_COPPER).toString(), defaultStrength);
		config.magnetBoosters.put(Registries.BLOCK.getId(Blocks.EXPOSED_CUT_COPPER).toString(), defaultStrength);
		config.magnetBoosters.put(Registries.BLOCK.getId(Blocks.WEATHERED_CUT_COPPER).toString(), defaultStrength);
		config.magnetBoosters.put(Registries.BLOCK.getId(Blocks.OXIDIZED_CUT_COPPER).toString(), defaultStrength);
		config.magnetBoosters.put(Registries.BLOCK.getId(Blocks.WAXED_COPPER_BLOCK).toString(), defaultStrength);
		config.magnetBoosters.put(Registries.BLOCK.getId(Blocks.WAXED_EXPOSED_COPPER).toString(), defaultStrength);
		config.magnetBoosters.put(Registries.BLOCK.getId(Blocks.WAXED_WEATHERED_COPPER).toString(), defaultStrength);
		config.magnetBoosters.put(Registries.BLOCK.getId(Blocks.WAXED_OXIDIZED_COPPER).toString(), defaultStrength);
		config.magnetBoosters.put(Registries.BLOCK.getId(Blocks.WAXED_CUT_COPPER).toString(), defaultStrength);
		config.magnetBoosters.put(Registries.BLOCK.getId(Blocks.WAXED_EXPOSED_CUT_COPPER).toString(), defaultStrength);
		config.magnetBoosters.put(Registries.BLOCK.getId(Blocks.WAXED_WEATHERED_CUT_COPPER).toString(), defaultStrength);
		config.magnetBoosters.put(Registries.BLOCK.getId(Blocks.WAXED_OXIDIZED_CUT_COPPER).toString(), defaultStrength);
		//// Default magnet boostables
		config.magnetBoostable.put("attraction:charged_lodestone", defaultStrength);
		return config;
	}

}
