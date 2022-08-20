package dev.mrturtle.attraction.advancement;

import net.minecraft.advancement.criterion.Criteria;

public class ModCriteria {
	public static ItemAttractedCriterion ITEM_ATTRACTED = new ItemAttractedCriterion();

	public static RideAttractedCriterion RIDE_ATTRACTED = new RideAttractedCriterion();

	public static StrikeLodestoneCriterion STRIKE_LODESTONE = new StrikeLodestoneCriterion();

	public static MagnetBoostElytraCriterion MAGNET_BOOST_ELYTRA = new MagnetBoostElytraCriterion();

	public static void register() {
		Criteria.register(ITEM_ATTRACTED);
		Criteria.register(RIDE_ATTRACTED);
		Criteria.register(STRIKE_LODESTONE);
		Criteria.register(MAGNET_BOOST_ELYTRA);
	}
}
