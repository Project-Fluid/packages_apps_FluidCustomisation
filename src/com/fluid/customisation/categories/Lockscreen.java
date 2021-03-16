/*
 * Copyright (C) 2014-2016 The Dirty Unicorns Project
 * Copyright (C) 2020 ProjectFluid
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

package com.fluid.customisation.categories;

import android.content.Context;
import android.content.ContentResolver;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.UserHandle;
import android.hardware.fingerprint.FingerprintManager;
import androidx.preference.*;
import com.android.internal.util.custom.FodUtils;
import android.provider.Settings;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.internal.logging.nano.MetricsProto.MetricsEvent;
import com.android.settings.Utils;

public class Lockscreen extends SettingsPreferenceFragment implements Preference.OnPreferenceChangeListener {

    public static final String TAG = "Lockscreen";
    private static final String POCKET_JUDGE = "pocket_judge";
    private static final String KEY_FOD_RECOGNIZING_ANIM = "fod_recognizing_animation";
    private static final String FINGERPRINT_VIB = "fingerprint_success_vib";
    private static final String AOD_SCHEDULE_KEY = "always_on_display_schedule";

    static final int MODE_DISABLED = 0;
    static final int MODE_NIGHT = 1;
    static final int MODE_TIME = 2;
    static final int MODE_MIXED_SUNSET = 3;
    static final int MODE_MIXED_SUNRISE = 4;

    private boolean mHasFod;
    private Preference mPocketJudge;
    private FingerprintManager mFingerprintManager;
    private SwitchPreference mFingerprintVib;
    private Context mContext;
    Preference mAODPref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.customisation_lockscreen);
        setRetainInstance(true);

        ContentResolver resolver = getActivity().getContentResolver();
        final Resources res = getResources();
        PreferenceScreen prefScreen = getPreferenceScreen();

        mContext = getContext();
       // Fod
        mHasFod = FodUtils.hasFodSupport(mContext);
        if (!mHasFod) {
            prefScreen.removePreference(findPreference(KEY_FOD_RECOGNIZING_ANIM));
        }
       // PocketMode
        mPocketJudge = (Preference) prefScreen.findPreference(POCKET_JUDGE);
        boolean mPocketJudgeSupported = res.getBoolean(
                com.android.internal.R.bool.config_pocketModeSupported);
        if (!mPocketJudgeSupported)
            prefScreen.removePreference(mPocketJudge);
       // FP vibration
        mFingerprintManager = (FingerprintManager) getActivity().getSystemService(Context.FINGERPRINT_SERVICE);
        mFingerprintVib = (SwitchPreference) findPreference(FINGERPRINT_VIB);
        if (mFingerprintManager == null || !mFingerprintManager.isHardwareDetected()){
            prefScreen.removePreference(mFingerprintVib);
        } else {
            mFingerprintVib.setChecked((Settings.System.getInt(getContentResolver(),
                    Settings.System.FINGERPRINT_SUCCESS_VIB, 1) == 1));
            mFingerprintVib.setOnPreferenceChangeListener(this);
        }
       // AOD
       mAODPref = findPreference(AOD_SCHEDULE_KEY);
       updateAlwaysOnSummary();
    }

    @Override
    public int getMetricsCategory() {
        return MetricsEvent.FLUID;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateAlwaysOnSummary();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void updateAlwaysOnSummary() {
        if (mAODPref == null) return;
        int mode = Settings.Secure.getIntForUser(getActivity().getContentResolver(),
                Settings.Secure.DOZE_ALWAYS_ON_AUTO_MODE, 0, UserHandle.USER_CURRENT);
        switch (mode) {
            default:
            case MODE_DISABLED:
                mAODPref.setSummary(R.string.disabled);
                break;
            case MODE_NIGHT:
                mAODPref.setSummary(R.string.night_display_auto_mode_twilight);
                break;
            case MODE_TIME:
                mAODPref.setSummary(R.string.night_display_auto_mode_custom);
                break;
            case MODE_MIXED_SUNSET:
                mAODPref.setSummary(R.string.always_on_display_schedule_mixed_sunset);
                break;
            case MODE_MIXED_SUNRISE:
                mAODPref.setSummary(R.string.always_on_display_schedule_mixed_sunrise);
                break;
        }
    }

    public boolean onPreferenceChange(Preference preference, Object objValue) {
        final String key = preference.getKey();
        ContentResolver resolver = getActivity().getContentResolver();
        if (preference == mFingerprintVib) {
            boolean value = (Boolean) objValue;
            Settings.System.putInt(resolver,
                    Settings.System.FINGERPRINT_SUCCESS_VIB, value ? 1 : 0);
        }
        return true;
    }
}
