package com.anish.myutils.utils

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.TextView
import com.anish.myutils.R
import com.anish.myutils.listener.DatePickedListener
import com.anish.myutils.listener.DateTimeInterface
import com.anish.myutils.listener.TimePickedListener
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone


object CalenderPickerDialog {
    private var mYear = 0
    private var mDay = 0
    private var mMonth = 0
    fun showCalender(
        context: Context,
        textView: TextView,
        onDatePicked: DatePickedListener,
        minDate: Long = 0,
        maxDate: Long = 0
    ) {
        val c = Calendar.getInstance()
        mYear = c.get(Calendar.YEAR)
        mMonth = c.get(Calendar.MONTH)
        mDay = c.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(
            context,
            { _, year, p2, p3 ->
                val day = if (p3 < 10) {
                    "0$p3"
                } else {
                    p3.toString()
                }

                val month = when (p2) {

                    in 0..9 -> {
                        val date = p2 + 1
                        "0$date"
                    }

                    else -> {
                        val date = p2 + 1
                        "$date"
                    }
                }


                val dates = "$day/$month/$year"
                val apiDate = "$year-$month-$day"
                textView.text = dates
                onDatePicked.selectedDate(dates)
            },
            mYear,
            mMonth, mDay
        )

        datePickerDialog.show()

        if (minDate > 0) {
            val dates = datePickerDialog.datePicker
//            val currentDate = Calendar.getInstance().timeInMillis
            dates.minDate = minDate
        }
        if (maxDate > 0) {
            val dates = datePickerDialog.datePicker
//            val currentDate = Calendar.getInstance().timeInMillis
            dates.maxDate = maxDate
        }

    }

    fun getCurrentDate(): String {
        val currentTime = Calendar.getInstance().time
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        return sdf.format(currentTime)
    }

    fun getCurrentDatedmy(): String {
        val currentTime = Calendar.getInstance().time
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        return sdf.format(currentTime)
    }

    fun getCalculatedDateDay(dateFormat: String, days: Int): String {
        val cal = Calendar.getInstance()
        val s = SimpleDateFormat(dateFormat, Locale.ENGLISH)
        cal.add(Calendar.DAY_OF_YEAR, days)
        return s.format(Date(cal.timeInMillis))
    }

    fun getCalculatedDateDayInMillis(days: Int): Long {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, days)
        return cal.timeInMillis
    }

    fun getCalculatedDateCustomTime(date: String, dateFormat: String, days: Int): String {
        val msdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

        val dates = msdf.parse(date)!!
//        dates.time
        val cal = Calendar.getInstance()
        cal.time = dates
        val s = SimpleDateFormat(dateFormat, Locale.ENGLISH)
        cal.add(Calendar.DAY_OF_YEAR, days)
        return s.format(Date(cal.timeInMillis))
    }

    fun getCalculatedDateCustomTimeMillis(date: String): Long {
        val msdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

        val dates = msdf.parse(date)!!
//        dates.time
        val cal = Calendar.getInstance()
        cal.time = dates
        return cal.timeInMillis
    }

    fun getCalculatedDateCustomTimeMillis(date: String, days: Int): Long {
        val msdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

        val dates = msdf.parse(date)!!
//        dates.time
        val cal = Calendar.getInstance()
        cal.time = dates
        cal.add(Calendar.DAY_OF_YEAR, days)
        return cal.timeInMillis
    }

    fun getCalculatedDate(date: String, dateFormat: String, days: Int): String? {
        val cal = Calendar.getInstance()
        val s = SimpleDateFormat(dateFormat, Locale.ENGLISH)
        cal.add(Calendar.DAY_OF_YEAR, days)
        try {
            return s.format(Date(s.parse(date)!!.time))
        } catch (e: ParseException) {
            Log.e("TAG", "Error in Parsing Date : " + e.message)
        }
        return null
    }

    fun timePickerDialog(context: Context, timePickedListener: TimePickedListener) {
        val timePickerDialog = TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                // logic to properly handle
                // the picked timings by user
                val formattedTime: String = when {
                    hourOfDay == 0 -> {
                        if (minute < 10) {
                            "${hourOfDay + 12}:0${minute} AM"
                        } else {
                            "${hourOfDay + 12}:${minute} AM"
                        }
                    }

                    hourOfDay > 12 -> {
                        if ((hourOfDay - 12) < 12) {
                            if (minute < 10) {
                                "0${hourOfDay - 12}:0${minute} PM"
                            } else {
                                "0${hourOfDay - 12}:${minute} PM"
                            }
                        } else {
                            if (minute < 10) {
                                "${hourOfDay - 12}:0${minute} PM"
                            } else {
                                "${hourOfDay - 12}:${minute} PM"
                            }

                        }

                    }

                    hourOfDay == 12 -> {
                        if (minute < 10) {
                            "${hourOfDay}:0${minute} PM"
                        } else {
                            "${hourOfDay}:${minute} PM"
                        }
                    }

                    else -> {
                        if (hourOfDay < 10) {
                            if (minute < 10) {
                                "0${hourOfDay}:${minute} AM"
                            } else {
                                "0${hourOfDay}:${minute} AM"
                            }
                        } else {
                            if (minute < 10) {
                                "${hourOfDay}:${minute} AM"
                            } else {
                                "${hourOfDay}:${minute} AM"
                            }

                        }

                    }
                }
                val selectedTime = "$hourOfDay:$minute"
                timePickedListener.selectedDate(formattedTime, selectedTime)

                //                previewSelectedTimeTextView.text = formattedTime
            },
            getCurrentHour().toInt(), getCurrentMinute().toInt(),
            false
        )
        timePickerDialog.show()
    }

    fun timePickerDialog24(context: Context, timePickedListener: TimePickedListener) {
        val timePickerDialog = TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                // logic to properly handle
                // the picked timings by user
                val formattedTime: String = when {
                    hourOfDay == 0 -> {
                        if (minute < 10) {
                            "${hourOfDay + 12}:0${minute} AM"
                        } else {
                            "${hourOfDay + 12}:${minute} AM"
                        }
                    }

                    hourOfDay > 12 -> {
                        if ((hourOfDay - 12) < 12) {
                            if (minute < 10) {
                                "0${hourOfDay - 12}:0${minute} PM"
                            } else {
                                "0${hourOfDay - 12}:${minute} PM"
                            }
                        } else {
                            if (minute < 10) {
                                "${hourOfDay - 12}:0${minute} PM"
                            } else {
                                "${hourOfDay - 12}:${minute} PM"
                            }

                        }

                    }

                    hourOfDay == 12 -> {
                        if (minute < 10) {
                            "${hourOfDay}:0${minute} PM"
                        } else {
                            "${hourOfDay}:${minute} PM"
                        }
                    }

                    else -> {
                        if (hourOfDay < 10) {
                            if (minute < 10) {
                                "0${hourOfDay}:${minute} AM"
                            } else {
                                "0${hourOfDay}:${minute} AM"
                            }
                        } else {
                            if (minute < 10) {
                                "${hourOfDay}:${minute} AM"
                            } else {
                                "${hourOfDay}:${minute} AM"
                            }

                        }

                    }
                }
                var newHour = if (hourOfDay < 10) {
                    "0$hourOfDay"
                } else {
                    "$hourOfDay"
                }
                var newMin = if (minute < 10) {
                    "0$minute"
                } else {
                    "$minute"
                }
                val selectedTime = "$newHour:$newMin"
                timePickedListener.selectedDate(formattedTime, selectedTime)

                //                previewSelectedTimeTextView.text = formattedTime
            },
            getCurrentHour().toInt(), getCurrentMinute().toInt(),
            true
        )
        timePickerDialog.show()
    }

    fun getCurrentTime(): String {
        val currentTime = Calendar.getInstance().time
        val sdf = SimpleDateFormat("hh:mm aa", Locale.ENGLISH)
        return sdf.format(currentTime)
    }

    fun getCurrentHour(): String {
        val currentTime = Calendar.getInstance().time
        val sdf = SimpleDateFormat("HH", Locale.ENGLISH)
        return sdf.format(currentTime)
    }

    fun getCurrentMinute(): String {
        val currentTime = Calendar.getInstance().time
        val sdf = SimpleDateFormat("mm", Locale.ENGLISH)
        return sdf.format(currentTime)
    }

    fun getCurrentTime24Hrs(): String {
        val currentTime = Calendar.getInstance().time
        val sdf24 = SimpleDateFormat("HH:mm", Locale.ENGLISH)
        return sdf24.format(currentTime)
    }


    fun getCurrentYear(): String {
        val currentTime = Calendar.getInstance().time
        val sdf = SimpleDateFormat("yyyy", Locale.ENGLISH)
        return sdf.format(currentTime)
    }

    fun getCurrentMonth(): String {
        val currentTime = Calendar.getInstance().time
        val sdf = SimpleDateFormat("MM", Locale.ENGLISH)
        return sdf.format(currentTime)
    }

    fun getCurrentWeek(): String {
        val currentTime = Calendar.getInstance().time
        val sdf = SimpleDateFormat("EEEE", Locale.ENGLISH)
        return sdf.format(currentTime)
    }


    fun getCalculatedDateDayInMillis(dateFormat: String, days: Int): Long {
        val cal = Calendar.getInstance()
        val s = SimpleDateFormat(dateFormat, Locale.ENGLISH)
        cal.add(Calendar.DAY_OF_YEAR, days)
        return cal.timeInMillis
    }


    fun getCustomDateInMillis(dateFormat: String, date: String): Long {
        val msdf = SimpleDateFormat(dateFormat, Locale.ENGLISH)

        val dates = msdf.parse(date)!!
//        dates.time
        val cal = Calendar.getInstance()
        cal.time = dates
        return cal.timeInMillis
    }


    fun getCustomFormatDate(original: String, defaultTime: String?, formatTime: String): String {

        val sdf = SimpleDateFormat(original, Locale.ENGLISH)
        val convertedSDF = SimpleDateFormat(formatTime, Locale.ENGLISH)

        val date = sdf.parse(defaultTime!!)!!
        return convertedSDF.format(date)

    }

    fun getDateFormatDefault(defaultTime: String?): String {
        if (defaultTime == null) return ""
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val convertedSDF = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
        val date = sdf.parse(defaultTime)!!
        return convertedSDF.format(date)

    }

    fun checkValidDate(defaultTime: String?): String {
        if (defaultTime == null) return ""
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val convertedSDF = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val date = sdf.parse(defaultTime)!!
        return convertedSDF.format(date)

    }

    fun get24to12(defaultTime: String?): String {
        if (defaultTime == null) return ""
        val sdf = SimpleDateFormat("HH:mm", Locale.ENGLISH)
        val convertedSDF = SimpleDateFormat("hh:mm aa", Locale.ENGLISH)
        val date = sdf.parse(defaultTime)!!
        return convertedSDF.format(date)

    }

    fun timePickerDialog(
        context: Context,
        timePickedListener: TimePickedListener,
        selectedTime24: String = getCurrentTime24Hrs()
    ) {

        val spl = selectedTime24.split(":")
        var hours = spl[0].toInt()
        var minute = spl[1].toInt()
        val timePickerDialog = TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                // logic to properly handle
                // the picked timings by user
                val formattedTime: String = when {
                    hourOfDay == 0 -> {
                        if (minute < 10) {
                            "${hourOfDay + 12}:0${minute} AM"
                        } else {
                            "${hourOfDay + 12}:${minute} AM"
                        }
                    }

                    hourOfDay > 12 -> {
                        if ((hourOfDay - 12) < 10) {
                            if (minute < 10) {
                                "0${hourOfDay - 12}:0${minute} PM"
                            } else {
                                "0${hourOfDay - 12}:${minute} PM"
                            }
                        } else {
                            if (minute < 10) {
                                "${hourOfDay - 12}:0${minute} PM"
                            } else {
                                "${hourOfDay - 12}:${minute} PM"
                            }

                        }

                    }

                    hourOfDay == 12 -> {
                        if (minute < 10) {
                            "${hourOfDay}:0${minute} PM"
                        } else {
                            "${hourOfDay}:${minute} PM"
                        }
                    }

                    else -> {
                        if (hourOfDay < 10) {
                            if (minute < 10) {
                                "0${hourOfDay}:0${minute} AM"
                            } else {
                                "0${hourOfDay}:${minute} AM"
                            }
                        } else {
                            if (minute < 10) {
                                "${hourOfDay}:${minute} AM"
                            } else {
                                "${hourOfDay}:${minute} AM"
                            }

                        }

                    }
                }
//                val selectedTime = "$hourOfDay:$minute"
                var newHour = if (hourOfDay < 10) {
                    "0$hourOfDay"
                } else {
                    "$hourOfDay"
                }
                var newMin = if (minute < 10) {
                    "0$minute"
                } else {
                    "$minute"
                }
                val selectedTime = "$newHour:$newMin"
                timePickedListener.selectedDate(formattedTime, selectedTime)

                //                previewSelectedTimeTextView.text = formattedTime
            },
            hours, minute,
            false
        )
        timePickerDialog.show()
    }


    fun getCurrentDateInMillis(): Long = Calendar.getInstance().timeInMillis


    fun localToUTC(dateFormat: String?, datesToConvert: String?): String? {
        var dateToReturn = datesToConvert
        val sdf = SimpleDateFormat(dateFormat, Locale.ENGLISH)
        sdf.timeZone = TimeZone.getDefault()

        var gmt: Date? = null
        val sdfOutPutToSend = SimpleDateFormat(dateFormat, Locale.ENGLISH)
        sdfOutPutToSend.timeZone = TimeZone.getTimeZone("UTC")
        try {
            gmt = sdf.parse(datesToConvert!!)
            dateToReturn = sdfOutPutToSend.format(gmt!!)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return dateToReturn
    }

    fun uTCToLocal(
        dateFormatInPut: String?,
        dateFomratOutPut: String?,
        datesToConvert: String?
    ): String? {
        var dateToReturn = datesToConvert
        val sdf = SimpleDateFormat(dateFormatInPut, Locale.ENGLISH)
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        var gmt: Date? = null
        val sdfOutPutToSend = SimpleDateFormat(dateFomratOutPut, Locale.ENGLISH)
        sdfOutPutToSend.timeZone = TimeZone.getDefault()
        try {
            gmt = sdf.parse(datesToConvert!!)
            dateToReturn = sdfOutPutToSend.format(gmt!!)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return dateToReturn
    }


    private fun timeCalender(timeZoneId: String, dateTimeInterface: DateTimeInterface) {

// Define the time zone ID
//        val timeZoneId = id // Example: Los Angeles time zone

// Get the desired time zone
        val timeZone: TimeZone = TimeZone.getTimeZone(timeZoneId)

// Get the current date and time in the specified time zone
        val calendar: Calendar = Calendar.getInstance(timeZone)

// Retrieve individual components from the calendar
        val year: Int = calendar.get(Calendar.YEAR)
        val month: Int = calendar.get(Calendar.MONTH) + 1 // Months are 0-based, so adding 1
        val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
        val hour: Int = calendar.get(Calendar.HOUR_OF_DAY)
        val minute: Int = calendar.get(Calendar.MINUTE)
        val second: Int = calendar.get(Calendar.SECOND)

//        val calendar2: Calendar = Calendar.getInstance()
        /*  calendar2.set(Calendar.HOUR_OF_DAY, hour)
          calendar2.set(Calendar.MINUTE, minute)*/

        calendar.set(year, month, day, hour, minute)

// Add 6 hours to the Calendar
        calendar.add(Calendar.HOUR_OF_DAY, 6)
        calendar.add(Calendar.MINUTE, 10)

        // Get the updated hour and minute
//        val updatedHour: Int = calendar2.get(Calendar.HOUR_OF_DAY)
//        val updatedMinute: Int = calendar2.get(Calendar.MINUTE)
        val updatedYear: Int = calendar.get(Calendar.YEAR)
        val updatedMonth: Int = calendar.get(Calendar.MONTH)
        val updatedDayOfMonth: Int = calendar.get(Calendar.DAY_OF_MONTH)
        val updatedHour: Int = calendar.get(Calendar.HOUR_OF_DAY)
        val updatedMinute: Int = calendar.get(Calendar.MINUTE)
// Print the date and time in the specified time zone
//        println("time zone Date: $year-$month-$day")
//        println("time zone Time: $hour:$minute:$second")
//        println("time zone Updated Date: $updatedYear-$updatedMonth-$updatedDayOfMonth")
//        println("time zone Updated Time: $updatedHour:$updatedMinute:$second")

        val fDay = if (updatedDayOfMonth < 10) {
            "0$updatedDayOfMonth"
        } else {
            updatedDayOfMonth.toString()
        }

        val fMonth = when (updatedMonth) {

            in 1..9 -> {
                "0$updatedMonth"
            }

            else -> {
                "$updatedMonth"
            }
        }
        var newHour = if (updatedHour < 10) {
            "0$updatedHour"
        } else {
            "$updatedHour"
        }
        var newMin = if (updatedMinute < 10) {
            "0$updatedMinute"
        } else {
            "$updatedMinute"
        }

        /*
                println("time zone formatted Updated Date: $updatedYear-$fMonth-$fDay")
                println("time zone formatted Updated Time: $newHour:$newMin")*/


        val dates = "$updatedYear-$fMonth-$fDay"
        val time = "$newHour:$newMin"
        val validDate = checkValidDate(dates)
        dateTimeInterface.date(validDate)
        val tim_12 = get24to12(time)
        dateTimeInterface.time(tim_12)

    }


    fun getDateFormatToDefault(fromFormat: String, defaultTime: String?): String {
        if (defaultTime == null) return ""
        val sdf = SimpleDateFormat(fromFormat, Locale.ENGLISH)
        val convertedSDF = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val date = sdf.parse(defaultTime)!!
        return convertedSDF.format(date)

    }

    fun getDateFormatToYourFormat(
        fromFormat: String,
        defaultTime: String?,
        yourFormat: String
    ): String {
        if (defaultTime == null) return ""
        val sdf = SimpleDateFormat(fromFormat, Locale.ENGLISH)
        val convertedSDF = SimpleDateFormat(yourFormat, Locale.ENGLISH)
        val date = sdf.parse(defaultTime)!!
        return convertedSDF.format(date)

    }

    fun get12to14(defaultTime: String?): String {
        if (defaultTime == null) return ""
        val sdf = SimpleDateFormat("hh:mm aa", Locale.ENGLISH)
        val convertedSDF = SimpleDateFormat("HH:mm", Locale.ENGLISH)
        val date = sdf.parse(defaultTime)!!
        return convertedSDF.format(date)

    }


    fun getCurrentTimeSecond(): String {
        val currentTime = Calendar.getInstance().time
        val sdf = SimpleDateFormat("hh:mm:ss aa", Locale.ENGLISH)
        return sdf.format(currentTime)
    }

}