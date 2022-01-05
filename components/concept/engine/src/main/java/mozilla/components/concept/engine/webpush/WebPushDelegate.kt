/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package mozilla.components.concept.engine.webpush

/**
 * Notifies applications or other components of engine events related to Web Push notifications.
 * 通知应用程序或其他组件与 Web 推送通知相关的引擎事件。
 */
interface WebPushDelegate {

    /**
     * Requests a WebPush subscription for the given Service Worker scope.
     * 为给定的服务工作线程范围请求 WebPush 订阅。
     */
    fun onGetSubscription(scope: String, onSubscription: (WebPushSubscription?) -> Unit) = Unit

    /**
     * Create a WebPush subscription for the given Service Worker scope.
     * 为给定的服务工作线程范围创建 WebPush 订阅。
     */
    fun onSubscribe(scope: String, serverKey: ByteArray?, onSubscribe: (WebPushSubscription?) -> Unit) = Unit

    /**
     * Remove a subscription for the given Service Worker scope.
     * 删除给定服务工作线程作用域的订阅。
     *
     * @return whether the unsubscribe was successful or not.
     *         取消订阅是否成功
     */
    fun onUnsubscribe(scope: String, onUnsubscribe: (Boolean) -> Unit) = Unit
}
