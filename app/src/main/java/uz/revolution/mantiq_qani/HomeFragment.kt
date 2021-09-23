package uz.revolution.mantiq_qani

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.revolution.mantiq_qani.dataBase.MySharedPreferences
import uz.revolution.mantiq_qani.dataBase.QuestionsData
import uz.revolution.mantiq_qani.databinding.FragmentHomeBinding
import uz.revolution.mantiq_qani.databinding.StartDialogBinding
import uz.revolution.mantiq_qani.dialogs.WinDialog
import uz.revolution.mantiq_qani.manager.MyManager
import uz.revolution.mantiq_qani.model.MyData
import uz.revolution.mantiq_qani.model.MyModel
import java.util.*

class HomeFragment : Fragment(), View.OnClickListener {

    lateinit var binding: FragmentHomeBinding
    private lateinit var myManager: MyManager
    private val questionsData = QuestionsData.getDatabase()
    private var arrayList: ArrayList<MyModel>? = null
    private var startName: String? = null
    private var timer: Timer? = null
    private var timercount = 1000
    private var timeShow: TextView? = null
    private var questionShow: TextView? = null
    private var format_time: String? = null
    private var hint: ImageButton? = null
    private var deleteLetter: ImageButton? = null
    private var currentScore: TextView? = null
    private var totalScore: TextView? = null
    private var level: TextView? = null
    private var variantGroup: ViewGroup? = null
    private var answerGroup: ViewGroup? = null
    private var totalCount = 0
    private var d = 1
    private var h = 1
    private val database = MySharedPreferences.getUsersDataList()

    private var isStarted = true


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        if (isStarted) {
            startDialog()
            isStarted = false
        }
        myManager = MyManager()
        loadData(myManager.level)
        loadView()
        loadDataToView()

        onInfoClick()
        onMenuClick()
        deleteLetter()
        hint()
        onAnswerClick()
        onVariantClick()

        return binding.root
    }

    private fun startDialog() {
        val view = StartDialogBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(binding.root.context)
        val alertDialog = dialog.create()

        view.startBtn.setOnClickListener {
            val name = view.editName.text.toString().trim()
            var userIndex = 0
            var isHave = false

            for (i in database.indices) {
                if (name.equals(database[i].name, ignoreCase = true)) {
                    startName = name
                    userIndex = i
                    isHave = true
                    break
                }
            }
            if (isHave) {
                myManager.totalScore = database[userIndex].score.toInt()
                myManager.level = database[userIndex].statusLevel!!.toInt()
                Log.d("AAAA", "onClick: " + database[userIndex].name)
                level!!.text = myManager.level.toString()
                totalScore!!.text = myManager.totalScore.toString()
                timercount = database[userIndex].timeCount
                timeShow()
                loadData(myManager.level)
                loadDataToView()
                alertDialog.cancel()
            } else {
                if (name.trim().isNotEmpty()) {
                    startName = name
                    alertDialog.cancel()
                    timeShow()
                    Toast.makeText(
                        binding.root.context,
                        "$name добро пожаловать в игру!",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        binding.root.context,
                        "$name Введите имя!",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

            }
        }
        alertDialog.setCancelable(false)
        alertDialog.setView(view.root)
        alertDialog.show()
    }

    fun timeShow() {
        timer = Timer()
        val timerTask: TimerTask = object : TimerTask() {
            override fun run() {
                timercount--
                var minute: Int
                val second: Int
                var hour: Int = timercount / 3600
                minute = (timercount - hour * 3600) / 60
                if (minute > 60) {
                    hour += minute / 60
                }
                second = timercount - 3600 * hour - 60 * minute
                if (second > 60) {
                    minute += second / 60
                }
                format_time = String.format("%02d:%02d:%02d", hour, minute, second)
                requireActivity().runOnUiThread { timeShow!!.text = format_time }
            }
        }
        timer!!.schedule(timerTask, 1000, 1000)
    }

    private fun loadView() {
        answerGroup = binding.answerGroup
        variantGroup = binding.variantGroup
        hint = binding.hint
        deleteLetter = binding.deleteLetter
        currentScore = binding.currentScore
        totalScore = binding.totalScore
        level = binding.level
        timeShow = binding.timeShow
        questionShow = binding.questionShow
    }

    private fun loadDataToView() {
        totalCount = 0
        questionShow!!.text = myManager.getQuestion()
        for (i in 0 until answerGroup!!.childCount) {
            val button = answerGroup!!.getChildAt(i) as Button
            button.text = ""
            if (i < myManager.getAnswerLength()) {
                button.visibility = View.VISIBLE
            } else {
                button.visibility = View.GONE
            }
        }
        for (i in 0 until variantGroup!!.childCount) {
            val button = variantGroup!!.getChildAt(i) as Button
            button.visibility = View.VISIBLE
            button.text = myManager.currentQuestion.variant?.get(i).toString()
            button.tag = i.toString()
        }
    }

    fun hint() {
        binding.hint.setOnClickListener {
            h.toString().toInt()
            while (h <= 2) {
                for (position in 0 until answerGroup!!.childCount) {
                    val answer = answerGroup!!.getChildAt(position) as Button
                    if (answer.text.toString() == "" && answer.visibility == View.VISIBLE) {
                        answer.text = arrayList!![myManager.level].answer[position].toString()
                        variantGroup!!.getChildAt(findLetter(position)).visibility = View.INVISIBLE
                        myManager.currentScore = myManager.currentScore - 2
                        currentScore!!.text = myManager.currentScore.toString()
                        answer.tag = variantGroup!!.getChildAt(myManager.level).tag.toString()
                        totalCount++
                        break
                    }
                }
                if (totalCount == myManager.getAnswerLength()) {
                    checkWin()
                }
                totalScore!!.text = myManager.totalScore.toString()
                h++
                break
            }
        }
    }

    fun deleteLetter() {
        binding.deleteLetter.setOnClickListener {
            d.toString().toInt()
            while (d <= 2) {
                for (position in 0 until variantGroup!!.childCount) {
                    val btn = variantGroup!!.getChildAt(position) as Button
                    if (!btn.text.toString().equals("", ignoreCase = true)) {
                        variantGroup!!.getChildAt(findWrongLetter(position)).visibility =
                            View.INVISIBLE
                        myManager.currentScore = myManager.currentScore - 2
                        currentScore!!.text = myManager.currentScore.toString()
                        break
                    }
                }
                d++
                break
            }
        }
    }

    fun findWrongLetter(position: Int): Int {
        var a = 0
        for (i in 0 until variantGroup!!.childCount) {
            val btn = variantGroup!!.getChildAt(i) as Button
            if (btn.visibility == View.VISIBLE && !arrayList!![myManager.level].answer.contains(
                    btn.text.toString()
                )
            ) {
                a = i
                break
            }
        }
        return a
    }

    fun findLetter(position: Int): Int {
        var a = 0
        for (i in 0 until variantGroup!!.childCount) {
            val btn = variantGroup!!.getChildAt(i) as Button
            if (btn.visibility == View.VISIBLE && btn.text.toString() == arrayList!![myManager.level].answer[position].toString() + "") {
                a = i
            }
        }
        return a
    }

    private fun undoLetter(letter: String) {
        for (i in 0 until variantGroup!!.childCount) {
            val btn = variantGroup!!.getChildAt(i) as Button
            if (btn.visibility == View.INVISIBLE && btn.text.toString() == letter) {
                btn.visibility = View.VISIBLE
                break
            }
        }
    }

    private fun checkWin() {
        var s = ""
        for (i in 0 until answerGroup!!.childCount) {
            val button = answerGroup!!.getChildAt(i) as Button
            s += button.text.toString()
        }
        if (myManager.checkAnswer(s)) {
            level!!.text = (myManager.level + 1).toString()
            currentScore!!.text = myManager.currentScore.toString()
            totalScore!!.text = myManager.totalScore.toString()
            //            loadData();
            if (!myManager.isEnd()) {
                loadDataToView()
                d = 1
                h = 1
            } else {
                level!!.text = myManager.level.toString()
                val winDialog = WinDialog(binding.root.context)
                winDialog.setText(myManager.totalScore, timeShow!!.text.toString())
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    winDialog.setOnPositiveClickListener(object :
                        WinDialog.OnPositiveClickListener {
                        override fun onClick(dialog: WinDialog) {
                            var isHave = false
                            val myData = MyData(
                                startName!!,
                                myManager.totalScore.toString(),
                                timeShow!!.text.toString(),
                                timercount,
                                myManager.level.toString()
                            )
                            for (i in 0 until database.size) {
                                if (database[i].name == startName) {
                                    isHave = true
                                }
                            }
                            if (isHave) {
                                MySharedPreferences.editUserData(myData)
                            } else MySharedPreferences.insertUser(myData)
                            loadData(myManager.level + 1)
                            level!!.text = (myManager.level + 1).toString()
                            currentScore!!.text = myManager.currentScore.toString()
                            totalScore!!.text = myManager.totalScore.toString()
                            loadDataToView()
                            d = 1
                            h = 1
                            dialog.cancel()
                        }
                    })
                        .setOnPlayAgainClickListener(object : WinDialog.OnPlayAgainClickListener {
                            override fun onClick(dialog: WinDialog) {
                                myManager.level = myManager.level - 4
                                var index = -1
                                for (i in 0 until database.size) {
                                    if (database[i].name == startName) {
                                        index = i
                                    }
                                }
                                myManager.totalScore = database[index].score.toInt()
                                totalScore!!.text = myManager.totalScore.toString()
                                loadData(myManager.level)
                                level!!.text = myManager.level.toString()
                                loadDataToView()
                                timeShow()
                                d = 1
                                h = 1
                                dialog.cancel()
                            }
                        })
                        .build()
                }
            }
        } else {
            Toast.makeText(binding.root.context, "Lose", Toast.LENGTH_SHORT).show()
        }
    }

    fun onInfoClick() {
        binding.infoBtn.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(
                "comment", """
     ${myManager.currentQuestion.source}
     
     ${myManager.currentQuestion.author}
     """.trimIndent()
            )
            findNavController().navigate(R.id.infoFragment, bundle)
        }
    }

    fun onMenuClick() {
        binding.menu.setOnClickListener {
            findNavController().navigate(R.id.menuFragment)
        }
    }

    private fun loadData(level: Int) {
        var level = level
        level = myManager.level
        arrayList = ArrayList()
        val random = Random()
        val strings = ArrayList<String>()
        var data = ArrayList<MyModel>()
        data = questionsData!!.getData(level)
        val s = "ёйфячыцувсмакепитрнгоьблшщдюжэзхъ"
        for (i in data.indices) {
            strings.add(data[i].answer)
        }
        for (i in strings.indices) {
            var array = strings[i]
            while (array.length < 20) {
                array += s[random.nextInt(s.length)].toString()
            }
            val characters: MutableList<Char> = ArrayList()
            for (c in array.toCharArray()) {
                characters.add(c)
            }
            val output = StringBuilder(array.length)
            while (characters.size != 0) {
                val randPicker = (Math.random() * characters.size).toInt()
                output.append(characters.removeAt(randPicker))
            }
            strings[i] = output.toString()
        }
        for (i in data.indices) {
            data[i].variant = strings[i]
        }
        if (questionsData != null) {
            for (i in questionsData.getData(level).indices) {
                arrayList!!.add(
                    MyModel(
                        data[i].variant?.trim(),
                        questionsData.getData(level)[i].question.trim(),
                        questionsData.getData(level)[i].answer.trim(),
                        questionsData.getData(level)[i].comment,
                        questionsData.getData(level)[i].source,
                        questionsData.getData(level)[i].author
                    )
                )
            }
        }
        myManager.setQuestions(arrayList)
    }

    override fun onClick(view: View?) {
        val constraintLayout = view?.parent as ConstraintLayout
        val parentID = constraintLayout.id

        when (parentID) {
            R.id.answer_group -> {
                val button = view as Button
                undoLetter(button.text.toString())
                for (i in 0 until variantGroup!!.childCount) {
                    val variant = variantGroup!!.getChildAt(i) as Button
                    if (variant.tag.toString() == button.tag.toString()) {
                        button.tag = ""
                        button.text = ""
                        variant.visibility = View.VISIBLE
                        totalCount--
                    }
                }
            }
            R.id.variant_group -> {
                val button = view as Button
                for (i in 0 until answerGroup!!.childCount) {
                    val answer = answerGroup!!.getChildAt(i) as Button
                    if (answer.text.toString() == "" && answer.visibility == View.VISIBLE) {
                        answer.text = button.text.toString()
                        button.visibility = View.INVISIBLE
                        answer.tag = button.tag.toString()
                        totalCount++
                        break
                    }
                }
                Log.i("AAAA", "OnVariantClick: $totalCount")
                if (totalCount == myManager.getAnswerLength()) {
                    checkWin()
                }
            }
        }

    }

    private fun onVariantClick() {
        binding.btn1.setOnClickListener(this)
        binding.btn2.setOnClickListener(this)
        binding.btn3.setOnClickListener(this)
        binding.btn4.setOnClickListener(this)
        binding.btn5.setOnClickListener(this)
        binding.btn6.setOnClickListener(this)
        binding.btn7.setOnClickListener(this)
        binding.btn8.setOnClickListener(this)
        binding.btn9.setOnClickListener(this)
        binding.btn10.setOnClickListener(this)
        binding.btn11.setOnClickListener(this)
        binding.btn12.setOnClickListener(this)
        binding.btn13.setOnClickListener(this)
        binding.btn14.setOnClickListener(this)
        binding.btn15.setOnClickListener(this)
        binding.btn16.setOnClickListener(this)
        binding.btn17.setOnClickListener(this)
        binding.btn18.setOnClickListener(this)
        binding.btn19.setOnClickListener(this)
        binding.btn20.setOnClickListener(this)
    }

    private fun onAnswerClick() {
        binding.answer1.setOnClickListener(this)
        binding.answer2.setOnClickListener(this)
        binding.answer3.setOnClickListener(this)
        binding.answer4.setOnClickListener(this)
        binding.answer5.setOnClickListener(this)
        binding.answer6.setOnClickListener(this)
        binding.answer7.setOnClickListener(this)
        binding.answer8.setOnClickListener(this)
        binding.answer9.setOnClickListener(this)
        binding.answer10.setOnClickListener(this)
        binding.answer11.setOnClickListener(this)
        binding.answer12.setOnClickListener(this)
        binding.answer13.setOnClickListener(this)
        binding.answer14.setOnClickListener(this)
        binding.answer15.setOnClickListener(this)
        binding.answer16.setOnClickListener(this)
    }

}