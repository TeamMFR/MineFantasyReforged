package minefantasy.mfr.client.render.block;

import minefantasy.mfr.api.weapon.IRackItem;
import minefantasy.mfr.block.BlockRack;
import minefantasy.mfr.tile.TileEntityRack;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.animation.FastTESR;

public class TileEntityRackRenderer<T extends TileEntity> extends FastTESR<T> {
	@Override
	public void renderTileEntityFast(T te, double x, double y, double z, float partialTick, int breakStage, float partial, BufferBuilder renderer) {
		EnumFacing facing = EnumFacing.NORTH;
		if (te.hasWorld()) {
			IBlockState state = te.getWorld().getBlockState(te.getPos());
			facing = state.getValue(BlockRack.FACING);
		}

		float itemsStart = 2F / 16F;
		float itemsGap = 4F / 16F;

		if (te instanceof TileEntityRack) {
			for (int a = 0; a < 4; a++) {
				float itemX;
				float itemY = 0.3F;
				float itemZ;
				float offset = 12F / 16F;

				GlStateManager.pushMatrix();
				GlStateManager.translate(x, y, z);
				ItemStack stack = ((TileEntityRack) te).getInventory().getStackInSlot(a);

				if (facing == EnumFacing.EAST) {
					itemX = 17F / 16F;
					itemZ = (itemsStart - (a * itemsGap) + offset);
				} else if (facing == EnumFacing.WEST) {
					itemX = -1F / 16F;
					itemZ = itemsStart + (a * itemsGap);
				} else if (facing == EnumFacing.SOUTH) {
					itemX = itemsStart + (a * itemsGap);
					itemZ = -9F / 16F;
				} else {
					itemX = (itemsStart - (a * itemsGap) + offset);
					itemZ = 24F / 16F;
				}
				float r = getRotationForItem(stack.getItem());
				float scale = 1.0F;

				if (stack.getItem() instanceof IRackItem) {
					IRackItem rackitem = (IRackItem) stack.getItem();
					scale = rackitem.getScale(stack);
					itemX += rackitem.getOffsetX(stack);
					itemY += rackitem.getOffsetY(stack);
					itemZ -= rackitem.getOffsetZ(stack);
					r += rackitem.getRotationOffset(stack);
					itemZ -= (scale - 1) / 2 * 5.5F / 16F;
					itemY -= (scale - 1) / 2F;
				}

				GlStateManager.translate(itemX, itemY, itemZ);

				GlStateManager.pushMatrix();
				GlStateManager.rotate(90F + facing.getHorizontalAngle(), 0, 1F, 0);
				GlStateManager.rotate(r, 0, 0, 1F);
				GlStateManager.scale(scale, scale, 1);

				GlStateManager.disableLighting();
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 15 * 16, 15 * 16);

				renderHungItem(stack);

				GlStateManager.enableLighting();

				GlStateManager.popMatrix();

				GlStateManager.popMatrix();
			}
		}
	}

	private void renderHungItem(ItemStack stack) {
		if (!stack.isEmpty()) {
			GlStateManager.pushMatrix();
			GlStateManager.scale(0.85F, 0.85F, 0.85F);

			float f4 = 0.0F;
			float f5 = 0.3F;
			GlStateManager.translate(-f4, -f5, 0.0F);
			GlStateManager.translate(-0.9375F, -0.0625F, 0.0F);
			Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.FIXED);

			GlStateManager.popMatrix();
		}
	}

	private float getRotationForItem(Item item) {
		String classname = item.getClass().getName();
		if (classname.endsWith("ItemCrossbow") || classname.endsWith("ItemBlunderbuss")
				|| classname.endsWith("ItemBlowgun") || classname.endsWith("ItemMusket")) {
			return 45F;
		}
		return -45F;
	}
}
