package cn.kotlin.wanadroid.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.kotlin.wanadroid.R

/**
 *
 * FileName: HomeFragment
 * Author: nanzong
 * Date: 2019/4/2 9:09 AM
 * Description:
 * History:
 *
 */
class HomeFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragement_hot, container, false)
        return view

    }

}

