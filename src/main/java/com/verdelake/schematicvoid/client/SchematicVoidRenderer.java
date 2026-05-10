package com.verdelake.schematicvoid.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.verdelake.schematicvoid.block.ModBlocks;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import org.joml.Matrix4f;
import net.minecraft.client.renderer.culling.Frustum;

@EventBusSubscriber(modid = "schematicvoid", value = Dist.CLIENT)
public class SchematicVoidRenderer {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("schematicvoid", "textures/block/schematic_void.png");

    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES) return;

        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null) return;

        ItemStack held = player.getMainHandItem();
        if (!held.is(ModBlocks.SCHEMATIC_VOID_BLOCK.get().asItem())) return;

        Level level = mc.level;
        if (level == null) return;

        PoseStack poseStack = event.getPoseStack();
        Camera camera = event.getCamera();
        Vec3 camPos = camera.getPosition();

        RenderSystem.setShaderTexture(0, TEXTURE);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.enableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        Frustum frustum = event.getFrustum();

        BlockPos playerPos = player.blockPosition();
        int radius = 32;
        float size = 0.4f;

        Tesselator tess = Tesselator.getInstance();
        BufferBuilder buffer = tess.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

        for (BlockPos pos : BlockPos.betweenClosed(
                playerPos.offset(-radius, -radius, -radius),
                playerPos.offset(radius, radius, radius)
        )) {
            if (!level.getBlockState(pos).is(ModBlocks.SCHEMATIC_VOID_BLOCK.get())) continue;
            AABB box = new AABB(pos);
            if (!frustum.isVisible(box)) continue;

            poseStack.pushPose();
            poseStack.translate(
                    pos.getX() - camPos.x + 0.5,
                    pos.getY() - camPos.y + 0.5,
                    pos.getZ() - camPos.z + 0.5
            );

            poseStack.mulPose(camera.rotation());

            Matrix4f matrix = poseStack.last().pose();

            buffer.addVertex(matrix, -size, -size, 0).setUv(0, 1);
            buffer.addVertex(matrix,  size, -size, 0).setUv(1, 1);
            buffer.addVertex(matrix,  size,  size, 0).setUv(1, 0);
            buffer.addVertex(matrix, -size,  size, 0).setUv(0, 0);

            poseStack.popPose();
        }

        MeshData data = buffer.build();
        if (data != null) {
            BufferUploader.drawWithShader(data);
        }

        RenderSystem.disableBlend();
    }
}