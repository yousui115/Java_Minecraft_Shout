package yousui115.shout.network;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import yousui115.shout.entity.EntityWhisper;

public class MessageShoutHdlr implements IMessageHandler<MessageShout, IMessage>
{
    /**
     * ■Client -> Server
     */
    @Override
    public IMessage onMessage(MessageShout message, MessageContext ctx)
    {
        //■ターゲットの確保
        EntityPlayer player = ctx.getServerHandler().playerEntity;
        Entity target = player.worldObj.getEntityByID(message.getTargetID());
        if (target != null && target instanceof EntityLivingBase)
        {
            //■Whisperの生成
            EntityWhisper whisper = new EntityWhisper(player.worldObj, (EntityLivingBase)target, message.getTickMax());
            whisper.setEntityId(message.getWhisperID());

            player.worldObj.addWeatherEffect(whisper);
        }

        return null;
    }

}
