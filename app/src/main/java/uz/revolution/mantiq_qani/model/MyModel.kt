package uz.revolution.mantiq_qani.model

class MyModel {
    private var _id = 0
    var variant: String? = null
    var question: String
    var answer: String
    var comment: String
    var source: String
    var author: String

    constructor(
        _id: Int,
        variant: String?,
        question: String,
        answer: String,
        comment: String,
        source: String,
        author: String
    ) {
        this._id = _id
        this.variant = variant
        this.question = question
        this.answer = answer
        this.comment = comment
        this.source = source
        this.author = author
    }

    constructor(
        variant: String?,
        question: String,
        answer: String,
        comment: String,
        source: String,
        author: String
    ) {
        this.variant = variant
        this.question = question
        this.answer = answer
        this.comment = comment
        this.source = source
        this.author = author
    }

    constructor(
        _id: Int,
        question: String,
        answer: String,
        comment: String,
        source: String,
        author: String
    ) {
        this._id = _id
        this.question = question
        this.answer = answer
        this.comment = comment
        this.source = source
        this.author = author
    }

    fun get_id(): Int {
        return _id
    }

    fun set_id(_id: Int) {
        this._id = _id
    }

    override fun toString(): String {
        return "MyModel{" +
                "_id=" + _id +
                ", variant='" + variant + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", comment='" + comment + '\'' +
                ", source='" + source + '\'' +
                ", author='" + author + '\'' +
                '}'
    }
}