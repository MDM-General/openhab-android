/*
 * Copyright (c) 2010-2019 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */

package org.openhab.habdroid.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.json.JSONException
import org.json.JSONObject

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.TimeZone

@Parcelize
data class CloudNotification internal constructor(
    val id: String,
    val message: String,
    val createdTimestamp: Long,
    val icon: String?,
    val severity: String?
) : Parcelable

@Throws(JSONException::class)
fun JSONObject.toCloudNotification(): CloudNotification {
    var created: Long = 0
    if (has("created")) {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'")
        format.timeZone = TimeZone.getTimeZone("UTC")
        try {
            created = format.parse(getString("created")).time
        } catch (e: ParseException) {
            // keep created at 0
        }
    }

    return CloudNotification(
        getString("_id"),
        getString("message"),
        created,
        optString("icon", null),
        optString("severity", null))
}