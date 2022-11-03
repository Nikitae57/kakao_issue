package ru.nikitae57.sandbox

import android.view.View
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test

class FirstScreenTest : TestCase() {

    @get:Rule
    val activityRule = activityScenarioRule<TestActivity>()

    @Test
    fun test() = before {
        activityRule.scenario.onActivity {
            it.setFragment(FirstFragment())
        }
    }.after {

    }.run {
        FirstScreen {
            image {
                val appContext = InstrumentationRegistry.getInstrumentation().context!!
                val expectedDrawable = appContext.resources.getDrawable(
                    R.drawable.ic_error,
                    appContext.theme
                )

                view.check(ViewAssertions.matches(DrawableMatcher(R.drawable.ic_error))) // this works
                hasDrawable(R.drawable.ic_error) // this works
                hasDrawable(expectedDrawable) // this doesn't 👺
            }
        }
    }
}

class DrawableMatcher(
    private val expectedResId: Int
) : TypeSafeMatcher<View>() {
    private var resourceName: String? = null

    override fun describeTo(description: Description?) {
        description?.appendText("with drawable from resource id: ")
        description?.appendValue(expectedResId)
    }

    override fun matchesSafely(item: View): Boolean {
        val resources = item.resources
        resourceName = resources.getResourceEntryName(expectedResId)
        val expectedDrawable = resources.getDrawable(expectedResId, item.context.theme)

        val expectedBitmap = expectedDrawable.toBitmap()
        val realBitmap = ((item as ImageView).drawable).toBitmap()
        return expectedBitmap.sameAs(realBitmap)
    }
}