package zenixmc.block;

import org.bukkit.util.Vector;

public class IVector {
    private int x, y, z;

    public IVector(int x, int y, int z) {
        this.x = x;
        this.setY(y);
        this.setZ(z);
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x
     *            the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y
     *            the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return the z
     */
    public int getZ() {
        return z;
    }

    /**
     * @param z
     *            the z to set
     */
    public void setZ(int z) {
        this.z = z;
    }

    public Vector toVector() {
        return new Vector(x, y, z);
    }
}
