package minefantasy.mf2.network.packet;

import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.EntityPlayer;

public abstract class PacketMF {

    public final FMLProxyPacket generatePacket() {
        ByteBuf buf = Unpooled.buffer(16);
        write(buf);
        return new FMLProxyPacket(buf, getChannel());
    }

    public abstract String getChannel();

    public abstract void write(ByteBuf out);

    public abstract void process(ByteBuf in, EntityPlayer user);
}
