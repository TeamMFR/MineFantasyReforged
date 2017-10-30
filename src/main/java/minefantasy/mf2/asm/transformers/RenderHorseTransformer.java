package minefantasy.mf2.asm.transformers;

import minefantasy.mf2.asm.ASMHelper;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import static minefantasy.mf2.asm.Mappings.*;

public class RenderHorseTransformer {

    public static byte[] transform(String name, String transformedName, byte[] bytes) {
        ClassNode horseRenderClass = ASMHelper.createClassFromByteArray(bytes);

        injectShouldRenderPass(horseRenderClass);
        //transformRenderVanillaHorse(horseRenderClass);

        return ASMHelper.createByteArrayFromClass(horseRenderClass, ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
    }

    private static void transformRenderVanillaHorse(MethodNode node) {

    }

    private static void injectShouldRenderPass(ClassNode node) {
        MethodNode newMethod = new MethodNode(Opcodes.ACC_PROTECTED, shouldRenderPass, "(Lnet/minecraft/entity/EntityLivingBase;IF)I", null, null);
        newMethod.visitCode();

        node.methods.add(newMethod);
    }

}
