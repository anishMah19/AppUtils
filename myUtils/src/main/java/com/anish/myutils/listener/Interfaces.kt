package com.anish.myutils.listener


import android.app.AlertDialog
import android.view.View
import java.io.File


interface AlertInterfaces {
    fun onViewCreated(view: View, dialog: AlertDialog)
}

interface BottomSheetInterface {
    fun onViewCreated(view: View)
    fun onClicked()
}

interface NoteInterface {
    fun onDone(note: String)
}

interface PinViewActionListener {
    fun onValid()
}

interface SimpleClick {
    fun onClicked()
}

interface FlightListener {
    fun onClicked(flightNo: String)
}

interface MenuClickListener {
    fun onMenuClicked(name: String)
}

interface FAQClickListener {
    fun onClicked(name: String, id: String)
}

interface OnDialogClicked {
    fun onClicked(id: Int)
}


interface ExtrasInterface {
    fun addData(id: String, value: String)

}

interface DatePickedListener {
    fun selectedDate(date: String)
}


interface TimePickedListener {
    fun selectedDate(timeFormatted: String, time24: String)
}

interface RefreshMap {
    fun refresh()
}



interface ShortcutClickListener {
    fun onClicked(title: String)
}


interface GetFileListener {
    fun onFileCreated(downloadFile: File?)
}

interface DateTimeInterface {
    fun date(date: String)
    fun time(time: String)
}

interface FirebaseOTPListener {
    fun onError(error: String)
    fun onSuccess(uid: String)
}



interface DateListener {
    fun value(date: String)
}


interface SliderListener {
    fun pressed(position: Int)
    fun onSkip()
}


interface AnyApiListener {
    fun onError(title: String?, message: String?)
    fun onLoading()
    fun onSuccess(data: Any?)
}

interface AdapterPositionListener {
    fun position(pos: Int)
}
