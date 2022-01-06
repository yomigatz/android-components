/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

@file:Suppress("Deprecation")

package mozilla.components.concept.engine

import android.graphics.Bitmap
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import mozilla.components.concept.engine.EngineView.InputResult.INPUT_RESULT_HANDLED
import mozilla.components.concept.engine.EngineView.InputResult.INPUT_RESULT_HANDLED_CONTENT
import mozilla.components.concept.engine.EngineView.InputResult.INPUT_RESULT_UNHANDLED
import mozilla.components.concept.engine.selection.SelectionActionDelegate

/**
 * View component that renders web content.
 * 呈现 Web 内容的视图组件。
 */
interface EngineView {

    /**
     * Convenience method to cast the implementation of this interface to an Android View object.
     * 将此接口的实现转换为 Android View 对象的方便方法。
     */
    fun asView(): View = this as View

    /**
     * Render the content of the given session.
     * 呈现给定会话的内容。
     */
    fun render(session: EngineSession)

    /**
     * Releases an [EngineSession] that is currently rendered by this view (after calling [render]).
     * 释放当前由此视图呈现的 [引擎会话]（在调用 [render] 之后）。
     *
     * Usually an app does not need to call this itself since [EngineView] will take care of that if it gets detached.
     * However there are situations where an app wants to hand-off rendering of an [EngineSession] to a different
     * 通常，应用程序不需要自己调用它，因为[EngineView]如果分离，它将负责处理。
     * 但是，在某些情况下，应用希望将 [EngineSession] 的渲染移交给其他呈现
     * [EngineView] without the current [EngineView] getting detached immediately.
     *          当前 [EngineView] 不会立即分离。
     */
    fun release()

    /**
     * To be called in response to [Lifecycle.Event.ON_RESUME]. See [EngineView]
     * implementations for details.
     */
    fun onResume() = Unit

    /**
     * To be called in response to [Lifecycle.Event.ON_PAUSE]. See [EngineView]
     * implementations for details.
     */
    fun onPause() = Unit

    /**
     * To be called in response to [Lifecycle.Event.ON_START]. See [EngineView]
     * implementations for details.
     */
    fun onStart() = Unit

    /**
     * To be called in response to [Lifecycle.Event.ON_STOP]. See [EngineView]
     * implementations for details.
     */
    fun onStop() = Unit

    /**
     * To be called in response to [Lifecycle.Event.ON_CREATE]. See [EngineView]
     * implementations for details.
     */
    fun onCreate() = Unit

    /**
     * To be called in response to [Lifecycle.Event.ON_DESTROY]. See [EngineView]
     * implementations for details.
     */
    fun onDestroy() = Unit

    /**
     * Check if [EngineView] can clear the selection.
     * true if can and false otherwise.
     * 检查[EngineView]是否可以清除选择。如果可以，则为真，否则为假。
     */
    fun canClearSelection(): Boolean = false

    /**
     * Check if [EngineView] can be scrolled vertically up.
     * true if can and false otherwise.
     * 检查[EngineView]是否可以垂直向上滚动。如果可以，则为真，否则为假。
     */
    fun canScrollVerticallyUp(): Boolean = true

    /**
     * Check if [EngineView] can be scrolled vertically down.
     * true if can and false otherwise.
     * 检查[EngineView]是否可以垂直向下滚动。如果可以，则为真，否则为假。
     */
    fun canScrollVerticallyDown(): Boolean = true

    /**
     * @return [InputResult] indicating how user's last [android.view.MotionEvent] was handled.
     *      [InputResult] 指示用户的最后一个 [android.view.MotionEvent] 是如何处理的。
     */
    @Deprecated("Not enough data about how the touch was handled", ReplaceWith("getInputResultDetail()"))
    fun getInputResult(): InputResult = InputResult.INPUT_RESULT_UNHANDLED

    /**
     * @return [InputResultDetail] indicating how user's last [android.view.MotionEvent] was handled.
     *         指示用户的最后一个 [android.view.MotionEvent] 的处理方式。
     */
    fun getInputResultDetail(): InputResultDetail = InputResultDetail.newInstance()

    /**
     * Request a screenshot of the visible portion of the web page currently being rendered.
     * 请求当前正在呈现的网页的可见部分的屏幕截图。
     *
     * @param onFinish A callback to inform that process of capturing a
     * thumbnail has finished. Important for engine-gecko: Make sure not to reference the
     * context or view in this callback to prevent memory leaks:
     * https://bugzilla.mozilla.org/show_bug.cgi?id=1678364
     *
     * 回调，通知捕获缩略图的过程已完成。对于引擎壁虎重要提示：
     * 请确保不要在此回调中引用上下文或视图，以防止内存泄漏：
     * https：//bugzilla.mozilla.org/show_bug.cgi？id=1678364
     */
    fun captureThumbnail(onFinish: (Bitmap?) -> Unit)

    /**
     * Clears the current selection if possible.
     * 如果可能，清除当前选择。
     */
    fun clearSelection() = Unit

    /**
     * Updates the amount of vertical space that is clipped or visibly obscured in the bottom portion of the view.
     * Tells the [EngineView] where to put bottom fixed elements so they are fully visible.
     * 更新视图底部被剪切或明显遮挡的垂直空间量。
     * 告诉 [EngineView] 将底部固定元素放在何处，以便它们完全可见。
     *
     * @param clippingHeight The height of the bottom clipped space in screen pixels.
     */
    fun setVerticalClipping(clippingHeight: Int)

    /**
     * Sets the maximum height of the dynamic toolbar(s).
     * 设置动态工具栏的最大高度。
     *
     * @param height The maximum possible height of the toolbar.
     */
    fun setDynamicToolbarMaxHeight(height: Int)

    /**
     * A delegate that will handle interactions with text selection context menus.
     * 将处理与文本选择上下文菜单交互的委托。
     */
    var selectionActionDelegate: SelectionActionDelegate?

    /**
     * Enumeration of all possible ways user's [android.view.MotionEvent] was handled.
     * 枚举了用户 [android.view.MotionEvent] 的所有可能处理方式。
     *
     * @see [INPUT_RESULT_UNHANDLED]
     * @see [INPUT_RESULT_HANDLED]
     * @see [INPUT_RESULT_HANDLED_CONTENT]
     */
    @Deprecated("Not enough data about how the touch was handled", ReplaceWith("InputResultDetail"))
    enum class InputResult(val value: Int) {
        /**
         * Last [android.view.MotionEvent] was not handled by neither us nor the webpage.
         */
        INPUT_RESULT_UNHANDLED(0),

        /**
         * We handled the last [android.view.MotionEvent].
         */
        INPUT_RESULT_HANDLED(1),

        /**
         * Webpage handled the last [android.view.MotionEvent].
         * (through it's own touch event listeners)
         */
        INPUT_RESULT_HANDLED_CONTENT(2),
    }
}

/**
 * [LifecycleObserver] which dispatches lifecycle events to an [EngineView].
 */
class LifecycleObserver(val engineView: EngineView) : androidx.lifecycle.LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        engineView.onPause()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        engineView.onResume()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        engineView.onStart()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        engineView.onStop()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        engineView.onCreate()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        engineView.onDestroy()
    }
}
