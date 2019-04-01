package cn.kotlin.wanadroid.view

import cn.kotlin.wanadroid.bean.LoginResponse

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