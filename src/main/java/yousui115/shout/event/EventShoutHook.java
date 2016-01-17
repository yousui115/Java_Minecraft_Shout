package yousui115.shout.event;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import yousui115.shout.Shout;
import yousui115.shout.entity.EntityWhisper;
import yousui115.shout.network.MessageShout;
import yousui115.shout.network.PacketHdlr;

/**
 * ■EventHook (シャウト用)
 *
 */
public class EventShoutHook
{
    //TODO プレイヤーのNBTに突っ込むか、カスタムデータで持たせるか。うーん
    @SideOnly(Side.CLIENT)
    private int coolTime = 0;
    @SideOnly(Side.CLIENT)
    private int coolTimeMax = 0;
    @SideOnly(Side.CLIENT)
    private static ResourceLocation resource = new ResourceLocation(Shout.MOD_ID, "textures/gui/icon_shout.png");

    /**
     * ■キーイベント
     *   (何かしらのキーが押された時だけ呼び出される)
     * @param event
     */
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onKeyInputEvent(KeyInputEvent event)
    {
        //■シャウトキーが押された かつ クールタイム終わってる
        if (Shout.proxy.pressedShoutKey() && coolTime == 0)
        {
            coolTime = coolTimeMax = 600;

            //■オーラウィスパー
            doShout_Whisper(coolTimeMax - 300);

        }
    }

    /**
     * ■クールタイムの減算
     * @param event
     */
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void decrementCoolTime(LivingUpdateEvent event)
    {
        if (event.entityLiving instanceof EntityPlayer &&
            event.entityLiving.worldObj.isRemote)
        {
            if (coolTime > 0)
            {
                coolTime--;
            }
        }
    }

    /**
     * ■クールタイム中のアイコン表示
     * @param event
     */
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void renderShoutOvrly(RenderGameOverlayEvent.Pre event)
    {
        Minecraft mc = Shout.proxy.getMC();

        //■特に理由は無いが、クロスヘアのタイミングで描画
        if (coolTime > 0 && event.type == ElementType.CROSSHAIRS)
        {
            int width = event.resolution.getScaledWidth();
            int height = event.resolution.getScaledHeight();

            mc.getTextureManager().bindTexture(resource);
            GlStateManager.enableBlend();
            GlStateManager.enableAlpha();
            float alpha = (float)coolTime / (float)coolTimeMax;
            GlStateManager.color(1f, 1f, 1f, alpha + 0.1f);
            mc.ingameGUI.drawTexturedModalRect(20, 20, 0, 0, 16, 16);
            GlStateManager.disableBlend();

            GlStateManager.color(1f, 1f, 1f, 1f);

        }
    }

    /* ============================== イカ、各シャウト処理 ============================== */

    /**
     * ■オーラウィスパー
     */
    @SideOnly(Side.CLIENT)
    private void doShout_Whisper(int tickTimeMax)
    {
        EntityPlayer player = Shout.proxy.getThePlayer();

        double expand = 30d;
        AxisAlignedBB aabb = AxisAlignedBB.fromBounds(  player.posX - expand,
                                                        player.posY - expand,
                                                        player.posZ - expand,
                                                        player.posX + expand,
                                                        player.posY + expand,
                                                        player.posZ + expand);

        List<Entity> entities = Shout.proxy.getThePlayer().worldObj.getEntitiesWithinAABBExcludingEntity(player, aabb);

        if (entities == null) { return; }

        for (Entity entity : entities)
        {
            //Shout.proxy.getThePlayer().addChatMessage(new ChatComponentText(entity.toString()));
            if (!(entity instanceof EntityLivingBase)) { continue; }
            if (entity.getDistanceToEntity(player) > (float)expand) { continue; }

            EntityWhisper whisper = new EntityWhisper(entity.worldObj, (EntityLivingBase)entity, tickTimeMax);

            entity.worldObj.addWeatherEffect(whisper);

            PacketHdlr.INSTANCE.sendToServer(new MessageShout(whisper));
        }
    }
}
