package cn.cyrus.myapplication

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager


class MainActivity : AppCompatActivity() {

    val handler = Handler()
    lateinit var task: Runnable
    val time = 3*1000L

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val vp = findViewById<ViewPager>(R.id.vp)

        val list = ArrayList<View>()

        for (i in 0..5) {
            list.add(layoutInflater.inflate(R.layout.item, null))
        }

        Log.d("test", list.size.toString())
        task = object :Runnable{
            override fun run() {
                vp.setCurrentItem(vp.currentItem+1,true)
            }

        }
        handler.postDelayed(task,time)
        vp.adapter = object : PagerAdapter() {
            override fun isViewFromObject(view: View, `object`: Any): Boolean {
                return view == `object`
            }

            override fun getCount(): Int {
                return Int.MAX_VALUE
            }

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                val pos = position % list.size
                val view = list[pos]
                container.addView(view)

                return view
            }

            override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

                container.removeView(`object` as View)
            }

        }
        vp.setPageTransformer(false) { page, position ->

            val SCALE_MAX = 0.8f;
            val ALPHA_MAX = 0.5f;

            val scale =
                if (position < 0) ((1 - SCALE_MAX) * position + 1) else ((SCALE_MAX - 1) * position + 1)
            val alpha =
                if (position < 0) ((1 - ALPHA_MAX) * position + 1) else ((ALPHA_MAX - 1) * position + 1)
            //为了滑动过程中，page间距不变，这里做了处理
            if (position < 0) {
                ViewCompat.setPivotX(page, page.width.toFloat())
                ViewCompat.setPivotY(page, (page.height / 2f))
            } else {
                ViewCompat.setPivotX(page, 0f)
                ViewCompat.setPivotY(page, page.getHeight() / 2f)
            }
            ViewCompat.setScaleX(page, scale)
            ViewCompat.setScaleY(page, scale)
            ViewCompat.setAlpha(page, Math.abs(alpha))

        }
        val indicator = findViewById<Indicator>(R.id.indicator)
        vp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {


            }

            override fun onPageSelected(position: Int) {
                handler.removeCallbacks(task)
                val pos = position % indicator.mCount
                Log.d("test", "pos:$pos,position:$position")
                indicator.moveTo(pos)
                handler.postDelayed(task,time)
            }

        })
        vp.offscreenPageLimit = 2
        vp.currentItem = Int.MAX_VALUE / 2 - Int.MAX_VALUE / 2 % list.size

        Log.d("test", "currentItem:${vp.currentItem}")


    }
}
