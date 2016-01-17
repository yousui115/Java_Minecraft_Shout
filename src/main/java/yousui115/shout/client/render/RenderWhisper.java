package yousui115.shout.client.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import yousui115.shout.Shout;
import yousui115.shout.entity.EntityWhisper;

public class RenderWhisper extends Render<EntityWhisper>
{
    //■りそーすろけーしょん
    protected static final ResourceLocation resource = new ResourceLocation(Shout.MOD_ID, "textures/entity/magic.png");
    //■てせれーたー
    protected static Tessellator tessellator = Tessellator.getInstance();
    //■わーるどれんだらー
    protected static WorldRenderer worldrenderer = tessellator.getWorldRenderer();

    private static double size = 1f;
    private static double[][] dVec = {  {-size, size, size},        //0
                                        {-size,-size, size},        //1
                                        { size,-size, size},        //2
                                        { size, size, size},        //3
                                        {-size, size,-size},        //4
                                        {-size,-size,-size},        //5
                                        { size,-size,-size},        //6
                                        { size, size,-size}};       //7

private static int[][] nVecPos = {  {0, 1, 2, 3},
                                    {3, 2, 6, 7},
                                    {0, 3, 7, 4},
                                    {1, 0, 4, 5},
                                    {2, 1, 5, 6},
                                    {4, 7, 6, 5}};
    /**
     * ■コンストラクタ
     */
    public RenderWhisper(RenderManager renderManager) { super(renderManager); }

    /**
     * ■描画処理
     */
    @Override
    public void doRender(EntityWhisper entity, double dx, double dy, double dz, float entityYaw, float partialTicks)
    {
        //■描画前処理
        this.preDraw();

        //■座標系の調整
        // ▼行列のコピー
        GlStateManager.pushMatrix();

        //■回転、位置の調整(FILOなので注意)
        // ▼2.位置
        GlStateManager.translate(dx, dy + (entity.target.height / 2f), dz);
        // ▼1.拡大率
        //GlStateManager.scale(2f, 2f, 2f);


        //■本処理
        //TODO
      //■描画モード
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);

        //■頂点情報
        for(int idx = 0; idx < nVecPos.length; idx++)
        {
            worldrenderer.pos(dVec[nVecPos[idx][0]][0], dVec[nVecPos[idx][0]][1], dVec[nVecPos[idx][0]][2]).tex(0d, 0d).normal(0f, 1f, 0f).endVertex();
            worldrenderer.pos(dVec[nVecPos[idx][1]][0], dVec[nVecPos[idx][1]][1], dVec[nVecPos[idx][1]][2]).tex(0d, 1d).normal(0f, 1f, 0f).endVertex();
            worldrenderer.pos(dVec[nVecPos[idx][2]][0], dVec[nVecPos[idx][2]][1], dVec[nVecPos[idx][2]][2]).tex(1d, 1d).normal(0f, 1f, 0f).endVertex();
            worldrenderer.pos(dVec[nVecPos[idx][3]][0], dVec[nVecPos[idx][3]][1], dVec[nVecPos[idx][3]][2]).tex(1d, 0d).normal(0f, 1f, 0f).endVertex();
        }

        //■描画
        tessellator.draw();


        //■座標系の後処理
        // ▼行列の削除
        GlStateManager.popMatrix();

        //■描画後処理
        this.postDraw();
    }

    /**
     * ■頭の上に名前を表示するか否か
     */
    @Override
    protected boolean canRenderName(EntityWhisper entity) { return false; }

    /**
     * ■リソースロケーション
     */
    @Override
    protected ResourceLocation getEntityTexture(EntityWhisper entity) { return this.resource; }


    /* ======================================== イカ、自作 =====================================*/

    /**
     * ■描画 前処理<br>
     *   1.画像のバインド<br>
     *   2.GlStateManagerの内部設定<br>
     *   3.頂点カラーの設定<br>
     * @param entityIn
     */
    protected void preDraw()
    {
        //■描画準備
        // ▼画像のバインド
        this.bindEntityTexture(null);

        // ▼テクスチャの貼り付け ON
        GlStateManager.enableTexture2D();

        // ▼ライティング OFF
        //GlStateManager.enableLighting();
        GlStateManager.disableLighting();

        // ▼陰影処理の設定(なめらか)
        //GlStateManager.shadeModel(GL11.GL_SMOOTH);

        // ▼ブレンドモード ON
        GlStateManager.enableBlend();
        // ▼加算+アルファ
        //GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        //GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_DST_ALPHA);
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);

        // ▼アルファ
        GlStateManager.disableAlpha();

        // ▼指定のテクスチャユニットとBrightnessX,Y
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 150f, 150f);

        // ▼法線の再スケーリング(?) ON
        GlStateManager.enableRescaleNormal();

        // ▼頂点カラー
        GlStateManager.color(1f, 0.0f, 0.0f, 0.5f);

        // ▼でぷす
        GlStateManager.disableDepth();
    }

    /**
     * ■描画 後処理
     */
    protected void postDraw()
    {
        // ▼でぷす
        GlStateManager.enableDepth();

        //■描画後始末
        //  注意:設定した全てを逆に設定し直すのはNG
        //       disableTexture2D()なんてしたら描画がえらい事に！
        // ▼法線の再スケーリング(?) OFF
        GlStateManager.disableRescaleNormal();

        // ▼指定のテクスチャユニットとBrightnessX,Y(値を上げれば明るく見える！)
        //OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 0f, 0f);

        // ▼アルファ
        GlStateManager.enableAlpha();

        // ▼ブレンドモード OFF
        GlStateManager.disableBlend();

        // ▼陰影処理の設定(フラット:一面同じ色)
        //GlStateManager.shadeModel(GL11.GL_FLAT);

        // ▼ライティング ON
        GlStateManager.enableLighting();
        //GlStateManager.disableLighting();

    }
}
