package uz.revolution.mantiq_qani.manager

import uz.revolution.mantiq_qani.model.MyModel
import java.util.*

class MyManager {
    private var questions: ArrayList<MyModel>? = null
    var level = 0
    var totalScore = 0
    private val deltaScore = 2
    private val maxScore = 10
    var currentScore = maxScore
    fun setQuestions(questions: ArrayList<MyModel>?) {
        this.questions = questions
    }

    val currentQuestion: MyModel
        get() = questions!![level]

    fun getAnswerLength(): Int {
        return currentQuestion.answer.length
    }

    private fun getAnswer(): String {
        return currentQuestion.answer
    }

    fun getQuestion(): String {
        return currentQuestion.question
    }

    fun checkAnswer(userAnswer: String): Boolean {
        return if (userAnswer.equals(
                getAnswer().trim { it <= ' ' }.replace(" ".toRegex(), ""),
                ignoreCase = true
            )
        ) {
            totalScore += currentScore
            currentScore = maxScore
            level++
            true
        } else {
            if (currentScore > deltaScore) {
                currentScore -= deltaScore
            }
            false
        }
    }

    fun isEnd(): Boolean {
        return level >= questions!!.size
    }

    operator fun hasNext(): Boolean {
        return level < questions!!.size
    }
}