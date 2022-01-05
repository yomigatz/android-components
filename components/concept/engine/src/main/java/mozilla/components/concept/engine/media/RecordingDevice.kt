/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package mozilla.components.concept.engine.media

/**
 * A recording device that can be used by web content.
 * 可由 Web 内容使用的录制设备。
 *
 * @property type The type of recording device (e.g. camera or microphone)
 *                  录音设备的类型（例如摄像头或麦克风）
 * @property status The status of the recording device (e.g. whether this device is recording)
 *                  录制设备的状态（例如，此设备是否正在录制）
 */
data class RecordingDevice(
    val type: Type,
    val status: Status
) {
    /**
     * Types of recording devices.
     */
    enum class Type {
        CAMERA,
        MICROPHONE
    }

    /**
     * States a recording device can be in.
     */
    enum class Status {
        INACTIVE,
        RECORDING
    }
}
