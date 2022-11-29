package com.example.wordsapp

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import junit.framework.Assert.assertEquals
import org.junit.Test


class NavigationTests {
    @Test
    fun navigate_to_words_nav_component(){

        //Create instance of test controller
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        //Launch LetterListFragment, must specify theme or test may crash
        val letterListScenario = launchFragmentInContainer<LetterListFragment>(themeResId =
        R.style.Theme_Words)

        //Declare navigation graph for nav controller to use for the launched fragment
        letterListScenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)

            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        //Trigger event to prompt navigation
        onView(withId(R.id.recycler_view))
            .perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click()))

        assertEquals(navController.currentDestination?.id, R.id.wordListFragment)

    }
}