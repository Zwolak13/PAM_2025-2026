package com.a160131_zwolak_dawid.components.Navigation

object Routes {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val DASHBOARD = "home"
    const val USER_BMI = "user_bmi?height={height}&weight={weight}"

    fun userBmi(height: String, weight: String): String {
        return "user_bmi?height=$height&weight=$weight"
    }
}
