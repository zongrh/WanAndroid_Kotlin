package cn.kotlin.wanandroid.presenter

/**
 *
 * FileName: KnowledgePresenter
 * Author: nanzong
 * Date: 2019/4/8 8:56 PM
 * Description:
 * History:
 *
 */
interface KnowledgePresenter {

    fun getKnowledge()

    fun getKnowledgeContent(page: Int, id: Int)

    fun collect(id: Int, position: Int)

    fun cancelCollect(id: Int, position: Int)

}