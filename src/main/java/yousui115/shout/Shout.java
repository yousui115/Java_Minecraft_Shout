package yousui115.shout;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import yousui115.shout.entity.EntityWhisper;
import yousui115.shout.event.EventShoutHook;
import yousui115.shout.network.PacketHdlr;

@Mod(modid = Shout.MOD_ID, version = Shout.VERSION)
public class Shout
{
    public static final String MOD_ID     = "shout";
    public static final String MOD_DOMAIN = "yousui115." + MOD_ID;
    public static final String VERSION    = "1.0";

    @SidedProxy(clientSide = MOD_DOMAIN + ".client.ClientProxy",
                serverSide = MOD_DOMAIN + ".CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        //■パケットとハンドラの登録
        PacketHdlr.registerMessage();

        //■Entity登録
        EntityRegistry.registerModEntity(EntityWhisper.class, "Whisper", 0, this, 64, 5, false);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        //■Entity,Renderの関連付け
        proxy.registerRenderers();

        //■キー登録
        proxy.registerKey();

        //■イベントフックの登録
        //  FMLCommonHandler.eventBus = MinecraftForge.EVENT_BUS;っぽいからどちらでも一緒？
        MinecraftForge.EVENT_BUS.register(new EventShoutHook());
    }
}
