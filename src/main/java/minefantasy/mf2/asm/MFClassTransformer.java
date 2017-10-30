package minefantasy.mf2.asm;

import minefantasy.mf2.asm.transformers.EntityHorseTransformer;
import net.minecraft.launchwrapper.IClassTransformer;

public class MFClassTransformer implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] classBytes) {
        if (transformedName.equals("net.minecraft.entity.passive.EntityHorse")) {
            return EntityHorseTransformer.transform(name, transformedName, classBytes);
        }

        return classBytes;
    }

}
