package models

/**
  * Created by knoldus on 7/3/17.
  */
case class User(name: String, fname:String, mname:String, lname:String,
                password: String, repassword:String, mobile:String, gender:String,
                age: Int, singing: Boolean, dancing: Boolean, reading: Boolean, sports: Boolean, isAdmin:Boolean, isEnabled:Boolean )
