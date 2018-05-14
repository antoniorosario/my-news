package com.example.antonio.mynews.utils

import android.content.Context
import com.example.antonio.mynews.R
import org.joda.time.DateTime
import org.joda.time.Duration
import org.joda.time.format.DateTimeFormat


//TODO This is okay for now, but find a better way.
/**
 * Returns the elapsed time between the present and publication time in minutes,
 * hours, days ,or a concrete date.
 */
fun DateTime.getElapsedPublicationTime(context: Context): String {


    // Create a new DateTime object that represents the current time.
    val currentTime = DateTime()

    // Create a duration object to represent the difference in time between the current time and  article publication date.
    val dur = Duration(this, currentTime)

    // Converts the duration between the current time and publication time from milliseconds to seconds
    val seconds = dur.standardSeconds
    // Converts the duration between the current time and publication time from milliseconds to minutes
    val minutes = dur.standardMinutes
    // Converts the duration between the current time and publication time from milliseconds to hours
    val hours = dur.standardHours
    // Converts the duration between the current time and publication time from milliseconds to days
    val days = dur.standardDays

    // if else statement returns either minutes, hours, days, or concrete dates depending on length of time elapsed
    // between current time and publication time.
    return when {
        minutes < 1 -> seconds.toString() + context.getString(R.string.seconds_elapsed_time)
        minutes <= 59 -> minutes.toString() + context.getString(R.string.minutes_elapsed_time)
        hours <= 23 -> hours.toString() + context.getString(R.string.hours_elapsed_time)
        days <= 3 -> days.toString() + context.getString(R.string.days_elapsed_time)
        else -> formatDate(this)
    }
}

/**
 * @param dateTimeObject is the dateTimeObject to be formatted
 * Returns the formatted date string (i.e. "10 December") from a DateTime object.
 */
private fun formatDate(dateTimeObject: DateTime): String {
    val formatter = DateTimeFormat.forPattern("d MMMMM")
    return dateTimeObject.toString(formatter)
}
