package com.belomor.attractgrouptestapp.models

import android.os.Parcel
import android.os.Parcelable

data class AttractGroupModel(
    val name : String,
    val date : Long,
    val info : String,
    val image : String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readLong(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeLong(date)
        parcel.writeString(info)
        parcel.writeString(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AttractGroupModel> {
        override fun createFromParcel(parcel: Parcel): AttractGroupModel {
            return AttractGroupModel(parcel)
        }

        override fun newArray(size: Int): Array<AttractGroupModel?> {
            return arrayOfNulls(size)
        }
    }
}
