package com.verdelake.schematicvoid.item;

import com.verdelake.schematicvoid.SchematicVoid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SchematicVoid.MODID);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
