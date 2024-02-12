package minefantasy.mfr.client.render.block;

import minefantasy.mfr.api.crafting.exotic.ISpecialCraftItem;
import minefantasy.mfr.api.heating.IHotItem;
import minefantasy.mfr.api.weapon.IRackItem;
import minefantasy.mfr.block.BlockAnvilMF;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.item.ItemHeated;
import minefantasy.mfr.item.ItemMetalComponent;
import minefantasy.mfr.tile.TileEntityAnvil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.oredict.OreDictionary;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;

public class TileEntityAnvilRenderer<T extends TileEntity> extends TileEntitySpecialRenderer<T> {

	private final int[][] GRID_LAYOUT = {{0, 1, 2, 3, 4, 5}, {6, 7, 8, 9, 10, 11}, {12, 13, 14, 15, 16, 17}, {18, 19, 20, 21, 22, 23}};


	public TileEntityAnvilRenderer() {}

	@Override
	public void render(@Nonnull T tile, double x, double y, double z, float partialTick, int breakStage, float alpha) {
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);

		if (tile instanceof TileEntityAnvil) {
			EnumFacing facing = EnumFacing.NORTH;
			if (tile.hasWorld()) {
				IBlockState state = tile.getWorld().getBlockState(tile.getPos());
				facing = state.getValue(BlockAnvilMF.FACING);
			}

			ItemStack resultStack = ((TileEntityAnvil) tile).getStaticResultStack();

			if (resultStack.getItem() instanceof ItemBlock) {
				resultStack = ItemStack.EMPTY;
			}

			ItemStackHandler inventory = ((TileEntityAnvil) tile).getInventory();

			ItemStack outputSlotStack = inventory.getStackInSlot(24);
			if (((TileEntityAnvil) tile).progress <= 0
					&& (outputSlotStack.isEmpty() || outputSlotStack.getItem() instanceof ISpecialCraftItem)) {
				for (int i = 0; i < 6; i++) {
					for (int j = 0; j < 4; j++) {
						renderItemInSlot(inventory.getStackInSlot(GRID_LAYOUT[j][i]), i, j, 0.075F, facing);
					}
				}
			} else {
				ItemStack outputStack = resultStack;
				if (!outputSlotStack.isEmpty() && !(outputSlotStack.getItem() instanceof ISpecialCraftItem)) {
					outputStack = outputSlotStack;
				}
				renderResultStack(outputStack, facing, (TileEntityAnvil) tile);
			}
		}

		GlStateManager.popMatrix();
	}

	private void renderResultStack(ItemStack stack, EnumFacing facing, TileEntityAnvil tile) {
		if (!stack.isEmpty()) {
			GlStateManager.pushMatrix();

			float rotate = 0F;
			float translateX = 0;
			float translateZ = 0;
			float scale = getItemScale(stack);

			switch (facing) {
				case NORTH:
					translateX = 0.6F;
					translateZ = 0.5F;
					rotate = 120F;
					break;
				case SOUTH:
					translateX = 0.4F;
					translateZ = 0.5F;
					rotate = 300F;
					break;
				case WEST:
					translateX = 0.5F;
					translateZ = 0.4F;
					rotate = 30F;
					break;
				case EAST:
					translateX = 0.5F;
					translateZ = 0.6F;
					rotate = 210F;
					break;
			}

			GlStateManager.translate(0F, 0.83F, 0F );
			GlStateManager.translate(0F, 0F, translateZ);
			GlStateManager.translate(translateX, 0F, 0F);
			GlStateManager.rotate(90F, 1F, 0F,0F);
			GlStateManager.rotate(rotate, 0F, 0F, 1F);
			GlStateManager.rotate(tile.getItemRotation(), 1F, 1F, 1F);
			GlStateManager.scale(scale, scale, scale);

			ItemStack hotOutput = stack;
			if (!(stack.getItem() instanceof IHotItem)) {
				hotOutput = ItemHeated.createHotItem(stack, tile.calcAverageTemp());
			}

			bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);
			RenderHelper.disableStandardItemLighting();
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

			Minecraft.getMinecraft().getRenderItem().renderItem(hotOutput, ItemCameraTransforms.TransformType.NONE);

			GlStateManager.enableLighting();
			GlStateManager.disableBlend();
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

			GlStateManager.popMatrix();
		}
	}

	public void renderItemInSlot(ItemStack stack, int i, int j, float scale, EnumFacing facing) {
		GlStateManager.pushMatrix();

		float rotate = 0F;
		float translateX = 0;
		float translateZ = 0;

		switch (facing) {
			case NORTH:
				translateX = 0.25F;
				translateZ = 0.35F;
				break;
			case SOUTH:
				translateX = -0.75F;
				translateZ = -0.65F;
				rotate = 180F;
				break;
			case WEST:
				translateX = -.75F;
				translateZ = 0.35F;
				rotate = 90F;
				break;
			case EAST:
				translateX = 0.25F;
				translateZ = -0.65F;
				rotate = 270F;
				break;
		}

		GlStateManager.rotate(rotate, 0F, 1F, 0F);

		GlStateManager.translate(0F, 0.82F, 0F);
		GlStateManager.translate(translateX + i/10F, 0F, 0F);
		GlStateManager.translate(0F, 0, translateZ + j/10F);

		GlStateManager.rotate(90F, 1F, 0F,0F);
		GlStateManager.rotate(90F, 0F, 0F, 1F);

		GlStateManager.scale(scale, scale, scale);

		bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);
		RenderHelper.disableStandardItemLighting();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		Minecraft.getMinecraft().getRenderItem().renderItem(stack, Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(stack, null, null));
		GlStateManager.enableLighting();
		GlStateManager.disableBlend();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.popMatrix();
	}

	private float getItemScale(ItemStack stack) {
		float scale = 0.5F;
		if (stack.getItem() instanceof IRackItem) {
			float rackScale = ((IRackItem) stack.getItem()).getScale(stack);
			if (rackScale > 1F) {
				scale = 1.25F;
			}
		}
		else if (stack.getItem() instanceof ItemMetalComponent) {
			scale = isArrowHead(stack.getItem()) ? 0.25F : 0.4F;
		}
		else if (OreDictionary.getOres("listAllIngots").stream().anyMatch(s -> stack.getItem() == s.getItem())) {
			scale = 0.25F;
		}
		return scale;
	}

	private boolean isArrowHead(Item item) {
		return item == MineFantasyItems.ARROWHEAD || item == MineFantasyItems.BODKIN_HEAD || item == MineFantasyItems.BROAD_HEAD;
	}
}
