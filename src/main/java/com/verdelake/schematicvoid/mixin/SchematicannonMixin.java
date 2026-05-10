package com.verdelake.schematicvoid.mixin;

import com.simibubi.create.content.schematics.cannon.SchematicannonBlockEntity;
import com.verdelake.schematicvoid.block.ModBlocks;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = SchematicannonBlockEntity.class, remap = false)
public abstract class SchematicannonMixin {

    @ModifyVariable(
            method = "launchBlockOrBelt", // Metodo en línea 636 del archivo original
            at = @At("HEAD"),
            argsOnly = true
    )
    private BlockState replaceVoidWithAir(BlockState blockState) {
        // Si el bloque que va a disparar es nuestro Void, le pasamos Aire
        // (Usa tu DeferredRegister para obtener la instancia del bloque)
        if (blockState != null && blockState.is(ModBlocks.SCHEMATIC_VOID_BLOCK.get())) {
            return Blocks.AIR.defaultBlockState();
        }
        return blockState;
    }
}