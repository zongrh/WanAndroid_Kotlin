package cn.kotlin.wanadroid.bean

/*
 * @Author nanzong
 * @Date 2019/3/28.
 * @Param 
 * @Description
 * @return 
 **/
class LoginResponse(var errorCode: Int,
                    var errorMsg: String?,
                    var data: Data) {
    data class Data(
            var id: Int,
            var username: String,
            var password: String,
            var icon: String?,
            var type: Int,
            var collectIds: List<Int>
    ) {
        override fun toString(): String {
            return "Data(id$id,username='$password',icon='$icon'," + "type='$type',collectIds=$collectIds)"
        }
    }

    override fun toString(): String {
        return "LoginResponse(errorCode=$errorCode,errorMsg=$errorMsg,data=$data)"
    }

}