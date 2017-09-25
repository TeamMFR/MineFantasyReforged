package minefantasy.mf2.client.render.mob;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderHorse;

@SideOnly(Side.CLIENT)
public class RenderVanillaHorse extends RenderHorse {

    public RenderVanillaHorse(ModelBase modelBase, float p_i1256_2_)
    {
        super(modelBase, p_i1256_2_);
    }

}