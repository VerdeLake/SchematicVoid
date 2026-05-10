package com.verdelake.schematicvoid.block;

import com.simibubi.create.api.schematic.requirement.SpecialBlockItemRequirement;
import com.simibubi.create.content.schematics.requirement.ItemRequirement;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

// Implementamos ISpecialBlockItemRequirement para que el cañón no pida ítems
public class SchematicVoidBlock extends Block implements SpecialBlockItemRequirement {
    public SchematicVoidBlock(Properties properties) {
        super(properties);
    }

    @Override
    public ItemRequirement getRequiredItems(BlockState state, BlockEntity be) {
        // Esto hace que NO aparezca en la lista de materiales (Checklist)
        return ItemRequirement.NONE;
    }


}