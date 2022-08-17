package dev.mrturtle.attraction.blocks.entity;

import dev.mrturtle.attraction.ModEntityTags;
import dev.mrturtle.attraction.ModItemTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class AbstractMagneticBlockEntity extends BlockEntity {
	double strength = 1f;

	public AbstractMagneticBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	public static void tick(World world, BlockPos pos, BlockState state, AbstractMagneticBlockEntity be) {
		if (!world.isClient) {
			// Armored entities
			List<LivingEntity> armored = world.getEntitiesByClass(LivingEntity.class, new Box(pos).expand(10, 10, 10), livingEntity -> {
				for (ItemStack itemStack : livingEntity.getArmorItems()) {
					if (itemStack.isIn(ModItemTags.MAGNETIC))
						return true;
				}
				return false;
			});
			for (LivingEntity entity : armored) {
				if (entity instanceof ServerPlayerEntity) {
					if (((ServerPlayerEntity) entity).getAbilities().flying)
						continue;
				}
				int armorCount = 0;
				for (ItemStack itemStack : entity.getArmorItems()) {
					if (itemStack.isIn(ModItemTags.MAGNETIC))
						armorCount += 1;
				}
				modifyVelocity(entity, pos, armorCount, be);
			}
			// Dropped items
			List<ItemEntity> itemEntities = world.getEntitiesByClass(ItemEntity.class, new Box(pos).expand(10, 10, 10), itemEntity -> itemEntity.getStack().isIn(ModItemTags.MAGNETIC));
			for (ItemEntity entity : itemEntities) {
				modifyVelocity(entity, pos, 1, be);
			}
			// Tagged entities
			List<Entity> taggedEntities = world.getEntitiesByClass(Entity.class, new Box(pos).expand(10, 10, 10), entity -> entity.getType().isIn(ModEntityTags.MAGNETIC));
			for (Entity entity : taggedEntities) {
				modifyVelocity(entity, pos, 1, be);
			}
		}
	}

	public static void modifyVelocity(Entity entity, BlockPos pos, double modifier, AbstractMagneticBlockEntity be) {
		Vec3d entityVec = entity.getPos();
		Vec3d center = Vec3d.ofCenter(pos);
		double distance = center.distanceTo(entityVec);
		if (distance < 7 && (distance >= 1 || be.strength < 0)) {
			Vec3d vec = center.subtract(entityVec);
			vec = vec.normalize().multiply((be.strength * modifier * 0.01f) * (7 - distance));
			entity.addVelocity(vec.x, vec.y, vec.z);
			entity.velocityModified = true;
		}
	}
}
