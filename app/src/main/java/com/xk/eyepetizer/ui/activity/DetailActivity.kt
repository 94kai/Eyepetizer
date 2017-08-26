package com.xk.eyepetizer.ui.activity

import android.graphics.Bitmap
import android.graphics.Matrix
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.xk.eyepetizer.R
import com.xk.eyepetizer.mvp.contract.DetailContract
import com.xk.eyepetizer.mvp.model.bean.Item
import com.xk.eyepetizer.mvp.presenter.DetailPresenter
import com.xk.eyepetizer.ui.adapter.DetailAdapter
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : AppCompatActivity(), DetailContract.IView {
    lateinit var presenter: DetailPresenter
    val adapter by lazy { DetailAdapter() }


    private fun initView() {
        rv_detail.layoutManager = LinearLayoutManager(this)
        rv_detail.adapter = adapter
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val itemData = intent.getSerializableExtra("data")
        setPresenter(DetailPresenter(this, itemData as Item))
        setContentView(R.layout.activity_detail)
        initView()
        loadData()
    }


    private fun loadData() {
        presenter.requestBasicDataFromMemory()
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


    override fun setRelated(items:ArrayList<Item>) {
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
}
