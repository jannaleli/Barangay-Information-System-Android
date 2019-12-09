package com.example.barangayinformationsystem

class ComplaintModel {
    var complaint_id: String = ""
    var attachment_id: String = ""
    var complaint_desc: String = ""
    var latitude: String = ""
    var longitude: String = ""
    var type: String = ""
    var user_id: String = ""
    var status: String = ""
    var create_date: String = ""

    constructor() : super() {}

    constructor(complaint_id: String,
                attachment_id: String,
                complaint_desc: String,
                latitude: String,
                longitude: String,
                type: String,
                user_id: String,
                create_date: String) {
        this.complaint_id = complaint_id
        this.attachment_id = attachment_id
        this.complaint_desc = complaint_desc
        this.latitude = latitude
        this.longitude = longitude
        this.type = type
        this.user_id = user_id
        this.create_date = create_date
        this.attachment_id = attachment_id



    }
}