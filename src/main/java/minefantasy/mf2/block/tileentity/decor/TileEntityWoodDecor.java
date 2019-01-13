package minefantasy.mf2.block.tileentity.decor;

import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.block.decor.BlockWoodDecor;
import minefantasy.mf2.network.NetworkUtils;
import minefantasy.mf2.network.packet.WoodDecorPacket;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;

public abstract class TileEntityWoodDecor extends TileEntity {
    private String tex;
    private CustomMaterial material;

    public TileEntityWoodDecor(String tex) {
        this.tex = tex;
        this.material = CustomMaterial.getMaterial("RefinedWood");
    }

    public TileEntityWoodDecor(String tex, CustomMaterial material) {
        this.tex = tex;
        this.material = material;
    }

    public static int getCapacity(int tier) {
        return (tier) * 4 + 4;
    }

    public CustomMaterial getMaterial() {
        return this.material != null ? this.material : trySetMaterial("RefinedWood");
    }

    public void setMaterial(CustomMaterial material) {
        this.material = material;
    }

    public String getMaterialName() {
        return this.material != null ? material.getName() : "RefinedWood";
    }

    public CustomMaterial trySetMaterial(String materialName) {
        CustomMaterial material = CustomMaterial.getMaterial(materialName);
        if (material != null) {
            this.material = material;
        }
        return this.material;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setString("Material", this.getMaterialName());
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.trySetMaterial(nbt.getString("Material"));
    }

    public int getCapacity() {
        CustomMaterial material = getMaterial();
        if (material != null) {
            return getCapacity(material.tier);
        }
        return getCapacity(0);
    }

    public String getTexName() {
        if (worldObj != null) {
            Block block = worldObj.getBlock(xCoord, yCoord, zCoord);
            if (block instanceof BlockWoodDecor) {
                this.tex = ((BlockWoodDecor) block).getFullTexName();
            }
        }
        return tex;
    }

    public void sendPacketToClient() {
        NetworkUtils.sendToWatchers(new WoodDecorPacket(this).generatePacket(), (WorldServer) worldObj, this.xCoord, this.zCoord);
    }
}
