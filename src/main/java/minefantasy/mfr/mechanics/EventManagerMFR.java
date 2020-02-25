package minefantasy.mfr.mechanics;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.api.armour.IPowerArmour;
import minefantasy.mfr.api.armour.ISpecialArmourMFR;
import minefantasy.mfr.api.armour.ItemArmourMFRBase;
import minefantasy.mfr.api.heating.IHotItem;
import minefantasy.mfr.api.heating.TongsHelper;
import minefantasy.mfr.api.helpers.ArmourCalculator;
import minefantasy.mfr.api.helpers.ArrowEffectsMF;
import minefantasy.mfr.api.helpers.CustomToolHelper;
import minefantasy.mfr.api.helpers.EntityHelper;
import minefantasy.mfr.api.helpers.PowerArmour;
import minefantasy.mfr.api.helpers.TacticalManager;
import minefantasy.mfr.api.helpers.ToolHelper;
import minefantasy.mfr.api.knowledge.ResearchLogic;
import minefantasy.mfr.api.material.CustomMaterial;
import minefantasy.mfr.api.rpg.LevelupEvent;
import minefantasy.mfr.api.rpg.RPGElements;
import minefantasy.mfr.api.rpg.SyncSkillEvent;
import minefantasy.mfr.api.stamina.StaminaBar;
import minefantasy.mfr.api.tool.IHuntingItem;
import minefantasy.mfr.api.tool.ISmithTongs;
import minefantasy.mfr.api.weapon.WeaponClass;
import minefantasy.mfr.client.render.RenderPowerArmour;
import minefantasy.mfr.config.ConfigExperiment;
import minefantasy.mfr.config.ConfigHardcore;
import minefantasy.mfr.config.ConfigStamina;
import minefantasy.mfr.entity.EntityCogwork;
import minefantasy.mfr.entity.EntityItemUnbreakable;
import minefantasy.mfr.entity.mob.EntityDragon;
import minefantasy.mfr.farming.FarmingHelper;
import minefantasy.mfr.init.ComponentListMFR;
import minefantasy.mfr.init.FoodListMFR;
import minefantasy.mfr.init.ToolListMFR;
import minefantasy.mfr.item.ClientItemsMF;
import minefantasy.mfr.item.weapon.ItemWeaponMFR;
import minefantasy.mfr.packet.LevelupPacket;
import minefantasy.mfr.packet.SkillPacket;
import minefantasy.mfr.util.MFRLogUtil;
import minefantasy.mfr.util.XSTRandom;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EventManagerMFR {

    public static final String hitspeedNBT = "MF_HitCooldown";
    public static final String injuredNBT = "MF_Injured";
    public static boolean displayOreDict;

    private static XSTRandom random = new XSTRandom();

    public static void addArmourDT(ItemStack armour, EntityPlayer user, List<String> list, boolean extra) {
        list.add("");
        String AC = ArmourCalculator.getArmourClass(armour);
        if (AC != null) {
            list.add(I18n.translateToLocal("attribute.armour." + AC));
        }
        list.add(TextFormatting.BLUE + I18n.translateToLocal("attribute.armour.protection"));
        addSingleDT(armour, user, 0, list, extra);
        addSingleDT(armour, user, 2, list, extra);
        addSingleDT(armour, user, 1, list, extra);
    }

    public static void addSingleDT(ItemStack armour, EntityPlayer user, int id, List<String> list, boolean extra) {
        EntityEquipmentSlot slot = ((ItemArmor) armour.getItem()).armorType;
        String attatch = "";

        int rating = (int) (ArmourCalculator.getDTForDisplayPiece(armour, id) * 100F);
        int equipped = (int) (ArmourCalculator.getDTForDisplayPiece(user.getItemStackFromSlot(slot), id) * 100F);

        if (rating > 0 || equipped > 0) {
            if (equipped > 0 && rating != equipped) {
                float d = rating - equipped;
                if (d > 0) {
                    attatch += TextFormatting.DARK_GREEN;
                }
                if (d < 0) {
                    attatch += TextFormatting.RED;
                }
                String d2 = ItemWeaponMFR.decimal_format.format(d);
                attatch += " (" + (d > 0 ? "+" : "") + d2 + ")";
            }
            list.add(TextFormatting.BLUE + I18n.translateToLocal("attribute.armour.rating." + id) + " "
                    + rating + attatch);
        }
    }

    public static void addArmourDR(ItemStack armour, EntityPlayer user, List<String> list, boolean extra) {
        list.add("");
        String AC = ArmourCalculator.getArmourClass(armour);
        if (AC != null) {
            list.add(I18n.translateToLocal("attribute.armour." + AC));
        }
        if (armour.getItem() instanceof ISpecialArmourMFR) {
            if (ArmourCalculator.advancedDamageTypes) {
                list.add(TextFormatting.BLUE + I18n.translateToLocal("attribute.armour.protection"));
                addSingleDR(armour, user, 0, list, extra, true);
                addSingleDR(armour, user, 2, list, extra, true);
                addSingleDR(armour, user, 1, list, extra, true);
            } else {
                addSingleDR(armour, user, 0, list, extra, false);
            }
        }
    }

    public static void addSingleDR(ItemStack armour, EntityPlayer user, int id, List<String> list, boolean extra,
            boolean advanced) {
        EntityEquipmentSlot slot = ((ItemArmor) armour.getItem()).armorType;
        String attatch = "";

        int rating = (int) (ArmourCalculator.getDRForDisplayPiece(armour, id) * 100F);
        int equipped = (int) (ArmourCalculator.getDRForDisplayPiece(user.getItemStackFromSlot(slot), id) * 100F);

        if (rating > 0 || equipped > 0) {
            if (equipped > 0 && rating != equipped) {
                float d = rating - equipped;
                if (d > 0) {
                    attatch += TextFormatting.DARK_GREEN;
                }
                if (d < 0) {
                    attatch += TextFormatting.RED;
                }
                String d2 = ItemWeaponMFR.decimal_format.format(d);
                attatch += " (" + (d > 0 ? "+" : "") + d2 + ")";
            }
            if (advanced) {
                list.add(TextFormatting.BLUE + I18n.translateToLocal("attribute.armour.rating." + id) + " "
                        + rating + attatch);
            } else {
                list.add(TextFormatting.BLUE + I18n.translateToLocal("attribute.armour.protection") + ": "
                        + rating + attatch);
            }
        }
    }

    public static String getRegisterName(Entity entity) {
        String s = EntityList.getEntityString(entity);

        if (s == null) {
            s = "generic";
        }

        return s;
    }

    public static void tickHitSpeeds(EntityLivingBase user) {
        int time = getHitspeedTime(user);
        if (time > 0) {
            time--;
            user.getEntityData().setInteger(hitspeedNBT, time);
        }
    }

    public static void setHitTime(EntityLivingBase user, int time) {
        user.getEntityData().setInteger(hitspeedNBT, time);
    }

    public static int getHitspeedTime(Entity entity) {
        if (entity != null && entity.getEntityData().hasKey(hitspeedNBT)) {
            return entity.getEntityData().getInteger(hitspeedNBT);
        }
        return 0;
    }

    public static int getInjuredTime(Entity entity) {
        if (entity.getEntityData().hasKey(injuredNBT)) {
            return entity.getEntityData().getInteger(injuredNBT);
        }
        return 0;
    }

    @SubscribeEvent
    public void tryDropItems(LivingDropsEvent event) {
        EntityLivingBase dropper = event.getEntityLiving();

        if (dropper instanceof EntityChicken) {
            int dropCount = 1 + random.nextInt(event.getLootingLevel() + 1 * 4);

            for (int a = 0; a < dropCount; a++) {
                dropper.entityDropItem(new ItemStack(Items.FEATHER), 0.0F);
            }
        }
        if (dropper.getEntityData().hasKey("MF_LootDrop")) {
            int id = dropper.getEntityData().getInteger("MF_LootDrop");
            Item drop = id == 0 ? ToolListMFR.loot_sack : id == 1 ? ToolListMFR.loot_sack_uc : ToolListMFR.loot_sack_rare;
            dropper.entityDropItem(new ItemStack(drop), 0.0F);
        }
        if (dropper instanceof EntityAgeable && dropper.getCreatureAttribute() != EnumCreatureAttribute.UNDEAD) {
            if (random.nextFloat() * (1 + event.getLootingLevel()) < 0.05F) {
                dropper.entityDropItem(new ItemStack(FoodListMFR.guts), 0.0F);
            }
        }
        if (dropper instanceof IAnimals && !(dropper instanceof IMob)) {
            if (ConfigHardcore.hunterKnife && !dropper.getEntityData().hasKey("hunterKill")) {
                event.setCanceled(true);
                return;
            }
            if (ConfigHardcore.lessHunt) {
                alterDrops(dropper, event);
            }
        }
        if (getRegisterName(dropper).contains("Horse")) {
            int dropCount = random.nextInt(3 + event.getLootingLevel());
            if (ConfigHardcore.lessHunt) {
                dropCount = 1 + random.nextInt(event.getLootingLevel() + 1);
            }

            Item meat = dropper.isBurning() ? FoodListMFR.horse_cooked : FoodListMFR.horse_raw;
            for (int a = 0; a < dropCount; a++) {
                dropper.entityDropItem(new ItemStack(meat), 0.0F);
            }
        }
        if (getRegisterName(dropper).contains("Wolf")) {
            int dropCount = random.nextInt(3 + event.getLootingLevel());
            if (ConfigHardcore.lessHunt) {
                dropCount = 1 + random.nextInt(event.getLootingLevel() + 1);
            }

            Item meat = dropper.isBurning() ? FoodListMFR.wolf_cooked : FoodListMFR.wolf_raw;
            for (int a = 0; a < dropCount; a++) {
                dropper.entityDropItem(new ItemStack(meat), 0.0F);
            }
        }
        dropLeather(event.getEntityLiving(), event);

        if (dropper instanceof EntitySkeleton) {
            EntitySkeleton skeleton = (EntitySkeleton) dropper;

            if ((skeleton.getHeldItemMainhand() == null || !(skeleton.getHeldItemMainhand().getItem() instanceof ItemBow))
                    && event.getDrops() != null && !event.getDrops().isEmpty()) {
                Iterator<EntityItem> list = event.getDrops().iterator();

                while (list.hasNext()) {
                    EntityItem entItem = list.next();
                    ItemStack drop = entItem.getItem();

                    if (drop.getItem() == Items.ARROW) {
                        entItem.setDead();
                    }
                }
            }
        }
    }

    private void dropLeather(EntityLivingBase mob, LivingDropsEvent event) {
        boolean dropHide = shouldAnimalDropHide(mob);
        Item hide = getHideFor(mob);

        if (event.getDrops() != null && !event.getDrops().isEmpty()) {
            Iterator<EntityItem> list = event.getDrops().iterator();

            while (list.hasNext()) {
                EntityItem entItem = list.next();
                ItemStack drop = entItem.getItem();

                if (drop.getItem() == Items.LEATHER) {
                    entItem.setDead();
                    dropHide = true;
                }
            }
        }
        if (dropHide && hide != null && !(ConfigHardcore.hunterKnife && !mob.getEntityData().hasKey("hunterKill"))) {
            mob.entityDropItem(new ItemStack(hide), 0.0F);
        }
    }

    private Item getHideFor(EntityLivingBase mob) {
        Item[] hide = new Item[]{ComponentListMFR.rawhideSmall, ComponentListMFR.rawhideMedium,
                ComponentListMFR.rawhideLarge};
        int size = getHideSizeFor(mob);
        if (mob.isChild()) {
            size--;
        }

        if (size <= 0) {
            return null;
        }
        if (size > hide.length) {
            size = hide.length;
        }

        return hide[size - 1];
    }

    private int getHideSizeFor(EntityLivingBase mob) {
        String mobName = mob.getClass().getName();
        if (mobName.endsWith("EntityCow") || mobName.endsWith("EntityHorse")) {
            return 3;
        }
        if (mobName.endsWith("EntitySheep")) {
            return 2;
        }
        if (mobName.endsWith("EntityPig")) {
            return 1;
        }

        int size = (int) (mob.width + mob.height + 1);
        if (size <= 1) {
            return 0;
        }
        if (size == 2) {
            return 1;
        } else if (size <= 4) {
            return 2;
        }
        return 3;
    }

    private boolean shouldAnimalDropHide(EntityLivingBase mob) {
        String mobName = mob.getClass().getName();
        if (mobName.endsWith("EntityWolf") || mobName.endsWith("EntityPig") || mobName.endsWith("EntitySheep")
                || mobName.endsWith("EntityCow") || mobName.endsWith("EntityHorse")) {
            return true;
        }
        if (mob instanceof EntityWolf || mob instanceof EntityCow || mob instanceof EntityPig
                || mob instanceof EntitySheep || mob instanceof EntityHorse) {
            return true;
        }
        return false;
    }

    @SubscribeEvent
    public void onDeath(LivingDeathEvent event) {
        if (!event.getEntityLiving().world.isRemote && event.getEntityLiving() instanceof EntityDragon && event.getSource() != null
                && event.getSource().getImmediateSource() != null && event.getSource().getImmediateSource() instanceof EntityPlayer) {
            PlayerTickHandlerMF.addDragonKill((EntityPlayer) event.getSource().getImmediateSource());
        }
        if (!event.getEntityLiving().world.isRemote && event.getEntityLiving() instanceof EntityPlayer && event.getSource() != null
                && event.getEntity() != null && event.getSource().getImmediateSource() instanceof EntityDragon) {
            PlayerTickHandlerMF.addDragonEnemyPts((EntityPlayer) event.getEntity(), -1);
        }
        Entity dropper = event.getEntity();

        boolean useArrows = true;
        try {
            Class.forName("minefantasy.mf2.api.helpers.ArrowEffectsMF");
        } catch (Exception e) {
            useArrows = false;
        }
        if (dropper != null && useArrows && ConfigExperiment.stickArrows && !dropper.world.isRemote) {
            ArrayList<ItemStack> stuckArrows = (ArrayList<ItemStack>) ArrowEffectsMF.getStuckArrows(dropper);
            if (stuckArrows != null && !stuckArrows.isEmpty()) {
                Iterator<ItemStack> list = stuckArrows.iterator();

                while (list.hasNext()) {
                    ItemStack arrow = list.next();
                    if (arrow != null) {
                        dropper.entityDropItem(arrow, 0.0F);
                    }
                }
            }
        }

    }

    @SubscribeEvent
    public void spawnEntity(EntityJoinWorldEvent event) {
        if (event.getEntity().isDead) {
            return;
        }
        if (event.getEntity() instanceof EntityItem && !(event.getEntity() instanceof EntityItemUnbreakable)) {
            EntityItem eitem = (EntityItem) event.getEntity();
            if (eitem.getItem() != null) {
                if (eitem.getItem().hasTagCompound()
                        && eitem.getItem().getTagCompound().hasKey("Unbreakable")) {
                    EntityItem newEntity = new EntityItemUnbreakable(event.getWorld(), eitem);
                    event.getWorld().spawnEntity(newEntity);
                    eitem.setDead();
                }
                if (isDragonforge(eitem.getItem())) {
                    MFRLogUtil.logDebug("Found dragon heart");
                    EntityItem newEntity = new EntityItemUnbreakable(event.getWorld(), eitem);
                    event.getWorld().spawnEntity(newEntity);
                    eitem.setDead();
                }
            }
        }
    }

    private boolean isDragonforge(ItemStack itemstack) {
        return itemstack.getItem() == ComponentListMFR.dragon_heart;
    }

    public void alterDrops(EntityLivingBase dropper, LivingDropsEvent event) {
        ArrayList<ItemStack> meats = new ArrayList<ItemStack>();

        if (event.getDrops() != null && !event.getDrops().isEmpty()) {
            Iterator<EntityItem> list = event.getDrops().iterator();

            while (list.hasNext()) {
                EntityItem entItem = list.next();
                ItemStack drop = entItem.getItem();
                boolean dropItem = true;

                if (drop.getItem() instanceof ItemFood) {
                    entItem.setDead();

                    if (!meats.isEmpty()) {
                        for (int a = 0; a < meats.size(); a++) {
                            ItemStack compare = meats.get(a);
                            if (drop.isItemEqual(compare)) {
                                dropItem = false;
                            }
                        }
                    }
                    if (dropItem) {
                        drop.setCount(1);
                        if (event.getLootingLevel() > 0) {
                            drop.grow(dropper.getRNG().nextInt(event.getLootingLevel() + 1));
                        }
                        meats.add(drop.copy());
                    }
                }
            }

            for (int a = 0; a < meats.size(); a++) {
                ItemStack meat = meats.get(a);
                dropper.entityDropItem(meat, 0.0F);
            }
        }
    }

    @SubscribeEvent
    public void killEntity(LivingDeathEvent event) {
        // killsCount
        EntityLivingBase dead = event.getEntityLiving();
        EntityLivingBase hunter = null;
        ItemStack weapon = null;
        DamageSource source = event.getSource();

        if (dead instanceof EntityWitch) {
            dropBook(dead, 0);
        }
        if (dead instanceof EntityVillager) {
            dropBook(dead, 1);
        }
        if (dead instanceof EntityZombie) {
            dropBook(dead, 2);
        }
        if (source != null && source.getImmediateSource() != null) {
            if (source.getImmediateSource() instanceof EntityLivingBase) {
                hunter = (EntityLivingBase) source.getImmediateSource();
                weapon = hunter.getHeldItemMainhand();
                if (hunter instanceof EntityPlayer) {
                    addKill((EntityPlayer) hunter, dead);
                }
            }
        }
        if (weapon != null) {
            String type = ToolHelper.getCrafterTool(weapon);
            if (weapon.getItem() instanceof IHuntingItem) {
                if (((IHuntingItem) weapon.getItem()).canRetrieveDrops(weapon)) {
                    dead.getEntityData().setBoolean("hunterKill", true);
                }
            } else if (type != null && type.equalsIgnoreCase("knife")) {
                dead.getEntityData().setBoolean("hunterKill", true);
            }
        }
    }

    private void dropBook(EntityLivingBase dead, int id) {
        if (dead.world.isRemote)
            return;
        Item book = null;
        if (id == 0) {
            float chance = random.nextFloat();
            if (chance > 0.75F) {
                book = ToolListMFR.skillbook_engineering;
            } else {
                book = ToolListMFR.skillbook_provisioning;
            }
        } else if (id == 1 && random.nextInt(5) == 0) {
            float chance = random.nextFloat();
            if (chance > 0.9F) {
                book = ToolListMFR.skillbook_engineering;
            } else if (chance > 0.6F) {
                book = ToolListMFR.skillbook_artisanry;
            } else if (chance > 0.3F) {
                book = ToolListMFR.skillbook_construction;
            } else {
                book = ToolListMFR.skillbook_provisioning;
            }
        } else if (id == 2 && random.nextInt(25) == 0) {
            float chance = random.nextFloat();
            if (chance > 0.9F) {
                book = ToolListMFR.skillbook_engineering;
            } else if (chance > 0.6F) {
                book = ToolListMFR.skillbook_artisanry;
            } else if (chance > 0.3F) {
                book = ToolListMFR.skillbook_construction;
            } else {
                book = ToolListMFR.skillbook_provisioning;
            }
        }
        if (book != null) {
            dead.entityDropItem(new ItemStack(book), 0F);
        }
    }

    private void addKill(EntityPlayer hunter, EntityLivingBase dead) {
        addKillTo(hunter, "killsCount");
        if (dead instanceof IMob) {
            addKillTo(hunter, "killsCountMob");
        } else if (dead instanceof IAnimals) {
            addKillTo(hunter, "killsCountAnimal");
        }
        if (dead instanceof EntityPlayer) {
            addKillTo(hunter, "killsCountPlayer");
        }
    }

    @SubscribeEvent
    public void useHoe(UseHoeEvent event) {
        Block block = (Block) event.getWorld().getBlockState(event.getPos());
        if (block != Blocks.FARMLAND && FarmingHelper.didHoeFail(event.getCurrent(), event.getWorld(), block == Blocks.GRASS)) {
            event.getEntityPlayer().swingArm(event.getEntityPlayer().getActiveHand());
            event.getWorld().playSound(event.getEntityPlayer(), event.getPos(), SoundEvents.ITEM_HOE_TILL, SoundCategory.AMBIENT, 12, 1F );
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void breakBlock(BreakEvent event) {
        // Block block = event.block;
        Block base = (Block) event.getWorld().getBlockState(event.getPos().add(0,-1,0));
        // int meta = event.blockMetadata;

        if (base != null && base == Blocks.FARMLAND && FarmingHelper.didHarvestRuinBlock(event.getWorld(), false)) {
            event.getWorld().setBlockState(event.getPos().add(0,-1,0), Blocks.DIRT.getDefaultState());
        }

        EntityPlayer player = event.getPlayer();
        if (player != null && !player.capabilities.isCreativeMode && !(player instanceof FakePlayer)) {
            playerMineBlock(event);
        }
    }

	/*@SubscribeEvent
    public void loadChunk(ChunkEvent.Load event) {
	}*/

    public void playerMineBlock(BlockEvent.BreakEvent event) {
        EntityPlayer player = event.getPlayer();
        ItemStack held = player.getHeldItemMainhand();
        Block broken = (Block) event.getState();

        if (broken != null && ConfigHardcore.HCCallowRocks) {
            if (held == null) {
                entityDropItem(event.getWorld(), event.getPos(), new ItemStack(ComponentListMFR.sharp_rock, random.nextInt(3) + 1));
            }
            if (held != null && held.getItem() == ComponentListMFR.sharp_rock && broken instanceof BlockLeaves) {
                if (random.nextInt(5) == 0) {
                    entityDropItem(event.getWorld(), event.getPos(), new ItemStack(Items.STICK, random.nextInt(3) + 1));
                }
                if (random.nextInt(3) == 0) {
                    entityDropItem(event.getWorld(), event.getPos(), new ItemStack(ComponentListMFR.vine, random.nextInt(3) + 1));
                }
            }
        }

        if (StaminaBar.isSystemActive && ConfigStamina.affectMining && StaminaBar.doesAffectEntity(player)) {
            float points = 2.0F * ConfigStamina.miningSpeed;
            ItemWeaponMFR.applyFatigue(player, points, 20F);

            if (points > 0 && !StaminaBar.isAnyStamina(player, false)) {
                player.addPotionEffect(new PotionEffect(Potion.getPotionById(18), 100, 1));
            }
        }
    }

    public EntityItem entityDropItem(World world, BlockPos pos, ItemStack item) {
        if (item.getCount() != 0 && item.getItem() != null) {
            EntityItem entityitem = new EntityItem(world, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, item);
            entityitem.setPickupDelay(10);
            world.spawnEntity(entityitem);
            return entityitem;
        }
        return null;
    }

    private void addKillTo(EntityPlayer hunter, String type) {
        int kills = hunter.getEntityData().hasKey(type) ? hunter.getEntityData().getInteger(type) : 0;
        kills++;
        hunter.getEntityData().setInteger(type, kills);
    }

    @SubscribeEvent
    public void setTooltip(ItemTooltipEvent event) {
        if (!event.getEntity().world.isRemote) {
            return;
        }

        if (event.getItemStack() != null) {
            boolean saidArtefact = false;
            int[] ids = OreDictionary.getOreIDs(event.getItemStack());
            boolean hasInfo = false;
            if (ids != null) {
                for (int id : ids) {
                    String s = OreDictionary.getOreName(id);
                    if (s != null) {
                        if (!hasInfo && s.startsWith("ingot")) {
                            String s2 = s.substring(5, s.length());
                            CustomMaterial material = CustomMaterial.getMaterial(s2);
                            if (material != null)
                                hasInfo = true;

                            CustomToolHelper.addComponentString(event.getItemStack(), event.getToolTip(), material);
                        }
                        if (s.startsWith("Artefact-")) {
                            if (!saidArtefact) {
                                String knowledge = s.substring(9).toLowerCase();

                                if (!ResearchLogic.hasInfoUnlocked(event.getEntityPlayer(), knowledge)) {
                                    saidArtefact = true;
                                    event.getToolTip().add(TextFormatting.AQUA
                                            + I18n.translateToLocal("info.hasKnowledge"));
                                }
                            }
                        } else if (displayOreDict) {
                            event.getToolTip().add("oreDict: " + s);
                        }
                    }
                }
            }

            if (event.getItemStack().hasTagCompound() && event.getItemStack().getTagCompound().hasKey("MF_Inferior")) {
                if (event.getItemStack().getTagCompound().getBoolean("MF_Inferior")) {
                    event.getToolTip()
                            .add(TextFormatting.RED + I18n.translateToLocal("attribute.inferior.name"));
                }
                if (!event.getItemStack().getTagCompound().getBoolean("MF_Inferior")) {
                    event.getToolTip()
                            .add(TextFormatting.GREEN + I18n.translateToLocal("attribute.superior.name"));
                }
            }
            if (event.getItemStack().getItem() instanceof ItemArmor
                    && (!(event.getItemStack().getItem() instanceof ItemArmourMFRBase) || ClientItemsMF.showSpecials(
                    event.getItemStack(), event.getEntityPlayer().world, event.getToolTip(), event.getFlags()))) {
                addArmourDR(event.getItemStack(), event.getEntityPlayer(), event.getToolTip(), event.getFlags().isAdvanced());
            }
            if (ArmourCalculator.advancedDamageTypes && ArmourCalculator.getRatioForWeapon(event.getItemStack()) != null) {
                displayWeaponTraits(ArmourCalculator.getRatioForWeapon(event.getItemStack()), event.getToolTip());
            }
            if (ToolHelper.shouldShowTooltip(event.getItemStack())) {
                showCrafterTooltip(event.getItemStack(), event.getToolTip());
            }
            if (event.getItemStack().hasTagCompound() && event.getItemStack().getTagCompound().hasKey("MF_CraftedByName")) {
                String name = event.getItemStack().getTagCompound().getString("MF_CraftedByName");
                boolean special = MineFantasyReborn.isNameModder(name);// Mod creators have highlights

                event.getToolTip().add((special ? TextFormatting.GREEN : "")
                        + I18n.translateToLocal("attribute.mfcraftedbyname.name") + ": " + name
                        + TextFormatting.GRAY);
            }
            WeaponClass WC = WeaponClass.findClassForAny(event.getItemStack());
            if (WC != null && RPGElements.isSystemActive && WC.parentSkill != null) {
                event.getToolTip().add(I18n.translateToLocal("weaponclass." + WC.name.toLowerCase()));
                float skillMod = RPGElements.getWeaponModifier(event.getEntityPlayer(), WC.parentSkill) * 100F;
                if (skillMod > 100)
                    event.getToolTip().add(I18n.translateToLocal("rpg.skillmod")
                            + ItemWeaponMFR.decimal_format.format(skillMod - 100) + "%");

            }
        }
    }

    private void displayWeaponTraits(float[] ratio, List<String> list) {
        int cutting = (int) (ratio[0] / (ratio[0] + ratio[1] + ratio[2]) * 100F);
        int piercing = (int) (ratio[2] / (ratio[0] + ratio[1] + ratio[2]) * 100F);
        int blunt = (int) (ratio[1] / (ratio[0] + ratio[1] + ratio[2]) * 100F);

        addDamageType(list, cutting, "cutting");
        addDamageType(list, piercing, "piercing");
        addDamageType(list, blunt, "blunt");
    }

    private void addDamageType(List<String> list, int value, String name) {
        if (value > 0) {
            String s = I18n.translateToLocal("attribute.weapon." + name);
            if (value < 100) {
                s += " " + value + "%";
            }
            list.add(s);
        }
    }

    private void showCrafterTooltip(ItemStack tool, List<String> list) {
        String toolType = ToolHelper.getCrafterTool(tool);
        int tier = ToolHelper.getCrafterTier(tool);
        float efficiency = ToolHelper.getCrafterEfficiency(tool);

        list.add(I18n.translateToLocal("attribute.mfcrafttool.name") + ": "
                + I18n.translateToLocal("tooltype." + toolType));
        list.add(I18n.translateToLocal("attribute.mfcrafttier.name") + ": " + tier);
        list.add(I18n.translateToLocal("attribute.mfcrafteff.name") + ": " + efficiency);
    }

    @SubscribeEvent
    public void updateEntity(LivingUpdateEvent event) {
        if (event.getEntity() instanceof EntityCogwork) {
            return;
        }
        EntityLivingBase entity = event.getEntityLiving();

        float lowHp = entity.getMaxHealth() / 5F;
        int injury = getInjuredTime(entity);

        if (ConfigHardcore.critLimp && (entity instanceof EntityLiving
                || !(entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.isCreativeMode))) {
            if (entity.getHealth() <= lowHp || injury > 0) {
                if (entity.getRNG().nextInt(10) == 0 && entity.onGround && !entity.isRiding()) {
                    entity.motionX = 0F;
                    entity.motionZ = 0F;
                }
                if (entity.ticksExisted % 15 == 0) {
                    entity.limbSwing = 2.0F;
                    float x = (float) (entity.posX + (random.nextFloat() - 0.5F) / 4F);
                    float y = (float) (entity.posY + entity.getEyeHeight() + (random.nextFloat() - 0.5F) / 4F);
                    float z = (float) (entity.posZ + (random.nextFloat() - 0.5F) / 4F);
                    entity.world.spawnParticle(EnumParticleTypes.REDSTONE, x, y, z, 0F, 0F, 0F);
                }
            }
            if (!entity.world.isRemote && entity.getHealth() <= (lowHp / 2) && entity.getRNG().nextInt(200) == 0) {
                entity.addPotionEffect(new PotionEffect(Potion.getPotionById(9), 100, 50));
            }
        }
        if (injury > 0 && !entity.world.isRemote) {
            injury--;
            entity.getEntityData().setInteger(injuredNBT, injury);
        }
        if (StaminaBar.isSystemActive && StaminaBar.doesAffectEntity(entity)) {
            StaminaMechanics.tickEntity(event.getEntityLiving());
        }
        tickHitSpeeds(event.getEntityLiving());

		/*if (event.entity.ticksExisted == 1 && event.entity instanceof EntityPlayer && !event.entity.worldObj.isRemote) {
		}*/

    }

    @SubscribeEvent
    public void clonePlayer(PlayerEvent.Clone event) {
        EntityPlayer origin = event.getOriginal();
        EntityPlayer spawn = event.getEntityPlayer();
        if (origin != null && spawn != null) {
            EntityHelper.cloneNBT(origin, spawn);
        }
    }

    @SubscribeEvent
    public void startUseItem(LivingEntityUseItemEvent.Start event) {
        EntityPlayer player = (EntityPlayer) event.getEntityLiving();
        if (event.getItem() != null && event.getItem().getItemUseAction() == EnumAction.BLOCK) {
            if ((StaminaBar.isSystemActive && TacticalManager.shouldStaminaBlock
                    && !StaminaBar.isAnyStamina(player, false)) || !CombatMechanics.isParryAvailable(player)) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void levelup(LevelupEvent event) {
        EntityPlayer player = event.thePlayer;
        if (!player.world.isRemote) {
            ((WorldServer) player.world).getEntityTracker().sendToTrackingAndSelf(player, new LevelupPacket(player, event.theSkill, event.theLevel).generatePacket());
            ((WorldServer) player.world).getEntityTracker().sendToTrackingAndSelf(player, new SkillPacket(player, event.theSkill).generatePacket());
        }
    }

    @SubscribeEvent
    public void syncSkill(SyncSkillEvent event) {
        EntityPlayer player = event.thePlayer;
        if (!player.world.isRemote) {
            ((WorldServer) player.world).getEntityTracker().sendToTrackingAndSelf(player, new SkillPacket(player, event.theSkill).generatePacket());
        }
    }

    @SubscribeEvent
    public void itemEvent(EntityItemPickupEvent event) {
        EntityPlayer player = event.getEntityPlayer();

        EntityItem drop = event.getItem();
        ItemStack item = drop.getItem();
        ItemStack held = player.getHeldItemMainhand();

        if (held != null && held.getItem() instanceof ISmithTongs) {
            if (!TongsHelper.hasHeldItem(held)) {
                if (isHotItem(item)) {
                    if (TongsHelper.trySetHeldItem(held, item)) {
                        drop.setDead();

                        if (event.isCancelable()) {
                            event.setCanceled(true);
                        }
                        return;
                    }
                }
            }
        }
        {
            if (ConfigHardcore.HCChotBurn && item != null && isHotItem(item)) {
                if (event.isCancelable()) {
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public void wakeUp(PlayerWakeUpEvent event) {
        PlayerTickHandlerMF.wakeUp(event.getEntityPlayer());
    }

    private boolean isHotItem(ItemStack item) {
        return item != null && (item.getItem() instanceof IHotItem);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void renderEntity(RenderPlayerEvent.Specials.Pre event) {
        Minecraft mc = Minecraft.getMinecraft();
        boolean showHeld = true;
        if (PowerArmour.isWearingCogwork(event.getEntityPlayer()) && mc.gameSettings.thirdPersonView != 0) {
            IPowerArmour cogwork = (IPowerArmour) event.getEntity().getRidingEntity();
            showHeld = !cogwork.isArmoured("right_arm");
        }
        event.setRenderItem(showHeld);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void renderEntity(RenderLivingEvent.Pre event) {
        if (!(event.getRenderer() instanceof RenderPowerArmour)) {
            boolean renderHead = false;
            boolean renderBody = false;
            boolean renderLeftArm = false;
            boolean renderRightArm = false;
            boolean renderLeftLeg = false;
            boolean renderRightLeg = false;
            Minecraft mc = Minecraft.getMinecraft();

            if (event.getEntity() instanceof EntityPlayer && !(event.getEntity() == mc.player
                    && (mc.currentScreen instanceof GuiContainerCreative || mc.currentScreen instanceof GuiInventory))
                    && PowerArmour.isWearingCogwork(event.getEntity()) && mc.gameSettings.thirdPersonView != 0) {
                IPowerArmour cogwork = (IPowerArmour) event.getEntity().getRidingEntity();
                renderHead = cogwork.isArmoured("left_leg");
                renderBody = cogwork.isArmoured("right_leg");
                renderLeftArm = cogwork.isArmoured("left_arm");
                renderRightArm = cogwork.isArmoured("right_arm");
                renderLeftLeg = cogwork.isArmoured("left_leg");
                renderRightLeg = cogwork.isArmoured("right_leg");
            }

            if (event.getRenderer() instanceof RenderPlayer) {
                RenderPlayer RP = (RenderPlayer) event.getRenderer();
                ModelBiped[] layers = new ModelBiped[]{RP.getMainModel()};

                for (ModelBiped model : layers) {
                    model.bipedHead.isHidden = model.bipedHeadwear.isHidden = model.bipedHead.isHidden = renderHead;
                    model.bipedBody.isHidden = renderBody;

                    model.bipedLeftArm.isHidden = renderLeftArm;
                    model.bipedRightArm.isHidden = renderRightArm;
                    model.bipedLeftLeg.isHidden = renderLeftLeg;
                    model.bipedRightLeg.isHidden = renderRightLeg;
                }
            }
        }
    }
}
