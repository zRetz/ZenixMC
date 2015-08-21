package zenixmc.utils;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class VectorUtil {

	/**
	 * Rotates a vector around the Y plane.
	 */
	public static Vector rotateXZ(Vector vec, double theta) {
		Vector vec2 = vec.clone();
		double x = vec2.getX();
		double z = vec2.getZ();
		vec2.setX(x * Math.cos(Math.toRadians(theta)) - z * Math.sin(Math.toRadians(theta)));
		vec2.setZ(x * Math.sin(Math.toRadians(theta)) + z * Math.cos(Math.toRadians(theta)));
		return vec2;
	}
	
	/**
	 * Rotates a vector around the Y plane.
	 */
	public static Vector rotateXY(Vector vec, double theta) {
		Vector vec2 = vec.clone();
		double x = vec2.getX();
		double y = vec2.getY();
		vec2.setX(x * Math.cos(Math.toRadians(theta)) - y * Math.sin(Math.toRadians(theta)));
		vec2.setY(x * Math.sin(Math.toRadians(theta)) + y * Math.cos(Math.toRadians(theta)));
		return vec2;
	}
	
	/**
	 * Rotates a vector around the Y plane.
	 */
	public static Vector rotateYZ(Vector vec, double theta) {
		Vector vec2 = vec.clone();
		double y = vec2.getY();
		double z = vec2.getZ();
		vec2.setX(y * Math.cos(Math.toRadians(theta)) - z * Math.sin(Math.toRadians(theta)));
		vec2.setZ(y * Math.sin(Math.toRadians(theta)) + z * Math.cos(Math.toRadians(theta)));
		return vec2;
	}
	
	public static Vector rotateVectorAroundVector(Vector axis, Vector rotator, double degrees) {
		double angle = Math.toRadians(degrees);
		Vector rotation = axis.clone();
		Vector rotate = rotator.clone();
		rotation = rotation.normalize();

		Vector thirdaxis = rotation.crossProduct(rotate).normalize().multiply(rotate.length());

		return rotate.multiply(Math.cos(angle)).add(thirdaxis.multiply(Math.sin(angle)));
	}
	
	public static Vector getDirection(Location point1, Location point2) {
		return point2.subtract(point1).toVector();
	}
}
