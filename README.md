## kotlin实现仿开眼app(持续更新，欢迎star)

> **开眼视频**是一款精品短视频日报应用，该项目是用kotlin，借助已知的一些开眼接口写的一个仿《开眼App》，主要是为了学习kotlin和一些UI效果

- kotlin
- rxjava
- retrofit
- mvp
- GSYVideoPlayer

根据已知的即可，我计划实现：每日精选、发现、热门、搜索几个模块

#### 每日精选

效果如图：

![](https://github.com/kaikaixue/Eyepetizer/blob/master/image/home_small.gif)

该页主要仿了官方app的几个UI

- 通过PageTransformer实现了ViewPager切换动画，代码[点击查看](https://github.com/kaikaixue/Eyepetizer/blob/master/app/src/main/java/com/xk/eyepetizer/ui/view/banner/HomeBannerTransformer.kt)
- 自定义一个文字动画（轮播图上的两行文字，逐字出现），代码[点击查看](https://github.com/kaikaixue/Eyepetizer/blob/master/app/src/main/java/com/xk/eyepetizer/ui/view/JumpShowTextView.kt)
- RecyclerView下拉刷新，放大第一个item且带阻尼效果，代码[点击查看](https://github.com/kaikaixue/Eyepetizer/blob/master/app/src/main/java/com/xk/eyepetizer/ui/view/PullRecyclerView.kt)
- Toolbar随当前item变化
- 底部自动加载
- ViewPage中有视频播放、图片展示两种类型

#### 发现（未完成）

#### 热门（未完成）

#### 搜索（未完成）



###API接口

[点击查看](https://github.com/kaikaixue/Eyepetizer/blob/master/app/src/main/java/com/xk/eyepetizer/api)

### 关于我

个人邮箱：3440395@qq.com

[GitHub主页](https://github.com/kaikaixue/)

[个人博客](http://xuekai.top)

## 声明

Api 数据都是来自开眼视频，数据接口均属于非正常渠道获取，请勿用于商业用途，原作公司拥有所有权利。