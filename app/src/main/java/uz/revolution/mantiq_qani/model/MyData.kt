package uz.revolution.mantiq_qani.model

class MyData {
    var name: String
    var score: String
    var time: String
    var timeCount: Int
    var statusLevel: String? = null

    constructor(name: String, score: String, time: String, timeCount: Int, statusLevel: String?) {
        this.name = name
        this.score = score
        this.time = time
        this.timeCount = timeCount
        this.statusLevel = statusLevel
    }

    constructor(name: String, score: String, time: String, timeCount: Int) {
        this.name = name
        this.score = score
        this.time = time
        this.timeCount = timeCount
    }
}