package minefantasy.mf2.api.crafting.engineer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;

import java.util.HashMap;

public interface ICrossbowPart {
    public static final HashMap<String, ICrossbowPart> components = new HashMap<String, ICrossbowPart>();

    /**
     * The type of component only supported types (minecase, bombcase, filling)
     */
    public String getComponentType();

    /**
     * The id, this depends on the component type
     */
    public int getID();

    /**
     * The Icon for the finished Model
     */
    @SideOnly(Side.CLIENT)
    public IIcon getIcon();

    /**
     * Gets the name for the part (mostly an adjective like "Heavy" or "Repeating")
     */
    public String getUnlocalisedName();

    /**
     * Modifies a value by name (such as "power" or "spread"), "capacity" is treated
     * as an int
     */
    public float getModifier(String type);

    /**
     * Only for stocks, whether it's defined as a hand crossbow
     */
    public boolean makesSmallWeapon();
}
