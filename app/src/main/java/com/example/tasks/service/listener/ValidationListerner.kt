package com.example.tasks.service.listener

class ValidationListerner(str: String = "") {

    private var mStatus: Boolean = true
    private var mMenssager = ""

    init {
        if (str.isNotEmpty()){
             mStatus = false
            mMenssager = str
        }

       }
    fun success ()= mStatus
    fun failure() = mMenssager

}