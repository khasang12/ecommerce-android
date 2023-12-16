package com.example.ecommerce_app.fragments

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.bumptech.glide.manager.Lifecycle
import com.example.ecommerce_app.NetworkIdlingResource
import com.example.ecommerce_app.NetworkManager
import com.example.ecommerce_app.fragments.auth.LoginFragment
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.example.ecommerce_app.R

@RunWith(AndroidJUnit4::class)
class LoginFragmentTest :TestCase() {

    private lateinit var scenario: FragmentScenario<LoginFragment>
    private val networkIdlingResource = NetworkIdlingResource()

    @Before
    fun setup(){
        scenario = launchFragmentInContainer<LoginFragment>(themeResId = R.style.Theme_Ecommerceapp)
    }

    @Test
    fun testLoginFragmentUI() {
        IdlingRegistry.getInstance().register(NetworkIdlingResource())
        NetworkManager.getInstance().setNetworkIdleState(true)
        // Launch the fragment within the activity

        // Perform UI interactions using Espresso
        onView(withId(R.id.et_reg_email)).perform(typeText("sk@gmail.com"), closeSoftKeyboard())
        onView(withId(R.id.et_reg_password)).perform(typeText("123456"), closeSoftKeyboard())
        onView(withId(R.id.button_loginpage_login)).perform(click())

        // Add assertions based on your UI behavior
        // For example, you might want to check if certain UI elements are displayed or navigate to a different screen
        onView(withId(R.id.home_header)).check(matches(isDisplayed()))
        IdlingRegistry.getInstance().unregister(NetworkIdlingResource())
        networkIdlingResource.unregisterCallback()
    }
}