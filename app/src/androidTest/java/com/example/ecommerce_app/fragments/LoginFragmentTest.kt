package com.example.ecommerce_app.fragments

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.bumptech.glide.manager.Lifecycle
import com.example.ecommerce_app.*
import com.example.ecommerce_app.activities.ShopActivity
import com.example.ecommerce_app.fragments.auth.LoginFragment
import com.example.ecommerce_app.fragments.auth.LoginFragmentDirections
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
@MediumTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class LoginFragmentTest :TestCase() {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private val networkIdlingResource = NetworkIdlingResource()

    @Before
    fun setup(){
        hiltRule.inject()
    }

    private fun launchFragment(navController: TestNavHostController) {
        launchFragmentInHiltContainer<LoginFragment>(themeResId = R.style.Theme_Ecommerceapp) {
            navController.setGraph(R.navigation.auth_graph)
            navController.setCurrentDestination(R.id.loginFragment)
            Navigation.setViewNavController(requireView(), navController)
        }
    }


    @Test
    fun testLoginFragmentToRegisterFragment() {
        //val navController = TestNavHostController(getApplicationContext())
        //launchFragment(navController)
        val navController = TestNavHostController(getApplicationContext())
        launchFragment(navController)
        IdlingRegistry.getInstance().register(NetworkIdlingResource())
        NetworkManager.getInstance().setNetworkIdleState(true)
        // Launch the fragment within the activity

        // Perform UI interactions using Espresso
        onView(withId(R.id.et_reg_email)).perform(typeText("sk@gmail.com"), closeSoftKeyboard())
        onView(withId(R.id.et_reg_password)).perform(typeText("123456"), closeSoftKeyboard())
        onView(withId(R.id.tv_reg_login)).perform(click())

        // For example, you might want to check if certain UI elements are displayed or navigate to a different screen
        assertEquals(navController.currentDestination?.id, R.id.registerFragment)
        IdlingRegistry.getInstance().unregister(NetworkIdlingResource())
        networkIdlingResource.unregisterCallback()
    }
}