package yousui115.shout.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import yousui115.shout.Shout;

public class PacketHdlr
{
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Shout.MOD_ID);

    /**
    * ■登録
    */
    public static void registerMessage()
    {
        /*IMesssageHandlerクラスとMessageクラスの登録。
        *第三引数：MessageクラスのMOD内での登録ID。256個登録できる
        *第四引数：送り先指定。クライアントかサーバーか、Side.CLIENT Side.SERVER*/
        INSTANCE.registerMessage(MessageShoutHdlr.class, MessageShout.class, 0, Side.SERVER);
    }
}
