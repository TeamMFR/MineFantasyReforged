package minefantasy.mfr.client.render;

import minefantasy.mfr.api.archery.IDisplayMFRAmmo;
import minefantasy.mfr.api.archery.IFirearm;
import minefantasy.mfr.api.crafting.IBasicMetre;
import minefantasy.mfr.api.crafting.IQualityBalance;
import minefantasy.mfr.api.tool.IScope;
import minefantasy.mfr.config.ConfigClient;
import minefantasy.mfr.container.ContainerAnvil;
import minefantasy.mfr.container.ContainerCarpenter;
import minefantasy.mfr.entity.EntityCogwork;
import minefantasy.mfr.item.ItemWeaponMFR;
import minefantasy.mfr.material.CustomMaterial;
import minefantasy.mfr.mechanics.AmmoMechanics;
import minefantasy.mfr.mechanics.StaminaBar;
import minefantasy.mfr.tile.TileEntityAnvil;
import minefantasy.mfr.tile.TileEntityCarpenter;
import minefantasy.mfr.tile.TileEntityTanningRack;
import minefantasy.mfr.util.ArmourCalculator;
import minefantasy.mfr.util.GuiHelper;
import minefantasy.mfr.util.PowerArmour;
import minefantasy.mfr.util.TextureHelperMFR;
import minefantasy.mfr.util.ToolHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.awt.*;

@SideOnly(Side.CLIENT)
public class MineFantasyHUD extends Gui {
	private static final Minecraft mc = Minecraft.getMinecraft();
	public static boolean isScoped = false;

	/**
	 * Gets the 2 config values to get the X,Y orient
	 */
	public static int[] getOrientsFor(int screenX, int screenY, int cfgX, int cfgY) {
		int xOrient = cfgX == -1 ? 0 : cfgX == 1 ? screenX : screenX / 2;
		int yOrient = cfgY == -1 ? 0 : cfgY == 1 ? screenY : screenY / 2;

		return new int[] {xOrient, yOrient};
	}

	public void renderViewport() {
		if (mc.player != null) {
			EntityPlayer player = mc.player;

			if (mc.gameSettings.thirdPersonView == 0) {
				if (!player.getHeldItemMainhand().isEmpty() && player.getHeldItemMainhand().getItem() instanceof IScope) {
					renderScope(player.getHeldItemMainhand());
				}
				if (player.getRidingEntity() != null && player.getRidingEntity() instanceof EntityCogwork) {
					renderPowerHelmet((EntityCogwork) player.getRidingEntity());
				}
			}
		}
	}

	public void renderGameOverlay() {
		if (mc.player != null) {
			EntityPlayer player = mc.player;

			if ((mc.currentScreen instanceof GuiInventory || mc.currentScreen instanceof GuiContainerCreative)) {
				renderArmourRating(player);
			} else {
				renderAmmo(player);
			}
			if (StaminaBar.isSystemActive && mc.playerController.gameIsSurvivalOrAdventure() && !PowerArmour.isWearingCogwork(player)) {
				renderStaminaBar(player);
			}
			Entity highlight = getClickedEntity();
			if (highlight instanceof EntityCogwork) {
				lookAtCogwork((EntityCogwork) highlight);
			}

			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

			BlockPos coords = getClickedBlock();
			if (coords == null)
				return;

			World world = player.world;
			TileEntity tile = world.getTileEntity(coords);
			if (tile != null) {
				if (tile instanceof TileEntityAnvil) {
					this.renderCraftMetre(player, (TileEntityAnvil) tile);
				}
				if (tile instanceof TileEntityCarpenter) {
					this.renderCraftMetre(player, (TileEntityCarpenter) tile);
				}
				if (tile instanceof TileEntityTanningRack) {
					this.renderCraftMetre(player, (TileEntityTanningRack) tile);
				}
				if (tile instanceof IBasicMetre) {
					this.renderCraftMetre((IBasicMetre) tile);
				}
				if (tile instanceof IQualityBalance) {
					this.renderQualityBalance((IQualityBalance) tile);
				}
			}
		}
	}

	private void renderScope(ItemStack item) {
		if (item.getItem() instanceof IFirearm) {
			float factor = ((IScope) item.getItem()).getZoom(item);
			if (factor > 0.1F) {
				GlStateManager.pushMatrix();
				ScaledResolution scaledresolution = new ScaledResolution(MineFantasyHUD.mc);
				int width = scaledresolution.getScaledWidth();
				int height = scaledresolution.getScaledHeight();

				bindTexture("textures/gui/scopes/scope_basic.png");
				int xPos = width / 2 - 128;
				int yPos = height / 2 - 128;
				this.drawTexturedModalRect(xPos, yPos, 0, 0, 256, 256);
				isScoped = true;
				GlStateManager.popMatrix();
			}
			else {
				isScoped = false;
			}
		}
	}

	private void lookAtCogwork(EntityCogwork suit) {
		GlStateManager.pushMatrix();
		GlStateManager.color(1.0F, 1.0F, 1.0F);
		ScaledResolution scaledresolution = new ScaledResolution(MineFantasyHUD.mc);
		int width = scaledresolution.getScaledWidth();
		int height = scaledresolution.getScaledHeight();

		renderCogworkFuel(width, height, suit);

		GlStateManager.popMatrix();
	}

	private void renderPowerHelmet(EntityCogwork suit) {
		GlStateManager.pushMatrix();
		GlStateManager.color(1.0F, 1.0F, 1.0F);
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		ScaledResolution scaledresolution = new ScaledResolution(MineFantasyHUD.mc);
		int width = scaledresolution.getScaledWidth();
		int height = scaledresolution.getScaledHeight();

		renderHelmetBlur(width, height);
		renderCogworkFuel(width, height, suit);

		GlStateManager.popMatrix();
	}

	private void renderCogworkFuel(int width, int height, EntityCogwork suit) {

		bindTexture("textures/gui/hud_overlay.png");
		int[] orientationAR = getOrientsFor(width, height, ConfigClient.CF_xOrient, ConfigClient.CF_yOrient);
		int xPos = orientationAR[0] + ConfigClient.CF_xPos;
		int yPos = orientationAR[1] + ConfigClient.CF_yPos;

		this.drawTexturedModalRect(xPos, yPos, 84, 38, 172, 20);
		int i = suit.getMetreScaled(160);
		this.drawTexturedModalRect(xPos + 6 + (160 - i), yPos + 11, 90, 20, i, 3);
	}

	private void renderHelmetBlur(int width, int height) {
		GlStateManager.disableDepth();
		GlStateManager.depthMask(false);
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.disableAlpha();
		bindTexture("textures/gui/scopes/cogwork_helm.png");
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuffer();
		bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		bufferBuilder.pos(0.0D, height, -90.0D).tex(0.0D, 1.0D).endVertex();
		bufferBuilder.pos(width, height, -90.0D).tex(1.0D, 1.0D).endVertex();
		bufferBuilder.pos(width, 0.0D, -90.0D).tex(1.0D, 0.0D).endVertex();
		bufferBuilder.pos(0.0D, 0.0D, -90.0D).tex(0.0D, 0.0D).endVertex();
		tessellator.draw();
		GlStateManager.depthMask(true);
		GlStateManager.enableDepth();
		GlStateManager.enableAlpha();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}

	public BlockPos getClickedBlock() {
		if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK) {
			return new BlockPos(mc.objectMouseOver.getBlockPos());
		}
		return null;
	}

	public Entity getClickedEntity() {
		if (mc.objectMouseOver != null
				&& mc.objectMouseOver.typeOfHit == RayTraceResult.Type.ENTITY) {
			return mc.objectMouseOver.entityHit;
		}
		return null;
	}

	private void renderArmourRating(EntityPlayer player) {
		int base = getBaseRating(player);
		ScaledResolution scaledresolution = new ScaledResolution(MineFantasyHUD.mc);
		int width = scaledresolution.getScaledWidth();
		int height = scaledresolution.getScaledHeight();

		int[] orientationAR = getOrientsFor(width, height, ConfigClient.AR_xOrient, ConfigClient.AR_yOrient);
		int xPosAR = orientationAR[0] + ConfigClient.AR_xPos;
		int yPosAR = orientationAR[1] + ConfigClient.AR_yPos;
		int y = 8;
		if (ArmourCalculator.advancedDamageTypes) {
			mc.fontRenderer.drawStringWithShadow(I18n.format("attribute.armour.protection"), xPosAR,
					yPosAR, Color.WHITE.getRGB());
			displayTraitValue(xPosAR, yPosAR + 8, 0, player, base);
			displayTraitValue(xPosAR, yPosAR + 16, 2, player, base);
			displayTraitValue(xPosAR, yPosAR + 24, 1, player, base);
			y = 32;
		} else {
			displayGeneralAR(xPosAR, yPosAR, player, base);
		}

		float weight = getBaseWeight(player);

		Iterable<ItemStack> armour = mc.player.getArmorInventoryList();
		for (ItemStack stack : armour) {
			weight += ArmourCalculator.getPieceWeight(stack, EntityLiving.getSlotForItemStack(stack));
		}

		String massString = CustomMaterial.getWeightString(weight);
		mc.fontRenderer.drawStringWithShadow(massString, xPosAR, yPosAR + y, Color.WHITE.getRGB());
	}

	private int getBaseRating(EntityPlayer player) {
		if (player.getRidingEntity() != null && player.getRidingEntity() instanceof EntityCogwork) {
			return ((EntityCogwork) player.getRidingEntity()).getArmourRating();
		}
		return 0;
	}

	private float getBaseWeight(EntityPlayer player) {
		if (player.getRidingEntity() != null && player.getRidingEntity() instanceof EntityCogwork) {
			return ((EntityCogwork) player.getRidingEntity()).getWeight();
		}
		return 0;
	}

	private void renderAmmo(EntityPlayer player) {
		ScaledResolution scaledresolution = new ScaledResolution(MineFantasyHUD.mc);
		int width = scaledresolution.getScaledWidth();
		int height = scaledresolution.getScaledHeight();

		int[] orientationAR = getOrientsFor(width, height, ConfigClient.AR_xOrient, ConfigClient.AR_yOrient);

		ItemStack held = player.getHeldItemMainhand();
		if (!held.isEmpty() && (held.getItem() instanceof IDisplayMFRAmmo)) {
			ItemStack arrow = AmmoMechanics.getAmmo(held);

			String text = I18n.format("info.bow.reload");
			if (!arrow.isEmpty()) {
				text = arrow.getDisplayName() + " x" + arrow.getCount();
			}
			int xPosAC = orientationAR[0] + ConfigClient.AC_xPos;
			int yPosAC = orientationAR[1] + ConfigClient.AC_yPos;

			mc.fontRenderer.drawStringWithShadow(text, xPosAC, yPosAC, Color.WHITE.getRGB());

			int cap = ((IDisplayMFRAmmo) held.getItem()).getAmmoCapacity(held);

			ItemStack ammo = AmmoMechanics.getArrowOnBow(held);
			int ammocount = ammo.isEmpty() ? 0 : ammo.getCount();
			if (cap > 1) {
				String ammostring = I18n.format("info.firearm.ammo", ammocount, cap);
				mc.fontRenderer.drawStringWithShadow(ammostring, xPosAC, yPosAC + 10, Color.WHITE.getRGB());
			}
		}
	}

	private void displayTraitValue(int xPosAR, int yPosAR, int id, EntityPlayer player, int base) {
		float AR = (int) (ArmourCalculator.getDRDisplay(player, id) * 100F) + base;
		mc.fontRenderer.drawStringWithShadow(I18n.format("attribute.armour.rating." + id) + " "
				+ ItemWeaponMFR.decimal_format.format(AR), xPosAR, yPosAR, Color.WHITE.getRGB());
	}

	private void displayGeneralAR(int xPosAR, int yPosAR, EntityPlayer player, int base) {
		float AR = ((int) (ArmourCalculator.getDRDisplay(player, 0) * 100F) + base);

		mc.fontRenderer.drawStringWithShadow(I18n.format("attribute.armour.protection") + ": "
				+ ItemWeaponMFR.decimal_format.format(AR), xPosAR, yPosAR, Color.WHITE.getRGB());
	}

	private void renderStaminaBar(EntityPlayer player) {
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		float staminaMax = StaminaBar.getTotalMaxStamina(player);
		float staminaAt = StaminaBar.getStaminaValue(player);

		float staminaPercentage = staminaMax > 0 ? Math.min(1.0F, staminaAt / staminaMax) : 0F;

		float flash = StaminaBar.getFlashTime(player);
		int stam = (int) Math.min(81F, (81F * staminaPercentage));
		ScaledResolution scaledresolution = new ScaledResolution(MineFantasyHUD.mc);
		int width = scaledresolution.getScaledWidth();
		int height = scaledresolution.getScaledHeight();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		bindTexture("textures/gui/hud_overlay.png");

		int[] orientation = getOrientsFor(width, height, ConfigClient.stam_xOrient, ConfigClient.stam_yOrient);
		int xPos = orientation[0] + ConfigClient.stam_xPos;
		int yPos = orientation[1] + ConfigClient.stam_yPos;
		this.drawTexturedModalRect(xPos, yPos, 0, 0, 81, 5);

		int modifier = modifyMetre(stam, 81, ConfigClient.stam_direction);
		this.drawTexturedModalRect(xPos + modifier, yPos, 0, 5, stam, 5);

		if (flash > 0 && player.ticksExisted % 10 < 5) {
			this.drawTexturedModalRect(xPos, yPos, 0, 10, 81, 5);
		}

		String stamTxt = (int) staminaAt + " / " + (int) staminaMax;
		boolean bonus = StaminaBar.getBonusStamina(player) > 0;

		if (mc.currentScreen instanceof GuiInventory) {
			mc.fontRenderer.drawStringWithShadow(stamTxt, xPos + 41 - (mc.fontRenderer.getStringWidth(stamTxt) / 2F),
					yPos - 2, bonus ? Color.CYAN.getRGB() : Color.WHITE.getRGB());
		}
		GlStateManager.disableBlend();
	}

	private int modifyMetre(int i, int max, int cfg) {
		if (cfg == 0) {
			if (max == i) {
				return (max - i) / 2;
			} else {
				return (max - i - 1) / 2;
			}
		}
		if (cfg == 1) {
			if (max == i) {
				return max - i;
			} else {
				return max - i - 1;
			}
		}
		return 0;
	}

	public void bindTexture(String image) {
		mc.renderEngine.bindTexture(TextureHelperMFR.getResource(image));
	}

	private void renderCraftMetre(EntityPlayer player, TileEntityAnvil tile) {
		if (player.openContainer instanceof ContainerAnvil) {
			return;
		}

		boolean knowsCraft = tile.doesPlayerKnowCraft(player);
		GlStateManager.pushMatrix();
		ScaledResolution scaledresolution = new ScaledResolution(MineFantasyHUD.mc);
		int width = scaledresolution.getScaledWidth();
		int height = scaledresolution.getScaledHeight();

		bindTexture("textures/gui/hud_overlay.png");
		int xPos = width / 2 - 86;
		int yPos = height - 69;

		this.drawTexturedModalRect(xPos, yPos, 84, 0, 172, 20);
		this.drawTexturedModalRect(xPos + 6, yPos + 12, 90, 20, tile.getProgressBar(160), 3);

		String s = knowsCraft ? tile.getResultName() : "????";
		mc.fontRenderer.drawString(s, xPos + 86 - (mc.fontRenderer.getStringWidth(s) / 2), yPos + 3, 0);
		GlStateManager.color(1.0F, 1.0F, 1.0F);

		if (knowsCraft && tile.getRequiredToolType() != null) {

			boolean available = ToolHelper.isToolSufficient(player.getHeldItem(EnumHand.MAIN_HAND), tile.getRequiredToolType(), tile.getToolTierNeeded());
			GuiHelper.renderToolIcon(this, tile.getRequiredToolType().getName(), tile.getToolTierNeeded(), xPos - 20, yPos, available);

			if (tile.getRequiredAnvilTier() > -1) {
				GuiHelper.renderToolIcon(this, "anvil", tile.getRequiredAnvilTier(), xPos + 172, yPos, tile.getTier() >= tile.getRequiredAnvilTier());
			}
		}

		GlStateManager.popMatrix();
	}

	private void renderCraftMetre(EntityPlayer player, TileEntityCarpenter tile) {
		if (player.openContainer instanceof ContainerCarpenter) {
			return;
		}

		boolean knowsCraft = tile.doesPlayerKnowCraft(player);
		GlStateManager.pushMatrix();
		ScaledResolution scaledresolution = new ScaledResolution(MineFantasyHUD.mc);
		int width = scaledresolution.getScaledWidth();
		int height = scaledresolution.getScaledHeight();

		bindTexture("textures/gui/hud_overlay.png");
		int xPos = width / 2 - 86;
		int yPos = height - 69;

		this.drawTexturedModalRect(xPos, yPos, 84, 0, 172, 20);
		this.drawTexturedModalRect(xPos + 6, yPos + 12, 90, 20, tile.getProgressBar(160), 3);

		String s = knowsCraft ? tile.getResultName() : "????";
		mc.fontRenderer.drawString(s, xPos + 86 - (mc.fontRenderer.getStringWidth(s) / 2), yPos + 3, 0);
		GlStateManager.color(1.0F, 1.0F, 1.0F);

		if (knowsCraft && !tile.getResultName().equalsIgnoreCase("") && tile.getRequiredToolType() != null) {
			boolean available = ToolHelper.isToolSufficient(player.getHeldItem(EnumHand.MAIN_HAND), tile.getRequiredToolType(), tile.getToolTierNeeded());
			GuiHelper.renderToolIcon(this, tile.getRequiredToolType().getName(), tile.getToolTierNeeded(), xPos - 20, yPos, available);
		}

		GlStateManager.popMatrix();
	}

	private void renderCraftMetre(EntityPlayer player, TileEntityTanningRack tile) {
		boolean knowsCraft = tile.doesPlayerKnowCraft();
		GlStateManager.pushMatrix();
		ScaledResolution scaledresolution = new ScaledResolution(MineFantasyHUD.mc);
		int width = scaledresolution.getScaledWidth();
		int height = scaledresolution.getScaledHeight();

		bindTexture("textures/gui/hud_overlay.png");
		int xPos = width / 2 - 86;
		int yPos = height - 69;

		this.drawTexturedModalRect(xPos, yPos, 84, 0, 172, 20);
		this.drawTexturedModalRect(xPos + 6, yPos + 12, 90, 20, tile.getProgressBar(160), 3);

		String s = knowsCraft ? tile.getResultName() : "????";
		ItemStack result = tile.getInventory().getStackInSlot(1);
		if (!result.isEmpty() && result.getCount() > 1) {
			s += " x" + result.getCount();
		}
		mc.fontRenderer.drawString(s, xPos + 86 - (mc.fontRenderer.getStringWidth(s) / 2), yPos + 3, 0);
		GlStateManager.color(1.0F, 1.0F, 1.0F);

		if (knowsCraft && tile.requiredToolType != null) {
			boolean available = ToolHelper.isToolSufficient(player.getHeldItem(EnumHand.MAIN_HAND), tile.requiredToolType, -1);
			GuiHelper.renderToolIcon(this, tile.requiredToolType.getName(), tile.tier, xPos - 20, yPos, available);
		}

		GlStateManager.popMatrix();
	}

	private void renderCraftMetre(IBasicMetre tile) {
		if (!tile.shouldShowMetre()) {
			return;
		}
		GlStateManager.pushMatrix();
		ScaledResolution scaledresolution = new ScaledResolution(MineFantasyHUD.mc);
		int width = scaledresolution.getScaledWidth();
		int height = scaledresolution.getScaledHeight();

		bindTexture("textures/gui/hud_overlay.png");
		int xPos = width / 2 - 86;
		int yPos = height - 69;

		this.drawTexturedModalRect(xPos, yPos, 84, 0, 172, 20);
		this.drawTexturedModalRect(xPos + 6, yPos + 12, 90, 20, tile.getMetreScale(160), 3);

		String s = tile.getLocalisedName();
		mc.fontRenderer.drawString(s, xPos + 86 - (mc.fontRenderer.getStringWidth(s) / 2), yPos + 3, 0);
		GlStateManager.color(1.0F, 1.0F, 1.0F);
		GlStateManager.popMatrix();
	}

	public void renderQualityBalance(IQualityBalance tile) {
		if (!tile.shouldShowMetre()) {
			return;
		}
		GlStateManager.pushMatrix();
		ScaledResolution scaledresolution = new ScaledResolution(MineFantasyHUD.mc);
		int width = scaledresolution.getScaledWidth();
		int height = scaledresolution.getScaledHeight();

		bindTexture("textures/gui/hud_overlay.png");
		int xPos = width / 2 - 86;
		int yPos = height - 69 + 17;
		int barwidth = 160;
		int centre = xPos + 5 + (barwidth / 2);

		this.drawTexturedModalRect(xPos, yPos, 84, 23, 172, 10);
		int markerPos = (int) (centre + (tile.getMarkerPosition() * barwidth / 2F));
		this.drawTexturedModalRect(markerPos, yPos + 1, 84, 33, 3, 5);

		int offset = (int) (tile.getThresholdPosition() / 2F * barwidth);
		this.drawTexturedModalRect(centre - offset - 1, yPos + 1, 87, 33, 2, 4);
		this.drawTexturedModalRect(centre + offset, yPos + 1, 89, 33, 2, 4);

		int offset2 = (int) (tile.getSuperThresholdPosition() / 2F * barwidth);
		this.drawTexturedModalRect(centre - offset2, yPos + 1, 91, 33, 1, 4);
		this.drawTexturedModalRect(centre + offset2, yPos + 1, 91, 33, 1, 4);

		GlStateManager.color(1.0F, 1.0F, 1.0F);
		GlStateManager.popMatrix();
	}
}
