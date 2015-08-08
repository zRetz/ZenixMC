package zenixmc.block;

import java.util.Iterator;

import org.bukkit.util.Vector;

/**
 * Traverse through the blocks hit by a ray. Based off <a href=
 * "http://gamedev.stackexchange.com/questions/47362/cast-ray-to-select-block-in-voxel-game"
 * >this post on stackoverflow</a>.
 *
 */
public class RayIterator implements Iterator<Vector> {
    /**
     * The maximum range of the ray.
     */
    private final double maxRange;

    /**
     * The current integral position.
     */
    private IVector currentCube;

    /**
     * The integral direction to move in.
     */
    private IVector signDirection;

    /**
     * The current floating point position.
     */
    private Vector tMax;

    /**
     * The direction to move in.
     */
    private Vector tDelta;

    /**
     * Whether the current position is till within the range.
     */
    private boolean inRange = true;

    /**
     * Initialize the iterator.
     *
     * @param maxRange
     *            The maximum range.
     * @param origin
     *            The starting point.
     * @param direction
     *            The direction to traverse to.
     */
    public RayIterator(int maxRange, Vector origin, Vector direction) {
        if (direction.lengthSquared() == 0) {
            throw new IllegalArgumentException(
                    "Direction may not be a null vector");
        }

        this.currentCube = new IVector(origin.getBlockX(), origin.getBlockY(),
                origin.getBlockZ());
        this.signDirection = new IVector(signOf(direction.getX()),
                signOf(direction.getY()), signOf(direction.getZ()));
        this.tMax = new Vector(
                internalBoundry(origin.getX(), direction.getX()),
                internalBoundry(origin.getY(), direction.getY()),
                internalBoundry(origin.getZ(), direction.getZ()));
        this.tDelta = new Vector(this.signDirection.getX() / direction.getX(),
                this.signDirection.getY() / direction.getY(),
                this.signDirection.getZ() / direction.getZ());

        this.maxRange = maxRange / Math.sqrt(direction.length());
    }

    /**
     * Returns the 1d normalised vector of a number.
     *
     * @param f
     *            The parameter to be normalised.
     * @return The normalised result.
     */
    protected final byte signOf(double f) {
        return f > 0 ? (byte) 1 : (f == 0 ? (byte) 0 : (byte) -1);
    }

    /**
     * Calculates the internal boundry.
     *
     * @param origin
     *            The starting position.
     * @param delta
     *            The delta in this direction.
     * @return The internal boundry.
     */
    protected final double internalBoundry(double origin, double delta) {
        if (delta < 0) {
            return internalBoundry(-origin, -delta);
        } else {
            origin = mod(origin, 1);
            // problem is now s+t*ds = 1
            return (1 - origin) / delta;
        }
    }

    /**
     * Correct version of mod.
     *
     * @param value
     *            Value to run modulus on.
     * @param modulus
     *            The modules base.
     * @return The processed value.
     */
    protected final double mod(double value, double modulus) {
        return (value % modulus + modulus) % modulus;
    }

    @Override
    public boolean hasNext() {
        return inRange;
    }

    @Override
    public Vector next() {
        if (tMax.getX() < tMax.getY()) {
            if (tMax.getX() < tMax.getZ()) {
                if (tMax.getX() > maxRange) {
                    inRange = false;
                }
                // Update which cube we are now in.
                currentCube.setX(currentCube.getX() + signDirection.getX());

                // Adjust tMaxX to the next X-oriented boundary crossing.
                tMax.setX(tMax.getX() + tDelta.getX());

                // Record the normal vector of the cube face we entered.
                final IVector face = new IVector(-signDirection.getX(), 0, 0);
            } else {
                if (tMax.getZ() > maxRange) {
                    inRange = false;
                }
                currentCube.setZ(currentCube.getZ() + signDirection.getZ());
                tMax.setZ(tMax.getZ() + tDelta.getZ());
                final IVector face = new IVector(0, 0, -signDirection.getZ());
            }
        } else {
            if (tMax.getX() < tMax.getZ()) {
                if (tMax.getY() > maxRange) {
                    inRange = false;
                }
                currentCube.setY(currentCube.getY() + signDirection.getY());

                tMax.setY(tMax.getY() + tDelta.getY());

                final IVector face = new IVector(0, -signDirection.getY(), 0);
            } else {
                // Identical to the second case, repeated for simplicity in
                // the conditionals.
                if (tMax.getZ() > maxRange) {
                    inRange = false;
                }
                currentCube.setZ(currentCube.getZ() + signDirection.getZ());
                tMax.setZ(tMax.getZ() + tDelta.getZ());
                final IVector face = new IVector(0, 0, -signDirection.getZ());
            }
        }

        return currentCube.toVector();
    }

    /**
     * Not supported.
     */
    @Override
    public void remove() {
        throw new UnsupportedOperationException("Remove is not supported on RayIterator.");
    }

}
