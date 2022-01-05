/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package mozilla.components.concept.engine.history

import mozilla.components.concept.storage.PageVisit

/**
 * An interface used for providing history information to an engine (e.g. for link highlighting),
 * and receiving history updates from the engine (visits to URLs, title changes).
 * 用于向引擎提供历史记录信息（例如，用于链接突出显示）以及从引擎接收历史记录更新（访问 URL、标题更改）的界面。
 *
 * Even though this interface is defined at the "concept" layer, its get* methods are tailored to
 * two types of engines which we support (system's WebView and GeckoView).
 * 尽管此接口是在"概念"层定义的，但其 get* 方法是针对我们支持的两种类型的引擎（系统的 WebView 和 GeckoView）量身定制的。
 */
interface HistoryTrackingDelegate {
    /**
     * A URI visit happened that an engine considers worthy of being recorded in browser's history.
     */
    suspend fun onVisited(uri: String, visit: PageVisit)

    /**
     * Title changed for a given URI.
     */
    suspend fun onTitleChanged(uri: String, title: String)

    /**
     * Preview image changed for a given URI.
     */
    suspend fun onPreviewImageChange(uri: String, previewImageUrl: String)

    /**
     * An engine needs to know "visited" (true/false) status for provided URIs.
     */
    suspend fun getVisited(uris: List<String>): List<Boolean>

    /**
     * An engine needs to know a list of all visited URIs.
     */
    suspend fun getVisited(): List<String>

    /**
     * Allows an engine to check if this URI is going to be accepted by the delegate.
     * This helps avoid unnecessary coroutine overhead for URIs which won't be accepted.
     */
    fun shouldStoreUri(uri: String): Boolean
}
