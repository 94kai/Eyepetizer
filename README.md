## kotlin实现仿开眼app

> **开眼视频**是一款精品短视频日报应用，该项目是用kotlin，借助已知的一些开眼接口写的一个仿《开眼App》，主要是为了学习kotlin和一些UI效果

[GitHub主页](https://github.com/kaikaixue/)

#### APK[点击这里](https://github.com/kaikaixue/Eyepetizer/tree/master/apk)
#### 百度云下载地址[点击这里](https://pan.baidu.com/s/1hsrOhBu)密码:ljt6

- kotlin
- rxjava
- retrofit
- mvp（第一次在项目中用，可能会有些过度使用、或者该用不用的毛病，欢迎指正）
- GSYVideoPlayer
- 自定义behavior
- 自定义下拉刷新

根据已知的接口，主要实现：每日精选、分类、热门几个模块

#### 每日精选

效果如图：


![](https://github.com/kaikaixue/Eyepetizer/blob/master/image/home1.png)  ![](https://github.com/kaikaixue/Eyepetizer/blob/master/image/home_small.gif)

该页主要仿了官方app的几个UI

- 通过PageTransformer实现了ViewPager切换动画，代码[点击查看](https://github.com/kaikaixue/Eyepetizer/blob/master/app/src/main/java/com/xk/eyepetizer/ui/view/home/banner/HomeBannerTransformer.kt)
- 自定义一个文字动画（轮播图上的两行文字，逐字出现），代码[点击查看](https://github.com/kaikaixue/Eyepetizer/blob/master/app/src/main/java/com/xk/eyepetizer/ui/view/common/JumpShowTextView.kt)(之前用ondraw的方法实现，结果发现当文字中有特殊字符的时候，宽度测量会有很大的偏差，所以用了新的方法：添加一个invisiable的textview用来占位，方法有些c粗暴，哈哈哈哈，有更好思路的同学欢迎提出)，旧代码在这里[点击查看](https://github.com/kaikaixue/Eyepetizer/blob/master/app/src/main/java/com/xk/eyepetizer/ui/view/common/JumpShowTextView1.kt)
- RecyclerView下拉刷新，放大第一个item且带阻尼效果，代码[点击查看](https://github.com/kaikaixue/Eyepetizer/blob/master/app/src/main/java/com/xk/eyepetizer/ui/view/home/PullRecyclerView.kt)
- Toolbar随当前item变化
- 底部自动加载
- ViewPage中有视频播放、图片展示两种类型


#### 详情页

写到详情页，kotlin也写了几天了，感觉对于kotlin特性的运用（比如函数参数）比每日精选那块要熟悉一点了，继续努力

效果如图：

![](https://github.com/kaikaixue/Eyepetizer/blob/master/image/detail1.png) ![](https://github.com/kaikaixue/Eyepetizer/blob/master/image/recommend.png) ![](https://github.com/kaikaixue/Eyepetizer/blob/master/image/reply.png) ![](https://github.com/kaikaixue/Eyepetizer/blob/master/image/detail.gif)

- item第一次加载的时候，文字跳跃出现，之后不会再跳跃
- 查看更多相关视频
- 支持查看评论
- 根据当前网络状态（流量、wifi）决定播放高清、标清视频
- 非wifi下视频所需流量提醒
- 支持全屏播放
- 播放器生命周期控制


- 进入作者页面

#### 分类

有了前面每日精选、详情页的基础，这个模块开发起来快多了，很多都是直接拿来用

主要就是自定义了一个behavior（谷歌官方有一个toolbarbehavior，但是跟这里的效果还是有差距的，所以只能自定义了）

效果如下：

![](https://github.com/kaikaixue/Eyepetizer/blob/master/image/category1.png) ![](https://github.com/kaikaixue/Eyepetizer/blob/master/image/category2.png) ![](https://github.com/kaikaixue/Eyepetizer/blob/master/image/category3.png) ![](https://github.com/kaikaixue/Eyepetizer/blob/master/image/category.gif)

#### 热门
有了前面的基础，这个模块没什么好说的，一个tablayout搞定

效果如下：

![](https://github.com/kaikaixue/Eyepetizer/blob/master/image/hot1.png) ![](https://github.com/kaikaixue/Eyepetizer/blob/master/image/hot.gif)


### API接口

[点击查看](https://github.com/kaikaixue/Eyepetizer/blob/master/app/src/main/java/com/xk/eyepetizer/api)

### 关于我

个人邮箱：3440395@qq.com

[GitHub主页](https://github.com/kaikaixue/)

[个人博客](http://xuekai.top)

## 声明

Api 数据都是来自开眼视频，数据接口均属于非正常渠道获取，请勿用于商业用途，原作公司拥有所有权利。
