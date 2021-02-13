/*
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

package com.fluid.customisation.util;

public class ThemeUtil {

    // SYSTEM THEMES
    public static final String[] SYSTEM_THEMES = {
            "default",
            "filled",
            "circular",
            "rounded",
            "sam",
            "victor",
            "kai"
    };

    public static String[] getArrayOfTheme(String themename) {
        switch (themename) {
                case "circular": {
                        return CIRCULAR_THEME_PACKAGES;
                }
                case "filled": {
                        return FILLED_THEME_PACKAGES;
                }
                case "rounded": {
                        return ROUNDED_THEME_PACKAGES;
                }
                case "sam": {
                        return SAM_THEME_PACKAGES;
                }
                case "victor": {
                        return VICTOR_THEME_PACKAGES;
                }
                case "kai": {
                        return KAI_THEME_PACKAGES;
                }
        }
        return null;
    }
    public static final String[] FILLED_THEME_PACKAGES = {
            "com.android.theme.icon_pack.filled.android",
            "com.android.theme.icon_pack.filled.systemui",
            "com.android.theme.icon_pack.filled.launcher",
            "com.android.theme.icon_pack.filled.settings",
            "com.android.theme.icon_pack.filled.themepicker"
    };
    public static final String[] CIRCULAR_THEME_PACKAGES = {
            "com.android.theme.icon_pack.circular.android",
            "com.android.theme.icon_pack.circular.systemui",
            "com.android.theme.icon_pack.circular.launcher",
            "com.android.theme.icon_pack.circular.settings",
            "com.android.theme.icon_pack.circular.themepicker"
    };
    public static final String[] ROUNDED_THEME_PACKAGES = {
            "com.android.theme.icon_pack.rounded.android",
            "com.android.theme.icon_pack.rounded.systemui",
            "com.android.theme.icon_pack.rounded.launcher",
            "com.android.theme.icon_pack.rounded.settings",
            "com.android.theme.icon_pack.rounded.themepicker"
    };
    public static final String[] SAM_THEME_PACKAGES = {
            "com.android.theme.icon_pack.sam.android",
            "com.android.theme.icon_pack.sam.systemui",
            "com.android.theme.icon_pack.sam.launcher",
            "com.android.theme.icon_pack.sam.settings",
            "com.android.theme.icon_pack.sam.themepicker"
    };
    public static final String[] VICTOR_THEME_PACKAGES = {
            "com.android.theme.icon_pack.victor.android",
            "com.android.theme.icon_pack.victor.systemui",
            "com.android.theme.icon_pack.victor.launcher",
            "com.android.theme.icon_pack.victor.settings",
            "com.android.theme.icon_pack.victor.themepicker"
    };
    public static final String[] KAI_THEME_PACKAGES = {
            "com.android.theme.icon_pack.kai.android",
            "com.android.theme.icon_pack.kai.systemui",
            "com.android.theme.icon_pack.kai.launcher",
            "com.android.theme.icon_pack.kai.settings",
            "com.android.theme.icon_pack.kai.themepicker"
    };
}
