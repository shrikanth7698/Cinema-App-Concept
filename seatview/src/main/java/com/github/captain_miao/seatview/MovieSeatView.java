package com.github.captain_miao.seatview;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Build;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

/**
 * @author YanLu
 * @since 16/10/01
 */
public class MovieSeatView extends View {
    private static final String TAG = "MovieSeatView";
    public final boolean isDebug = true;

    private BaseSeatMo[][] mSeatTable;
    private int mRowSize;
    private int mColumnSize;

    // scale
    public float mScaleX4Padding = 1.f;
    public float mScaleY4Padding = 1.f;
    public float mScaleFactor   = 1.f;
    public float mScaleFactorMinBest = 1.4f;
    public float mScaleFactorMaxBest = 2f;
    public float mScaleFactorMin = 0.5f;
    public float mScaleFactorMax = 3.0f;
    // move
    public int mTranslateX  = 0;
    public int mTranslateY  = 0;

    private GestureDetectorCompat mGestureDetector;
    private ScaleGestureDetector mScaleGestureDetector;

    private int mIconOnSaleResId;
    private int mIconSoldResId;
    private int mIconSelectedResId;

    private Bitmap mIconOnSale;
    private Bitmap mIconSold;
    private Bitmap mIconSelected;




    // view width and height
    int mSeatPadding;
    int mViewWidth;
    int mViewHeight;
    private float mSeatWidth;
    private float mSeatHeight;
    private boolean mShowOverview;



    private Matrix mMatrix = new Matrix();

    private SeatPresenter mPresenter;


    public MovieSeatView(Context context) {
        super(context);
    }

    public MovieSeatView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MovieSeatView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSeatView(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MovieSeatView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initSeatView(context, attrs);
    }

    private void initSeatView(Context context, AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MovieSeatView);
        if(typedArray.hasValue(R.styleable.MovieSeatView_iconOnSale)
                && typedArray.hasValue(R.styleable.MovieSeatView_iconSold)
                && typedArray.hasValue(R.styleable.MovieSeatView_iconSelected)){
            mIconOnSaleResId = typedArray.getResourceId(R.styleable.MovieSeatView_iconOnSale, 0);
            mIconSoldResId = typedArray.getResourceId(R.styleable.MovieSeatView_iconSold, 0);
            mIconSelectedResId = typedArray.getResourceId(R.styleable.MovieSeatView_iconSelected, 0);

            mSeatPadding = (int) (typedArray.getDimension(R.styleable.MovieSeatView_seatPadding, 0f) + 0.5f) * 2;
            mSeatWidth = typedArray.getDimension(R.styleable.MovieSeatView_seatWidth,
                    getResources().getDimension(R.dimen.default_seat_width)) + mSeatPadding;
            mSeatHeight = typedArray.getDimension(R.styleable.MovieSeatView_seatHeight,
                    getResources().getDimension(R.dimen.default_seat_height)) + mSeatPadding;
            mShowOverview = typedArray.getBoolean(R.styleable.MovieSeatView_showOverView, true);
            mScaleFactorMin = typedArray.getFloat(R.styleable.MovieSeatView_seatScaleFactorMin, mScaleFactorMin);
            mScaleFactorMinBest = typedArray.getFloat(R.styleable.MovieSeatView_seatScaleFactorMinBest, mScaleFactorMinBest);
            mScaleFactorMax = typedArray.getFloat(R.styleable.MovieSeatView_seatScaleFactorMax, mScaleFactorMax);
            mScaleFactorMaxBest = typedArray.getFloat(R.styleable.MovieSeatView_seatScaleFactorMaxBest, mScaleFactorMaxBest);

            typedArray.recycle();
        } else {
            typedArray.recycle();
            throw new RuntimeException("must has iconSeatOnSale, iconSeatSold and iconSeatSelected");
        }


        mScaleGestureDetector = new ScaleGestureDetector(getContext(), mScaleGestureListener);
        mGestureDetector = new GestureDetectorCompat(getContext(), mGestureListener);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        boolean retVal = mScaleGestureDetector.onTouchEvent(event);
        retVal = mGestureDetector.onTouchEvent(event) || retVal;
        return retVal || super.onTouchEvent(event);

    }




    @Override
    protected void onDraw(Canvas canvas) {
        if (mSeatTable != null && mSeatTable.length > 0) {
            drawSeat(canvas);
        }
    }


    Paint paint = new Paint();
    private void drawSeat(Canvas canvas) {
        long startTime = System.currentTimeMillis();
        if(mIconOnSale == null){
            mIconOnSale = BitmapFactory.decodeResource(getResources(), mIconOnSaleResId);
            mScaleFactor = mSeatWidth / mIconOnSale.getWidth();
        }
        if(mIconSold == null){
            mIconSold = BitmapFactory.decodeResource(getResources(), mIconSoldResId);
        }
        if(mIconSelected == null){
            mIconSelected = BitmapFactory.decodeResource(getResources(), mIconSelectedResId);
        }
        int seatWidth = (int) (mSeatWidth * mScaleFactor);
        int seatHeight = (int) (mSeatHeight * mScaleFactor);
        mScaleX4Padding = 1 - mSeatPadding / (mSeatWidth * mScaleFactor);
        mScaleY4Padding = 1 - mSeatPadding / (mSeatHeight * mScaleFactor);
        // draw begin
        mViewWidth = getMeasuredWidth();
        mViewHeight = getMeasuredHeight();

        int m = mTranslateY + seatHeight;
        m = m >= 0 ? 0 : -m / seatHeight;
        int n = Math.min(mRowSize - 1, m + (mViewHeight / seatHeight) + 2);//两边多显示1列,避免临界的突然消失的现象

        int k = (int)(mTranslateX + seatWidth + 0.5f);
        k = k > 0 ? 0 : -k / seatWidth;
        int l = Math.min(mColumnSize - 1, k + (mViewWidth / seatWidth) + 2);// draw +2 column
        for (int i = m; i <= n; i++) {
            for (int j = k; j <= l; j++) {
                BaseSeatMo seat = mSeatTable[i][j];
                int left = j * (seatWidth ) + mTranslateX;
                int top = i * (seatHeight) + mTranslateY;
                mMatrix.setTranslate(left, top);
                mMatrix.postScale(mScaleFactor, mScaleFactor, left, top);
                mMatrix.postScale(mScaleX4Padding, mScaleY4Padding, left, top);
                if(seat != null) {
                    if (seat.isOnSale()) {
                        canvas.drawBitmap(mIconOnSale, mMatrix, paint);
                    } else if (seat.isSold()) {
                        canvas.drawBitmap(mIconSold, mMatrix, paint);
                    } else if (seat.isSelected()) {
                        canvas.drawBitmap(mIconSelected, mMatrix, paint);
                    }
                }
            }
        }

        if (isDebug) {
            long drawTime = System.currentTimeMillis() - startTime;
            Log.d(TAG, "draw seat time(ms):" + drawTime);
        }
    }


    public void setSeatTable(BaseSeatMo[][] seatTable) {
        this.mSeatTable = seatTable;
        mRowSize = mSeatTable.length;
        mColumnSize = mSeatTable[0].length;
        invalidate();
    }

    public SeatPresenter getPresenter() {
        return mPresenter;
    }

    public void setPresenter(SeatPresenter presenter) {
        mPresenter = presenter;
    }



    /**
     * The scale listener, used for handling multi-finger scale gestures.
     */
    private final ScaleGestureDetector.OnScaleGestureListener mScaleGestureListener
            = new ScaleGestureDetector.SimpleOnScaleGestureListener() {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor(); // scale change since previous event
            mScaleFactor = Math.max(mScaleFactorMin, Math.min(mScaleFactor, mScaleFactorMax));
            ViewCompat.postInvalidateOnAnimation(MovieSeatView.this);
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            if(mScaleFactor < mScaleFactorMinBest){
                mZoomAnimation.start(mScaleFactor, mScaleFactorMinBest);
            } else if(mScaleFactor > mScaleFactorMaxBest){
                mZoomAnimation.start(mScaleFactor, mScaleFactorMaxBest);
            } else {
                ViewCompat.postInvalidateOnAnimation(MovieSeatView.this);
            }
        }
    };


    /**
     * The gesture listener, used for handling simple gestures such as double touches, scrolls,
     * and flings.
     */
    private final GestureDetector.SimpleOnGestureListener mGestureListener
                = new GestureDetector.SimpleOnGestureListener() {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            mTranslateX -= distanceX;
            mTranslateY -= distanceY;
            ViewCompat.postInvalidateOnAnimation(MovieSeatView.this);

            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return onClickSeat(e);
        }

    };



    private boolean onClickSeat(MotionEvent e) {
        float x = e.getX() - mTranslateX;
        float y = e.getY() - mTranslateY;
        float w = (mSeatWidth) * mScaleFactor;
        float h = (mSeatHeight) * mScaleFactor;
        int positionRow = (int)(y / h);
        int positionColumn = (int)( x / w);
        int row = positionRow < mRowSize ? positionRow: -1;
        int column = positionColumn < mColumnSize ? positionColumn: -1;
        if(row >= 0 && column >= 0) {
            BaseSeatMo seat = mSeatTable[row][column];
            if (seat != null) {
                if (mPresenter != null && mPresenter.onClickSeat(row, column, seat)) {
                    if(mScaleFactor < mScaleFactorMinBest){
                        mZoomAnimation.start(mScaleFactor, mScaleFactorMinBest);
                    } else if(mScaleFactor > mScaleFactorMaxBest){
                        mZoomAnimation.start(mScaleFactor, mScaleFactorMaxBest);
                    } else {
                        ViewCompat.postInvalidateOnAnimation(MovieSeatView.this);
                    }
                }
            }
        }

        return true;
    }


    ValuesAnimation<Float> mZoomAnimation = new ValuesAnimation<Float>(getContext()) {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            mScaleFactor = (float) animation.getAnimatedValue();
            ViewCompat.postInvalidateOnAnimation(MovieSeatView.this);
        }

        @Override
        public Float evaluate(float fraction, Float startValue, Float endValue) {
            return startValue + (fraction) * (endValue - startValue);
        }
    };

    ValuesAnimation mMoveAnimation = new ValuesAnimation(getContext()) {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {

        }

        @Override
        public Object evaluate(float fraction, Object startValue, Object endValue) {
            return null;
        }
    };
}
