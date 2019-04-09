package cn.kotlin.wanandroid.view

import cn.kotlin.wanandroid.bean.HotBean

/**
 *
 * FileName: HotView
 * Author: nanzong
 * Date: 2019/4/9 9:11 PM
 * Description:
 * History:
 *
 */
interface HotView :iBaseView{

    fun getHotKeySuc(result:HotBean)

    fun getCommonUse(result: HotBean)

}