package dev.mrturtle.attraction.advancement;

import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class StrikeLodestoneCriterion extends AbstractCriterion<StrikeLodestoneCriterion.Conditions> {
	static final Identifier ID = new Identifier("attraction", "strike_lodestone");

	@Override
	public Identifier getId() {
		return ID;
	}

	@Override
	public Conditions conditionsFromJson(JsonObject jsonObject, EntityPredicate.Extended player, AdvancementEntityPredicateDeserializer advancementEntityPredicateDeserializer) {
		NumberRange.IntRange count = NumberRange.IntRange.fromJson(jsonObject.get("count"));
		return new Conditions(player, count);
	}

	public void trigger(ServerPlayerEntity player, int count) {
		this.trigger(player, conditions -> conditions.matches(count));
	}

	public static class Conditions extends AbstractCriterionConditions {
		private final NumberRange.IntRange count;
		public Conditions(EntityPredicate.Extended player, NumberRange.IntRange count) {
			super(ID, player);
			this.count = count;
		}

		@Override
		public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
			JsonObject jsonObject = super.toJson(predicateSerializer);
			jsonObject.add("count", count.toJson());
			return jsonObject;
		}

		public boolean matches(int count) {
			return (this.count.test(count));
		}
	}
}
