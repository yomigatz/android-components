/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package mozilla.components.concept.engine

import android.util.JsonWriter

/**
 * The state of an [EngineSession]. An instance can be obtained from [EngineSession.saveState]. Creating a new
 * [EngineSession] and calling [EngineSession.restoreState] with the same state instance should restore the previous
 * session.
 * [引擎会话]的状态。可以从 [EngineSession.saveState] 获取实例。
 * 使用相同的状态实例创建新的 [EngineSession] 并调用 [EngineSession.restoreState] 应会还原上一个会话。
 */
interface EngineSessionState {
    /**
     * Writes this state as JSON to the given [JsonWriter].
     * 将此状态作为 JSON 写入给定的 [JsonWriter]。
     *
     * When reading JSON from disk [Engine.createSessionState] can be used to turn it back into an [EngineSessionState]
     * instance.
     * 从磁盘 [Engine.createSessionState] 读取 JSON 时，可以使用将其转回 [EngineSessionState] 实例。
     */
    fun writeTo(writer: JsonWriter)
}

/**
 * An interface describing a storage layer for an [EngineSessionState].
 */
interface EngineSessionStateStorage {
    /**
     * Writes a [state] with a provided [uuid] as its identifier.
     *
     * @return A boolean flag indicating if the write was a success.
     */
    suspend fun write(uuid: String, state: EngineSessionState): Boolean

    /**
     * Reads an [EngineSessionState] given a provided [uuid] as its identifier.
     *
     * @return A [EngineSessionState] if one is present for the given [uuid], `null` otherwise.
     */
    suspend fun read(uuid: String): EngineSessionState?

    /**
     * Deletes persisted [EngineSessionState] for a given [uuid].
     */
    suspend fun delete(uuid: String)

    /**
     * Deletes all persisted [EngineSessionState] instances.
     */
    suspend fun deleteAll()
}
