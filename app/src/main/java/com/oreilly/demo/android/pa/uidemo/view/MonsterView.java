//package com.oreilly.demo.android.pa.uidemo.view;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.Paint.Style;
//import android.util.AttributeSet;
//import android.view.View;
//
//import com.oreilly.demo.android.pa.uidemo.R;
//import com.oreilly.demo.android.pa.uidemo.model.Monster;
//import com.oreilly.demo.android.pa.uidemo.model.Monsters;
//
//
///**
// * I see spots!
// *
// * @author <a href="mailto:android@callmeike.net">Blake Meike</a>
// */
//public class MonsterView extends View {
//
//    private volatile Monsters monsters;
//
//    /**
//     * @param context the rest of the application
//     */
//    public MonsterView(Context context) {
//        super(context);
//        setFocusableInTouchMode(true);
//    }
//
//    /**
//     * @param context
//     * @param attrs
//     */
//    public MonsterView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        setFocusableInTouchMode(true);
//    }
//
//    /**
//     * @param context
//     * @param attrs
//     * @param defStyle
//     */
//    public MonsterView(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//        setFocusableInTouchMode(true);
//    }
//
//    /**
//     * @param monsters
//     */
//    public void setMonsters(Monsters monsters) { this.monsters = monsters; }
//
//    /**
//     * @see android.view.View#onDraw(android.graphics.Canvas)
//     */
//    @Override protected void onDraw(Canvas canvas) {
//        Paint paint = new Paint();
//        paint.setStyle(Style.STROKE);
//        paint.setColor(monsters.getLastMonster().getStatus() ? Color.YELLOW : Color.GREEN);
//        if (null == monsters) { return; }
//        else{
//            Bitmap bitmap_green = BitmapFactory.decodeResource(getResources(), R.drawable.green_monster);
//            for (Monster monster : monsters.getMonsters()) {
//                canvas.drawBitmap(bitmap_green, monster.getX(), monster.getY(), paint);
//            }
//        }
////        if (null == dots) { return; }
//    }
//}
