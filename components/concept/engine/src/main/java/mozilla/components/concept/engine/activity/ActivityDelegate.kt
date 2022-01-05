/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package mozilla.components.concept.engine.activity

import android.content.Intent
import android.content.IntentSender

/**
 * Notifies applications or other components of engine events that require interaction with an Android Activity.
 * 将需要与 Android 活动交互的引擎事件通知应用程序或其他组件。
 */
interface ActivityDelegate {

    /**
     * Requests an [IntentSender] is started on behalf of the engine.
     * 请求代表引擎启动 [IntentSender]。
     *
     * @param intent The [IntentSender] to be started through an Android Activity.
     * @param onResult The callback to be invoked when we receive the result with the intent data.
     */
    fun startIntentSenderForResult(intent: IntentSender, onResult: (Intent?) -> Unit) = Unit
}
