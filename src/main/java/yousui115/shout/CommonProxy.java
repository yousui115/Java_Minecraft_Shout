package yousui115.shout;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;

public class CommonProxy
{
    public void registerRenderers() {}

    public void registerKey() {}
    public boolean pressedShoutKey() { return false; }

    /* ============================  イカ、インスタント取得メソッド ============================ */

    public Minecraft getMC() { return null; }
    public EntityPlayer getThePlayer() { return null; }
    public RenderManager getRenderMgr() { return null; }
    public RenderItem getRenderItem() { return null; }
}
