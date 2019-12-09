package com.example.barangayinformationsystem

public class ClearanceModel
{

    var username: String = ""
    var user_id: String   = ""
    var attachment_id: String  = ""
    var expiration_date: String  = ""
    var government_id: String   = ""
    var reason: String   = ""
    var status: String  = ""
    constructor() : super() {}

    constructor(username: String,
                user_id: String,
                attachment_id: String,
                expiration_date: String,
                government_id: String,
                reason: String,
                status: String) {
        this.username = username
        this.user_id = user_id
        this.attachment_id = attachment_id
        this.expiration_date = expiration_date
        this.government_id = government_id
        this.reason = reason
        this.status = status



    }
}
