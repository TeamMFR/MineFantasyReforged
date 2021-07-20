package minefantasy.mfr.item;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.api.tier.IToolMaterial;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.init.MineFantasyTabs;
import minefantasy.mfr.mechanics.StaminaBar;
import minefantasy.mfr.proxy.IClientRegister;
import minefantasy.mfr.util.ModelLoaderHelper;
import minefantasy.mfr.util.ToolHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

/**
 * @author Anonymous Productions
 */
public class ItemClimbingPick extends ItemPickaxe implements IToolMaterial, IClientRegister {
	private final Random rand = new Random();
	private final int itemRarity;

	public ItemClimbingPick(String name, ToolMaterial material, int rarity) {
		super(material);
		itemRarity = rarity;
		setRegistryName(name);
		setUnlocalizedName(name);

		setCreativeTab(MineFantasyTabs.tabGadget);
		setMaxDamage(material.getMaxUses());

		MineFantasyReforged.PROXY.addClientRegister(this);
	}

	public static boolean tryPerformAbility(EntityPlayer user, float points) {
		if (StaminaBar.isSystemActive && StaminaBar.doesAffectEntity(user)) {
			points *= StaminaBar.getClimbinbDecayModifier(user, true);
			if (StaminaBar.isStaminaAvailable(user, points, true)) {
				ItemWeaponMFR.applyFatigue(user, points);
				return true;
			} else {
				return false;
			}
		}
		return true;
	}

	@Override
	public EnumRarity getRarity(ItemStack item) {
		int lvl = itemRarity + 1;

		if (item.isItemEnchanted()) {
			if (lvl == 0) {
				lvl++;
			}
			lvl++;
		}
		if (lvl >= MineFantasyItems.RARITY.length) {
			lvl = MineFantasyItems.RARITY.length - 1;
		}
		return MineFantasyItems.RARITY[lvl];
	}

	@Override
	public ToolMaterial getMaterial() {
		return toolMaterial;
	}

	@Override
	public int getMaxDamage(ItemStack stack) {
		return ToolHelper.setDuraOnQuality(stack, super.getMaxDamage());
	}

	public boolean isInWall(EntityPlayer player, boolean init) {
		if (player.getHeldItemMainhand().isEmpty())
			return false;

		World world = player.world;
		RayTraceResult rayTraceResult = this.rayTrace(world, player, true);

		if (rayTraceResult == null) {
			return false;
		} else {
			if (rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) {
				BlockPos pos = rayTraceResult.getBlockPos();

				NBTTagCompound nbt = getOrCreateNBT(player.getHeldItemMainhand());
				if (init) {
					nbt.setInteger("MF_HeldPosX", pos.getX());
					nbt.setInteger("MF_HeldPosY", pos.getY());
					nbt.setInteger("MF_HeldPosZ", pos.getZ());
				} else {
					int x1 = nbt.getInteger("MF_HeldPosX");
					int y1 = nbt.getInteger("MF_HeldPosY");
					int z1 = nbt.getInteger("MF_HeldPosZ");

					if (x1 != pos.getX() || y1 != pos.getY() || z1 != pos.getZ()) {
						player.stopActiveHand();
						return false;
					}
				}

				IBlockState block = world.getBlockState(pos);

				if (player.posY < (pos.getY() + 2.8F) && player.posY > (pos.getY() - 3)
						&& player.getDistance(pos.getX() + 0.5, player.posY, pos.getZ() + 0.5) < 1D && player.fallDistance < 5
						&& block.getMaterial().isSolid() && block.isOpaqueCube()) {
					return true;
				}
			}
		}
		return false;
	}

	private NBTTagCompound getOrCreateNBT(ItemStack item) {
		if (!item.hasTagCompound()) {
			item.setTagCompound(new NBTTagCompound());
		}
		return item.getTagCompound();
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
		float cost = 0.5F;
		if (stack.getItemDamage() >= stack.getMaxDamage()) {
			player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
		}
		World world = player.world;
		RayTraceResult rayTraceResult = this.rayTrace(world, (EntityPlayer) player, true);

		if (rayTraceResult == null) {
			if (!world.isRemote && StaminaBar.isSystemActive && !tryPerformAbility((EntityPlayer) player, cost)) {
				player.stopActiveHand();
			}
			return;
		} else {
			if (rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) {
				BlockPos pos = rayTraceResult.getBlockPos();
				IBlockState block = world.getBlockState(pos);

				if (isInWall((EntityPlayer) player, false)) {
					if (!player.isHandActive()) {
						cost *= 0.5F;
						player.motionY = 0;
					} else {

						player.fallDistance = 0;
						if (!player.isSneaking() && player.posY < pos.getY() + 3) {
							player.motionY = 0.05D;
						} else if (player.isSneaking() && player.posY > pos.getY() - 2) {
							player.motionY = -0.05D;
							cost *= 0.75F;
						} else {
							player.motionY = 0;
							cost *= 0.5F;
						}
					}
				}
			}
		}
		if (!world.isRemote && StaminaBar.isSystemActive && !tryPerformAbility((EntityPlayer) player, cost)) {
			player.stopActiveHand();
		}
	}

	@Override
	public EnumAction getItemUseAction(ItemStack item) {
		return EnumAction.BOW;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer user, EnumHand hand) {
		ItemStack item = user.getHeldItem(hand);
		if (user.isHandActive())
			return ActionResult.newResult(EnumActionResult.PASS, item);
		if (StaminaBar.isSystemActive && !StaminaBar.isAnyStamina(user, true)) {
			return ActionResult.newResult(EnumActionResult.PASS, item);
		}

		if (isInWall(user, true)) {
			user.setActiveHand(hand);
			if (!world.isRemote) {
				item.damageItem(1, user);
			}
			user.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 2.0F);
		}
		return ActionResult.newResult(EnumActionResult.FAIL, item);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack item) {
		return Integer.MAX_VALUE;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerClient() {
		ModelLoaderHelper.registerItem(this);
	}
}
