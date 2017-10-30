package minefantasy.mf2.asm;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

@IFMLLoadingPlugin.SortingIndex(1001)
@IFMLLoadingPlugin.TransformerExclusions("minefantasy.mf2.asm")
@IFMLLoadingPlugin.MCVersion("1.7.10")
public class MFLoadingPlugin implements IFMLLoadingPlugin {

    @Override
    public String[] getASMTransformerClass() {
        ASMHelper.isASMEnabled = true;
        return new String[]{MFClassTransformer.class.getName()};
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
}
