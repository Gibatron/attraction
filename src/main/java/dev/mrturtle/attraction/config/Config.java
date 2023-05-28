package dev.mrturtle.attraction.config;

import dev.mrturtle.attraction.ModBlockTags;
import dev.mrturtle.attraction.ModEntityTags;
import dev.mrturtle.attraction.ModItemTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;

import java.util.HashMap;

public class Config {
	public HashMap<String, Double> magneticBlocks = new HashMap<>();
	public HashMap<String, Double> magneticEntities = new HashMap<>();
	public HashMap<String, Double> magneticItems = new HashMap<>();
	public HashMap<String, Double> magnetBoostable = new HashMap<>();
	public HashMap<String, Double> magnetBoosters = new HashMap<>();

	public boolean isBlockMagnetic(BlockState state) {
		return state.isIn(ModBlockTags.MAGNETIC) || ConfigManager.config.magneticBlocks.containsKey(getBlockKey(state));
	}

	public boolean isEntityMagnetic(Entity entity) {
		return entity.getType().isIn(ModEntityTags.MAGNETIC) || ConfigManager.config.magneticEntities.containsKey(getEntityKey(entity));
	}

	public boolean isItemMagnetic(ItemStack stack) {
		return stack.isIn(ModItemTags.MAGNETIC) || ConfigManager.config.magneticItems.containsKey(getItemKey(stack));
	}

	public boolean isBlockBoostable(BlockState state) {
		return state.isIn(ModBlockTags.BOOSTABLE) || ConfigManager.config.magnetBoostable.containsKey(getBlockKey(state));
	}

	public boolean isBlockBooster(BlockState state) {
		return state.isIn(ModBlockTags.BOOSTER) || ConfigManager.config.magnetBoosters.containsKey(getBlockKey(state));
	}

	public double getBlockMagneticValue(BlockState state) {
		return ConfigManager.config.magneticBlocks.getOrDefault(getBlockKey(state), 1.0);
	}

	public double getBlockBoostValue(BlockState state) {
		return ConfigManager.config.magnetBoosters.getOrDefault(getBlockKey(state), 1.0);
	}

	public double getEntityMagneticValue(Entity entity) {
		return ConfigManager.config.magneticEntities.getOrDefault(getEntityKey(entity), 1.0);
	}

	public double getItemMagneticValue(ItemStack stack) {
		return ConfigManager.config.magneticItems.getOrDefault(getItemKey(stack), 1.0);
	}

	private String getBlockKey(BlockState state) {
		return Registries.BLOCK.getId(state.getBlock()).toString();
	}

	private String getEntityKey(Entity entity) {
		return Registries.ENTITY_TYPE.getId(entity.getType()).toString();
	}

	private String getItemKey(ItemStack stack) {
		return Registries.ITEM.getId(stack.getItem()).toString();
	}
}
