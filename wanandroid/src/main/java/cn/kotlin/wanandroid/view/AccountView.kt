package cn.kotlin.wanandroid.view

import cn.kotlin.wanandroid.bean.LoginResponse

/*
 * @Author nanzong
 * @Date 2019/3/28.
 * @Param 
 * @Description
 * @return 
 **/
interface AccountView : iBaseView {

    fun LoginSuccess(result: LoginResponse)

    fun registerSuccess(result: LoginResponse)

}