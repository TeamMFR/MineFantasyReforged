package minefantasy.mf2.mechanics;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.api.armour.IPowerArmour;
import minefantasy.mf2.api.armour.ISpecialArmourMF;
import minefantasy.mf2.api.armour.ItemArmourMFBase;
import minefantasy.mf2.api.heating.IHotItem;
import minefantasy.mf2.api.heating.TongsHelper;
import minefantasy.mf2.api.helpers.*;
import minefantasy.mf2.api.knowledge.ResearchLogic;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.api.rpg.LevelupEvent;
import minefantasy.mf2.api.rpg.RPGElements;
import minefantasy.mf2.api.rpg.SyncSkillEvent;
import minefantasy.mf2.api.stamina.StaminaBar;
import minefantasy.mf2.api.tool.IHuntingItem;
import minefantasy.mf2.api.tool.ISmithTongs;
import minefantasy.mf2.api.weapon.WeaponClass;
import minefantasy.mf2.client.render.RenderPowerArmour;
import minefantasy.mf2.config.ConfigExperiment;
import minefantasy.mf2.config.ConfigHardcore;
import minefantasy.mf2.config.ConfigStamina;
import minefantasy.mf2.entity.EntityCogwork;
import minefantasy.mf2.entity.EntityItemUnbreakable;
import minefantasy.mf2.entity.mob.EntityDragon;
import minefantasy.mf2.farming.FarmingHelper;
import minefantasy.mf2.integration.CustomStone;
import minefantasy.mf2.item.ClientItemsMF;
import minefantasy.mf2.item.food.FoodListMF;
import minefantasy.mf2.item.list.ComponentListMF;
import minefantasy.mf2.item.list.ToolListMF;
import minefantasy.mf2.item.weapon.ItemWeaponMF;
import minefantasy.mf2.network.packet.LevelupPacket;
import minefantasy.mf2.network.packet.SkillPacket;
import minefantasy.mf2.util.MFLogUtil;
import minefantasy.mf2.util.XSTRandom;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EventManagerMF {

    public static final String hitspeedNBT = "MF_HitCooldown";
    public static final String injuredNBT = "MF_Injured";
    public static boolean displayOreDict;

    private static XSTRandom random = new XSTRandom();

    public static void addArmourDT(ItemStack armour, EntityPlayer user, List<String> list, boolean extra) {
        list.add("");
        String AC = ArmourCalculator.getArmourClass(armour);
        if (AC != null) {
            list.add(StatCollector.translateToLocal("attribute.armour." + AC));
        }
        list.add(EnumChatFormatting.BLUE + StatCollector.translateToLocal("attribute.armour.protection"));
        addSingleDT(armour, user, 0, list, extra);
        addSingleDT(armour, user, 2, list, extra);
        addSingleDT(armour, user, 1, list, extra);
    }

    public static void addSingleDT(ItemStack armour, EntityPlayer user, int id, List<String> list, boolean extra) {
        int slot = ((ItemArmor) armour.getItem()).armorType;
        String attatch = "";

        int rating = (int) (ArmourCalculator.getDTForDisplayPiece(armour, id) * 100F);
        int equipped = (int) (ArmourCalculator.getDTForDisplayPiece(user.getCurrentArmor(3 - slot), id) * 100F);

        if (rating > 0 || equipped > 0) {
            if (equipped > 0 && rating != equipped) {
                float d = rating - equipped;
                if (d > 0) {
                    attatch += EnumChatFormatting.DARK_GREEN;
                }
                if (d < 0) {
                    attatch += EnumChatFormatting.RED;
                }
                String d2 = ItemWeaponMF.decimal_format.format(d);
                attatch += " (" + (d > 0 ? "+" : "") + d2 + ")";
            }
            list.add(EnumChatFormatting.BLUE + StatCollector.translateToLocal("attribute.armour.rating." + id) + " "
                    + rating + attatch);
        }
    }

    public static void addArmourDR(ItemStack armour, EntityPlayer user, List<String> list, boolean extra) {
        list.add("");
        String AC = ArmourCalculator.getArmourClass(armour);
        if (AC != null) {
            list.add(StatCollector.translateToLocal("attribute.armour." + AC));
        }
        if (armour.getItem() instanceof ISpecialArmourMF) {
            if (ArmourCalculator.advancedDamageTypes) {
                list.add(EnumChatFormatting.BLUE + StatCollector.translateToLocal("attribute.armour.protection"));
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
        int slot = ((ItemArmor) armour.getItem()).armorType;
        String attatch = "";

        int rating = (int) (ArmourCalculator.getDRForDisplayPiece(armour, id) * 100F);
        int equipped = (int) (ArmourCalculator.getDRForDisplayPiece(user.getCurrentArmor(3 - slot), id) * 100F);

        if (rating > 0 || equipped > 0) {
            if (equipped > 0 && rating != equipped) {
                float d = rating - equipped;
                if (d > 0) {
                    attatch += EnumChatFormatting.DARK_GREEN;
                }
                if (d < 0) {
                    attatch += EnumChatFormatting.RED;
                }
                String d2 = ItemWeaponMF.decimal_format.format(d);
                attatch += " (" + (d > 0 ? "+" : "") + d2 + ")";
            }
            if (advanced) {
                list.add(EnumChatFormatting.BLUE + StatCollector.translateToLocal("attribute.armour.rating." + id) + " "
                        + rating + attatch);
            } else {
                list.add(EnumChatFormatting.BLUE + StatCollector.translateToLocal("attribute.armour.protection") + ": "
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
        EntityLivingBase dropper = event.entityLiving;

        if (dropper instanceof EntityChicken) {
            int dropCount = 1 + random.nextInt(event.lootingLevel + 1 * 4);

            for (int a = 0; a < dropCount; a++) {
                dropper.entityDropItem(new ItemStack(Items.feather), 0.0F);
            }
        }
        if (dropper.getEntityData().hasKey("MF_LootDrop")) {
            int id = dropper.getEntityData().getInteger("MF_LootDrop");
            Item drop = id == 0 ? ToolListMF.loot_sack : id == 1 ? ToolListMF.loot_sack_uc : ToolListMF.loot_sack_rare;
            dropper.entityDropItem(new ItemStack(drop), 0.0F);
        }
        if (dropper instanceof EntityAgeable && dropper.getCreatureAttribute() != EnumCreatureAttribute.UNDEAD) {
            if (random.nextFloat() * (1 + event.lootingLevel) < 0.05F) {
                dropper.entityDropItem(new ItemStack(FoodListMF.guts), 0.0F);
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
            int dropCount = random.nextInt(3 + event.lootingLevel);
            if (ConfigHardcore.lessHunt) {
                dropCount = 1 + random.nextInt(event.lootingLevel + 1);
            }

            Item meat = dropper.isBurning() ? FoodListMF.horse_cooked : FoodListMF.horse_raw;
            for (int a = 0; a < dropCount; a++) {
                dropper.entityDropItem(new ItemStack(meat), 0.0F);
            }
        }
        if (getRegisterName(dropper).contains("Wolf")) {
            int dropCount = random.nextInt(3 + event.lootingLevel);
            if (ConfigHardcore.lessHunt) {
                dropCount = 1 + random.nextInt(event.lootingLevel + 1);
            }

            Item meat = dropper.isBurning() ? FoodListMF.wolf_cooked : FoodListMF.wolf_raw;
            for (int a = 0; a < dropCount; a++) {
                dropper.entityDropItem(new ItemStack(meat), 0.0F);
            }
        }
        dropLeather(event.entityLiving, event);

        if (dropper instanceof EntitySkeleton) {
            EntitySkeleton skeleton = (EntitySkeleton) dropper;

            if ((skeleton.getHeldItem() == null || !(skeleton.getHeldItem().getItem() instanceof ItemBow))
                    && event.drops != null && !event.drops.isEmpty()) {
                Iterator<EntityItem> list = event.drops.iterator();

                while (list.hasNext()) {
                    EntityItem entItem = list.next();
                    ItemStack drop = entItem.getEntityItem();

                    if (drop.getItem() == Items.arrow) {
                        entItem.setDead();
                    }
                }
            }
        }
    }

    private void dropLeather(EntityLivingBase mob, LivingDropsEvent event) {
        boolean dropHide = shouldAnimalDropHide(mob);
        Item hide = getHideFor(mob);

        if (event.drops != null && !event.drops.isEmpty()) {
            Iterator<EntityItem> list = event.drops.iterator();

            while (list.hasNext()) {
                EntityItem entItem = list.next();
                ItemStack drop = entItem.getEntityItem();

                if (drop.getItem() == Items.leather) {
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
        Item[] hide = new Item[]{ComponentListMF.rawhideSmall, ComponentListMF.rawhideMedium,
                ComponentListMF.rawhideLarge};
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

        int size = mob.myEntitySize.ordinal() + 1;
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
        if (!event.entityLiving.worldObj.isRemote && event.entityLiving instanceof EntityDragon && event.source != null
                && event.source.getEntity() != null && event.source.getEntity() instanceof EntityPlayer) {
            PlayerTickHandlerMF.addDragonKill((EntityPlayer) event.source.getEntity());
        }
        if (!event.entityLiving.worldObj.isRemote && event.entityLiving instanceof EntityPlayer && event.source != null
                && event.source.getEntity() != null && event.source.getEntity() instanceof EntityDragon) {
            PlayerTickHandlerMF.addDragonEnemyPts((EntityPlayer) event.entityLiving, -1);
        }
        Entity dropper = event.entity;

        boolean useArrows = true;
        try {
            Class.forName("minefantasy.mf2.api.helpers.ArrowEffectsMF");
        } catch (Exception e) {
            useArrows = false;
        }
        if (dropper != null && useArrows && ConfigExperiment.stickArrows && !dropper.worldObj.isRemote) {
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
        if (event.entity.isDead) {
            return;
        }
        if (event.entity instanceof EntityItem && !(event.entity instanceof EntityItemUnbreakable)) {
            EntityItem eitem = (EntityItem) event.entity;
            if (eitem.getEntityItem() != null) {
                if (eitem.getEntityItem().hasTagCompound()
                        && eitem.getEntityItem().getTagCompound().hasKey("Unbreakable")) {
                    EntityItem newEntity = new EntityItemUnbreakable(event.world, eitem);
                    event.world.spawnEntityInWorld(newEntity);
                    eitem.setDead();
                }
                if (isDragonforge(eitem.getEntityItem())) {
                    MFLogUtil.logDebug("Found dragon heart");
                    EntityItem newEntity = new EntityItemUnbreakable(event.world, eitem);
                    event.world.spawnEntityInWorld(newEntity);
                    eitem.setDead();
                }
            }
        }
    }

    private boolean isDragonforge(ItemStack itemstack) {
        return itemstack.getItem() == ComponentListMF.dragon_heart;
    }

    public void alterDrops(EntityLivingBase dropper, LivingDropsEvent event) {
        ArrayList<ItemStack> meats = new ArrayList<ItemStack>();

        if (event.drops != null && !event.drops.isEmpty()) {
            Iterator<EntityItem> list = event.drops.iterator();

            while (list.hasNext()) {
                EntityItem entItem = list.next();
                ItemStack drop = entItem.getEntityItem();
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
                        drop.stackSize = 1;
                        if (event.lootingLevel > 0) {
                            drop.stackSize += dropper.getRNG().nextInt(event.lootingLevel + 1);
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
        EntityLivingBase dead = event.entityLiving;
        EntityLivingBase hunter = null;
        ItemStack weapon = null;
        DamageSource source = event.source;

        if (dead instanceof EntityWitch) {
            dropBook(dead, 0);
        }
        if (dead instanceof EntityVillager) {
            dropBook(dead, 1);
        }
        if (dead instanceof EntityZombie) {
            dropBook(dead, 2);
        }
        if (source != null && source.getEntity() != null) {
            if (source.getEntity() instanceof EntityLivingBase) {
                hunter = (EntityLivingBase) source.getEntity();
                weapon = hunter.getHeldItem();
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
        if (dead.worldObj.isRemote)
            return;
        Item book = null;
        if (id == 0) {
            float chance = random.nextFloat();
            if (chance > 0.75F) {
                book = ToolListMF.skillbook_engineering;
            } else {
                book = ToolListMF.skillbook_provisioning;
            }
        } else if (id == 1 && random.nextInt(5) == 0) {
            float chance = random.nextFloat();
            if (chance > 0.9F) {
                book = ToolListMF.skillbook_engineering;
            } else if (chance > 0.6F) {
                book = ToolListMF.skillbook_artisanry;
            } else if (chance > 0.3F) {
                book = ToolListMF.skillbook_construction;
            } else {
                book = ToolListMF.skillbook_provisioning;
            }
        } else if (id == 2 && random.nextInt(25) == 0) {
            float chance = random.nextFloat();
            if (chance > 0.9F) {
                book = ToolListMF.skillbook_engineering;
            } else if (chance > 0.6F) {
                book = ToolListMF.skillbook_artisanry;
            } else if (chance > 0.3F) {
                book = ToolListMF.skillbook_construction;
            } else {
                book = ToolListMF.skillbook_provisioning;
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
        Block block = event.world.getBlock(event.x, event.y, event.z);
        if (block != Blocks.farmland && FarmingHelper.didHoeFail(event.current, event.world, block == Blocks.grass)) {
            event.entityPlayer.swingItem();
            event.world.playAuxSFXAtEntity(event.entityPlayer, 2001, event.x, event.y, event.z,
                    Block.getIdFromBlock(block) + (event.world.getBlockMetadata(event.x, event.y, event.z) << 12));
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void breakBlock(BreakEvent event) {
        // Block block = event.block;
        Block base = event.world.getBlock(event.x, event.y - 1, event.z);
        // int meta = event.blockMetadata;

        if (base != null && base == Blocks.farmland && FarmingHelper.didHarvestRuinBlock(event.world, false)) {
            event.world.setBlock(event.x, event.y - 1, event.z, Blocks.dirt);
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
        ItemStack held = player.getHeldItem();
        Block broken = event.block;

        if (broken != null && ConfigHardcore.HCCallowRocks) {
            if (held == null && CustomStone.isStone(broken)) {
                entityDropItem(event.world, event.x, event.y, event.z,
                        new ItemStack(ComponentListMF.sharp_rock, random.nextInt(3) + 1));
            }
            if (held != null && held.getItem() == ComponentListMF.sharp_rock && broken instanceof BlockLeavesBase) {
                if (random.nextInt(5) == 0) {
                    entityDropItem(event.world, event.x, event.y, event.z,
                            new ItemStack(Items.stick, random.nextInt(3) + 1));
                }
                if (random.nextInt(3) == 0) {
                    entityDropItem(event.world, event.x, event.y, event.z,
                            new ItemStack(ComponentListMF.vine, random.nextInt(3) + 1));
                }
            }
        }

        if (StaminaBar.isSystemActive && ConfigStamina.affectMining && StaminaBar.doesAffectEntity(player)) {
            float points = 2.0F * ConfigStamina.miningSpeed;
            ItemWeaponMF.applyFatigue(player, points, 20F);

            if (points > 0 && !StaminaBar.isAnyStamina(player, false)) {
                player.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 100, 1));
            }
        }
    }

    public EntityItem entityDropItem(World world, int x, int y, int z, ItemStack item) {
        if (item.stackSize != 0 && item.getItem() != null) {
            EntityItem entityitem = new EntityItem(world, x + 0.5D, y + 0.5D, z + 0.5D, item);
            entityitem.delayBeforeCanPickup = 10;
            world.spawnEntityInWorld(entityitem);
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
        if (!event.entity.worldObj.isRemote) {
            return;
        }

        if (event.itemStack != null) {
            boolean saidArtefact = false;
            int[] ids = OreDictionary.getOreIDs(event.itemStack);
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

                            CustomToolHelper.addComponentString(event.itemStack, event.toolTip, material);
                        }
                        if (s.startsWith("Artefact-")) {
                            if (!saidArtefact) {
                                String knowledge = s.substring(9).toLowerCase();

                                if (!ResearchLogic.hasInfoUnlocked(event.entityPlayer, knowledge)) {
                                    saidArtefact = true;
                                    event.toolTip.add(EnumChatFormatting.AQUA
                                            + StatCollector.translateToLocal("info.hasKnowledge"));
                                }
                            }
                        } else if (displayOreDict) {
                            event.toolTip.add("oreDict: " + s);
                        }
                    }
                }
            }

            if (event.itemStack.hasTagCompound() && event.itemStack.getTagCompound().hasKey("MF_Inferior")) {
                if (event.itemStack.getTagCompound().getBoolean("MF_Inferior")) {
                    event.toolTip
                            .add(EnumChatFormatting.RED + StatCollector.translateToLocal("attribute.inferior.name"));
                }
                if (!event.itemStack.getTagCompound().getBoolean("MF_Inferior")) {
                    event.toolTip
                            .add(EnumChatFormatting.GREEN + StatCollector.translateToLocal("attribute.superior.name"));
                }
            }
            if (event.itemStack.getItem() instanceof ItemArmor
                    && (!(event.itemStack.getItem() instanceof ItemArmourMFBase) || ClientItemsMF.showSpecials(
                    event.itemStack, event.entityPlayer, event.toolTip, event.showAdvancedItemTooltips))) {
                addArmourDR(event.itemStack, event.entityPlayer, event.toolTip, event.showAdvancedItemTooltips);
            }
            if (ArmourCalculator.advancedDamageTypes && ArmourCalculator.getRatioForWeapon(event.itemStack) != null) {
                displayWeaponTraits(ArmourCalculator.getRatioForWeapon(event.itemStack), event.toolTip);
            }
            if (ToolHelper.shouldShowTooltip(event.itemStack)) {
                showCrafterTooltip(event.itemStack, event.toolTip);
            }
            if (event.itemStack.hasTagCompound() && event.itemStack.getTagCompound().hasKey("MF_CraftedByName")) {
                String name = event.itemStack.getTagCompound().getString("MF_CraftedByName");
                boolean special = MineFantasyII.isNameModder(name);// Mod creators have highlights

                event.toolTip.add((special ? EnumChatFormatting.GREEN : "")
                        + StatCollector.translateToLocal("attribute.mfcraftedbyname.name") + ": " + name
                        + EnumChatFormatting.GRAY);
            }
            WeaponClass WC = WeaponClass.findClassForAny(event.itemStack);
            if (WC != null && RPGElements.isSystemActive && WC.parentSkill != null) {
                event.toolTip.add(StatCollector.translateToLocal("weaponclass." + WC.name.toLowerCase()));
                float skillMod = RPGElements.getWeaponModifier(event.entityPlayer, WC.parentSkill) * 100F;
                if (skillMod > 100)
                    event.toolTip.add(StatCollector.translateToLocal("rpg.skillmod")
                            + ItemWeaponMF.decimal_format.format(skillMod - 100) + "%");

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
            String s = StatCollector.translateToLocal("attribute.weapon." + name);
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

        list.add(StatCollector.translateToLocal("attribute.mfcrafttool.name") + ": "
                + StatCollector.translateToLocal("tooltype." + toolType));
        list.add(StatCollector.translateToLocal("attribute.mfcrafttier.name") + ": " + tier);
        list.add(StatCollector.translateToLocal("attribute.mfcrafteff.name") + ": " + efficiency);
    }

    @SubscribeEvent
    public void updateEntity(LivingUpdateEvent event) {
        if (event.entity instanceof EntityCogwork) {
            return;
        }
        EntityLivingBase entity = event.entityLiving;

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
                    entity.worldObj.spawnParticle("reddust", x, y, z, 0F, 0F, 0F);
                }
            }
            if (!entity.worldObj.isRemote && entity.getHealth() <= (lowHp / 2) && entity.getRNG().nextInt(200) == 0) {
                entity.addPotionEffect(new PotionEffect(Potion.confusion.id, 100, 50));
            }
        }
        if (injury > 0 && !entity.worldObj.isRemote) {
            injury--;
            entity.getEntityData().setInteger(injuredNBT, injury);
        }
        if (StaminaBar.isSystemActive && StaminaBar.doesAffectEntity(entity)) {
            StaminaMechanics.tickEntity(event.entityLiving);
        }
        tickHitSpeeds(event.entityLiving);

		/*if (event.entity.ticksExisted == 1 && event.entity instanceof EntityPlayer && !event.entity.worldObj.isRemote) {
		}*/

    }

    @SubscribeEvent
    public void clonePlayer(PlayerEvent.Clone event) {
        EntityPlayer origin = event.original;
        EntityPlayer spawn = event.entityPlayer;
        if (origin != null && spawn != null) {
            EntityHelper.cloneNBT(origin, spawn);
        }
    }

    @SubscribeEvent
    public void startUseItem(PlayerUseItemEvent.Start event) {
        EntityPlayer player = event.entityPlayer;
        if (event.item != null && event.item.getItemUseAction() == EnumAction.block) {
            if ((StaminaBar.isSystemActive && TacticalManager.shouldStaminaBlock
                    && !StaminaBar.isAnyStamina(player, false)) || !CombatMechanics.isParryAvailable(player)) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void levelup(LevelupEvent event) {
        EntityPlayer player = event.thePlayer;
        if (!player.worldObj.isRemote) {
            ((WorldServer) player.worldObj).getEntityTracker().func_151248_b(player,
                    new LevelupPacket(player, event.theSkill, event.theLevel).generatePacket());
            ((WorldServer) player.worldObj).getEntityTracker().func_151248_b(player,
                    new SkillPacket(player, event.theSkill).generatePacket());
        }
    }

    @SubscribeEvent
    public void syncSkill(SyncSkillEvent event) {
        EntityPlayer player = event.thePlayer;
        if (!player.worldObj.isRemote) {
            ((WorldServer) player.worldObj).getEntityTracker().func_151248_b(player,
                    new SkillPacket(player, event.theSkill).generatePacket());
        }
    }

    @SubscribeEvent
    public void itemEvent(EntityItemPickupEvent event) {
        EntityPlayer player = event.entityPlayer;

        EntityItem drop = event.item;
        ItemStack item = drop.getEntityItem();
        ItemStack held = player.getHeldItem();

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
        PlayerTickHandlerMF.wakeUp(event.entityPlayer);
    }

    private boolean isHotItem(ItemStack item) {
        return item != null && (item.getItem() instanceof IHotItem);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void renderEntity(RenderPlayerEvent.Specials.Pre event) {
        Minecraft mc = Minecraft.getMinecraft();
        boolean showHeld = true;
        if (PowerArmour.isWearingCogwork(event.entityPlayer) && mc.gameSettings.thirdPersonView != 0) {
            IPowerArmour cogwork = (IPowerArmour) event.entity.ridingEntity;
            showHeld = !cogwork.isArmoured("right_arm");
        }
        event.renderItem = showHeld;
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void renderEntity(RenderLivingEvent.Pre event) {
        if (!(event.renderer instanceof RenderPowerArmour)) {
            boolean renderHead = false;
            boolean renderBody = false;
            boolean renderLeftArm = false;
            boolean renderRightArm = false;
            boolean renderLeftLeg = false;
            boolean renderRightLeg = false;
            Minecraft mc = Minecraft.getMinecraft();

            if (event.entity instanceof EntityPlayer && !(event.entity == mc.thePlayer
                    && (mc.currentScreen instanceof GuiContainerCreative || mc.currentScreen instanceof GuiInventory))
                    && PowerArmour.isWearingCogwork(event.entity) && mc.gameSettings.thirdPersonView != 0) {
                IPowerArmour cogwork = (IPowerArmour) event.entity.ridingEntity;
                renderHead = cogwork.isArmoured("left_leg");
                renderBody = cogwork.isArmoured("right_leg");
                renderLeftArm = cogwork.isArmoured("left_arm");
                renderRightArm = cogwork.isArmoured("right_arm");
                renderLeftLeg = cogwork.isArmoured("left_leg");
                renderRightLeg = cogwork.isArmoured("right_leg");
            }

            if (event.renderer instanceof RenderPlayer) {
                RenderPlayer RP = (RenderPlayer) event.renderer;
                ModelBiped[] layers = new ModelBiped[]{RP.modelArmor, RP.modelArmorChestplate};

                for (ModelBiped model : layers) {
                    model.bipedHead.isHidden = model.bipedHeadwear.isHidden = model.bipedEars.isHidden = renderHead;
                    model.bipedBody.isHidden = model.bipedCloak.isHidden = renderBody;

                    model.bipedLeftArm.isHidden = renderLeftArm;
                    model.bipedRightArm.isHidden = renderRightArm;
                    model.bipedLeftLeg.isHidden = renderLeftLeg;
                    model.bipedRightLeg.isHidden = renderRightLeg;
                }
            }
        }
    }
}
