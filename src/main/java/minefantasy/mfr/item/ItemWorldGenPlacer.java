package minefantasy.mfr.item;

import minefantasy.mfr.util.MFRLogUtil;
import minefantasy.mfr.util.ModelLoaderHelper;
import minefantasy.mfr.world.gen.structure.WorldGenAncientAltar;
import minefantasy.mfr.world.gen.structure.WorldGenAncientForge;
import minefantasy.mfr.world.gen.structure.WorldGenDwarvenStronghold;
import minefantasy.mfr.world.gen.structure.WorldGenStructureBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemWorldGenPlacer extends ItemBaseMFR {
	private final String structure;

	public ItemWorldGenPlacer(String name, String structure) {
		super(name);
		this.setCreativeTab(CreativeTabs.MISC);
		this.structure = structure;
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer user, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack item = user.getHeldItem(hand);
		if (!user.canPlayerEdit(pos, facing, item)) {
			return EnumActionResult.FAIL;
		} else {
			if (!world.isRemote) {

				WorldGenStructureBase wg = getWorldGen(this.structure);
				if (wg instanceof WorldGenDwarvenStronghold) {
					WorldGenDwarvenStronghold ds = (WorldGenDwarvenStronghold) wg;
					MFRLogUtil.logDebug("DS: Try Cliff Build");
					if (!ds.generate(world, itemRand, pos)) {
						MFRLogUtil.logDebug("Failed... DS: Try Surface Build");
						ds.setSurfaceMode(true);
						if (ds.generate(world, itemRand, pos)) {
							MFRLogUtil.logDebug("Success... DS: Placed Surface Build");
						} else {
							MFRLogUtil.logDebug("Failed... DS: No Build Placed");
						}
					} else {
						MFRLogUtil.logDebug("Success... DS: Placed Cliff Build");
					}
				} else {
					wg.generate(world, itemRand, pos);
				}
			}
			return EnumActionResult.PASS;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getItemStackDisplayName(ItemStack item) {
		return I18n.translateToLocal("item.world_gen_placer") + " " + structure;
	}

	private WorldGenStructureBase getWorldGen(String structure) {
		switch (structure) {
			case "world_gen_ancient_forge":
				return new WorldGenAncientForge();
			case "world_gen_ancient_altar":
				return new WorldGenAncientAltar();
			case "world_gen_dwarven_stronghold":
				return new WorldGenDwarvenStronghold();
		}
		return new WorldGenAncientForge();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerClient() {
		ModelLoaderHelper.registerItem(this, 0, structure);
	}
}
