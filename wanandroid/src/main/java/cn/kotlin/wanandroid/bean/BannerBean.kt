package cn.kotlin.wanandroid.bean

/**
 *
 * FileName: BannerBean
 * Author: nanzong
 * Date: 2019/4/3 10:34 AM
 * Description:
 * History:
 *
 */
class BannerBean(var errorCode: String,
                 var errorMsg: String,
                 var data: List<Data>?) {

    data class Data(var id: Int,
                    var url: String,
                    var imagePath: String,
                    var title: String,
                    var desc: String,
                    var isVisible: Int,
                    var type: Int) {
        override fun toString(): String {
            return "Data(id='$id',url='$url',imagePath='$imagePath',title='$title', desc='$desc',isVisible='$isVisible',type='$type')"
        }
    }

    override fun toString(): String {
        return "BannerBean(errorCode='$errorCode',errorMsg='$errorMsg',data='$data')"
    }
}