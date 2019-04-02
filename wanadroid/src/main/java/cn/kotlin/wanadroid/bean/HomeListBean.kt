package cn.kotlin.wanadroid.bean

import java.io.Serializable

/**
 *
 * FileName: HomeListBean
 * Author: nanzong
 * Date: 2019/4/2 2:27 PM
 * Description:
 * History:
 *
 */
class HomeListBean(var errorCode: Int,
                   var errorMsg: String?,
                   var data: Data) {

    data class Data(var offset: Int,
                    var size: Int,
                    var total: Int,
                    var pageCount: Int,
                    var curPage: Int,
                    var over: Boolean,
                    var datas: List<Datas>?) {
        override fun toString(): String {
            return "Data(offset=$offset, size=$size, total=$total, pageCount=$pageCount, curPage=$curPage, over=$over, datas=$datas)"
        }
    }

    data class Datas(var id: Int,
                     var originId: Int,
                     var title: String,
                     var chapterId: Int,
                     var chapterName: String?,
                     var envelopePic: Any,
                     var link: String,
                     var author: String,
                     var origin: Any,
                     var publishTime: Long,
                     var zan: Any,
                     var desc: Any,
                     var visible: Int,
                     var niceDate: String,
                     var courseId: Int,
                     var collect: Boolean) : Serializable

    override fun toString(): String {
        return "HomeListBean(errorCode=$errorCode, errorMsg=$errorMsg, data=$data)"
    }
}