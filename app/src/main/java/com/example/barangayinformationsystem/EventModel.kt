package com.example.barangayinformationsystem

public class EventModel {
    var event_id: String = ""
    var attachment_id: String = ""
    var end_date: String = ""
    var event_desc: String = ""
    var event_title: String = ""
    var start_date: String = ""
    var type: String = ""
    constructor() : super() {}

    constructor(event_id: String,
                attachment_id: String,
                end_date: String,
                event_desc: String,
                event_title: String,
                start_date: String,
                type: String) {
        this.event_id = event_id
        this.attachment_id = attachment_id
        this.end_date = end_date
        this.event_desc = event_desc
        this.event_title = event_title
        this.start_date = start_date
        this.type = type



    }

}