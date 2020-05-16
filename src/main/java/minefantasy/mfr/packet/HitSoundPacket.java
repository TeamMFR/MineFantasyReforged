package minefantasy.mfr.packet;

import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import minefantasy.mfr.config.ConfigClient;
import minefantasy.mfr.init.SoundsMFR;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Objects;

public class HitSoundPacket extends PacketMF {
    public static final String packetName = "MF2_Hitsound";
    private SoundEvent sound;
    private String type;
    private String material;
    private int entId;

    public HitSoundPacket(String type, String material, Entity hit) {
        this.sound = getSound(type, material);
        this.type = type;
        this.material = material;
        this.entId = hit.getEntityId();
    }

    public HitSoundPacket() {
    }

    @Override
    public void process(ByteBuf packet, EntityPlayer player) {
        entId = packet.readInt();
        Entity entity = player.world.getEntityByID(entId);
        sound = getSound(ByteBufUtils.readUTF8String(packet),ByteBufUtils.readUTF8String(packet));
        
        if (entity != null) {
            if (ConfigClient.playHitsound) {
                entity.world.playSound(entity.posX, entity.posY, entity.posZ, sound, SoundCategory.NEUTRAL, 1.0F, 1.0F, false);
            }
        }
    }

    @Override
    public String getChannel() {
        return packetName;
    }

    @Override
    public void write(ByteBuf packet) {
        packet.writeInt(entId);
        ByteBufUtils.writeUTF8String(packet, type);
        ByteBufUtils.writeUTF8String(packet, material);
    }
    
    public static SoundEvent getSound (String type, String material){
        if (type.equals("blunt") && material.equals("wood")){
            return SoundsMFR.BLUNT_WOOD;
        }
        if (type.equals("blunt") && material.equals("metal")){
            return SoundsMFR.BLUNT_METAL;
        }
        if (type.equals("blunt") && material.equals("stone")){
            return SoundsMFR.BLUNT_STONE;
        }
        if (type.equals("blade") && material.equals("wood")){
            return SoundsMFR.BLADE_WOOD;
        }
        if (type.equals("blade") && material.equals("metal")){
            return SoundsMFR.BLADE_METAL;
        }
        if (type.equals("blade") && material.equals("stone")){
            return SoundsMFR.BLADE_STONE;
        }
        return null;
    }
}
