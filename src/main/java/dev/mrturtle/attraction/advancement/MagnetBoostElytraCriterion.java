package dev.mrturtle.attraction.advancement;

import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class MagnetBoostElytraCriterion extends AbstractCriterion<MagnetBoostElytraCriterion.Conditions> {
	static final Identifier ID = new Identifier("attraction", "magnet_boost_elytra");

	@Override
	public Identifier getId() {
		return ID;
	}

	@Override
	public Conditions conditionsFromJson(JsonObject jsonObject, EntityPredicate.Extended player, AdvancementEntityPredicateDeserializer advancementEntityPredicateDeserializer) {
		return new Conditions(player);
	}

	public void trigger(ServerPlayerEntity player) {
		this.trigger(player, conditions -> true);
	}

	public static class Conditions extends AbstractCriterionConditions {
		public Conditions(EntityPredicate.Extended player) {
			super(ID, player);
		}

		@Override
		public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
			return super.toJson(predicateSerializer);
		}
	}
}
