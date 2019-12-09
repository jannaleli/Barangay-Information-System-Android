package com.example.barangayinformationsystem

import com.fasterxml.jackson.databind.annotation.JsonDeserialize



class ResidentModel {
    var user_id: String = ""
    var address: String = ""
    var birthdate: String = ""
    var createdate: String = ""
    var firstname: String = ""
    var lastname : String = ""
    var mobilenumber: String = ""
    var zipcode: String = ""
    var attachment_id: String = ""
    var civil_status: String = ""
    var ctc_no: String = ""
    var tin_no: String = ""
    var place_of_birth: String = ""
    var height: String = ""
    var weight: String = ""
    var control_no: String = ""
    var contact_no: String = ""
    var profession: String = ""
    var gross_income: String = ""
    var username: String = ""
    var password: String = ""
    var gender: String = ""
    var status: String = ""

    constructor() : super() {}

    constructor(user_id: String,
                address: String,
                birthdate: String,
                createdate: String,
                firstname: String,
                lastname: String,
                mobilenumber: String,
                zipcode: String,
                attachment_id: String,
                civil_status: String,
                ctc_no: String,
                tin_no: String,
                place_of_birth: String,
                height: String,
                weight: String,
                control_no: String,
                profession: String,
                gross_income: String,
                username: String,
                password: String,
                gender: String,
                status: String) {
        this.user_id = user_id
        this.address = address
        this.birthdate = birthdate
        this.createdate = createdate
        this.firstname = firstname
        this.lastname = lastname
        this.mobilenumber = mobilenumber
        this.zipcode = zipcode
        this.attachment_id = attachment_id
        this.civil_status = civil_status
        this.ctc_no = ctc_no
        this.tin_no = tin_no
        this.place_of_birth = place_of_birth
        this.height = height
        this.weight = weight
        this.control_no = control_no
        this.profession = profession
        this.gross_income = gross_income
        this.username = username
        this.password = password
        this.gender = gender
        this.status = status


    }
}