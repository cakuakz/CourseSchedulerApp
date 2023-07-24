package com.dicoding.courseschedule.ui.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.notification.DailyReminder
import com.dicoding.courseschedule.util.NightMode

class SettingsFragment : PreferenceFragmentCompat() {

    private lateinit var dailyReminder: DailyReminder

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        //TODO 10 : Update theme based on value in ListPreference
        val themePreference = findPreference<ListPreference>(getString(R.string.pref_key_dark))
        themePreference?.setOnPreferenceChangeListener { preference, newValue ->
            val nightMode = when ((newValue as String).uppercase()) {
                NightMode.ON.name -> NightMode.ON
                NightMode.OFF.name -> NightMode.OFF
                else -> NightMode.AUTO
            }
            updateTheme(nightMode.value)
            true
        }
        //TODO 11 : Schedule and cancel notification in DailyReminder based on SwitchPreference
        updateNotificationPreference()
    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }

    private fun updateNotificationPreference() {
        dailyReminder = DailyReminder()
        val notificationPreference = findPreference<SwitchPreference>(getString(R.string.pref_key_notify))
        notificationPreference?.setOnPreferenceChangeListener { preference, newValue ->
            val shouldEnableNotification = newValue as Boolean
            if (shouldEnableNotification) {
                context?.let { dailyReminder.setDailyReminder(it) }
            } else {
                context?.let { dailyReminder.cancelAlarm(it) }
            }
            true
        }
    }
}