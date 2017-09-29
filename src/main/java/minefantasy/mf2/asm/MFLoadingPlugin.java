package minefantasy.mf2.asm;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import minefantasy.mf2.asm.transformers.EntityHorseTransformer;
import net.minecraft.launchwrapper.IClassTransformer;

import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.7.10")
public class MFLoadingPlugin implements IFMLLoadingPlugin, IClassTransformer {

    @Override
    public String[] getASMTransformerClass() {
        ASMHelper.isASMEnabled = false;
        /* TODO
        Config option for ASM patches
         */
        return new String[]{this.getClass().getName()};
    }

    @Override
    public String getModContainerClass() {

        return null;
    }

    @Override
    public String getSetupClass() {

        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        ASMHelper.isSrg = (Boolean) data.get("runtimeDeobfuscationEnabled");
        new Mappings();
    }

    @Override
    public String getAccessTransformerClass() {

        return null;
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] classBytes) {
        /*if (transformedName.equals("net.minecraft.entity.passive.EntityHorse")) {
            return EntityHorseTransformer.transform(name, transformedName, classBytes);
        }*/

        return classBytes;
    }
}
