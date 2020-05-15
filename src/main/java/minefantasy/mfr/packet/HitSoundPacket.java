package minefantasy.mfr.packet;

import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import minefantasy.mfr.config.ConfigClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Objects;

public class HitSoundPacket extends PacketMF {
    public static final String packetName = "MF2_Hitsound";
    private SoundEvent sound;
    private int entId;

    public HitSoundPacket(SoundEvent sound, Entity hit) {
        this.sound = sound;
        this.entId = hit.getEntityId();
    }

    public HitSoundPacket() {
    }

    @Override
    public void process(ByteBuf packet, EntityPlayer player) {
        entId = packet.readInt();
        Entity entity = player.world.getEntityByID(entId);
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
        ByteBufUtils.writeUTF8String(packet, sound.toString());
    }
}
