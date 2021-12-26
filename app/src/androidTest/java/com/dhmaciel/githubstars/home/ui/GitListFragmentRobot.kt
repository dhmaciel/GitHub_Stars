package com.dhmaciel.githubstars.home.ui

import androidx.test.core.app.launchActivity

class GitListFragmentRobot {

    infix fun checkIf(func: GitListFragmentRobotResult.() -> Unit) =
        GitListFragmentRobotResult().apply(func)

}

class GitListFragmentRobotResult {

}

internal fun onLaunch(
    func: GitListFragmentRobot.() -> Unit = {}
): GitListFragmentRobot {
    launchActivity<MainActivity>()
    return GitListFragmentRobot().apply(func)
}