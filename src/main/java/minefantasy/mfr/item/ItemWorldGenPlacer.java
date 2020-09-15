package minefantasy.mfr.item;

import minefantasy.mfr.mechanics.worldGen.structure.WorldGenAncientAlter;
import minefantasy.mfr.mechanics.worldGen.structure.WorldGenAncientForge;
import minefantasy.mfr.mechanics.worldGen.structure.WorldGenStructureBase;
import minefantasy.mfr.mechanics.worldGen.structure.dwarven.WorldGenDwarvenStronghold;
import minefantasy.mfr.util.MFRLogUtil;
import minefantasy.mfr.util.ModelLoaderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemWorldGenPlacer extends ItemBaseMFR {
	private String structure;

	public ItemWorldGenPlacer(String name, String structure) {
		super(name);
		this.setCreativeTab(CreativeTabs.MISC);
		this.structure = structure;
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer user, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (true) {
			if (user.world.isRemote)
				user.sendMessage(new TextComponentString("This item is currently disabled"));
			return EnumActionResult.PASS;
		}

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
	public String getItemStackDisplayName(ItemStack item) {
		return I18n.format("item.world_gen_placer") + " " + structure;
	}

	private WorldGenStructureBase getWorldGen(String structure) {
		if (structure.equals("WorldGenAncientForge")) {
			return new WorldGenAncientForge();
		} else if (structure.equals("WorldGenAncientAlter")) {
			return new WorldGenAncientAlter();
		} else if (structure.equals("WorldGenDwarvenStronghold")) {
			return new WorldGenDwarvenStronghold();
		}
		return new WorldGenAncientForge();
	}

	@Override
	public void registerClient() {
		// so that we don't need a new icon for all the items, using a generic world_gen_placer.json model
		ModelLoaderHelper.registerItem(this, 0, "world_gen_placer");
	}
}
