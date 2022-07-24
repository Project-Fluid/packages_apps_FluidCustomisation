/*
 * Copyright (C) 2022 Project Fluid
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.projectfluid.customisation.fragment

import com.android.internal.logging.nano.MetricsProto.MetricsEvent
import com.android.internal.util.fluid.FluidUtils

import android.app.ActivityManagerNative
import android.content.Context
import android.content.ContentResolver

import android.os.Bundle

import android.provider.Settings;

import androidx.preference.Preference
import androidx.preference.PreferenceScreen
import androidx.preference.SwitchPreference

import com.android.settings.R
import com.android.settings.SettingsPreferenceFragment

class StatusBar : SettingsPreferenceFragment(), Preference.OnPreferenceChangeListener {

    private lateinit var mEnableCombinedSignalIcons: SwitchPreference;

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.statusbar)

        val resolver: ContentResolver = getActivity().getContentResolver()
        val prefSet: PreferenceScreen = getPreferenceScreen()

        mEnableCombinedSignalIcons = findPreference(COMBINED_SIGNAL_ICONS) as SwitchPreference?
        val def = Settings.System.getString(
            getContentResolver(),
            COMBINED_SIGNAL_ICONS
        )
        mEnableCombinedSignalIcons.isChecked = (def != null && def.toInt() == 1)
        mEnableCombinedSignalIcons.onPreferenceChangeListener = this
    }

    override fun onPreferenceChange(preference: Preference, newValue: Any?): Boolean {
       if (preference === mEnableCombinedSignalIcons) {
            val value = newValue as Boolean
            Settings.System.putString(
                getActivity().getContentResolver(),
                COMBINED_SIGNAL_ICONS, if (value) "1" else "0"
            )
            FluidUtils.showSystemUiRestartDialog(getActivity())
            return true
        }
        return true
    }

    override fun getMetricsCategory(): Int = MetricsEvent.FLUID

    companion object {
        const val TAG = "FluidCustomisation"
        private const val COMBINED_SIGNAL_ICONS = "combined_status_bar_signal_icons"
    }
}
