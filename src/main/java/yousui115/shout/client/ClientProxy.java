package yousui115.shout.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import org.lwjgl.input.Keyboard;

import yousui115.shout.CommonProxy;
import yousui115.shout.client.render.RenderWhisper;
import yousui115.shout.entity.EntityWhisper;

public class ClientProxy extends CommonProxy
{
    /**
     * ■モデルの登録
     */
    @Override
    public void registerRenderers()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityWhisper.class, new RenderWhisper(getRenderMgr()));
    }

    /**
     * ■シャウトキー
     */
    public static final KeyBinding shoutKey = new KeyBinding("key.shout", Keyboard.KEY_R, "Shout");
    @Override
    public void registerKey() { ClientRegistry.registerKeyBinding(shoutKey); }
    @Override
    public boolean pressedShoutKey() { return shoutKey.isPressed(); }

    /* ============================  イカ、インスタント取得メソッド ============================ */

    @Override
    public Minecraft getMC() { return Minecraft.getMinecraft(); }
    @Override
    public EntityPlayer getThePlayer() { return Minecraft.getMinecraft().thePlayer; }
    @Override
    public RenderManager getRenderMgr() { return Minecraft.getMinecraft().getRenderManager(); }
    @Override
    public RenderItem getRenderItem() { return Minecraft.getMinecraft().getRenderItem(); }
}
