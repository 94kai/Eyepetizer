package com.xk.eyepetizer.mvp.model.bean

/**
 * Created by xuekai on 2017/8/20.
 */
data class Item(val type: String, val data: Data?, val tag: String) {

    data class Data(val dataType: String, val text: String, val id: Int, val title: String, val slogan: String?,
                    val description: String,
                    val provider: Provider,
                    val category: String,
                    val author: Author,
                    val cover: Cover,
                    val playUrl: String,
                    val thumbPlayUrl: String,
                    val duration: Long,
                    val webUrl: WebUrl,
                    val library: String,
                    val playInfo: Any,
                    val consumption: Any,
                    val campaign: Any,
                    val waterMarks: Any,
                    val adTrack: Any,
                    val tags: ArrayList<Tag>,
                    val type: String,
                    val titlePgc: Any,
                    val descriptionPgc: Any,
                    val remark: String,
                    val idx: Int,
                    val shareAdTrack: Any,
                    val favoriteAdTrack: Any,
                    val webAdTrack: Any,
                    val date: Long,
                    val promotion: Any,
                    val label: Any,
                    val labelList: Any,
                    val descriptionEditor: String,
                    val collected: Boolean,
                    val played: Boolean,
                    val subtitles: Any,
                    val lastViewTime: Any,
                    val playlists: Any
    ) {
        data class Tag(val id: Int, val name: String, val actionUrl: String, val adTrack: Any)
        data class Author(val icon: String)

        data class Provider(val name: String, val alias: String, val icon: String)

        data class Cover(val feed: String, val detail: String,
                         val blurred: String, val sharing: String, val homepage: String)

        data class WebUrl(val raw: String, val forWeibo: String)

    }
}
