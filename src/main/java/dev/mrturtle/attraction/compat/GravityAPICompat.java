package dev.mrturtle.attraction.compat;

import com.fusionflux.gravity_api.api.GravityChangerAPI;
import com.fusionflux.gravity_api.util.RotationUtil;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class GravityAPICompat {
	public static Vec3d checkForGravity(Vec3d vec, Entity entity) {
		Direction direction = GravityChangerAPI.getGravityDirection(entity);
		return RotationUtil.vecWorldToPlayer(vec, direction);
	}
}
