package com.example.wynkbasic.content.db.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.Index

@Entity(
    primaryKeys = ["parent_id", "child_id"],
    indices = [Index("parent_id"), Index("child_id")],
    foreignKeys = [ForeignKey(
        entity = Item::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("parent_id")
    ), ForeignKey(
        entity = Item::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("child_id")
    )]
)
class ItemRelation(
    @ColumnInfo(name = "parent_id")
    var parentId: String,

    @ColumnInfo(name = "child_id")
    var childId: String,

    @ColumnInfo(name = "parent_title")
    var title: String,

    var rank: Int
)