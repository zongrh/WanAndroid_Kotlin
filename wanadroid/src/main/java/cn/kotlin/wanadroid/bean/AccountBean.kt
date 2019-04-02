package cn.kotlin.wanadroid.bean

import cn.kotlin.wanadroid.utils.Constant
import cn.kotlin.wanadroid.utils.Preference
import cn.kotlin.wanadroid.utils.Utils
import java.io.Serializable

/**
 *
 * FileName: AccountBean
 * Author: nanzong
 * Date: 2019/4/1 2:49 PM
 * Description:记得当前账号
 * History:
 *
 */
//记录当前账号
class AccountBean private constructor(var id: Int, var username: String,var icon: String,
                                      var type: Int, var collectIds: List<Int>?,
                                      var isLogin: Boolean) : Serializable {

    companion object {
        val instance: AccountBean by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            if (Utils.restoreObject(Constant.accountInfo) != null) {
                var account :AccountBean = Utils.restoreObject(Constant.accountInfo) as AccountBean
                AccountBean(account.id, account.username, account.icon,
                        account.type, account.collectIds, account.isLogin)
            } else {
                AccountBean(0, "", "", 0, null, false)
            }
        }
    }


    fun clear() {
        Preference.clear()
        id = 0
        username = ""
        icon = ""
        type = 0
        collectIds = null
        isLogin = false
        Utils.writeToCache(Constant.accountInfo,AccountBean.instance)
    }

    override fun toString(): String {
        return "AccountBean(id=$id, username='$username', icon='$icon', type=$type, collectIds=$collectIds, isLogin=$isLogin)"
    }
}