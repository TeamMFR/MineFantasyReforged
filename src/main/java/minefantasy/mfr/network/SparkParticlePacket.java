package minefantasy.mfr.network;

import io.netty.buffer.ByteBuf;
import minefantasy.mfr.util.ParticleBuilder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class SparkParticlePacket extends PacketMF {
	double x, y, z;

	public SparkParticlePacket(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public SparkParticlePacket() {
	}

	@Override
	protected void writeToStream(ByteBuf data) {
		data.writeDouble(x);
		data.writeDouble(y);
		data.writeDouble(z);
	}



	@Override
	protected void readFromStream(ByteBuf data) {
		x = data.readDouble();
		y = data.readDouble();
		z = data.readDouble();
	}

	@Override
	protected void execute(EntityPlayer player) {
		if (player.world.isRemote) {
			generateSparks(player.world, x, y, z);
		}
	}

	public static void generateSparks(World world, double x, double y, double z) {
		for(int i = 0; i < (int) MathHelper.nextDouble(world.rand, 50, 100); i++){
			double randVelX = MathHelper.nextDouble(world.rand, -0.5, 0.5);
			double randVelZ = MathHelper.nextDouble(world.rand, -0.5, 0.5);

			float yaw = calcAngleFromVelocity(randVelX, randVelZ);
			ParticleBuilder.create(ParticleBuilder.Type.SPARK)
					.pos(x, y + 0.86F, z)
					.time((int) MathHelper.nextDouble(world.rand, 10, 35))
					.scale(0.075F)
					.face(yaw, 90)
					.clr(255, 125, 0)
					.fade(255,0,0)
					.vel(randVelX, 0, randVelZ)
					.gravity(true)
					.spawn(world);
		}
	}

	private static float calcAngleFromVelocity(double randVelX, double randVelZ) {
		if ((randVelX > 0.05 && randVelZ < -0.05) || (randVelX < -0.05 && randVelZ > 0.05)){
			return  45;
		}
		else if ((randVelX > 0.05 && randVelZ > 0.05) || (randVelX < -0.05 && randVelZ < -0.05)) {
			return -45;
		}
		else if (randVelX >= 0.05 || randVelX <= -0.05) {
			return 90;
		} else {
			return 0;
		}
	}
}
