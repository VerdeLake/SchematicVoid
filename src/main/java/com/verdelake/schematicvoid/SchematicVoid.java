package com.verdelake.schematicvoid;

import com.simibubi.create.AllCreativeModeTabs;
import com.verdelake.schematicvoid.block.ModBlocks;
import com.verdelake.schematicvoid.item.ModItems;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@Mod(SchematicVoid.MODID)
public class SchematicVoid {
    public static final String MODID = "schematicvoid";

    public SchematicVoid(IEventBus modEventBus) {
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        modEventBus.addListener(this::addCreative);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == AllCreativeModeTabs.BASE_CREATIVE_TAB.getKey()) {
            event.accept(ModBlocks.SCHEMATIC_VOID_BLOCK);
        }
    }
}