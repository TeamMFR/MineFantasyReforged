package minefantasy.mfr.client.gui;

import codechicken.lib.texture.TextureUtils;
import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.init.MineFantasyKnowledgeList;
import minefantasy.mfr.mechanics.RPGElements;
import minefantasy.mfr.mechanics.knowledge.InformationBase;
import minefantasy.mfr.mechanics.knowledge.InformationList;
import minefantasy.mfr.mechanics.knowledge.InformationPage;
import minefantasy.mfr.mechanics.knowledge.ResearchLogic;
import minefantasy.mfr.network.NetworkHandler;
import minefantasy.mfr.network.ResearchRequestPacket;
import minefantasy.mfr.util.GuiHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiKnowledgeMenu extends GuiScreen {
	private static final int columnMin = InformationList.minDisplayColumn * 24 - 112;
	private static final int rowMin = InformationList.minDisplayRow * 24 - 112;
	private static final int columnMax = InformationList.maxDisplayColumn * 24 - 77;
	private static final int rowMax = InformationList.maxDisplayRow * 24 - 77;
	private static final ResourceLocation screenTex = new ResourceLocation(MineFantasyReborn.MOD_ID, "textures/gui/knowledge/knowledge.png");
	private static final ResourceLocation buyTex = new ResourceLocation(MineFantasyReborn.MOD_ID, "textures/gui/knowledge/purchase.png");
	private static final ResourceLocation skillTex = new ResourceLocation(MineFantasyReborn.MOD_ID, "textures/gui/knowledge/skill_list.png");
	protected static int informationWidth = 256;
	protected static int informationHeight = 202;
	protected static int mouseX;
	protected static int mouseY;
	protected static float scaleMultiplier = 1.0F;
	protected static double displayColumnModified1;
	protected static double displayColumnModified2;
	protected static double displayColumnModified3;
	protected static double displayRowModified1;
	protected static double displayRowModified2;
	protected static double displayRowModified3;
	private static int mouseDistanceBooleanCheck;
	private static boolean allDiscovered = true;
	private static int currentPage = -1;
	public int buyWidth = 225;
	public int buyHeight = 72;
	int offsetByX = 70;
	int offsetByY = 0;
	private InformationBase selected = null;
	private InformationBase highlighted = null;
	private GuiButton button;
	private LinkedList<InformationBase> informationList = new LinkedList<InformationBase>();
	private EntityPlayer player;
	private boolean hasScroll = false;
	private boolean canPurchase = false;

	public GuiKnowledgeMenu(EntityPlayer user) {
		this.player = user;
		short short1 = 141;
		short short2 = 141;
		GuiKnowledgeMenu.displayColumnModified1 = GuiKnowledgeMenu.displayColumnModified2 = GuiKnowledgeMenu.displayColumnModified3 = MineFantasyKnowledgeList.gettingStarted.displayColumn * 24 - short1 / 2 - 12;
		GuiKnowledgeMenu.displayRowModified1 = GuiKnowledgeMenu.displayRowModified2 = GuiKnowledgeMenu.displayRowModified3 = MineFantasyKnowledgeList.gettingStarted.displayRow * 24 - short2 / 2;
		informationList.clear();
		for (Object achievement : InformationList.knowledgeList) {
			if (!InformationPage.isInfoInPages((InformationBase) achievement)) {
				informationList.add((InformationBase) achievement);
			}
		}
		for (Object base : InformationList.knowledgeList.toArray()) {
			if (!ResearchLogic.hasInfoUnlocked(user, (InformationBase) base)) {
				allDiscovered = false;
				break;
			}
		}
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		int i1 = (this.width - GuiKnowledgeMenu.informationWidth) / 2 + offsetByX;
		int j1 = (this.height - GuiKnowledgeMenu.informationHeight) / 2 + offsetByY;

		this.buttonList.clear();
		this.buttonList.add(new GuiOptionButton(1, this.width / 2 + 24, this.height / 2 + 101, I18n.format("gui.done")));
		this.buttonList.add(button = new GuiButton(2, (width - informationWidth) / 2 + 24, height / 2 + 101, 125, 20, InformationPage.getTitle(currentPage)));

		int purchasex = i1 + (informationWidth - buyWidth) / 2;
		int purchasey = j1 + (informationHeight - buyHeight) / 2;
		// PURCHASE SCREEN
		this.buttonList.add(new GuiButton(3, purchasex + 19, purchasey + 47, 81, 20, I18n.format("gui.purchase")));
		this.buttonList.add(new GuiButton(4, purchasex + 125, purchasey + 47, 81, 20, I18n.format("gui.cancel")));
	}

	@Override
	protected void mouseClicked(int x, int y, int button) throws IOException {
		if (selected == null && button == 0 && highlighted != null) {
			if (ResearchLogic.hasInfoUnlocked(player, highlighted) && !highlighted.getPages().isEmpty()) {
				player.openGui(MineFantasyReborn.INSTANCE, NetworkHandler.GUI_RESEARCH_BOOK, player.world, 0, highlighted.ID, 0);
			} else if (highlighted.isEasy() && ResearchLogic.canPurchase(player, highlighted)) {
				selected = highlighted;
				setPurchaseAvailable(player);
			}
		}
		super.mouseClicked(x, y, button);
	}

	@Override
	protected void actionPerformed(GuiButton GuiButton) {
		if (GuiButton.id == 1) {
			this.mc.displayGuiScreen((GuiScreen) null);
		}

		if (selected == null && GuiButton.id == 2) {
			currentPage++;

			if (currentPage >= InformationPage.getInfoPages().size()) {
				currentPage = -1;
			}
			button.displayString = InformationPage.getTitle(currentPage);
		}

		if (GuiButton.id == 3 && selected != null) {
			NetworkHandler.sendToPlayer((EntityPlayerMP) player, new ResearchRequestPacket(player, selected.ID));
			selected = null;
		}
		if (GuiButton.id == 4 && selected != null) {
			selected = null;
		}
	}

	/**
	 * Fired when a key is typed. This is the equivalent of
	 * KeyListener.keyTyped(KeyEvent e).
	 */
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (keyCode == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
			this.mc.displayGuiScreen((GuiScreen) null);
			this.mc.setIngameFocus();
		} else {
			super.keyTyped(typedChar, keyCode);
		}
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		{
			int k;

			if (selected == null && Mouse.isButtonDown(0)) {
				k = (this.width - GuiKnowledgeMenu.informationWidth) / 2 + offsetByX;
				int l = (this.height - GuiKnowledgeMenu.informationHeight) / 2 + offsetByY;
				int i1 = k + 8;
				int j1 = l + 17;

				if ((GuiKnowledgeMenu.mouseDistanceBooleanCheck == 0 || GuiKnowledgeMenu.mouseDistanceBooleanCheck == 1) && mouseX >= i1 && mouseX < i1 + 224 && mouseY >= j1 && mouseY < j1 + 155) {
					if (GuiKnowledgeMenu.mouseDistanceBooleanCheck == 0) {
						GuiKnowledgeMenu.mouseDistanceBooleanCheck = 1;
					} else {
						GuiKnowledgeMenu.displayColumnModified2 -= (mouseX - GuiKnowledgeMenu.mouseX) * GuiKnowledgeMenu.scaleMultiplier;
						GuiKnowledgeMenu.displayRowModified2 -= (mouseY - GuiKnowledgeMenu.mouseY) * GuiKnowledgeMenu.scaleMultiplier;
						GuiKnowledgeMenu.displayColumnModified3 = GuiKnowledgeMenu.displayColumnModified1 = GuiKnowledgeMenu.displayColumnModified2;
						GuiKnowledgeMenu.displayRowModified3 = GuiKnowledgeMenu.displayRowModified1 = GuiKnowledgeMenu.displayRowModified2;
					}

					GuiKnowledgeMenu.mouseX = mouseX;
					GuiKnowledgeMenu.mouseY = mouseY;
				}
			} else {
				GuiKnowledgeMenu.mouseDistanceBooleanCheck = 0;
			}

			k = Mouse.getDWheel();
			float f4 = GuiKnowledgeMenu.scaleMultiplier;

			if (k < 0) {
				GuiKnowledgeMenu.scaleMultiplier += 0.25F;
			} else if (k > 0) {
				GuiKnowledgeMenu.scaleMultiplier -= 0.25F;
			}

			GuiKnowledgeMenu.scaleMultiplier = MathHelper.clamp(GuiKnowledgeMenu.scaleMultiplier, 1.0F, 3.0F);

			if (GuiKnowledgeMenu.scaleMultiplier != f4) {
				float f6 = f4 - GuiKnowledgeMenu.scaleMultiplier;
				float f5 = f4 * GuiKnowledgeMenu.informationWidth;
				float f1 = f4 * GuiKnowledgeMenu.informationHeight;
				float f2 = GuiKnowledgeMenu.scaleMultiplier * GuiKnowledgeMenu.informationWidth;
				float f3 = GuiKnowledgeMenu.scaleMultiplier * GuiKnowledgeMenu.informationHeight;
				GuiKnowledgeMenu.displayColumnModified2 -= (f2 - f5) * 0.5F;
				GuiKnowledgeMenu.displayRowModified2 -= (f3 - f1) * 0.5F;
				GuiKnowledgeMenu.displayColumnModified3 = GuiKnowledgeMenu.displayColumnModified1 = GuiKnowledgeMenu.displayColumnModified2;
				GuiKnowledgeMenu.displayRowModified3 = GuiKnowledgeMenu.displayRowModified1 = GuiKnowledgeMenu.displayRowModified2;
			}

			if (GuiKnowledgeMenu.displayColumnModified3 < columnMin) {
				GuiKnowledgeMenu.displayColumnModified3 = columnMin;
			}

			if (GuiKnowledgeMenu.displayRowModified3 < rowMin) {
				GuiKnowledgeMenu.displayRowModified3 = rowMin;
			}

			if (GuiKnowledgeMenu.displayColumnModified3 >= columnMax) {
				GuiKnowledgeMenu.displayColumnModified3 = columnMax - 1;
			}

			if (GuiKnowledgeMenu.displayRowModified3 >= rowMax) {
				GuiKnowledgeMenu.displayRowModified3 = rowMax - 1;
			}

			GlStateManager.disableDepth();
			this.drawDefaultBackground();
			this.renderMainPage(mouseX, mouseY, f);
			this.drawOverlay();
		}
		if (buttonList.get(2) != null) {
			((GuiButton) buttonList.get(2)).visible = selected != null;
			((GuiButton) buttonList.get(2)).enabled = selected != null && canPurchase;
			((GuiButton) buttonList.get(3)).visible = selected != null;
		}
	}

	/**
	 * Called from the main game loop to update the screen.
	 */
	@Override
	public void updateScreen() {
		{
			GuiKnowledgeMenu.displayColumnModified1 = GuiKnowledgeMenu.displayColumnModified2;
			GuiKnowledgeMenu.displayRowModified1 = GuiKnowledgeMenu.displayRowModified2;
			double d0 = GuiKnowledgeMenu.displayColumnModified3 - GuiKnowledgeMenu.displayColumnModified2;
			double d1 = GuiKnowledgeMenu.displayRowModified3 - GuiKnowledgeMenu.displayRowModified2;

			if (d0 * d0 + d1 * d1 < 4.0D) {
				GuiKnowledgeMenu.displayColumnModified2 += d0;
				GuiKnowledgeMenu.displayRowModified2 += d1;
			} else {
				GuiKnowledgeMenu.displayColumnModified2 += d0 * 0.85D;
				GuiKnowledgeMenu.displayRowModified2 += d1 * 0.85D;
			}
		}
	}

	protected void drawOverlay() {
		int i = (this.width - GuiKnowledgeMenu.informationWidth) / 2 + offsetByX;
		int j = (this.height - GuiKnowledgeMenu.informationHeight) / 2 + offsetByY;

		this.fontRenderer.drawString(I18n.format("gui.information"), i + 15, j + 5, 4210752);
	}

	protected void renderMainPage(int mx, int my, float f) {
		int k = MathHelper.floor(GuiKnowledgeMenu.displayColumnModified1 + (GuiKnowledgeMenu.displayColumnModified2 - GuiKnowledgeMenu.displayColumnModified1) * f);
		int l = MathHelper.floor(GuiKnowledgeMenu.displayRowModified1 + (GuiKnowledgeMenu.displayRowModified2 - GuiKnowledgeMenu.displayRowModified1) * f);

		if (k < columnMin) {
			k = columnMin;
		}

		if (l < rowMin) {
			l = rowMin;
		}

		if (k >= columnMax) {
			k = columnMax - 1;
		}

		if (l >= rowMax) {
			l = rowMax - 1;
		}

		int i1 = (this.width - GuiKnowledgeMenu.informationWidth) / 2 + offsetByX;
		int j1 = (this.height - GuiKnowledgeMenu.informationHeight) / 2 + offsetByY;
		int k1 = i1 + 16;
		int l1 = j1 + 17;
		this.zLevel = 0.0F;
		GlStateManager.depthFunc(GL11.GL_GEQUAL);
		GlStateManager.pushMatrix();
		GlStateManager.translate(k1, l1, -200.0F);
		GlStateManager.scale(1.0F / GuiKnowledgeMenu.scaleMultiplier, 1.0F / GuiKnowledgeMenu.scaleMultiplier, 0.0F);
		GlStateManager.enableTexture2D();
		GlStateManager.enableRescaleNormal();
		GlStateManager.enableColorMaterial();

		int j2 = l + 288 >> 4;
		int k2 = (k + 288) % 16;
		int l2 = (l + 288) % 16;
		float f1 = 16.0F / GuiKnowledgeMenu.scaleMultiplier;
		float f2 = 16.0F / GuiKnowledgeMenu.scaleMultiplier;
		int i3;
		int j3;
		int k3;

		for (i3 = 0; i3 * f1 - l2 < 155.0F; ++i3) {
			float f3 = 0.6F - (j2 + i3) / 25.0F * 0.3F;
			GlStateManager.color(f3, f3, f3, 1.0F);

			for (j3 = 0; j3 * f2 - k2 < 224.0F; ++j3) {
				TextureAtlasSprite sprite = TextureUtils.getBlockTexture("planks_oak");

				this.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
				this.drawTexturedModalRect(j3 * 16 - k2, i3 * 16 - l2, sprite, 16, 16);
			}
		}

		GlStateManager.enableDepth();
		GlStateManager.depthFunc(GL11.GL_LEQUAL);
		this.mc.getTextureManager().bindTexture(screenTex);
		int researchVisibility;
		int j4;
		int l4;

		List<InformationBase> achievementList = (currentPage == -1 ? informationList
				: InformationPage.getInfoPage(currentPage).getInfoList());
		for (i3 = 0; i3 < achievementList.size(); ++i3) {
			InformationBase achievement1 = achievementList.get(i3);

			if (achievement1.parentInfo != null && achievementList.contains(achievement1.parentInfo)) {
				j3 = achievement1.displayColumn * 24 - k + 11;
				k3 = achievement1.displayRow * 24 - l + 11;
				l4 = achievement1.parentInfo.displayColumn * 24 - k + 11;
				int l3 = achievement1.parentInfo.displayRow * 24 - l + 11;
				boolean flag5 = ResearchLogic.hasInfoUnlocked(player, achievement1);
				boolean flag6 = ResearchLogic.canUnlockInfo(player, achievement1);
				researchVisibility = ResearchLogic.func_150874_c(player, achievement1);
				j4 = -16777216;

				if (flag5) {
					j4 = -6250336;
				} else if (flag6) {
					j4 = -16711936;
				}
				if (researchVisibility <= getVisibleRange()[0] && k3 >= -24 && j3 >= -24 && k3 <= GuiKnowledgeMenu.informationHeight && j3 <= GuiKnowledgeMenu.informationWidth && l4 <= GuiKnowledgeMenu.informationHeight && j4 <= GuiKnowledgeMenu.informationWidth) {

					this.drawHorizontalLine(j3, l4, k3, j4);
					this.drawVerticalLine(l4, k3, l3, j4);

					if (j3 > l4) {
						this.drawTexturedModalRect(j3 - 11 - 7, k3 - 5, 114, 234, 7, 11);
					} else if (j3 < l4) {
						this.drawTexturedModalRect(j3 + 11, k3 - 5, 107, 234, 7, 11);
					} else if (k3 > l3) {
						this.drawTexturedModalRect(j3 - 5, k3 - 11 - 7, 96, 234, 11, 7);
					} else if (k3 < l3) {
						this.drawTexturedModalRect(j3 - 5, k3 + 11, 96, 241, 11, 7);
					}
				}
			}
		}

		InformationBase achievement = null;
		RenderItem renderitem = Minecraft.getMinecraft().getRenderItem();
		float f4 = (mx - k1) * GuiKnowledgeMenu.scaleMultiplier;
		float f5 = (my - l1) * GuiKnowledgeMenu.scaleMultiplier;
		RenderHelper.enableGUIStandardItemLighting();
		GlStateManager.enableRescaleNormal();
		GlStateManager.enableColorMaterial();
		int i5;
		int j5;

		for (l4 = 0; l4 < achievementList.size(); ++l4) {
			InformationBase achievement2 = achievementList.get(l4);
			i5 = achievement2.displayColumn * 24 - k;
			j5 = achievement2.displayRow * 24 - l;

			if (i5 >= -24 && j5 >= -24 && i5 <= 224.0F * GuiKnowledgeMenu.scaleMultiplier && j5 <= 155.0F * GuiKnowledgeMenu.scaleMultiplier) {
				researchVisibility = ResearchLogic.func_150874_c(player, achievement2);
				float f6;

				if (ResearchLogic.hasInfoUnlocked(player, achievement2)) {
					f6 = 0.75F;
					GlStateManager.color(f6, f6, f6, 1.0F);
				} else if (ResearchLogic.canUnlockInfo(player, achievement2)) {
					f6 = 1.0F;
					GlStateManager.color(0.5F, 1.0F, 0.5F, 1.0F);
				} else if (researchVisibility < getVisibleRange()[1]) {
					f6 = 0.3F;
					GlStateManager.color(f6, f6, f6, 1.0F);
				} else if (researchVisibility == getVisibleRange()[1]) {
					f6 = 0.2F;
					GlStateManager.color(f6, f6, f6, 1.0F);
				} else {
					if (researchVisibility != getVisibleRange()[2]) {
						continue;
					}

					f6 = 0.1F;
					GlStateManager.color(f6, f6, f6, 1.0F);
				}

				this.mc.getTextureManager().bindTexture(screenTex);

				GlStateManager.enableBlend();// Forge: Specifically enable blend because it is needed here. And we fix
				// Generic RenderItem's leakage of it.
				if (achievement2.getSpecial()) {
					this.drawTexturedModalRect(i5 - 2, j5 - 2, 26, 202, 26, 26);
				} else if (achievement2.getPerk()) {
					this.drawTexturedModalRect(i5 - 2, j5 - 2, 52, 202, 26, 26);
				} else {
					this.drawTexturedModalRect(i5 - 2, j5 - 2, 0, 202, 26, 26);
				}
				GlStateManager.disableBlend(); // Forge: Cleanup states we set.

				if (!ResearchLogic.canUnlockInfo(player, achievement2)) {
					f6 = 0.1F;
					GlStateManager.color(f6, f6, f6, 1.0F);
				}

				GlStateManager.enableCull();
				renderitem.renderItemAndEffectIntoGUI(achievement2.theItemStack, i5 + 3, j5 + 3);
				GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

				if (f4 >= i5 && f4 <= i5 + 22 && f5 >= j5 && f5 <= j5 + 22) {
					achievement = achievement2;
				}
			}
		}

		GlStateManager.disableDepth();
		GlStateManager.enableBlend();
		GlStateManager.popMatrix();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(screenTex);
		this.drawTexturedModalRect(i1, j1, 0, 0, GuiKnowledgeMenu.informationWidth, GuiKnowledgeMenu.informationHeight);

		this.zLevel = 0.0F;
		GlStateManager.depthFunc(GL11.GL_LEQUAL);
		GlStateManager.disableDepth();
		GlStateManager.enableTexture2D();

		if (selected != null) {
			int purchasex = i1 + (informationWidth - buyWidth) / 2;
			int purchasey = j1 + (informationHeight - buyHeight) / 2;
			renderPurchaseScreen(purchasex, purchasey, mx, my);
		}
		drawSkillList();
		super.drawScreen(mx, my, f);

		highlighted = achievement;
		if (selected == null && achievement != null) {
			String s1 = achievement.getDisplayName();
			String s2 = achievement.getDescription();
			i5 = mx + 12;
			j5 = my - 4;
			researchVisibility = ResearchLogic.func_150874_c(player, achievement);

			if (!ResearchLogic.canUnlockInfo(player, achievement)) {
				String tooltipString;
				int k4;

				if (researchVisibility == 3) {
					s1 = I18n.format("research.unknown");
					j4 = Math.max(this.fontRenderer.getStringWidth(s1), 120);
					tooltipString = I18n.format("research.requires", achievement.parentInfo.getDisplayName());
					k4 = this.fontRenderer.getWordWrappedHeight(tooltipString, j4);
					this.drawGradientRect(i5 - 3, j5 - 3, i5 + j4 + 3, j5 + k4 + 12 + 3, -1073741824, -1073741824);
					this.fontRenderer.drawSplitString(tooltipString, i5, j5 + 12, j4, -9416624);
				} else if (researchVisibility < 3) {
					j4 = Math.max(this.fontRenderer.getStringWidth(s1), 120);

					tooltipString = I18n.format("research.requires", achievement.parentInfo.getDisplayName());
					k4 = this.fontRenderer.getWordWrappedHeight(tooltipString, j4);
					this.drawGradientRect(i5 - 3, j5 - 3, i5 + j4 + 3, j5 + k4 + 12 + 3, -1073741824, -1073741824);
					this.fontRenderer.drawSplitString(tooltipString, i5, j5 + 12, j4, -9416624);
				} else {
					s1 = null;
				}
			} else {
				j4 = Math.max(this.fontRenderer.getStringWidth(s1), 120);
				int k5 = this.fontRenderer.getWordWrappedHeight(s2, j4);

				if (ResearchLogic.hasInfoUnlocked(player, achievement)
						|| ResearchLogic.canUnlockInfo(player, achievement)) {
					k5 += 12;
				}

				this.drawGradientRect(i5 - 3, j5 - 3, i5 + j4 + 3, j5 + k5 + 3 + 12, -1073741824, -1073741824);
				this.fontRenderer.drawSplitString(s2, i5, j5 + 12, j4, -6250336);

				if (ResearchLogic.hasInfoUnlocked(player, achievement)) {
					this.fontRenderer.drawStringWithShadow(I18n.format("information.discovered"), i5,
							j5 + k5 + 4, -7302913);
				} else if (InformationBase.easyResearch && ResearchLogic.canUnlockInfo(player, achievement)) {
					this.fontRenderer.drawStringWithShadow(
							I18n.format("information.buy"), i5,
							j5 + k5 + 4, -7302913);
				}
			}

			if (s1 != null) {
				this.fontRenderer.drawStringWithShadow(s1, i5, j5,
						ResearchLogic.canUnlockInfo(player, achievement) ? (achievement.getSpecial() ? -128 : -1)
								: (achievement.getSpecial() ? -8355776 : -8355712));
			}
		}

		GlStateManager.enableDepth();
		RenderHelper.disableStandardItemLighting();
	}

	/**
	 * Returns true if this GUI should pause the game when it is displayed in
	 * single-player
	 */
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	private int[] getVisibleRange() {
		return new int[] {1, 2, 3};
	}

	private void renderPurchaseScreen(int x, int y, int mx, int my) {
		if (selected != null) {
			String[] requirements = selected.getRequiredSkills();
			int size = 0;
			if (requirements != null) {
				size = requirements.length;
			}
			this.mc.getTextureManager().bindTexture(buyTex);
			this.drawTexturedModalRect(x, y, 0, 0, buyWidth, 27);// Top
			for (int a = 0; a < size; a++) {
				this.drawTexturedModalRect(x, y + 27 + (a * 19), 0, 27, buyWidth, 19);// Middle
			}
			this.drawTexturedModalRect(x, y + 27 + (size * 19), 0, 46, buyWidth, 26);// Bottom

			if (buttonList.get(2) != null && buttonList.get(3) != null) {
				int j1 = (this.height - GuiKnowledgeMenu.informationHeight) / 2;
				int purchasey = j1 + (informationHeight - buyHeight) / 2;

				int offset = -19;
				if (requirements != null) {
					offset += (19 * requirements.length);
				}
				((GuiButton) buttonList.get(2)).y = purchasey + 47 + offset;
				((GuiButton) buttonList.get(3)).y = purchasey + 47 + offset;
			}
			int red = GuiHelper.getColourForRGB(220, 0, 0);
			int white = 16777215;
			mc.fontRenderer.drawString(selected.getDisplayName(), x + 22, y + 12, white, false);

			if (hasScroll) {
				mc.fontRenderer.drawStringWithShadow(
						I18n.format("knowledge.has_scroll"), x + 20,
						y + 32, red);
			} else {
				for (int a = 0; a < requirements.length; a++) {
					boolean isUnlocked = selected.isUnlocked(a, mc.player);
					String text = requirements[a];
					mc.fontRenderer.drawStringWithShadow(text, x + 20, y + 32 + (a * 19), isUnlocked ? white : red);
				}
			}
		}
		GlStateManager.color(255, 255, 255);
	}

	private void setPurchaseAvailable(EntityPlayer user) {
		if (selected != null) {
			canPurchase = selected.hasSkillsUnlocked(user);
		} else {
			canPurchase = false;
		}
	}

	protected void drawSkillList() {
		GlStateManager.pushMatrix();

		int skillWidth = 143;
		int skillHeight = 156;
		int x = (this.width - GuiKnowledgeMenu.informationWidth) / 2 - skillWidth + offsetByX;
		int y = (this.height - GuiKnowledgeMenu.informationHeight) / 2 + offsetByY;
		this.mc.getTextureManager().bindTexture(skillTex);

		this.drawTexturedModalRect(x, y, 0, 0, skillWidth, skillHeight);

		drawSkill(x + 20, y + 20, Skill.ARTISANRY);
		drawSkill(x + 20, y + 44, Skill.CONSTRUCTION);
		drawSkill(x + 20, y + 68, Skill.PROVISIONING);
		drawSkill(x + 20, y + 92, Skill.ENGINEERING);
		drawSkill(x + 20, y + 116, Skill.COMBAT);

		drawSkillName(x + 20, y + 20, Skill.ARTISANRY);
		drawSkillName(x + 20, y + 44, Skill.CONSTRUCTION);
		drawSkillName(x + 20, y + 68, Skill.PROVISIONING);
		drawSkillName(x + 20, y + 92, Skill.ENGINEERING);
		drawSkillName(x + 20, y + 116, Skill.COMBAT);

		GlStateManager.popMatrix();
	}

	protected void drawSkill(int x, int y, Skill skill) {
		if (skill != null) {
			int xp = skill.getXP(player)[0];
			int max = skill.getXP(player)[1];
			if (xp > max)
				xp = max;

			float scale = (float) xp / (float) max;
			this.drawTexturedModalRect(x + 22, y + 13, 0, 156, (int) (78F * scale), 5);

			// mc.fontRenderer.drawString(skill.getDisplayName(), x+2, y+1, 0);
			// mc.fontRenderer.drawString(""+level, x+1, y+10, 0);
		}
	}

	protected void drawSkillName(int x, int y, Skill skill) {
		if (skill != null) {
			int level = RPGElements.getLevel(player, skill);
			mc.fontRenderer.drawString(skill.getDisplayName(), x + 2, y + 1, 0);
			mc.fontRenderer.drawString("" + level, x + 1, y + 10, 0);
		}
	}
}