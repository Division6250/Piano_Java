package com.example.piano;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class PianoView extends View {
    public static final int NUMBER_OF_KEYS = 14;
    private Paint black, white, yellow, blackLine;
    private ArrayList<Key> whites, blacks;
    private int keyWidth, keyHeight;

    private SoundManager soundManager;

    public PianoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        blackLine = new Paint();
        blackLine.setColor(Color.BLACK);
        blackLine.setStrokeWidth(3);

        black = new Paint();
        black.setColor(Color.BLACK);
        black.setStyle(Paint.Style.FILL);

        white = new Paint();
        white.setColor(Color.WHITE);
        white.setStyle(Paint.Style.FILL);

        yellow = new Paint();
        yellow.setColor(Color.YELLOW);
        yellow.setStyle(Paint.Style.FILL);

        whites = new ArrayList<>();
        blacks = new ArrayList<>();

        soundManager = SoundManager.getInstance();
        soundManager.init(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        keyWidth = w / NUMBER_OF_KEYS;
        keyHeight = h;

        for (int i = 0; i < NUMBER_OF_KEYS; i++) {
            int left = i * keyWidth;
            int right = left + keyWidth;

            RectF rect = new RectF(left, 0, right, h);
            whites.add(new Key(i + 1, rect));

            if (i != 0 && i != 3 && i != 7 && i != 10) {
                RectF rect2 = new RectF((float)(i-1) * keyWidth + 0.75f * keyWidth, 0, (float) i * keyWidth + 0.25f * keyWidth, 0.67f * keyHeight);
                blacks.add(new Key(i + 1, rect2));
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (Key k: whites) {
            canvas.drawRect(k.rect, k.down ? yellow : white);
        }

        for (int i = 1; i < NUMBER_OF_KEYS; i++) {
            canvas.drawLine(i * keyWidth, 0, i * keyWidth, keyHeight, black);
        }

        for (Key k: blacks) {
            canvas.drawRect(k.rect, k.down ? yellow : black);
        }
    }

    boolean soundReady = true;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        boolean isDownAction = action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE;

        for (int touchIndex = 0; touchIndex < event.getPointerCount(); touchIndex++) {
            float x = event.getX(touchIndex);
            float y = event.getY(touchIndex);

            boolean blackClicked = false;
            for (Key k: blacks) {
                if (k.rect.contains(x, y)) {
                    k.down = isDownAction;
                    blackClicked = true;
                }
                else {
                    k.down = false;
                }
            }

            for (Key k: whites) {
                if (k.rect.contains(x, y) && !blackClicked) {
                    k.down = isDownAction;
                }
                else {
                    k.down = false;
                }
            }

            if (soundReady) {
                for (Key k: whites) {
                    if (k.down) {
                        switch (k.sound) {
                            case 1:
                                soundManager.playSound(R.raw.c2);
                                break;
                            case 2:
                                soundManager.playSound(R.raw.d2);
                                break;
                            case 3:
                                soundManager.playSound(R.raw.e2);
                                break;
                            case 4:
                                soundManager.playSound(R.raw.f2);
                                break;
                            case 5:
                                soundManager.playSound(R.raw.g2);
                                break;
                            case 6:
                                soundManager.playSound(R.raw.a2);
                                break;
                            case 7:
                                soundManager.playSound(R.raw.b2);
                                break;
                            case 8:
                                soundManager.playSound(R.raw.c3);
                                break;
                            case 9:
                                soundManager.playSound(R.raw.d3);
                                break;
                            case 10:
                                soundManager.playSound(R.raw.e3);
                                break;
                            case 11:
                                soundManager.playSound(R.raw.f3);
                                break;
                            case 12:
                                soundManager.playSound(R.raw.g3);
                                break;
                            case 13:
                                soundManager.playSound(R.raw.a3);
                                break;
                            case 14:
                                soundManager.playSound(R.raw.b3);
                                break;
                            default:
                                break;
                        }
                    }
                }

                for (Key k: blacks) {
                    if (k.down) {
                        switch (k.sound) {
                            case 2:
                                soundManager.playSound(R.raw.db2);
                                break;
                            case 3:
                                soundManager.playSound(R.raw.eb2);
                                break;
                            case 5:
                                soundManager.playSound(R.raw.gb2);
                                break;
                            case 6:
                                soundManager.playSound(R.raw.ab2);
                                break;
                            case 7:
                                soundManager.playSound(R.raw.bb2);
                                break;
                            case 9:
                                soundManager.playSound(R.raw.db3);
                                break;
                            case 10:
                                soundManager.playSound(R.raw.eb3);
                                break;
                            case 12:
                                soundManager.playSound(R.raw.gb3);
                                break;
                            case 13:
                                soundManager.playSound(R.raw.ab3);
                                break;
                            case 14:
                                soundManager.playSound(R.raw.bb3);
                                break;
                        }
                    }
                }
                soundReady = false;
            }

        }

        invalidate();

        if (action == MotionEvent.ACTION_UP)
            soundReady = true;

        // invalidateOutline();
        return true;
    }
}
