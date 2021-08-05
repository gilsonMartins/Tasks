package com.example.tasks.service.model

import com.google.gson.annotations.SerializedName

class TaskModel {

    @SerializedName("Id")
    var id = 1

    @SerializedName("PriorityId")
    var priorityId = 1

    @SerializedName("Description")
    var description = ""

    @SerializedName("DueDate")
    var dueDate = "1"

    @SerializedName("Complete")
    var Complete = false
}