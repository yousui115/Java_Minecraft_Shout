package yousui115.shout.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityWeatherEffect;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityWhisper extends EntityWeatherEffect
{
    //■標的
    public EntityLivingBase target;

    //■生存期間
    public int tickMax = 0;

    /**
     * ■コンストラクタ
     */
    public EntityWhisper(World worldIn) { super(worldIn); }
    public EntityWhisper(World worldIn, EntityLivingBase targetIn, int tickMaxIn)
    {
        this(worldIn);

        this.target = targetIn;

        this.tickMax = tickMaxIn;

        setSize(1f, 1f);

        traceTarget();
    }

    @Override
    protected void entityInit(){}
    @Override
    protected void readEntityFromNBT(NBTTagCompound tagCompund) {}
    @Override
    protected void writeEntityToNBT(NBTTagCompound tagCompound) {}

    @Override
    public void onUpdate()
    {
        //■ターゲットが生存 かつ 生存期間内 である
        if (this.target != null &&
            !this.target.isDead &&
            ticksExisted < tickMax)
        {
            traceTarget();
        }
        else
        {
            this.setDead();
        }
    }

    /**
     * ■とりあえず、パラメータを真似とけ、的な？みたいな？
     */
    protected void traceTarget()
    {
        this.lastTickPosX = target.lastTickPosX;
        this.lastTickPosY = target.lastTickPosY;
        this.lastTickPosZ = target.lastTickPosZ;
        this.prevPosX = target.prevPosX;
        this.prevPosY = target.prevPosY;
        this.prevPosZ = target.prevPosZ;
        this.posX = target.posX;
        this.posY = target.posY;
        this.posZ = target.posZ;

        this.prevDistanceWalkedModified = target.prevDistanceWalkedModified;

        this.serverPosX = target.serverPosX;
        this.serverPosY = target.serverPosY;
        this.serverPosZ = target.serverPosZ;

        this.motionX = target.motionX;
        this.motionY = target.motionY;
        this.motionZ = target.motionZ;

        this.prevRotationPitch = target.prevRotationPitch;
        this.prevRotationYaw   = target.prevRotationYaw;
        this.rotationPitch = target.rotationPitch;
        this.rotationYaw   = target.rotationYaw;
    }
}
