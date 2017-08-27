package com.xk.eyepetizer.ui.activity

import android.graphics.Bitmap
import android.graphics.Matrix
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.xk.eyepetizer.R
import com.xk.eyepetizer.mvp.contract.DetailContract
import com.xk.eyepetizer.mvp.model.bean.Issue
import com.xk.eyepetizer.mvp.model.bean.Item
import com.xk.eyepetizer.mvp.presenter.DetailPresenter
import com.xk.eyepetizer.showToast
import com.xk.eyepetizer.ui.adapter.DetailAdapter
import com.xk.eyepetizer.ui.view.detail.*
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.layout_detail_more_listview.view.*


class DetailActivity : AppCompatActivity(), DetailContract.IView {


    lateinit var presenter: DetailPresenter
    val adapter by lazy { DetailAdapter() }
    val itemData: Item by lazy {
        intent.getSerializableExtra("data") as Item
    }

    private fun initView() {
        rv_detail.layoutManager = LinearLayoutManager(this)
        rv_detail.adapter = adapter
        initListener()
    }

    private fun initListener() {
        adapter.setOnItemClick({ presenter.requestBasicDataFromMemory(it) },
                { url, title -> presenter.requestDetailMoreList(url!!, title!!) },
                {
                    when (it) {
                        BTN_AUTHOR -> showToast("跳到作者")
                        BTN_DOWLOAD -> showToast("下载（未实现）")
                        BTN_REPLY -> showToast("回复（未实现）")
                        BTN_FAVORITES -> showToast("喜欢（未实现）")
                        BTN_WATCH -> showToast("加关注（未实现）")
                        BTN_SHARE -> showToast("分享（未实现）")
                    }
                })
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setPresenter(DetailPresenter(this))
        setContentView(R.layout.activity_detail)
        initView()
        loadData()
    }


    private fun loadData() {
        presenter.requestBasicDataFromMemory(itemData)
    }

    override fun setPresenter(presenter: DetailContract.IPresenter) {
        this.presenter = presenter as DetailPresenter
    }


    override fun setPlayer(playUrl: String) {
        videoView.setUp(playUrl, false, "")
        videoView.startPlayLogic()
//        GSYVideoType.setShowType(GSYVideoType.SCREEN_MATCH_FULL)
    }

    override fun setMovieAuthorInfo(info: Item) {
        adapter.addData(info)
    }


    override fun setRelated(items: ArrayList<Item>) {
        adapter.addData(items)
    }

    override fun setBackground(url: String) {
        Glide.with(this).load(url).asBitmap()
                .transform(object : BitmapTransformation(this) {
                    override fun getId(): String {
                        return ""
                    }

                    override fun transform(pool: BitmapPool?, toTransform: Bitmap?, outWidth: Int, outHeight: Int): Bitmap {
                        //如果想让图片旋转 非常简单，主要借助于Matrix对象:矩阵对象，将图片转化成像素矩阵。
                        val matrix = Matrix()
                        //执行旋转，参数为旋转角度
                        matrix.postRotate(90f)
                        //将图形的像素点按照这个矩阵进行旋转
                        //将矩阵加载到bitmap对象上，进行输出就可以了  如何创建Bitmap对象
                        //待旋转的bitmap对象，待旋转图片的宽度，待旋转图片的高度
                        return Bitmap.createBitmap(toTransform, 0, 0, toTransform!!.getWidth(), toTransform.getHeight(), matrix, true)
                    }
                })
                .format(DecodeFormat.PREFER_ARGB_8888).centerCrop().into(background)
    }

    override fun showMoreList(title: String) {
        rv_detail.visibility = View.GONE
        detailMoreListView.visibility = View.VISIBLE
        detailMoreListView.title.text = title
    }

    override fun setMoreList(issue: Issue) {
        showToast("设置数据")
    }
}
