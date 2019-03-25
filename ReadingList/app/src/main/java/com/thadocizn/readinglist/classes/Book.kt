package com.thadocizn.readinglist.classes

import java.io.Serializable

class Book : Serializable {
    var title: String? = null
    var reasonToRead: String? = null
    var id: String? = null
    var isHasBeenRead: Boolean = false

    fun toCsvString(): String {
        return String.format("%s,%s,%s,%b".replaceAll(" ", ","), this.title, this.reasonToRead, this.id, this.isHasBeenRead)
    }

    constructor(csv: String) {
        val csvs = csv.split(",")
        this.title = csvs[0]
        this.reasonToRead = csvs[1]
        this.id = csvs[2]
        this.isHasBeenRead = Boolean.parseBoolean(csvs[3])
    }

    constructor(title: String, reasonToRead: String, id: String, hasBeenRead: Boolean) {

        this.title = title
        this.reasonToRead = reasonToRead
        this.id = id
        this.isHasBeenRead = hasBeenRead
    }

}
