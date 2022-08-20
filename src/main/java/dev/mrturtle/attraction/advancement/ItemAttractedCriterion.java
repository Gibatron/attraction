package dev.mrturtle.attraction.advancement;

import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.BlockPredicate;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class ItemAttractedCriterion extends AbstractCriterion<ItemAttractedCriterion.Conditions> {
	static final Identifier ID = new Identifier("attraction", "item_attracted");

	@Override
	public Identifier getId() {
		return ID;
	}

	@Override
	public Conditions conditionsFromJson(JsonObject jsonObject, EntityPredicate.Extended player, AdvancementEntityPredicateDeserializer advancementEntityPredicateDeserializer) {
		ItemPredicate item = ItemPredicate.fromJson(jsonObject.get("item"));
		BlockPredicate block = BlockPredicate.fromJson(jsonObject.get("block"));
		return new Conditions(player, item, block);
	}

	public void trigger(ServerPlayerEntity player, ItemStack itemStack, ServerWorld world, BlockPos blockPos) {
		this.trigger(player, conditions -> conditions.matches(itemStack, world, blockPos));
	}

	public static class Conditions extends AbstractCriterionConditions {
		private final ItemPredicate item;
		private final BlockPredicate block;
		public Conditions(EntityPredicate.Extended player, ItemPredicate item, BlockPredicate block) {
			super(ID, player);
			this.item = item;
			this.block = block;
		}

		@Override
		public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
			JsonObject jsonObject = super.toJson(predicateSerializer);
			jsonObject.add("item", item.toJson());
			jsonObject.add("block", block.toJson());
			return jsonObject;
		}

		public boolean matches(ItemStack itemStack, ServerWorld world, BlockPos blockPos) {
			return (this.item.test(itemStack)) && this.block.test(world, blockPos);
		}
	}
}
