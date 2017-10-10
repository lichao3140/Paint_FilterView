package com.lqh.lichao.paint_filterview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.EmbossMaskFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

/**
 * 滤镜效果
 * Created by Administrator on 2017-10-10.
 */

public class FilterView extends View {

    Paint paint;
    Bitmap bitmap;
    private int progress;

    public FilterView(Context context) {
        super(context);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.xyjy2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        RectF rectF = new RectF(0, 100, bitmap.getWidth(), bitmap.getHeight());
        paint.reset();
        paint.setColor(Color.RED);
        //canvas.drawRect(rectF,paint);
        //canvas.translate(600,0); //平移
        /**
         * Create a blur maskfilter.  模糊效果
         *
         * @param radius 阴影的半径
         * @param style  NORMOL -- 整个图像都被模糊掉
         *               SOLID -- 图像边界外产生一层与Paint颜色一致阴影效果，不影响图像的本身
         *               OUTER -- 图像边界外产生一层阴影，并且将图像变成透明效果
         *               INNER -- 在图像内部边沿产生模糊效果
         * @return
         */
        //paint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.NORMAL));

        /**
         * Create an emboss maskfilter  浮雕立体效果
         *
         * @param direction  指定光源的位置，长度为xxx的数组标量[x,y,z]
         * @param ambient    环境光的因子 （0~1），越接近0，环境光越暗
         * @param specular   镜面反射系数 越接近0，镜面反射越强
         * @param blurRadius 模糊半径 值越大，模糊效果越明显
         */
        //paint.setMaskFilter(new EmbossMaskFilter(new float[]{1,1,1},0.2f,60,80));
        canvas.drawBitmap(bitmap, null, rectF, paint);

        // 平移运算---Green + 100 加法 R G B A
        /*ColorMatrix colorMatrix = new ColorMatrix(new float[]{
                1,0,0,0,0,
                0,1,0,0,100,
                0,0,1,0,0,
                0,0,0,1,0,
        });*/

        // 反相效果 -- 底片效果
        /*ColorMatrix colorMatrix = new ColorMatrix(new float[]{
                -1,0,0,0,255,
                0,-1,0,0,255,
                0,0,-1,0,255,
                0,0,0,1,0,
        });*/

        // 缩放运算---乘法 -- 颜色增强
        /*ColorMatrix colorMatrix = new ColorMatrix(new float[]{
                1.2f, 0,0,0,0,
                0,1.2f,0,0,0,
                0,0,1.2f,0,0,
                0,0,0,1.2f,0,
        });*/

        // 黑白照片
        // 去色原理：只要把R G B 三通道的色彩信息设置成一样，那么图像就会变成灰色，
        // 同时为了保证图像亮度不变，同一个通道里的R+G+B =1
        //
        /*ColorMatrix colorMatrix = new ColorMatrix(new float[]{
                0.213f, 0.715f,0.072f,0,0,
                0.213f, 0.715f,0.072f,0,0,
                0.213f, 0.715f,0.072f,0,0,
                0,0,0,1,0,
        });*/

        // 发色效果---（比如红色和绿色交换）
        /*ColorMatrix colorMatrix = new ColorMatrix(new float[]{
                0,1,0,0,0,
                1,0,0,0,0,
                0,0,1,0,0,
                0,0,0,1,0,
        });*/

        // 复古效果
        /*ColorMatrix colorMatrix = new ColorMatrix(new float[]{
                1/2f,1/2f,1/2f,0,0,
                1/3f, 1/3f,1/3f,0,0,
                1/4f,1/4f,1/4f,0,0,
                0,0,0,1,0,
        });*/

        // 颜色通道过滤
        /*ColorMatrix colorMatrix = new ColorMatrix(new float[]{
                1, 0,0,0,0,
                0,0,0,0,0,
                0,0,0,0,0,
                0,0,0,1,0,
        });*/

        ColorMatrix colorMatrix = new ColorMatrix();
        //colorMatrix.setScale(1.2f,1.2f,1.2f,1);//高亮
        //colorMatrix.setSaturation(progress);//饱和度
        //aixs-- 0 红色轴，1，绿色，2，蓝色
        // degrees -- 旋转的角度
        colorMatrix.setRotate(0, progress);

        RectF rectF2 = new RectF(400, 100, 400 + bitmap.getWidth(), bitmap.getHeight());
        //paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        // LightingColorFilter只是修改RGB值
        //paint.setColorFilter(new LightingColorFilter(0xffffff,progress));
        //paint.setColorFilter(new PorterDuffColorFilter(Color.BLUE, PorterDuff.Mode.DST_IN));
        paint.setColorFilter(new PorterDuffColorFilter(Color.argb(255,140,90,200), PorterDuff.Mode.MULTIPLY));
        canvas.drawBitmap(bitmap, null, rectF2, paint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //progress += 1f;
                //progress += 20f;
                progress = 0xff0000;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                progress = 0x000000;
                invalidate();
                break;
        }
        return true;
    }
}
