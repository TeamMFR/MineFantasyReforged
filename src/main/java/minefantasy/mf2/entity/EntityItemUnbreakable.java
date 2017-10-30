package minefantasy.mf2.entity;

import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.item.ItemExpireEvent;

import java.util.Iterator;

public class EntityItemUnbreakable extends EntityItem {
    public EntityItemUnbreakable(World world) {
        super(world);
    }

    public EntityItemUnbreakable(World world, EntityItem parent) {
        super(world, parent.posX, parent.posY, parent.posZ, parent.getEntityItem());
        this.mimicSpeed(parent);
        delayBeforeCanPickup = parent.delayBeforeCanPickup;
        isImmuneToFire = true;
    }

    public void mimicSpeed(EntityItem parent) {
        this.motionX = parent.motionX;
        this.motionY = parent.motionY;
        this.motionZ = parent.motionZ;
    }

    @Override
    public boolean attackEntityFrom(DamageSource src, float dam) {
        if (isBreakable() && !src.isFireDamage()) {
            return super.attackEntityFrom(src, dam);
        }
        return false;
    }

    @Override
    public void onUpdate() {
        ItemStack stack = this.getDataWatcher().getWatchableObjectItemStack(10);
        if (stack != null && stack.getItem() != null) {
            if (stack.getItem().onEntityItemUpdate(this)) {
                return;
            }
        }

        if (this.getEntityItem() == null) {
            this.setDead();
        } else {
            this.onEntityUpdate();

            if (this.delayBeforeCanPickup > 0) {
                --this.delayBeforeCanPickup;
            }

            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            if (!isBreakable() && posY <= 1.0F) {
                motionY = 0D;
            } else {
                this.motionY -= 0.03999999910593033D;
            }
            this.noClip = this.func_145771_j(this.posX, (this.boundingBox.minY + this.boundingBox.maxY) / 2.0D,
                    this.posZ);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            boolean flag = (int) this.prevPosX != (int) this.posX || (int) this.prevPosY != (int) this.posY
                    || (int) this.prevPosZ != (int) this.posZ;

            if (flag || this.ticksExisted % 25 == 0) {
                if (this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY),
                        MathHelper.floor_double(this.posZ)).getMaterial() == Material.lava) {
                    this.motionY = 0.20000000298023224D;
                    this.motionX = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F;
                    this.motionZ = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F;
                }

                if (!this.worldObj.isRemote) {
                    this.searchForOtherItemsNearby();
                }
            }

            float f = 0.98F;

            if (this.onGround) {
                f = this.worldObj.getBlock(MathHelper.floor_double(this.posX),
                        MathHelper.floor_double(this.boundingBox.minY) - 1,
                        MathHelper.floor_double(this.posZ)).slipperiness * 0.98F;
            }

            this.motionX *= f;
            this.motionY *= 0.9800000190734863D;
            this.motionZ *= f;

            if (this.onGround) {
                this.motionY *= -0.5D;
            }

            ++this.age;

            ItemStack item = getDataWatcher().getWatchableObjectItemStack(10);

            if (isBreakable()) {
                if (!this.worldObj.isRemote && this.age >= lifespan) {
                    if (item != null) {
                        ItemExpireEvent event = new ItemExpireEvent(this,
                                (item.getItem() == null ? 6000 : item.getItem().getEntityLifespan(item, worldObj)));
                        if (MinecraftForge.EVENT_BUS.post(event)) {
                            lifespan += event.extraLife;
                        } else {
                            this.setDead();
                        }
                    } else {
                        this.setDead();
                    }
                }
            }

            if (item != null && item.stackSize <= 0) {
                this.setDead();
            }
        }
    }

    private boolean isBreakable() {
        return false;
    }

    private void searchForOtherItemsNearby() {
        Iterator iterator = this.worldObj
                .getEntitiesWithinAABB(EntityItem.class, this.boundingBox.expand(0.5D, 0.0D, 0.5D)).iterator();

        while (iterator.hasNext()) {
            EntityItem entityitem = (EntityItem) iterator.next();
            this.combineItems(entityitem);
        }
    }

    @Override
    public void playSound(String sound, float volume, float pitch) {
        if (sound.equalsIgnoreCase("random.fizz")) {
            return;
        }
        this.worldObj.playSoundAtEntity(this, sound, volume, pitch);
    }
}
