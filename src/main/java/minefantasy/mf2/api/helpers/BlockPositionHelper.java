package minefantasy.mf2.api.helpers;

import net.minecraftforge.common.util.ForgeDirection;

public class BlockPositionHelper {
    // private static ForgeDirection[] FD = new
    // ForgeDirection[]{ForgeDirection.SOUTH, ForgeDirection.WEST,
    // ForgeDirection.NORTH, ForgeDirection.EAST};

    /**
     * Gets the Grid slot in relation to mouse click
     *
     * @param clickX    the area clicked on X (called in blockActivated)
     * @param clickY    the area clicked on Y (called in blockActivated)
     * @param xBound    the x boundry of the block
     * @param xBound2   the x max boundry of the block
     * @param yBound    the y boundry of the block
     * @param yBound2   the y max boundry of the block
     * @param xSlots    the slot count x
     * @param ySlots    the slot count y
     * @param direction the direction the block is facing
     */
    public static int[] getCoordsFor(float clickX, float clickY, float xBound, float xBound2, float yBound,
                                     float yBound2, int xSlots, int ySlots, int direction) {
        if (clickX < xBound || clickX > xBound2 || clickY < yBound || clickY > yBound2) {
            return null;
        }

        clickX -= xBound;
        clickY -= yBound;

        float xMax = xBound2 - xBound;
        float yMax = yBound2 - yBound;

        int xSlot = 0;
        int ySlot = 0;

        float xSpace = xMax / xSlots;
        float ySpace = yMax / ySlots;

        for (int xT = 0; xT < xSlots; xT++) {
            float MinSpace = xSpace * xT;
            float MaxSpace = xSpace * (xT + 1);
            if (clickX < MaxSpace && clickX > MinSpace) {
                xSlot = xT;
            }
        }

        for (int yT = 0; yT < ySlots; yT++) {
            float MinSpace = ySpace * yT;
            float MaxSpace = ySpace * (yT + 1);
            if (clickY < MaxSpace && clickY > MinSpace) {
                ySlot = yT;
            }
        }
        return translateCoords(xSlot, ySlot, xSlots, ySlots, direction);
    }

    /**
     * Translates the coords on angles (NORTH is default)
     *
     * @param x    the slot x
     * @param y    the slot y
     * @param maxX the slot count x
     * @param maxY the slot count y
     * @param dir  the facing direction
     */
    public static int[] translateCoords(int x, int y, int maxX, int maxY, int direction) {
        ForgeDirection dir = ForgeDirection.getOrientation(direction);
        if (dir == ForgeDirection.NORTH) {
            int newX = (maxX - x - 1);
            int newY = (maxY - y - 1);
            return new int[]{newX, newY};
        }

        if (dir == ForgeDirection.WEST) {
            int newX = (y);
            int newY = (maxX - x - 1);
            return new int[]{newX, newY};
        }
        if (dir == ForgeDirection.EAST) {
            int newY = (x);
            int newX = (maxY - y - 1);
            return new int[]{newX, newY};
        }

        return new int[]{x, y};
    }
}
