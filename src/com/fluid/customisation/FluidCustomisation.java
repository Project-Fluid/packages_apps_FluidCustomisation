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

package com.fluid.customisation;

import com.android.internal.logging.nano.MetricsProto;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Surface;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.preference.Preference;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.android.settings.SettingsPreferenceFragment;

import androidx.preference.CheckBoxPreference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceManager;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;

import com.fluid.customisation.categories.Themes;
import com.fluid.customisation.categories.StatusBar;
import com.fluid.customisation.categories.QS;
import com.fluid.customisation.categories.Lockscreen;
import com.fluid.customisation.categories.Extras;

public class FluidCustomisation extends SettingsPreferenceFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_fluid_customisation, container, false);
        final BottomNavigationView bottomNavigation = (BottomNavigationView) view.findViewById(R.id.bottom_navigation);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (item.getItemId() == bottomNavigation.getSelectedItemId()) {
                    return false;
                } else {
                    if (item.getItemId() == R.id.navigation_themes) {
                        switchFragment(new Themes());

                    } else if (item.getItemId() == R.id.navigation_statusbar) {
                        switchFragment(new StatusBar());

                    } else if (item.getItemId() == R.id.navigation_qs) {
                        switchFragment(new QS());

                    } else if (item.getItemId() == R.id.navigation_lockscreen) {
                        switchFragment(new Lockscreen());

                    } else if (item.getItemId() == R.id.navigation_extras) {
                        switchFragment(new Extras());
                    }
                    return true;
                }
            }
        });

        setHasOptionsMenu(true);
        bottomNavigation.setSelectedItemId(R.id.navigation_themes);
        switchFragment(new Themes());
        return view;
    }

    private void switchFragment(Fragment fragment) {
        getFragmentManager().beginTransaction().replace(R.id.fragment_layout, fragment).commit();
    }
    
    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.FLUID;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
}
