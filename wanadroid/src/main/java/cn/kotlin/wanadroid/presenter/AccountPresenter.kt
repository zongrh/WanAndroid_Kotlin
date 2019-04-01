package cn.kotlin.wanadroid.presenter

/**
 *
 * FileName: AccountPresenter
 * Author: nanzong
 * Date: 2019/3/31 10:59 PM
 * Description: ${DESCRIPTION}
 * History:
 *
 */

interface AccountPresenter {

    fun login(username: String, password: String)

    fun register(username: String, password: String, repassword: String)

}
