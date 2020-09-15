package minefantasy.mfr.item;

import minefantasy.mfr.api.knowledge.IArtefact;
import minefantasy.mfr.block.decor.BlockSchematic;
import minefantasy.mfr.init.CreativeTabMFR;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ItemArtefact extends ItemBaseMFR implements IArtefact {
	public static final String MYTHIC = "mythic_artefacts";
	public static final String DWARVEN = "dwarven_artefacts";
	public static final String GNOMISH = "gnomish_artefacts";

	public final int studyTime;
	public final EnumRarity rarity;
	public final String[] researches;
	public final String lootType;
	public final int dropWeight;

	public ItemArtefact(String name, EnumRarity rarity, String lootType, int dropChance) {
		this(name, 0, rarity, lootType, dropChance);
	}

	public ItemArtefact(String name, int studyTime, EnumRarity rarity, String lootType, int dropChance, String... researches) {
		super(name);
		this.studyTime = studyTime;
		this.rarity = rarity;
		this.researches = researches;
		this.lootType = lootType;
		this.dropWeight = dropChance;

		this.setCreativeTab(CreativeTabMFR.tabMaterialsMFR);
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return rarity;
	}

	@Override
	public int getStudyTime(ItemStack item) {
		return studyTime;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer user, EnumHand hand) {
		ItemStack item = user.getHeldItem(hand);
		if (!world.isRemote) {
			RayTraceResult movingobjectposition = this.rayTrace(world, user, false);

			if (movingobjectposition == null) {
				return ActionResult.newResult(EnumActionResult.PASS, item);
			} else {
				if (BlockSchematic.useSchematic(item, world, user, movingobjectposition)) {
					item.shrink(1);
					return ActionResult.newResult(EnumActionResult.PASS, item);
				}
			}
		}
		return ActionResult.newResult(EnumActionResult.FAIL, item);
	}
}
