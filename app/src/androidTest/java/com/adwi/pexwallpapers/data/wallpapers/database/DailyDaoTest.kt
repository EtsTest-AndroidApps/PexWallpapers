package com.adwi.pexwallpapers.data.wallpapers.database

import androidx.test.filters.SmallTest
import com.adwi.pexwallpapers.CoroutineAndroidTestRule
import com.adwi.pexwallpapers.data.database.wallpapers.WallpaperDatabase
import com.adwi.pexwallpapers.data.database.wallpapers.dao.DailyDao
import com.adwi.pexwallpapers.data.database.wallpapers.dao.WallpapersDao
import com.adwi.pexwallpapers.data.wallpapers.database.entity.DailyMock
import com.adwi.pexwallpapers.data.wallpapers.database.entity.WallpaperMockAndroid
import com.google.common.truth.Truth
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
@ExperimentalCoroutinesApi
@SmallTest
class DailyDaoTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val coroutineScope = CoroutineAndroidTestRule()

    @Inject
    @Named("test_database")
    lateinit var database: WallpaperDatabase

    private lateinit var dailyDao: DailyDao
    private lateinit var wallpapersDao: WallpapersDao

    private val firstDaily = DailyMock.first
    private val secondDaily = DailyMock.second

    private val firstWallpaper = WallpaperMockAndroid.first
    private val secondWallpaper = WallpaperMockAndroid.second

    @Before
    fun setup() {
        hiltRule.inject()
        dailyDao = database.dailyDao()
        wallpapersDao = database.wallpaperDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertDaily_getBackListWithOneDaily_returnTrue() =
        coroutineScope.dispatcher.runBlockingTest {

            val dailyList = listOf(firstDaily)
            val wallpaperList = listOf(firstWallpaper)

            dailyDao.insertDailyWallpapers(dailyList)
            wallpapersDao.insertWallpapers(wallpaperList)

            val actual = dailyDao.getAllDailyWallpapers().first()
            Truth.assertThat(actual).isEqualTo(wallpaperList)
        }
}