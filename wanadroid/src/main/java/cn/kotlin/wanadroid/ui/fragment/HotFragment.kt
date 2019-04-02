package cn.kotlin.wanadroid.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.kotlin.wanadroid.R

/**
 *
 * FileName: ContentFragment
 * Author: nanzong
 * Date: 2019/4/2 9:15 AM
 * Description:
 * History:
 *
 */
class HotFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragement_home, container, false)
        return view
    }
}