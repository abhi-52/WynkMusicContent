package com.example.wynkbasic.content.db.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity

@Entity(primaryKeys = [ "collection_id", "item_id" ])
class Collection(@ColumnInfo(name = "collection_id")
                 var id: String,

                 @ColumnInfo(name = "item_id")
                 var itemId: String,

                 @ColumnInfo(name = "collection_title")
                 var title: String,

                 var rank: Int)