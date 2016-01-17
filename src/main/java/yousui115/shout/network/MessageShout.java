package yousui115.shout.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import yousui115.shout.entity.EntityWhisper;

public class MessageShout implements IMessage
{
    private int whisperID;
    private int targetID;
    private int tickMax;

    public MessageShout(){}
    public MessageShout(EntityWhisper whisperIn)
    {
        this.whisperID = whisperIn.getEntityId();
        this.targetID = whisperIn.target.getEntityId();
        this.tickMax = whisperIn.tickMax;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.whisperID = buf.readInt();
        this.targetID = buf.readInt();
        this.tickMax = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.whisperID);
        buf.writeInt(this.targetID);
        buf.writeInt(this.tickMax);
    }

    public int getWhisperID() { return this.whisperID; }
    public int getTargetID() { return this.targetID; }
    public int getTickMax() { return this.tickMax; }
}
