package com.example.wynkbasic.content.db.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity

@Entity(primaryKeys = ["parent_id", "child_id"])
class ItemRelation(@ColumnInfo(name = "parent_id")
                   var id: String,

                   @ColumnInfo(name = "child_id")
                   var itemId: String,

                   @ColumnInfo(name = "parent_title")
                   var title: String,

                   var rank: Int)