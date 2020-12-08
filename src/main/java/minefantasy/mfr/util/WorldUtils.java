package minefantasy.mfr.util;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nullable;
import java.util.Optional;

public class WorldUtils {

	public static Optional<TileEntity> getTile(IBlockAccess world, BlockPos pos) {
		return getTile(world, pos, TileEntity.class);
	}

	public static <T> Optional<T> getTile(@Nullable IBlockAccess world, @Nullable BlockPos pos, Class<T> teClass) {
		if (world == null || pos == null) {
			return Optional.empty();
		}

		TileEntity te = world.getTileEntity(pos);

		if (teClass.isInstance(te)) {
			return Optional.of(teClass.cast(te));
		}

		return Optional.empty();
	}
}
