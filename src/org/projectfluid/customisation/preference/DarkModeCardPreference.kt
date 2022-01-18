/*
 * Copyright (C) 2022 Project Fluid
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.projectfluid.customisation.preference;

import android.app.UiModeManager
import android.content.Context
import android.util.AttributeSet

import com.android.settings.R

import org.projectfluid.ui.preference.FluidCardDarkModePreferenceBase

class DarkModeCardPreference(context: Context, attrs: AttributeSet) : FluidCardDarkModePreferenceBase(context, attrs) {

    private val uiModeManager: UiModeManager = context.getSystemService(UiModeManager::class.java)

    override fun setNightModeActivated(checked: Boolean) {
        uiModeManager.nightMode = when (checked) {
            true -> UiModeManager.MODE_NIGHT_YES
            false -> UiModeManager.MODE_NIGHT_NO
        }
    }

    override val defaultSummary = context.resources.getString(R.string.dark_mode_summary_default)
    override val powerSaveSummary = context.resources.getString(R.string.dark_mode_summary_powersave)
}