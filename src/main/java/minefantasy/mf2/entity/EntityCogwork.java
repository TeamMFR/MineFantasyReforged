package minefantasy.mf2.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.api.armour.ArmourDesign;
import minefantasy.mf2.api.armour.IPowerArmour;
import minefantasy.mf2.api.helpers.*;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.block.list.BlockListMF;
import minefantasy.mf2.config.ConfigArmour;
import minefantasy.mf2.item.list.ComponentListMF;
import minefantasy.mf2.network.ClientProxyMF;
import minefantasy.mf2.network.packet.CogworkControlPacket;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.ForgeHooks;

public class EntityCogwork extends EntityLivingBase implements IPowerArmour {
    public static final float base_armour_units = 30F;
    private static final float general_step_height = 1.0F;
    private static final float base_frame_weight = 100F;
    public static float base_fuel_minutes = 20F;
    public static int maxBolts = 16;
    public static int allowedBulk = 1;
    public static float rating_modifier = 1.0F;
    public static float health_modifier = 1.0F;
    private int noMoveTime = 0;
    private ItemStack[] items = new ItemStack[]{};
    private float forwardControl, strafeControl;
    private boolean jumpControl;
    private int jumpTimer = 0;
    private boolean alternateStep;

    public EntityCogwork(World world) {
        super(world);
        this.stepHeight = general_step_height;
        this.preventEntitySpawning = true;
        this.setSize(1.5F, 2.5F);
    }

    public EntityCogwork(World world, double posX, double posY, double posZ) {
        this(world);
        this.setPosition(posX, posY, posZ);
    }

    @SideOnly(Side.CLIENT)
    public static int getArmourRating(CustomMaterial base) {
        if (base != null) {
            float ratio = base.hardness * ArmourDesign.COGWORK.getRating() * rating_modifier;
            return (int) (ratio * ArmourCalculator.armourRatingScale);
        }
        return 0;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(12, "");
        this.dataWatcher.addObject(13, Float.valueOf(0F));
        this.dataWatcher.addObject(14, Integer.valueOf(0));
    }

    public int getBolts() {
        return this.dataWatcher.getWatchableObjectInt(14);
    }

    public void setBolts(int value) {
        this.dataWatcher.updateObject(14, value);
    }

    public float getFuel() {
        return this.dataWatcher.getWatchableObjectFloat(13);
    }

    public void setFuel(float level) {
        this.dataWatcher.updateObject(13, level);
    }

    public String getCustomMaterial() {
        return this.dataWatcher.getWatchableObjectString(12);
    }

    public void setCustomMaterial(String name) {
        if (name.length() == 0) {
            setHealth(getMaxHealth());
        }
        this.dataWatcher.updateObject(12, name);
    }

    @Override
    public ItemStack getHeldItem() {
        return null;
    }

    @Override
    public ItemStack getEquipmentInSlot(int p_71124_1_) {
        return null;
    }

    @Override
    public void setCurrentItemOrArmor(int p_70062_1_, ItemStack p_70062_2_) {
    }

    @Override
    public ItemStack[] getLastActiveItems() {
        return items;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (noMoveTime > 0) {
            moveForward = moveStrafing = 0;
            --noMoveTime;
        }

        float fuel = this.getFuel();
        float maxfuel = this.getMaxFuel();
        if (isPowered()) {
            fuel -= getFuelDecay();
        }
        fuel = MathHelper.clamp_float(fuel, 0F, maxfuel);
        setFuel(fuel);

        /*
         * if(isSprinting()) { if(riddenByEntity == null || this.getMoveForward() <= 0
         * || !isPowered()) { setSprinting(false); } }
         */
        if (this.riddenByEntity != null) {
            stepHeight = general_step_height;
            updateRider();
            if (riddenByEntity.isBurning() && this.isFullyArmoured()) {
                riddenByEntity.extinguish();
            }

            // ARROWS
            if (riddenByEntity instanceof EntityLivingBase) {
                int arrows = ((EntityLivingBase) riddenByEntity).getArrowCountInEntity();
                ((EntityLivingBase) riddenByEntity).setArrowCountInEntity(0);
                int my_arrows = this.getArrowCountInEntity();
                this.setArrowCountInEntity(my_arrows + arrows);

                if (this.isFullyArmoured()) {
                    ((EntityLivingBase) riddenByEntity).setAir(300);
                }

                if (ticksExisted % 20 == 0) {
                    for (int a = 0; a < 4; a++) {
                        if (!allowEquipment((EntityLivingBase) riddenByEntity)) {
                            riddenByEntity.mountEntity(null);
                            break;
                        }
                    }
                }
            }

            // DAMAGE
            if (this.motionX * this.motionX + this.motionZ * this.motionZ > 2.500000277905201E-7D
                    && this.rand.nextInt(5) == 0) {
                int i = MathHelper.floor_double(this.posX);
                int j = MathHelper.floor_double(this.posY - 0.20000000298023224D - this.yOffset);
                int k = MathHelper.floor_double(this.posZ);
                Block block = this.worldObj.getBlock(i, j, k);

                if (block.getMaterial() != Material.air) {
                    this.worldObj.spawnParticle(
                            "blockcrack_" + Block.getIdFromBlock(block) + "_" + this.worldObj.getBlockMetadata(i, j, k),
                            this.posX + (this.rand.nextFloat() - 0.5D) * this.width, this.boundingBox.minY + 0.1D,
                            this.posZ + (this.rand.nextFloat() - 0.5D) * this.width,
                            4.0D * (this.rand.nextFloat() - 0.5D), 0.5D, (this.rand.nextFloat() - 0.5D) * 4.0D);
                }
                if (!worldObj.isRemote && ConfigArmour.cogworkGrief) {
                    damageBlock(block, i, j, k, worldObj.getBlockMetadata(i, j, k));
                    block = this.worldObj.getBlock(i, j + 1, k);
                    damageSurface(block, i, j + 1, k, worldObj.getBlockMetadata(i, j, k));
                }
            }
        } else {
            motionX = motionZ = 0;
            this.setMoveForward(0F);
            this.setMoveStrafe(0F);
            stepHeight = 0;
            this.limbSwing = this.limbSwingAmount = this.prevLimbSwingAmount = 0;
            this.rotationPitch = 20F;
            this.rotationYawHead = this.rotationYaw;
            this.prevRotationYawHead = this.prevRotationYaw;
            this.swingProgress = this.swingProgressInt = 0;
        }
        if (jumpTimer > 0) {
            --jumpTimer;
        }
        onPortalTick();
    }

    /**
     * Modifier for actions and fuel decay (weight of the suit)
     */
    private float getFuelCost() {
        float mass = this.getWeight();
        return mass / 200F;
    }

    /**
     * Rate of constant fuel droppage
     *
     * @return
     */
    private float getFuelDecay() {
        return getFuelCost() * (isSprinting() ? 3.0F : 1.0F);
    }

    private void damageBlock(Block block, int x, int y, int z, int blockMetadata) {
        if (block == Blocks.grass || block == Blocks.farmland) {
            worldObj.setBlock(x, y, z, Blocks.dirt, 0, 2);
        }
        if (block.getMaterial() == Material.glass) {
            worldObj.setBlockToAir(x, y, z);
            this.worldObj.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "dig.glass", 1.0F,
                    0.9F + (rand.nextFloat() * 0.2F));
        }
        if (block == Blocks.ice) {
            worldObj.setBlock(x, y, z, Blocks.water, 0, 2);
            this.worldObj.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "dig.glass", 1.0F,
                    0.9F + (rand.nextFloat() * 0.2F));
        }
        if (block.getMaterial() == Material.leaves) {
            worldObj.setBlock(x, y, z, Blocks.water, 0, 2);
            this.worldObj.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "dig.grass", 1.0F,
                    0.9F + (rand.nextFloat() * 0.2F));
        }
    }

    private void damageSurface(Block block, int x, int y, int z, int blockMetadata) {
        if (block.getBlockHardness(worldObj, x, y, z) == 0
                && (block.getMaterial() == Material.vine || block.getMaterial() == Material.plants)) {
            worldObj.setBlockToAir(x, y, z);
            this.worldObj.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "dig.grass", 1.0F,
                    0.9F + (rand.nextFloat() * 0.2F));
        }
        if (block == Blocks.snow_layer) {
            worldObj.setBlockToAir(x, y, z);
            this.worldObj.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "dig.cloth", 1.0F,
                    0.9F + (rand.nextFloat() * 0.2F));
        }
    }

    @Override
    public void knockBack(Entity hitter, float f, double d, double d1) {
    }

    public CustomMaterial getPlating() {
        return CustomMaterial.getMaterial(getCustomMaterial());
    }

    public void updateRider() {
        if (!isPowered())
            return;

        if (worldObj.isRemote && riddenByEntity != null && riddenByEntity instanceof EntityPlayer) {
            float forward = ((EntityPlayer) riddenByEntity).moveForward;
            float strafe = ((EntityPlayer) riddenByEntity).moveStrafing;

            if (riddenByEntity instanceof EntityClientPlayerMP) {
                boolean jump = ClientProxyMF.isUserJumpCommand(riddenByEntity);
                if (jump != jumpControl || forward != forwardControl || strafe != strafeControl) {
                    this.forwardControl = forward;
                    this.strafeControl = strafe;
                    this.jumpControl = jump;

                    ((EntityClientPlayerMP) riddenByEntity).sendQueue
                            .addToSendQueue(new CogworkControlPacket(this).generatePacket());
                }
            }
        }
        if (ticksExisted % 100 == 0) {
            if (rand.nextInt(20) == 0) {
                this.playSound("minefantasy2:entity.cogwork.toot", 0.5F, 1.0F);
            }
            this.playSound("minefantasy2:entity.cogwork.idle", 0.5F, 0.75F + rand.nextFloat() * 0.5F);
        }

        if (!worldObj.isRemote) {
            if (this.jumpControl && jumpTimer == 0) {
                this.jumpTimer = 10;
            }
            if (!this.isInWater() && !this.handleLavaMovement()) {
                if (this.onGround && jumpTimer == 8) {
                    this.jump();
                }
            }
        }
    }

    protected void jump() {
        spendFuel(5F);
        worldObj.playSoundEffect(posX, posY, posZ, "tile.piston.out", 2.0F, 1.0F);
        this.motionY = 0.41999998688697815D;
        if (this.isSprinting()) {
            float f = this.rotationYaw * 0.017453292F;
            this.motionX -= MathHelper.sin(f) * 0.2F;
            this.motionZ += MathHelper.cos(f) * 0.2F;
        }
        this.isAirBorne = true;
        ForgeHooks.onLivingJump(this);
    }

    private void spendFuel(float cost) {
        this.setFuel(Math.max(0F, getFuel() - cost * getFuelCost()));
    }

    @Override
    public boolean isSprinting() {
        return isPowered() && forwardControl > 0 && riddenByEntity != null && riddenByEntity.isSprinting();
    }

    public float getMoveForward() {
        return forwardControl;
    }

    public void setMoveForward(float f) {
        forwardControl = f;
    }

    public float getMoveStrafe() {
        return strafeControl;
    }

    public void setMoveStrafe(float f) {
        strafeControl = f;
    }

    public boolean getJumpControl() {
        return jumpControl;
    }

    public void setJumpControl(boolean b) {
        this.jumpControl = b;
        ;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25F);
    }

    @Override
    public boolean shouldRiderFaceForward(EntityPlayer player) {
        return true;
    }

    @Override
    public boolean shouldRiderSit() {
        return false;
    }

    @Override
    public double getMountedYOffset() {
        return 0.8625D;
    }

    @Override
    public boolean canBeCollidedWith() {
        return riddenByEntity == null && super.canBeCollidedWith();
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    public boolean interactFirst(EntityPlayer user) {
        if (user.isSwingInProgress) {
            return false;
        }

        ItemStack item = user.getHeldItem();
        if (item != null) {
            float fuel_item = PowerArmour.getFuelValue(item);
            if (fuel_item > 0) {
                float fuel = getFuel();
                float max = getMaxFuel();
                if (fuel < max) {
                    fuel += Math.max(0F, fuel_item * ConfigArmour.cogworkFuelUnits);
                    if (fuel > max) {
                        fuel = max;
                    }
                    setFuel(fuel);

                    if (!user.capabilities.isCreativeMode) {
                        --item.stackSize;

                        ItemStack container = item.getItem().getContainerItem(item);
                        if (container != null) {
                            if (item.stackSize >= 1) {
                                if (!user.inventory.addItemStackToInventory(container)) {
                                    user.entityDropItem(container, 0F);
                                }
                            }
                        }
                        if (item.stackSize <= 0) {
                            user.setCurrentItemOrArmor(0, container);
                        }
                    }
                }
                return true;
            }
            if (this.riddenByEntity != null) {
                return false;
            }
            if (this.isUnderRepairFrame()) {
                if (getPlating() == null && item.getItem() == ComponentListMF.cogwork_armour) {
                    CustomMaterial material = CustomToolHelper.getCustomPrimaryMaterial(item);
                    if (material != null) {
                        this.playSound("mob.horse.armor", 1.0F, 1.0F);
                        int boltCount = this.getBolts();
                        if (boltCount < maxBolts) {
                            if (!user.isSwingInProgress && user.capabilities.isCreativeMode
                                    || user.inventory.consumeInventoryItem(ComponentListMF.bolt)) {
                                ++boltCount;
                                setBolts(boltCount);
                            }
                            user.swingItem();
                            return true;
                        }
                        this.setCustomMaterial(material.name);
                        float damagePercent = 1F - ((float) item.getItemDamage() / (float) item.getMaxDamage());
                        this.setHealth(getMaxHealth() * damagePercent);
                        if (!user.capabilities.isCreativeMode) {
                            --item.stackSize;
                            if (item.stackSize <= 0) {
                                user.setCurrentItemOrArmor(0, null);
                            }
                        }
                        user.swingItem();
                        return true;
                    }
                }
                if (this.getPlating() != null && ToolHelper.getCrafterTool(item).equalsIgnoreCase("spanner")) {
                    this.playSound("mob.horse.armor", 1.2F, 1.0F);
                    user.swingItem();
                    int boltCount = this.getBolts();
                    if (boltCount > 0) {
                        if (!worldObj.isRemote) {
                            ItemStack bolt = new ItemStack(ComponentListMF.bolt, boltCount);
                            if (!user.capabilities.isCreativeMode && !user.inventory.addItemStackToInventory(bolt)) {
                                this.entityDropItem(bolt, 0.0F);
                            }
                        }
                        setBolts(0);
                    }
                    float damagePercent = 1F - (getHealth() / getMaxHealth());
                    if (!worldObj.isRemote) {
                        ItemStack armour = ComponentListMF.cogwork_armour.createComm(getPlating().name, 1,
                                damagePercent);
                        if (!user.capabilities.isCreativeMode && !user.inventory.addItemStackToInventory(armour)) {
                            this.entityDropItem(armour, 0.0F);
                        }
                    }
                    this.playSound("mob.irongolem.hit", 1.0F, 1.0F);
                    this.setCustomMaterial("");
                    return true;
                }
            }
        }
        if (user.ridingEntity == null) {
            if (!this.allowEquipment(user)) {
                return true;
            } else {
                this.noMoveTime = 20;
                user.moveForward = user.moveStrafing = 0F;
                user.mountEntity(this);
                this.playSound("tile.piston.in", 1.0F, 0.6F);
                return true;
            }
        }
        return false;
    }

    private float getMaxFuel() {
        return base_fuel_minutes * 1200F;
    }

    private boolean allowEquipment(EntityLivingBase user) {
        if (allowedBulk >= 2) {
            return true;// Any Armour
        }

        float bulk = ArmourCalculator.getEquipmentBulk(user);
        if (allowedBulk >= 0) {
            if (bulk > allowedBulk) {
                if (user instanceof EntityPlayer && worldObj.isRemote) {
                    ((EntityPlayer) user).addChatComponentMessage(new ChatComponentTranslation("vehicle.tooBigArmour"));
                }
                return false;
            }
            return true;
        }
        for (int a = 0; a < 4; a++) {
            if (user.getEquipmentInSlot(a + 1) != null) {
                if (user instanceof EntityPlayer && worldObj.isRemote) {
                    ((EntityPlayer) user).addChatComponentMessage(new ChatComponentTranslation("vehicle.noArmour"));
                }
                return false;
            }
        }
        return true;
    }

    @Override
    protected boolean isMovementBlocked() {
        return true;
    }

    @Override
    public boolean isPowered() {
        return noMoveTime == 0 && getFuel() > 0 && riddenByEntity != null;
    }

    @Override
    public void moveEntityWithHeading(float strafe, float forward) {
        if (isPowered() && this.riddenByEntity != null && this.riddenByEntity instanceof EntityLivingBase) {
            EntityLivingBase user = (EntityLivingBase) riddenByEntity;

            this.prevRotationYaw = this.rotationYaw = this.riddenByEntity.rotationYaw;
            this.rotationPitch = this.riddenByEntity.rotationPitch * 0.5F;
            this.setRotation(this.rotationYaw, this.rotationPitch);
            this.rotationYawHead = this.renderYawOffset = this.rotationYaw;
            strafe = MathHelper.clamp_float(this.getMoveStrafe(), -1F, 1F) * 0.5F;
            forward = MathHelper.clamp_float(this.getMoveForward(), -1F, 1F) * 0.5F;

            if (forward <= 0.0F) {
                forward *= 0.5F;// Backstep slower
            }

            if (!this.worldObj.isRemote) {
                this.setAIMoveSpeed(
                        (float) this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue());
                moveCogwork(strafe, forward);
            }

            user.prevLimbSwingAmount = user.limbSwingAmount;
            double d1 = this.posX - this.prevPosX;
            double d0 = this.posZ - this.prevPosZ;
            float f4 = MathHelper.sqrt_double(d1 * d1 + d0 * d0) * 4.0F;

            if (f4 > 1.0F) {
                f4 = 1.0F;
            }

            user.limbSwingAmount += (f4 - user.limbSwingAmount) * 0.4F;
            user.limbSwing += user.limbSwingAmount;
        } else {
            super.moveEntityWithHeading(strafe, forward);
        }
    }

    private float getSpeedModifier() {
        return isSprinting() ? 2.0F : 1.0F;
    }

    public void moveCogwork(float p_70612_1_, float p_70612_2_) {
        double d0;

        if (this.isInWater() || this.handleLavaMovement()) {
            d0 = this.posY;
            this.motionY -= 0.03D;

            if (this.isCollidedHorizontally && this.isOffsetPositionInLiquid(this.motionX,
                    this.motionY + 0.6000000238418579D - this.posY + d0, this.motionZ)) {
                this.motionY = 0.30000001192092896D;
            }
        }
        {
            float f2 = 0.91F;

            if (this.onGround) {
                f2 = this.worldObj.getBlock(MathHelper.floor_double(this.posX),
                        MathHelper.floor_double(this.boundingBox.minY) - 1,
                        MathHelper.floor_double(this.posZ)).slipperiness * 0.91F;
            }

            float f3 = 0.16277136F / (f2 * f2 * f2);
            float f4;

            if (this.onGround) {
                f4 = this.getAIMoveSpeed() * f3;
            } else {
                f4 = this.jumpMovementFactor;
            }
            if (riddenByEntity == null) {
                f4 = 0F;
            }
            if (isSprinting()) {
                f4 *= 2.0F;
            }

            this.moveFlying(p_70612_1_, p_70612_2_, f4);
            f2 = 0.91F;

            if (this.onGround) {
                f2 = this.worldObj.getBlock(MathHelper.floor_double(this.posX),
                        MathHelper.floor_double(this.boundingBox.minY) - 1,
                        MathHelper.floor_double(this.posZ)).slipperiness * 0.91F;
            }

            if (this.isOnLadder()) {
                float f5 = 0.15F;

                if (this.motionX < (-f5)) {
                    this.motionX = (-f5);
                }

                if (this.motionX > f5) {
                    this.motionX = f5;
                }

                if (this.motionZ < (-f5)) {
                    this.motionZ = (-f5);
                }

                if (this.motionZ > f5) {
                    this.motionZ = f5;
                }

                this.fallDistance = 0.0F;

                if (this.motionY < -0.15D) {
                    this.motionY = -0.15D;
                }
            }

            this.moveEntity(this.motionX, this.motionY, this.motionZ);

            if (this.isCollidedHorizontally && this.isOnLadder()) {
                this.motionY = 0.2D;
            }

            if (this.worldObj.isRemote && (!this.worldObj.blockExists((int) this.posX, 0, (int) this.posZ)
                    || !this.worldObj.getChunkFromBlockCoords((int) this.posX, (int) this.posZ).isChunkLoaded)) {
                if (this.posY > 0.0D) {
                    this.motionY = -0.1D;
                } else {
                    this.motionY = 0.0D;
                }
            } else {
                this.motionY -= 0.08D;
            }

            this.motionY *= 0.9800000190734863D;
            this.motionX *= f2;
            this.motionZ *= f2;
        }

        this.prevLimbSwingAmount = this.limbSwingAmount;
        d0 = this.posX - this.prevPosX;
        double d1 = this.posZ - this.prevPosZ;
        float f6 = MathHelper.sqrt_double(d0 * d0 + d1 * d1) * 4.0F;

        if (f6 > 1.0F) {
            f6 = 1.0F;
        }

        this.limbSwingAmount += (f6 - this.limbSwingAmount) * 0.4F;
        this.limbSwing += this.limbSwingAmount;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setString("Plating", this.getCustomMaterial());
        nbt.setFloat("Fuel", getFuel());
        nbt.setInteger("Bolts", getBolts());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        if (nbt.hasKey("Plating", 8) && nbt.getString("Plating").length() > 0) {
            this.setCustomMaterial(nbt.getString("Plating"));
        }
        this.setFuel(nbt.getFloat("Fuel"));
        setBolts(nbt.getInteger("Bolts"));
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    /**
     * Returns the sound this mob makes on death.
     */
    @Override
    protected String getDeathSound() {
        return "mob.irongolem.death";
    }

    @Override
    protected String getHurtSound() {
        return "mob.irongolem.hurt";
    }

    @Override
    protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_) {
        String s = alternateStep ? "in" : "out";
        alternateStep = !alternateStep;
        this.playSound("tile.piston." + s, 0.25F, 1.0F);
        this.playSound("mob.irongolem.walk", 1.0F, 1.0F);
    }

    @Override
    protected void updatePotionEffects() {
    }

    @Override
    public void addPotionEffect(PotionEffect effect) {
    }

    @Override
    public boolean isPotionApplicable(PotionEffect effect) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void performHurtAnimation() {
    }

    @Override
    public float getAIMoveSpeed() {
        return riddenByEntity == null ? 1F : super.getAIMoveSpeed();
    }

    @Override
    public boolean isFullyArmoured() {
        return getPlating() != null;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float damage) {
        return attackEntityFrom(source, damage, true);
    }

    /**
     * Modified attackEntityFrom
     *
     * @param shouldPass is used to determine if the wearer should take the hit
     */
    public boolean attackEntityFrom(DamageSource source, float damage, boolean shouldPass) {
        if (shouldPass && this.riddenByEntity != null) {
            return this.riddenByEntity.attackEntityFrom(source, damage);
        }

        if (source.isExplosion()) {
            this.noMoveTime = 20;
            damage *= 1.5F;
        }
        return super.attackEntityFrom(source, modifyDamage(this, damage, source));
    }

    public boolean shouldBlockPoisonOrMagic() {
        return getPlating() != null;
    }

    @Override
    public float modifyDamage(EntityLivingBase user, float damage, DamageSource src) {
        if (src.getDamageType().equalsIgnoreCase("humanstomp")) {
            return 0F;
        }
        if (src == DamageSource.wither || src.isMagicDamage()) {
            return shouldBlockPoisonOrMagic() ? 0F : damage;
        }

        if (user != this) {
            this.attackEntityFrom(src, damage, false);
        }

        float AC = 2.0F;
        float fResist = 0.0F;
        CustomMaterial plating = getPlating();
        if (plating != null) {
            AC = plating.hardness * ArmourDesign.COGWORK.getRating() * rating_modifier;
            fResist = plating.getFireResistance() / 100F;
        }

        if (src.isFireDamage()) {
            if (user == this) {
                return damage * MathHelper.clamp_float(1F - fResist, 0F, 1F);
            } else if (isFullyArmoured()) {
                return 0F;// All or nothing depends on if armour is full
            }
        }
        if (user == this) {
            return damage;
        }

        if (src.isUnblockable() && !isFullyArmoured()) {
            return damage;
        }
        return damage * 1F / AC;
    }

    @Override
    public void setFire(int i) {
    }

    @Override
    protected void damageEntity(DamageSource source, float dam) {
        CustomMaterial plating = this.getPlating();
        boolean canDestroy = false;// Only spanner or fire can destroy frames
        boolean isFrame = plating == null;

        if (source.getSourceOfDamage() != null && source.getSourceOfDamage() instanceof EntityLivingBase) {
            canDestroy = ToolHelper.getCrafterTool(((EntityLivingBase) source.getSourceOfDamage()).getHeldItem())
                    .equalsIgnoreCase("spanner");
        }
        if (source.isFireDamage() || source.canHarmInCreative()) {
            canDestroy = true;
        }

        if (isFrame) {
            if (canDestroy && riddenByEntity == null) {
                setHealth(0);
            } else {
                setHealth(getMaxHealth());
            }
            return;
        } else if (dam != 0.0F) {
            if (plating != null) {
                float HP = plating.durability * ArmourDesign.COGWORK.getDurability() * 20F * health_modifier;
                dam *= (this.getMaxHealth() / HP);
            }
            dam = this.applyPotionDamageCalculations(source, dam);

            float hp = this.getHealth() - dam;
            if (hp <= 1.0F) {
                hp = getMaxHealth();
                destroyArmour();
            }
            this.setHealth(hp);

            if (!source.isFireDamage()) {
                this.playSound("mob.irongolem.hit", 1.0F,
                        (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            }
        }
    }

    private void destroyArmour() {
        this.setCustomMaterial("");
    }

    @Override
    public void heal(float amount) {
    }

    @SideOnly(Side.CLIENT)
    public int getMetreScaled(int i) {
        return (int) (i / getMaxFuel() * getFuel());
    }

    @Override
    protected void fall(float distance) {
        if (distance > 2) {
            float power = Math.min(distance / 2F, 3F);
            initImpactLanding(posX, posY, posZ, power / 2F, ConfigArmour.cogworkGrief);
        }
    }

    public Shockwave initImpactLanding(double x, double y, double z, float power, boolean grief) {
        noMoveTime = 10;
        return newShockwave(x, y, z, power, false, grief);
    }

    @Override
    protected void collideWithEntity(Entity hit) {
        super.collideWithEntity(hit);
        if (!isSprinting() || !isPowered() || hit == riddenByEntity) {
            return;
        }
        this.playSound(this.getHurtSound(), this.getSoundVolume(), this.getSoundPitch());
        float modifier = width * width * height / hit.width * hit.width * hit.height;// compare volume
        float force = (float) Math.hypot(motionX, motionZ) * modifier;
        TacticalManager.knockbackEntity(hit, this, force, force / 4F);
        hit.attackEntityFrom(
                DamageSource.causeMobDamage((riddenByEntity != null && riddenByEntity instanceof EntityLivingBase)
                        ? (EntityLivingBase) riddenByEntity
                        : this),
                force);
    }

    /**
     * returns a new explosion. Does initiation (at time of writing Explosion is not
     * finished)
     */
    public Shockwave newShockwave(double x, double y, double z, float power, boolean fire, boolean grief) {
        Shockwave explosion = new Shockwave("humanstomp", worldObj,
                this.riddenByEntity != null ? this.riddenByEntity : this, x, y, z, power);
        explosion.isFlaming = fire;
        explosion.isGriefing = grief;
        explosion.isSmoking = grief;
        explosion.initiate();
        explosion.decorateWave(true);
        return explosion;
    }

    @Override
    protected void dropFewItems(boolean pkill, int looting) {
        this.dropItem(Item.getItemFromBlock(BlockListMF.cogwork_helm), 1);
        this.dropItem(Item.getItemFromBlock(BlockListMF.cogwork_chest), 1);
        this.dropItem(Item.getItemFromBlock(BlockListMF.cogwork_legs), 1);

    }

    @SideOnly(Side.CLIENT)
    public int getArmourRating() {
        return getArmourRating(getPlating());
    }

    public float getWeight() {
        float weight = base_frame_weight;// Weight of frame
        CustomMaterial plating = getPlating();
        if (plating != null) {
            weight += plating.density * base_armour_units;
        }
        return weight;
    }

    public boolean isUnderRepairFrame() {
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(this.posY + 3);
        int k = MathHelper.floor_double(this.posZ);

        return PowerArmour.isStationBlock(worldObj, i, j, k);
    }

    private void onPortalTick() {
        if (!this.worldObj.isRemote && this.worldObj instanceof WorldServer) {
            this.worldObj.theProfiler.startSection("portal");
            MinecraftServer minecraftserver = ((WorldServer) this.worldObj).func_73046_m();
            int i = this.getMaxInPortalTime();

            if (this.inPortal) {
                if (minecraftserver.getAllowNether()) {
                    if (this.ridingEntity == null && this.portalCounter++ >= i) {
                        this.portalCounter = i;
                        this.timeUntilPortal = this.getPortalCooldown();
                        byte b0;

                        if (this.worldObj.provider.dimensionId == -1) {
                            b0 = 0;
                        } else {
                            b0 = -1;
                        }
                        this.travelToDimension(b0);
                    }

                    this.inPortal = false;
                }
            } else {
                if (this.portalCounter > 0) {
                    this.portalCounter -= 4;
                }

                if (this.portalCounter < 0) {
                    this.portalCounter = 0;
                }
            }

            if (this.timeUntilPortal > 0) {
                --this.timeUntilPortal;
            }

            this.worldObj.theProfiler.endSection();
        }
    }

    @Override
    public void travelToDimension(int id) {
        if (this.riddenByEntity != null) {
            riddenByEntity.travelToDimension(id);
        }
        super.travelToDimension(id);
    }

    @Override
    public boolean isArmoured(String piece) {
        return isFullyArmoured();
    }
}