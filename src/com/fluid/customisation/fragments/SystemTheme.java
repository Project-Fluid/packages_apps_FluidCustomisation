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
import android.view.View;

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

    private static final String KEY_SELECTOR_PREFERENCE = "system_theme_selector";
    private static final String SETTINGS_ACTIVE_OVERLAY_KEY = "active_overlay_key";

    private ContentResolver mResolver;
    private IOverlayManager mOverlayService;

    private LayoutPreference mSelectorPreference;

    private ImageButton mSelectorImageButton1;
    private ImageButton mSelectorImageButton2;
    private ImageButton mSelectorImageButton3;
    private ImageButton mSelectorImageButton4;
    private ImageButton mSelectorImageButton5;
    private ImageButton mSelectorImageButton6;
    private ImageButton mSelectorImageButton7;

    private String currentActiveOverlay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSelectorPreference = findPreference(KEY_SELECTOR_PREFERENCE);

        mOverlayService = IOverlayManager.Stub
            .asInterface(ServiceManager.getService(Context.OVERLAY_SERVICE));
        
        initViews();
    }
    public void initViews() {
        mSelectorImageButton1 = mSelectorPreference.findViewById(R.id.selectorImageButton1);
        mSelectorImageButton2 = mSelectorPreference.findViewById(R.id.selectorImageButton2);
        mSelectorImageButton3 = mSelectorPreference.findViewById(R.id.selectorImageButton3);
        mSelectorImageButton4 = mSelectorPreference.findViewById(R.id.selectorImageButton4);
        mSelectorImageButton5 = mSelectorPreference.findViewById(R.id.selectorImageButton5);
        mSelectorImageButton6 = mSelectorPreference.findViewById(R.id.selectorImageButton6);
        mSelectorImageButton7 = mSelectorPreference.findViewById(R.id.selectorImageButton7);

        mSelectorImageButton1.setOnClickListener(onSelectorImageButton1ClickListener);
        mSelectorImageButton2.setOnClickListener(onSelectorImageButton2ClickListener);
        mSelectorImageButton3.setOnClickListener(onSelectorImageButton3ClickListener);
        mSelectorImageButton4.setOnClickListener(onSelectorImageButton4ClickListener);
        mSelectorImageButton5.setOnClickListener(onSelectorImageButton5ClickListener);
        mSelectorImageButton6.setOnClickListener(onSelectorImageButton6ClickListener);
        mSelectorImageButton7.setOnClickListener(onSelectorImageButton7ClickListener);

        initActive();
    }

    public void initActive() {

        if (Settings.Secure.getString(getContext().getContentResolver(), SETTINGS_ACTIVE_OVERLAY_KEY) != null) {
            currentActiveOverlay = Settings.Secure.getString(getContext().getContentResolver(), SETTINGS_ACTIVE_OVERLAY_KEY);

            } else { 
            currentActiveOverlay = "undefined";
        }

        switch (currentActiveOverlay) {
            case "default":
                mSelectorImageButton1.setBackgroundResource(R.drawable.imagebutton_bg_active);
                break;
            case "filled":
                mSelectorImageButton2.setBackgroundResource(R.drawable.imagebutton_bg_active);
                break;
            case "circular":
                mSelectorImageButton3.setBackgroundResource(R.drawable.imagebutton_bg_active);
                break;
            case "rounded":
                mSelectorImageButton4.setBackgroundResource(R.drawable.imagebutton_bg_active);
                break;
            case "sam":
                mSelectorImageButton5.setBackgroundResource(R.drawable.imagebutton_bg_active);
                break;
            case "victor":
                mSelectorImageButton6.setBackgroundResource(R.drawable.imagebutton_bg_active);
                break;
            case "kai":
                mSelectorImageButton7.setBackgroundResource(R.drawable.imagebutton_bg_active);
                break;
            case "undefined":
                break;

        }
    }

    public void setActive(String buttonClicked) {

        reset();
        switch (buttonClicked) {
            case "default":
                mSelectorImageButton1.setBackgroundResource(R.drawable.imagebutton_bg_active);
                Settings.Secure.putString(getContext().getContentResolver(), SETTINGS_ACTIVE_OVERLAY_KEY,  buttonClicked);
                break;
            case "filled":
                mSelectorImageButton2.setBackgroundResource(R.drawable.imagebutton_bg_active);
                Settings.Secure.putString(getContext().getContentResolver(), SETTINGS_ACTIVE_OVERLAY_KEY,  buttonClicked);
                for(int i = 0; i< ThemeUtil.FILLED_THEME_PACKAGES.length; i++){
                    try {
                        mOverlayService.setEnabled(ThemeUtil.FILLED_THEME_PACKAGES[i], true, USER_CURRENT);
                    } catch (RemoteException re) {
                        throw re.rethrowFromSystemServer();
                    }
                }
                break;
            case "circular":
                mSelectorImageButton3.setBackgroundResource(R.drawable.imagebutton_bg_active);
                Settings.Secure.putString(getContext().getContentResolver(), SETTINGS_ACTIVE_OVERLAY_KEY,  buttonClicked);
                for(int i = 0; i< ThemeUtil.CIRCULAR_THEME_PACKAGES.length; i++){
                    try {
                        mOverlayService.setEnabled(ThemeUtil.CIRCULAR_THEME_PACKAGES[i], true, USER_CURRENT);
                    } catch (RemoteException re) {
                        throw re.rethrowFromSystemServer();
                    }
                }
                break;
            case "rounded":
                mSelectorImageButton4.setBackgroundResource(R.drawable.imagebutton_bg_active);
                Settings.Secure.putString(getContext().getContentResolver(), SETTINGS_ACTIVE_OVERLAY_KEY,  buttonClicked);
                for(int i = 0; i< ThemeUtil.ROUNDED_THEME_PACKAGES.length; i++) {
                    try {
                        mOverlayService.setEnabled(ThemeUtil.ROUNDED_THEME_PACKAGES[i], true, USER_CURRENT);
                    } catch (RemoteException re) {
                        throw re.rethrowFromSystemServer();
                    }
                }
                break;
            case "sam":
                mSelectorImageButton5.setBackgroundResource(R.drawable.imagebutton_bg_active);
                Settings.Secure.putString(getContext().getContentResolver(), SETTINGS_ACTIVE_OVERLAY_KEY,  buttonClicked);
                for(int i = 0; i< ThemeUtil.SAM_THEME_PACKAGES.length; i++){
                    try {
                        mOverlayService.setEnabled(ThemeUtil.SAM_THEME_PACKAGES[i], true, USER_CURRENT);
                    } catch (RemoteException re) {
                        throw re.rethrowFromSystemServer();
                    }
                }
                break;
            case "victor":
                mSelectorImageButton6.setBackgroundResource(R.drawable.imagebutton_bg_active);
                Settings.Secure.putString(getContext().getContentResolver(), SETTINGS_ACTIVE_OVERLAY_KEY,  buttonClicked);
                for(int i = 0; i< ThemeUtil.VICTOR_THEME_PACKAGES.length; i++){
                    try {
                        mOverlayService.setEnabled(ThemeUtil.VICTOR_THEME_PACKAGES[i], true, USER_CURRENT);
                    } catch (RemoteException re) {
                        throw re.rethrowFromSystemServer();
                    }
                }
                break;
            case "kai":
                mSelectorImageButton7.setBackgroundResource(R.drawable.imagebutton_bg_active);
                Settings.Secure.putString(getContext().getContentResolver(), SETTINGS_ACTIVE_OVERLAY_KEY,  buttonClicked);
                for(int i = 0; i< ThemeUtil.KAI_THEME_PACKAGES.length; i++){
                    try {
                        mOverlayService.setEnabled(ThemeUtil.KAI_THEME_PACKAGES[i], true, USER_CURRENT);
                    } catch (RemoteException re) {
                        throw re.rethrowFromSystemServer();
                    }
                }
                break;
        }
    }
    
    public void reset() {
        mSelectorImageButton1.setBackgroundResource(R.drawable.imagebutton_bg_normal);
        mSelectorImageButton2.setBackgroundResource(R.drawable.imagebutton_bg_normal);
        mSelectorImageButton3.setBackgroundResource(R.drawable.imagebutton_bg_normal);
        mSelectorImageButton4.setBackgroundResource(R.drawable.imagebutton_bg_normal);
        mSelectorImageButton5.setBackgroundResource(R.drawable.imagebutton_bg_normal);
        mSelectorImageButton6.setBackgroundResource(R.drawable.imagebutton_bg_normal);
        mSelectorImageButton7.setBackgroundResource(R.drawable.imagebutton_bg_normal);

        for(int i = 0; i< ThemeUtil.FILLED_THEME_PACKAGES.length; i++){
            try {
                mOverlayService.setEnabled(ThemeUtil.FILLED_THEME_PACKAGES[i], false, USER_CURRENT);
            } catch (RemoteException re) {
                throw re.rethrowFromSystemServer();
            }
        }
        for(int i = 0; i< ThemeUtil.CIRCULAR_THEME_PACKAGES.length; i++){
            try {
                mOverlayService.setEnabled(ThemeUtil.CIRCULAR_THEME_PACKAGES[i], false, USER_CURRENT);
            } catch (RemoteException re) {
                throw re.rethrowFromSystemServer();
            }
        }
        for(int i = 0; i< ThemeUtil.ROUNDED_THEME_PACKAGES.length; i++){
            try {
                mOverlayService.setEnabled(ThemeUtil.ROUNDED_THEME_PACKAGES[i], false, USER_CURRENT);
            } catch (RemoteException re) {
                throw re.rethrowFromSystemServer();
            }
        }
        for(int i = 0; i< ThemeUtil.SAM_THEME_PACKAGES.length; i++){
            try {
                mOverlayService.setEnabled(ThemeUtil.SAM_THEME_PACKAGES[i], false, USER_CURRENT);
            } catch (RemoteException re) {
                throw re.rethrowFromSystemServer();
            }
        }
        for(int i = 0; i< ThemeUtil.VICTOR_THEME_PACKAGES.length; i++){
            try {
                mOverlayService.setEnabled(ThemeUtil.VICTOR_THEME_PACKAGES[i], false, USER_CURRENT);
            } catch (RemoteException re) {
                throw re.rethrowFromSystemServer();
            }
        }
        for(int i = 0; i< ThemeUtil.KAI_THEME_PACKAGES.length; i++){
            try {
                mOverlayService.setEnabled(ThemeUtil.KAI_THEME_PACKAGES[i], false, USER_CURRENT);
            } catch (RemoteException re) {
                throw re.rethrowFromSystemServer();
            }
        }
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

    private View.OnClickListener onSelectorImageButton1ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setActive("default");
        }
    };
    private View.OnClickListener onSelectorImageButton2ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setActive("filled");
        }
    };
    private View.OnClickListener onSelectorImageButton3ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setActive("circular");
        }
    };
    private View.OnClickListener onSelectorImageButton4ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setActive("rounded");
        }
    };
    private View.OnClickListener onSelectorImageButton5ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setActive("sam");
        }
    };
    private View.OnClickListener onSelectorImageButton6ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setActive("victor");
        }
    };
    private View.OnClickListener onSelectorImageButton7ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setActive("kai");
        }
    };

}
