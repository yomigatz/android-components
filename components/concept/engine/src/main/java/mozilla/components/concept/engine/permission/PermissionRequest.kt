/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package mozilla.components.concept.engine.permission

/**
 * Represents a permission request, used when engines need access to protected
 * resources. Every request must be handled by either calling [grant] or [reject].
 * 表示一个权限请求，在引擎需要访问受保护的资源时使用。每个请求都必须通过调用 [grant] 或 [reject] 来处理。
 */
interface PermissionRequest {
    /**
     * The origin URI which caused the permissions to be requested.
     */
    val uri: String?

    /**
     * A unique identifier for the request.
     */
    val id: String

    /**
     * List of requested permissions.
     */
    val permissions: List<Permission>

    /**
     * Grants the provided permissions, or all requested permissions, if none
     * are provided.
     *
     * @param permissions the permissions to grant.
     */
    fun grant(permissions: List<Permission> = this.permissions)

    /**
     * Grants this permission request if the provided predicate is true
     * for any of the requested permissions.
     *
     * @param predicate predicate to test for.
     * @return true if the permission request was granted, otherwise false.
     */
    fun grantIf(predicate: (Permission) -> Boolean): Boolean {
        return if (permissions.any(predicate)) {
            this.grant()
            true
        } else {
            false
        }
    }

    /**
     * Rejects the requested permissions.
     */
    fun reject()

    fun containsVideoAndAudioSources() = false
}

/**
 * Represents all the different supported permission types.
 * 表示所有不同的受支持权限类型。
 *
 * @property id an optional native engine-specific ID of this permission.
 * @property desc an optional description of what this permission type is for.
 */
@Suppress("UndocumentedPublicClass")
sealed class Permission {
    abstract val id: String?
    abstract val desc: String?

    data class ContentAudioCapture(override val id: String? = "", override val desc: String? = "") : Permission()
    data class ContentAudioMicrophone(override val id: String? = "", override val desc: String? = "") : Permission()
    data class ContentAudioOther(override val id: String? = "", override val desc: String? = "") : Permission()
    data class ContentGeoLocation(override val id: String? = "", override val desc: String? = "") : Permission()
    data class ContentNotification(override val id: String? = "", override val desc: String? = "") : Permission()
    data class ContentProtectedMediaId(override val id: String? = "", override val desc: String? = "") : Permission()
    data class ContentVideoCamera(override val id: String? = "", override val desc: String? = "") : Permission()
    data class ContentVideoCapture(override val id: String? = "", override val desc: String? = "") : Permission()
    data class ContentVideoScreen(override val id: String? = "", override val desc: String? = "") : Permission()
    data class ContentVideoOther(override val id: String? = "", override val desc: String? = "") : Permission()
    data class ContentAutoPlayAudible(override val id: String? = "", override val desc: String? = "") : Permission()
    data class ContentAutoPlayInaudible(override val id: String? = "", override val desc: String? = "") : Permission()
    data class ContentPersistentStorage(override val id: String? = "", override val desc: String? = "") : Permission()
    data class ContentMediaKeySystemAccess(override val id: String? = "", override val desc: String? = "") :
        Permission()
    data class ContentCrossOriginStorageAccess(override val id: String? = "", override val desc: String? = "") :
        Permission()

    data class AppCamera(override val id: String? = "", override val desc: String? = "") : Permission()
    data class AppAudio(override val id: String? = "", override val desc: String? = "") : Permission()
    data class AppLocationCoarse(override val id: String? = "", override val desc: String? = "") : Permission()
    data class AppLocationFine(override val id: String? = "", override val desc: String? = "") : Permission()

    data class Generic(override val id: String?, override val desc: String? = "") : Permission()
}
