package com.example.coursework

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule


class NewGame : AppCompatActivity() {

    private var displayArithmetic = ArrayList<String>()          //arraylist to store the strings to display the expression
    private var sumArithmetic = ArrayList<Double>()              //arraylist to store the final sum values
    private val arithmeticOperations = listOf("", " + ", " - ", " * ", " / ")
    private var correctAnswers = 0
    private var wrongAnswers = 0
    private var correctAnswersStreak = 0

    private lateinit var correctAnswersLabel: TextView
    private lateinit var wrongAnswersLabel: TextView
    private lateinit var questionLayout: ConstraintLayout
    private lateinit var resultLayout: ConstraintLayout

    private lateinit var timeLabel: TextView
    private var time: Long = 50000
    private lateinit var timer: CountDownTimer

    private lateinit var lhsAnswerBox: TextView
    private lateinit var rhsAnswerBox: TextView
    private lateinit var displaySolutionResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_game)

        timeLabel = findViewById(R.id.textView8)
        questionLayout = findViewById(R.id.questionView)      //layout to view the expression and buttons
        resultLayout = findViewById(R.id.resultsView)        //layout to view the result
        correctAnswersLabel = findViewById(R.id.textView10)
        wrongAnswersLabel = findViewById(R.id.textView11)

        lhsAnswerBox = findViewById(R.id.textView4)       //left hand side text view
        rhsAnswerBox = findViewById(R.id.textView6)       //right hand side text view

        if (savedInstanceState != null) {
            correctAnswersStreak = savedInstanceState.getInt("CorrectAnswersStreak")
            correctAnswers = savedInstanceState.getInt("CorrectAnswers")
            wrongAnswers = savedInstanceState.getInt("WrongAnswers")
            time = savedInstanceState.getLong("Time")
            displayArithmetic.add(savedInstanceState.getString("DisplayArithmeticOne").toString())
            displayArithmetic.add(savedInstanceState.getString("DisplayArithmeticTwo").toString())
            sumArithmetic.add(savedInstanceState.getDouble("SumArithmeticOne"))
            sumArithmetic.add(savedInstanceState.getDouble("SumArithmeticTwo"))

            lhsAnswerBox.text = displayArithmetic[0]
            rhsAnswerBox.text = displayArithmetic[1]
            countDown()
            colorStreakBox()
        }
        else{
            startGame()
            countDown()
        }

        val greaterButton = findViewById<Button>(R.id.button3)  //greater than button
        val equalButton = findViewById<Button>(R.id.button2)    //equals button
        val lessButton = findViewById<Button>(R.id.button)      //less than button

        greaterButton.setOnClickListener {
            checkAnswer(1)
        }
        equalButton.setOnClickListener {
            checkAnswer(2)
        }
        lessButton.setOnClickListener {
            checkAnswer(3)
        }
    }

    /**
     * on save instance to save the key/necessary values when activity is destroyed
     */
    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putInt("CorrectAnswersStreak", correctAnswersStreak)
        savedInstanceState.putInt("CorrectAnswers", correctAnswers)
        savedInstanceState.putInt("WrongAnswers", wrongAnswers)
        savedInstanceState.putLong("Time", time)
        savedInstanceState.putString("DisplayArithmeticOne", displayArithmetic[0])
        savedInstanceState.putString("DisplayArithmeticTwo", displayArithmetic[1])
        savedInstanceState.putDouble("SumArithmeticOne", sumArithmetic[0])
        savedInstanceState.putDouble("SumArithmeticTwo", sumArithmetic[1])
    }
    /**
     * method to set the count down
     */
    private fun countDown(){
        timer = object : CountDownTimer(time , 1) {
            override fun onTick(millisUntilFinished: Long) {
                timeLabel.text = (millisUntilFinished / 1000).toString()
                time = millisUntilFinished
            }

            override fun onFinish() {
                questionLayout.isVisible = false
                resultLayout.isVisible = true
                correctAnswersLabel.text = correctAnswers.toString()
                wrongAnswersLabel.text = wrongAnswers.toString()
            }
        }.start()
    }

    /**
     * method to update the progress on the correct answers streak
     */
    private fun colorStreakBox(){
        val box1 = findViewById<TextView>(R.id.decoCorrectStreak1)  //five boxes to represent each correct answer in the streak
        val box2 = findViewById<TextView>(R.id.decoCorrectStreak2)
        val box3 = findViewById<TextView>(R.id.decoCorrectStreak3)
        val box4 = findViewById<TextView>(R.id.decoCorrectStreak4)
        val box5 = findViewById<TextView>(R.id.decoCorrectStreak5)

        if (correctAnswersStreak == 1){
            box1.setBackgroundColor(Color.GREEN)
        }
        if (correctAnswersStreak == 2){
            box1.setBackgroundColor(Color.GREEN)
            box2.setBackgroundColor(Color.GREEN)
        }
        if (correctAnswersStreak == 3){
            box1.setBackgroundColor(Color.GREEN)
            box2.setBackgroundColor(Color.GREEN)
            box3.setBackgroundColor(Color.GREEN)
        }
        if (correctAnswersStreak == 4){
            box1.setBackgroundColor(Color.GREEN)
            box2.setBackgroundColor(Color.GREEN)
            box3.setBackgroundColor(Color.GREEN)
            box4.setBackgroundColor(Color.GREEN)
        }
        if (correctAnswersStreak == 5){
            box1.setBackgroundColor(Color.GREEN)
            box2.setBackgroundColor(Color.GREEN)
            box3.setBackgroundColor(Color.GREEN)
            box5.setBackgroundColor(Color.GREEN)
            Timer().schedule(1000) {
                box5.setBackgroundColor(Color.WHITE)            //once the streak is reached all the boxes are reset to default color
                box4.setBackgroundColor(Color.WHITE)
                box3.setBackgroundColor(Color.WHITE)
                box2.setBackgroundColor(Color.WHITE)
                box1.setBackgroundColor(Color.WHITE)
            }
        }
    }
    /**
     * @param answer user input value
     */
    private fun checkAnswer(answer: Int){
        displaySolutionResult = findViewById(R.id.displaySolutionResult)
        val lhs = sumArithmetic[0]      //left hand side answer
        val rhs = sumArithmetic[1]      //right hand side answer

        if ((lhs == rhs) && (answer==2)) {      //checking if the user input answer is same as the real answer
            correctAnswers += 1
            displaySolutionResult.text = getString(R.string.correct)
            displaySolutionResult.setTextColor(Color.GREEN)
            correctAnswersStreak += 1
        }
        else if ((lhs < rhs) && (answer==3)) {
            correctAnswers += 1
            displaySolutionResult.text = getString(R.string.correct)
            displaySolutionResult.setTextColor(Color.GREEN)
            correctAnswersStreak += 1
        }
        else if ((lhs > rhs) && (answer==1)) {
            correctAnswers += 1
            displaySolutionResult.text = getString(R.string.correct)
            displaySolutionResult.setTextColor(Color.GREEN)
            correctAnswersStreak += 1
        }
        else{
            wrongAnswers += 1
            displaySolutionResult.text = getString(R.string.wrong)
            displaySolutionResult.setTextColor(Color.RED)
        }
        colorStreakBox()
        startGame()
        if (correctAnswersStreak == 5) {
            timer.cancel()
            time += 10000
            correctAnswersStreak = 0
        }
        countDown()
        Timer().schedule(1000) {
            displaySolutionResult.text = ""
        }
    }

    /**
     * method to start the game creating two arithmetic expressions and displaying them respectively
     */
    private fun startGame() {
        displayArithmetic.clear()  //resetting the array
        sumArithmetic.clear()      //resetting the array

        createArithmetic()         //creating the arithmetic expression once
        createArithmetic()         //creating the arithmetic expression twice

        lhsAnswerBox.text = displayArithmetic[0]
        rhsAnswerBox.text = displayArithmetic[1]
    }

    /**
     * method to create an random arithmetic expression
     */
    private fun createArithmetic(){
        val arithmeticOne = ((1..4).random())    //generating a random number between 1 and 4 inclusive
        val numbers = ArrayList<Double>()        //generating random numbers for the arithmetic expression
        for(item in 1..arithmeticOne){
            val randomNumber = ((1..20).random())
            numbers.add(randomNumber.toDouble())
        }
        println(numbers)
        val operations = ArrayList<Int>()       //generating random arithmetic operations for the expression
        val numberOfOperations = arithmeticOne -1           // key value 1 for addition
        for(item in 1..numberOfOperations){                 // key value 2 for subtraction
            val randomNumber = ((1..4).random())            // key value 3 for multiplication
            operations.add(randomNumber)                    // key value 4 for division
        }
        if(4 in operations){                //if the generated operations contain a division whole number function is called
             createWholeNumberSolution(numbers,operations)
        }
        createSolutionLessThanHundred(numbers,operations)   //checking if the final sum of the value is less than hundred
        println(numbers)
        if(arithmeticOne == 1){             //creating an arithmetic expression according to the number of generated numbers
            displayArithmetic.add(numbers[0].toInt().toString())
            sumArithmetic.add(numbers[0])
        }
        if(arithmeticOne == 2){
            twoDigitsArithmeticQuestion(numbers, operations)
        }
        if(arithmeticOne == 3){
            threeDigitsArithmeticQuestion(numbers, operations)
        }
        if(arithmeticOne == 4){
            fourDigitsArithmeticQuestion(numbers, operations)
        }
        println(sumArithmetic)
    }

    private fun createSolutionLessThanHundred(numbers : ArrayList<Double>, operations: ArrayList<Int>){
        var value = numbers[0]
        var operationIndex = 0
        for (i in 1 until numbers.size){                 //loop iterating through the second index
            if (operations[operationIndex] == 1){        //if the operations are not division normal arithmetic operations takes place
                value += numbers[i]
                if (value >= 100.0){                     //the value is not a whole number falls into a loop
                    value -= numbers[i]                  //until the newly generated number does give a whole number after dividing
                    var valid = true
                    while (valid){
                        val randomNumber = ((1..20).random())
                        if ((value + randomNumber) <= 100.0){
                            numbers[i] = randomNumber.toDouble()
                            value += numbers[i]
                            valid = false
                        }
                    }
                }
            }
            if (operations[operationIndex] == 2){
                value -= numbers[i]
            }
            if (operations[operationIndex] == 3){
                value *= numbers[i]
                if (value >= 100.0){                     //the value is not a whole number falls into a loop
                    value /= numbers[i]                  //until the newly generated number does give a whole number after dividing
                    var valid = true
                    while (valid){
                        val randomNumber = ((1..20).random())
                        if ((value * randomNumber) < 100.0){
                            numbers[i] = randomNumber.toDouble()
                            value *= numbers[i]
                            valid = false
                        }
                    }
                }
            }
            if (operations[operationIndex] == 4){       //if the operation is division check after the dividing the value is whole number
                value /= numbers[i]
            }
            operationIndex += 1     //adding one to the operation index
        }
    }
    /**
     * method to create the final answer of the arithmetic expression a whole number without decimals
     * @param numbers arraylist containing all the random generated numbers
     * @param operations arraylist containing all the random generated operations
     */
    private fun createWholeNumberSolution(numbers: ArrayList<Double>, operations: ArrayList<Int>)  {
        var value = numbers[0]                           //first generated random number
        var operationIndex = 0                           //variable to hold the index position of the operation
        for (i in 1 until numbers.size){                 //loop iterating through the second index
            if (operations[operationIndex] == 1){        //if the operations are not division normal arithmetic operations takes place
                value += numbers[i]
            }
            if (operations[operationIndex] == 2){
                value -= numbers[i]
            }
            if (operations[operationIndex] == 3){
                value *= numbers[i]
            }
            if (operations[operationIndex] == 4){       //if the operation is division check after the dividing the value is whole number
                value /= numbers[i]
                if (value % 1 != 0.0){                  //the value is not a whole number falls into a loop
                    value *= numbers[i]                 //until the newly generated number does give a whole number after dividing
                    var valid = true
                    while (valid){
                        val randomNumber = ((1..20).random())
                        if ((value/randomNumber) % 1 == 0.0){
                            numbers[i] = randomNumber.toDouble()
                            value /= numbers[i]
                            valid = false
                        }
                    }
                }
            }
            operationIndex += 1     //adding one to the operation index
        }
    }

    /**
     * @param numbers arraylist containing all the random generated numbers
     * @param operations arraylist containing all the random operations
     */
    private fun twoDigitsArithmeticQuestion(numbers: ArrayList<Double>, operations: ArrayList<Int>) {
        var display = " "
        var sum = 0.0
        if (operations[0] == 1){
            display = "" + numbers[0].toInt() + "+" + numbers[1].toInt()
            sum = numbers[0]+numbers[1]
        }
        if (operations[0] == 2){
            display = "" + numbers[0].toInt() + "-" + numbers[1].toInt()
            sum = numbers[0]-numbers[1]
        }
        if (operations[0] == 3){
            display = "" + numbers[0].toInt() + "*" + numbers[1].toInt()
            sum = numbers[0]*numbers[1]
        }
        if (operations[0] == 4){
            display = "" + numbers[0].toInt() + "/" + numbers[1].toInt()
            sum = numbers[0]/numbers[1]
        }
        displayArithmetic.add(display)
        sumArithmetic.add(sum)
    }

    /**
     * @param numbers arraylist containing all the random generated numbers
     * @param operations arraylist containing all the random operations
     */
    private fun threeDigitsArithmeticQuestion(numbers: ArrayList<Double>, operations: ArrayList<Int>){
        var needsBracket = false
        for(eachOperation in operations){
            if (eachOperation == 3 || eachOperation == 4){
                needsBracket = true
                break
            }
        }
        var value = numbers[0]
        var operationIndex = 0
        for (i in 1 until numbers.size) {                 //loop iterating through the second index
            if (operations[operationIndex] == 1) {        //if the operations are not division normal arithmetic operations takes place
                value += numbers[i]
            }
            if (operations[operationIndex] == 2) {
                value -= numbers[i]
            }
            if (operations[operationIndex] == 3) {
                value *= numbers[i]
            }
            if (operations[operationIndex] == 4) {
                value /= numbers[i]
            }
            operationIndex += 1
        }

        val display = if(needsBracket){
            "( " + numbers[0].toInt() + arithmeticOperations[operations[0]] + numbers[1].toInt() + " )" + arithmeticOperations[operations[1]] + numbers[2].toInt()
        } else{
            "" + numbers[0].toInt() + arithmeticOperations[operations[0]] + numbers[1].toInt() + arithmeticOperations[operations[1]] + numbers[2].toInt()
        }
        displayArithmetic.add(display)
        sumArithmetic.add(value)
    }
    /**
     * @param numbers arraylist containing all the random generated numbers
     * @param operations arraylist containing all the random operations
     */
    private fun fourDigitsArithmeticQuestion(numbers: ArrayList<Double>, operations: ArrayList<Int>) {
        var needsBracket = false
        for(eachOperation in operations){
            if (eachOperation == 3 || eachOperation == 4){
                needsBracket = true
                break
            }
        }
        var value = numbers[0]
        var operationIndex = 0
        for (i in 1 until numbers.size) {                 //loop iterating through the second index
            if (operations[operationIndex] == 1) {        //if the operations are not division normal arithmetic operations takes place
                value += numbers[i]
            }
            if (operations[operationIndex] == 2) {
                value -= numbers[i]
            }
            if (operations[operationIndex] == 3) {
                value *= numbers[i]
            }
            if (operations[operationIndex] == 4) {
                value /= numbers[i]
            }
            operationIndex += 1
        }

        val display = if(needsBracket){
            "(( " + numbers[0].toInt() + arithmeticOperations[operations[0]] + numbers[1].toInt() + " )" + arithmeticOperations[operations[1]] + numbers[2].toInt() + " )" + arithmeticOperations[operations[2]] + numbers[3].toInt()
        } else{
            "" + numbers[0].toInt() + arithmeticOperations[operations[0]] + numbers[1].toInt() + arithmeticOperations[operations[1]] + numbers[2].toInt() + arithmeticOperations[operations[2]] + numbers[3].toInt()
        }
        displayArithmetic.add(display)
        sumArithmetic.add(value)
    }
}