package io.altenems.companion.android.settings

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import io.altenems.companion.android.settings.assist.DefaultAssistantManager
import io.altenems.companion.android.settings.language.LanguagesProvider
import javax.inject.Inject

class SettingsFragmentFactory @Inject constructor(
    private val settingsPresenter: SettingsPresenter,
    private val languagesProvider: LanguagesProvider,
    private val defaultAssistantManager: DefaultAssistantManager,
) : FragmentFactory() {
    @SuppressLint("NewApi")
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            SettingsFragment::class.java.name -> SettingsFragment(
                presenter = settingsPresenter,
                langProvider = languagesProvider,
                defaultAssistantManager = defaultAssistantManager,
            )
            else -> super.instantiate(classLoader, className)
        }
    }
}
