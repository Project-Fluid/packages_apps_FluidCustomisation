package org.projectfluid.customisation.preference

import android.content.Context
import android.util.AttributeSet

import org.projectfluid.ui.preference.FluidSystemThemePreferenceBase

class SystemThemePreference(context: Context, attributeSet: AttributeSet)
    : FluidSystemThemePreferenceBase(context, attributeSet) {

    // remove these comments later yes yes
    // open the fitting fragment for more settings (called on button press)
    override fun openMonetOrThemeSettings() {
        //when (monetEnabled) {
        //    true -> Toast.makeText(context, "Open monet settings", Toast.LENGTH_SHORT).show()
        //    false -> Toast.makeText(context, "Open theme settings", Toast.LENGTH_SHORT).show()
        //}
    }

    // check state whether monet do be enabled
    override fun isMonetEnabled(): Boolean {
        return true
    }

    // toggle monet on or off system-wide
    override fun toggleMonet(activated: Boolean?) {
    }
}