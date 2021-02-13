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

package com.fluid.customisation.fragments;

import com.fluid.customisation.util.ThemeUtil;

import static android.os.UserHandle.USER_CURRENT;

import android.content.ContentResolver;
import android.content.Context;
import android.content.om.IOverlayManager;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.Settings;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import android.view.View;
import android.view.LayoutInflater;

import androidx.preference.*;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.internal.logging.nano.MetricsProto.MetricsEvent;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.widget.LayoutPreference;

import com.android.settings.R;

import java.util.ArrayList;
import java.util.List;

public class SystemTheme extends DashboardFragment {

    public static final String TAG = "SystemTheme";
    public static final String SETTINGS_ACTIVE_OVERLAY_KEY = "active_overlay_key";

    private static final String KEY_SELECTOR_PREFERENCE = "system_theme_selector";
    private String currentActiveOverlay;

    private ContentResolver mResolver;
    private IOverlayManager mOverlayService;

    private LayoutPreference mSelectorPreference;
    private LinearLayout mParentLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSelectorPreference = findPreference(KEY_SELECTOR_PREFERENCE);
        mParentLayout = (LinearLayout) mSelectorPreference.findViewById(R.id.selector_button_frame);

        mOverlayService = IOverlayManager.Stub.asInterface(ServiceManager.getService(Context.OVERLAY_SERVICE));

        initViews();
    }

    public void initViews() {
        mParentLayout.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(getContext());

        if (Settings.Secure.getString(getContext().getContentResolver(), SETTINGS_ACTIVE_OVERLAY_KEY) != null) {
            currentActiveOverlay = Settings.Secure.getString(getContext().getContentResolver(), SETTINGS_ACTIVE_OVERLAY_KEY);
            } else { 
            currentActiveOverlay = "undefined";
        }
        
        for(int i = 0; i< ThemeUtil.SYSTEM_THEMES.length; i++){
            String theme = ThemeUtil.SYSTEM_THEMES[i];
            View view = inflater.inflate(R.layout.system_theme_selector_button_singleton, null);
            TextView label = view.findViewById(R.id.btn_label);
            ImageButton imageButton = view.findViewById(R.id.btn_imagebutton);
            String nameStringCapitalized = theme.substring(0, 1).toUpperCase() + theme.substring(1);

            label.setText(nameStringCapitalized);

            if (theme.equalsIgnoreCase(currentActiveOverlay)) {
                imageButton.setBackgroundResource(R.drawable.imagebutton_bg_active);
            }

            imageButton.setImageResource(getContext().getResources().getIdentifier("ic_systemicons_" + theme, "drawable", getContext().getPackageName()));
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setActive(theme);
                }
            });
            mParentLayout.addView(view);
        };
    }

    public void setActive(String buttonClicked) {
        reset();

        if (buttonClicked == "default") {
            Settings.Secure.putString(getContext().getContentResolver(), SETTINGS_ACTIVE_OVERLAY_KEY, buttonClicked);
            return;
        }

        Settings.Secure.putString(getContext().getContentResolver(), SETTINGS_ACTIVE_OVERLAY_KEY, buttonClicked);
        for(int i = 0; i< ThemeUtil.getArrayOfTheme(buttonClicked).length; i++){
            try {
                mOverlayService.setEnabled(ThemeUtil.getArrayOfTheme(buttonClicked)[i], true, USER_CURRENT);
            } catch (RemoteException re) {
                throw re.rethrowFromSystemServer();
            }
        }
    }

    public void reset() {
        for (int i = 0; i< ThemeUtil.SYSTEM_THEMES.length; i++) {
            String theme = ThemeUtil.SYSTEM_THEMES[i];

            if (theme != "default") {
                for (int packageCount = 0; packageCount< ThemeUtil.getArrayOfTheme(theme).length; packageCount++) {
                    try {
                        mOverlayService.setEnabled(ThemeUtil.getArrayOfTheme(theme)[packageCount], false, USER_CURRENT);
                    } catch (RemoteException re) {
                        throw re.rethrowFromSystemServer();
                    }
                }
            }
        }
        initViews();
    }

    @Override
    public int getMetricsCategory() {
        return MetricsEvent.FLUID;
    }

    @Override
    protected String getLogTag() {
        return TAG;
    }

    public boolean onPreferenceChange(Preference preference, Object newValue) {
        return false;
    }

    @Override
    protected int getPreferenceScreenResId() {
        return R.xml.customisation_systemtheme;
    }
}
