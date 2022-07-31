package org.projectfluid.customisation.preference

import android.content.Context
import android.util.AttributeSet
import android.widget.Toast

import org.projectfluid.ui.preference.FluidSystemThemePreferenceBase

class SystemThemePreference(context: Context, attributeSet: AttributeSet)
    : FluidSystemThemePreferenceBase(context, attributeSet) {

    // open the fitting fragment for more settings (called on button press)
    override fun openThemeSettings() {
        Toast.makeText(context, "Monet switch coming soon", Toast.LENGTH_SHORT).show()
    }

    // check state whether monet do be enabled
    override fun isMonetEnabled(): Boolean {
        return true
    }

    // toggle monet on or off system-wide
    override fun toggleMonet(activated: Boolean?) {
    }
}
