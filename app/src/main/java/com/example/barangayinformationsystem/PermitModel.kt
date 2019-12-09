package com.example.barangayinformationsystem

public class PermitModel
 {
     var username: String = ""
     var user_id: String = ""
     var attachment_id: String = ""
     var business_activity: String = ""
     var business_building_no: String = ""
     var business_name: String = ""
     var business_street: String = ""
     var capitalization: String = ""
     var ctc_no: String = ""
     var gross_sale: String = ""
     var lessor_bldg_no: String = ""
     var lessor_city: String = ""
     var lessor_emailaddr: String = ""
     var lessor_name: String = ""
     var lessor_province: String = ""
     var lessor_street: String = ""
     var lessor_subdv: String = ""
     var monthly_rental: String = ""
     var sec_no: String = ""
     var approval_date: String = ""
     var lessor_barangay: String = ""
     var status: String = ""

     constructor() : super() {}

     constructor(username: String,
                 user_id: String,
                 attachment_id: String,
                 business_activity: String,
                 business_building_no: String,
                 business_name: String,
                 business_street: String,
                 capitalization: String,
                 ctc_no: String,
                 gross_sale: String,
                 lessor_bldg_no: String,
                 lessor_city: String,
                 lessor_emailaddr: String,
                 lessor_barangay: String,
                 lessor_name: String,
                 lessor_province: String,
                 lessor_street: String,
                 lessor_subdv: String,
                 monthly_rental: String,
                 sec_no: String,
                 approval_date: String,
                 status: String) {
         this.username = username
         this.user_id = user_id
         this.attachment_id = attachment_id
         this.business_activity = business_activity
         this.business_building_no = business_building_no
         this.business_name = business_name
         this.business_street = business_street
         this.capitalization = capitalization
         this.attachment_id = attachment_id
         this.ctc_no = ctc_no
         this.gross_sale = gross_sale
         this.lessor_bldg_no = lessor_bldg_no
         this.lessor_city = lessor_city
         this.lessor_emailaddr = lessor_emailaddr
         this.lessor_name = lessor_name
         this.lessor_province = lessor_province
         this.lessor_street = lessor_street
         this.lessor_subdv = lessor_subdv
         this.monthly_rental = monthly_rental
         this.sec_no = sec_no
         this.approval_date = approval_date
         this.lessor_barangay = lessor_barangay
         this.status = status


     }
}