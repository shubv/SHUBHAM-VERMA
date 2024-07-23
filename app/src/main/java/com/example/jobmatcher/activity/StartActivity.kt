package com.example.jobmatcher.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.jobmatcher.R
import com.example.jobmatcher.manager.PrefManager


class StartActivity : AppCompatActivity() {


    private var viewPager: ViewPager? = null
    private var myViewPagerAdapter: MyViewPagerAdapter? = null
    private var dotsLayout: LinearLayout? = null
    private var dots: Array<TextView?>? = null
    private var layouts: IntArray? = null
    private var btnSkip: TextView? = null
    private var mainBg: RelativeLayout? = null
    private var btnNext: Button? = null
    private var prefManager: PrefManager? = null


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= 21) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        setContentView(R.layout.activity_start)

         prefManager = PrefManager(this)
        if (!prefManager!!.isFirstTimeLaunch()) {
            launchHomeScreen()
            finish()
        }

        viewPager = findViewById(R.id.view_pager) as ViewPager
        dotsLayout = findViewById(R.id.layoutDots) as LinearLayout
        mainBg = findViewById(R.id.main_bg) as RelativeLayout
        btnSkip = findViewById(R.id.btn_skip) as TextView
        btnNext = findViewById(R.id.btn_next) as Button
         layouts = intArrayOf(
             R.layout.welcome1,
             R.layout.welcome2,
             R.layout.welcome3


        )

// adding bottom dots
        addBottomDots(0+1)

// making notification bar transparent
        changeStatusBarColor()

        myViewPagerAdapter = MyViewPagerAdapter()
        viewPager!!.adapter = myViewPagerAdapter
        viewPager!!.addOnPageChangeListener(viewPagerPageChangeListener)

        btnSkip!!.setOnClickListener {
            launchHomeScreen()
        }

        btnNext!!.setOnClickListener {
            // checking for last page if true launch MainActivity
            val current = getItem(+1)
            if (current < layouts!!.size) {
                // move to next screen
                viewPager!!.currentItem = current
            } else {
                launchHomeScreen()
            }
        }



    }
    private fun addBottomDots(currentPage: Int) {
        Log.e("=>>" ,"addBottomDots: "+layouts?.size )
        dots = layouts?.let { arrayOfNulls<TextView>(it.size) }


        val colorsActive = resources.getIntArray(R.array.array_dot_active)
        val colorsInactive = resources.getIntArray(R.array.array_dot_inactive)

        dotsLayout!!.removeAllViews()
        /*for (i in dots!!.indices) {
            dots!![i] = TextView(this)
            dots!![i]?.text = Html.fromHtml(".")
            dots!![i]?.textSize = 35f
            dots!![i]?.setTextColor(colorsInactive[currentPage])
            dotsLayout!!.addView(dots!![i])
        }

        if (dots!!.isNotEmpty())
            dots!![currentPage]?.setTextColor(colorsActive[currentPage])*/

        for (i in dots!!.indices) {
            Log.e("1==>>", "addBottomDots: "+dots!!.size )
            Log.e("1currentPage==>>", "addBottomDots: "+currentPage )
            dots!![i] = TextView(this)
          dots!![i]!!.text = Html.fromHtml("&#8226;")
           dots!![i]!!.textSize = 35f
           dots!![i]!!.setTextColor(Color.WHITE)
            dotsLayout!!.addView(dots!![i])
        }

        if (dots!!.size > 0)
            Log.e("2==>>", "addBottomDots: "+dots!!.size )
        Log.e("2currentPage==>>", "addBottomDots: "+currentPage )
           // dots!![currentPage]!!.setTextColor(colorsActive[currentPage])
    }

    private fun getItem(i: Int): Int {
        return viewPager!!.currentItem + i
    }

    private fun launchHomeScreen() {
       // prefManager!!.setFirstTimeLaunch(false)
        startActivity(Intent(this@StartActivity, WelcomeActivity::class.java))
        finish()
    }

    // viewpager change listener
    private val viewPagerPageChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageSelected(position: Int) {
            addBottomDots(position+1)
            Log.e("===>>", "onPageSelected: "+position )

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts!!.size - 1) {
                // last page. make button text to GOT IT
                mainBg!!.setBackground(getDrawable(R.drawable.welcome_bg3))
                btnNext!!.text = getString(R.string.start)
                btnSkip!!.visibility = View.GONE
            } else if (position==0)
            {
                mainBg!!.setBackground(getDrawable(R.drawable.welcome_bg))
                btnNext!!.text = getString(R.string.started)
            }  else{
                mainBg!!.setBackground(getDrawable(R.drawable.welcome_bg2))
                // still pages are left
                btnNext!!.text = getString(R.string.next)
                btnSkip!!.visibility = View.VISIBLE
            }
        }

        override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {}

        override fun onPageScrollStateChanged(arg0: Int) {}
    }

    // Making notification bar transparent
    private fun changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    /**
     * View pager adapter
     */
    inner class MyViewPagerAdapter : PagerAdapter() {
        private var layoutInflater: LayoutInflater? = null

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            layoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            val view = layoutInflater!!.inflate(layouts!![position], container, false)
            container.addView(view)

            return view
        }

        override fun getCount(): Int {
            return layouts!!.size
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            val view = `object` as View
            container.removeView(view)
        }
    }


}