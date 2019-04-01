package cn.kotlin.wanadroid.view

/*
 * @Author nanzong
 * @Date 2019/3/28.
 * @Param 
 * @Description
 * @return 
 **/
interface iBaseView {

    fun loading()

    fun loadComplete()

    fun loadError(msg:String)

}