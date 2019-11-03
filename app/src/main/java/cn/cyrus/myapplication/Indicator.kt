package cn.cyrus.myapplication

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator

/**
 *  description:
 *  author:Chen Lei
 *  createtime:11/02/19
 */

class Indicator: View {

    constructor(context: Context):super(context)

    constructor(context: Context,attributeSet: AttributeSet):super(context,attributeSet)

    constructor(context: Context,attributeSet: AttributeSet,defStyleAttr: Int):super(context,attributeSet,defStyleAttr)


    var mCurrentPos = 0
    var mCount = 5
    var mWidth =0f

    var mCurrntLeft = 0f

    val mPaint:Paint by lazy{
        val p = Paint()
        p.color = Color.YELLOW
        p.style = Paint.Style.FILL
        return@lazy p
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        mWidth = width / mCount.toFloat()
    }




    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas!!.drawRect(mCurrntLeft,0f,mCurrntLeft+ mWidth, height.toFloat(),mPaint)
    }


    fun moveTo(pos:Int){
        val valueAnimator = ValueAnimator.ofFloat(mCurrntLeft, pos*mWidth)
        valueAnimator.setDuration(1000)
        valueAnimator.setInterpolator(LinearInterpolator())
        valueAnimator.addUpdateListener {
                mCurrntLeft = it.getAnimatedValue() as Float
                invalidate()
        }
        valueAnimator.start()
        mCurrentPos = pos
    }
}