package minefantasy.mfr.client.render.block;

import minefantasy.mfr.api.weapon.IRackItem;
import minefantasy.mfr.block.BlockRack;
import minefantasy.mfr.tile.TileEntityRack;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public class TileEntityRackRenderer<T extends TileEntity> extends TileEntitySpecialRenderer<T> {

	@Override
	public void render(T te, double x, double y, double z, float partialTick, int breakStage, float partial) {
		EnumFacing facing = EnumFacing.NORTH;
		if (te.hasWorld()) {
			IBlockState state = te.getWorld().getBlockState(te.getPos());
			facing = state.getValue(BlockRack.FACING);
		}

		float itemsStart = 2F / 16F;
		float itemsGap = 4F / 16F;

		if (te instanceof TileEntityRack) {
			for (int a = 0; a < 4; a++) {

				ItemStack stack = ((TileEntityRack) te).getInventory().getStackInSlot(a);
				float itemX;
				float itemY = 0.3F;
				float itemZ;
				float offset = 12F / 16F;
				float r = getRotationForItem(stack.getItem());
				float scale = 1.0F;

				GlStateManager.pushMatrix();
				GlStateManager.translate(x, y, z);

				if (stack.getItem() instanceof IRackItem) {
					IRackItem rack_item = (IRackItem) stack.getItem();
					scale = rack_item.getScale(stack);
					r += rack_item.getRotationOffset(stack);
					itemY += rack_item.getOffsetY(stack);

					if (facing == EnumFacing.EAST) {
						itemX = 17F / 16F;
						itemZ = (itemsStart - (a * itemsGap) + offset);
						itemX -= rack_item.getOffsetX(stack);
					} else if (facing == EnumFacing.WEST) {
						itemX = -1F / 16F;
						itemZ = itemsStart + (a * itemsGap);
						itemX += rack_item.getOffsetX(stack);
					} else if (facing == EnumFacing.SOUTH) {
						itemX = itemsStart + (a * itemsGap);
						itemZ = -8F / 16F;
						itemZ += rack_item.getOffsetZ(stack);
					} else {
						itemX = (itemsStart - (a * itemsGap) + offset);
						itemZ = 24F / 16F;
						itemZ -= rack_item.getOffsetZ(stack);
					}
				}
				else {
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
				}

				GlStateManager.translate(itemX, itemY, itemZ);

				GlStateManager.pushMatrix();

				if (stack.getItem() instanceof IRackItem && ((IRackItem) stack.getItem()).flip(stack) && (facing == EnumFacing.EAST || facing == EnumFacing.WEST)){
					GlStateManager.rotate(90F + facing.getOpposite().getHorizontalAngle(), 0, 1F, 0);
				}
				else {
					GlStateManager.rotate(90F + facing.getHorizontalAngle(), 0, 1F, 0);
				}

				GlStateManager.rotate(r, 0, 0, 1F);
				GlStateManager.scale(scale, scale, 1);

				renderHungItem(stack);

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
		if (classname.endsWith("ItemCrossbow") || classname.endsWith("ItemBlunderbuss") || classname.endsWith("ItemBlowgun") || classname.endsWith("ItemMusket")) {
			return 45F;
		}
		return -45F;
	}
}
