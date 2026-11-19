package fan.app.state

import android.app.Activity
import android.app.Application
import android.os.Bundle

class FanAppState(private val app: Application) {
    private var started = 0
    private val _activities = mutableListOf<Activity>()
    val activities: List<Activity>
        get() = _activities.toList()

    var onFg: (() -> Unit)? = null
    var onBg: (() -> Unit)? = null

    init {
        reg()
    }

    private fun reg() {
        app.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityStarted(activity: Activity) {
                started++
                if (started == 1) {
                    onFg?.invoke()
                }
            }

            override fun onActivityStopped(activity: Activity) {
                if (started == 0) return
                started--
                if (started == 0) {
                    onBg?.invoke()
                }
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                _activities.add(activity)
            }

            override fun onActivityDestroyed(activity: Activity) {
                _activities.remove(activity)
            }

            override fun onActivityResumed(activity: Activity) {}
            override fun onActivityPaused(activity: Activity) {}
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
        })
    }
}
