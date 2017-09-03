package com.xk.eyepetizer.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.xk.eyepetizer.R
import com.xk.eyepetizer.mvp.model.bean.Category
import com.xk.eyepetizer.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_category_detail.*

/**
 * Created by xuekai on 2017/9/3.
 */
class CategoryDetailActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val serializableExtra = intent.getSerializableExtra("data") as Category
        setContentView(R.layout.activity_category_detail)

        category_header.setData(serializableExtra)


//        Thread({
//            Thread.sleep(2000)
//            var time = 0
//            while (time < 10) {
//                Log.i("CategoryDetailActivity","onCreate-->${time}")
//                Thread.sleep(100)
//                time += 1
//                if ((time <= 10)) {
//                    runOnUiThread { category_header.setCollaspsedProgress(time) }
//
//                }
//            }
//            Thread.sleep(1000)
//
//            while (time > 0) {
//                Log.i("CategoryDetailActivity","onCreate-->${time}")
//                Thread.sleep(100)
//                time -= 1
//                if ((time >= 0)) {
//                    runOnUiThread { category_header.setCollaspsedProgress(time) }
//
//                }
//            }
//        })
//                .start()

        rv_category_detail.layoutManager = LinearLayoutManager(this)

        rv_category_detail.adapter = MAdapter()
    }

    class MAdapter : RecyclerView.Adapter<MAdapter.ViewHolder>() {
        override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
            (holder?.itemView as TextView).text = "  " + position
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MAdapter.ViewHolder {
            val textView = TextView(parent?.context)
            textView.text = "ssssss"
            return ViewHolder(textView)
        }


        override fun getItemCount(): Int {
            return 100
        }

        class ViewHolder : RecyclerView.ViewHolder {
            constructor(itemView: View?) : super(itemView)
        }
    }
}