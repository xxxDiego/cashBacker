package com.astetech.omnifidelidade.models

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "cashback")
data class Cashback (
    @PrimaryKey val id: Int,
    @DrawableRes val image: Int?,
    @DrawableRes val imageUrl: String,
    @NonNull  val empresa: String,
    @NonNull  val loja: String,
    @NonNull val valor: String,
    @NonNull val valorCompra: String,
    @NonNull val valorUtilizado: String,
    @NonNull @ColumnInfo(name = "data_validade") val dataValidade: String,
    @NonNull @ColumnInfo(name = "data_compra") val dataCompra: String
) : Parcelable



