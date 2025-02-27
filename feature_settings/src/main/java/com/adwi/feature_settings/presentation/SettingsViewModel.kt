package com.adwi.feature_settings.presentation

import androidx.paging.ExperimentalPagingApi
import com.adwi.components.IoDispatcher
import com.adwi.components.base.BaseViewModel
import com.adwi.components.ext.onDispatcher
import com.adwi.feature_settings.data.database.SettingsDao
import com.adwi.feature_settings.data.database.model.Settings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@HiltViewModel
class SettingsViewModel
@Inject constructor(
    private val settingsDao: SettingsDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {


    private val _settings: MutableStateFlow<Settings> = MutableStateFlow(Settings())
    //    private val _saveState: MutableStateFlow<Result> = MutableStateFlow(Result.Idle)

    val settings = _settings.asStateFlow()
    //    val saveState = _saveState.asStateFlow()

    fun getSettings() {
        onDispatcher(ioDispatcher) {
            settingsDao.getSettings().collect {
                _settings.value = it
            }
        }
    }

    // Notifications
    fun updatePushNotifications(checked: Boolean) {
        onDispatcher(ioDispatcher) {
            settingsDao.updatePushNotifications(checked)
        }
    }

    fun updateNewWallpaperSet(checked: Boolean) {
        onDispatcher(ioDispatcher) {
            settingsDao.updateNewWallpaperSet(checked)
        }
    }

    fun updateWallpaperRecommendations(checked: Boolean) {
        onDispatcher(ioDispatcher) {
            settingsDao.updateWallpaperRecommendations(checked)
        }
    }

    // Automation
    fun updateAutoChangeWallpaper(checked: Boolean) {
        onDispatcher(ioDispatcher) {
            settingsDao.updateAutoChangeWallpaper(checked)
        }
    }

    fun updateAutoHome(checked: Boolean) {
        onDispatcher(ioDispatcher) {
            settingsDao.updateAutoHome(checked)
        }
    }

    fun updateAutoLock(checked: Boolean) {
        onDispatcher(ioDispatcher) {
            settingsDao.updateAutoLock(checked)
        }
    }

    fun updateMinutes(value: Int) {
        onDispatcher(ioDispatcher) {
            settingsDao.updateMinutes(value)
        }
    }

    fun updateHours(value: Int) {
        onDispatcher(ioDispatcher) {
            settingsDao.updateHours(value)
        }
    }

    fun updateDays(value: Int) {
        onDispatcher(ioDispatcher) {
            settingsDao.updateDays(value)
        }
    }

    // Data saver
    fun updateActivateDataSaver(checked: Boolean) {
        onDispatcher(ioDispatcher) {
            settingsDao.updateActivateDataSaver(checked)
        }
    }

    fun updateDownloadWallpapersOverWiFi(checked: Boolean) {
        onDispatcher(ioDispatcher) {
            settingsDao.updateDownloadWallpapersOverWiFi(checked)
        }
    }

    fun updateDownloadMiniaturesLowQuality(checked: Boolean) {
        onDispatcher(ioDispatcher) {
            settingsDao.updateDownloadMiniaturesLowQuality(checked)
        }
    }

    fun updateAutoChangeOverWiFi(checked: Boolean) {
        onDispatcher(ioDispatcher) {
            settingsDao.updateAutoChangeOverWiFi(checked)
        }
    }

    // Performance
    fun updateDisableShadows(checked: Boolean) {
        onDispatcher(ioDispatcher) {
            settingsDao.updateDisableShadows(checked)
        }
    }

    fun updateDisableParallax(checked: Boolean) {
        onDispatcher(ioDispatcher) {
            settingsDao.updateDisableParallax(checked)
        }
    }

    fun resetSettings() {
        onDispatcher(ioDispatcher) {
            settingsDao.insertSettings(Settings.default)
        }
    }
}