import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.websarva.wings.android.myapplication.R

class MainActivity_______ : AppCompatActivity() {
    private lateinit var resultTextView: TextView
    private var operand: Double = 0.0
    private var isOperatorPressed: Boolean = false
    private var operator: Char = ' '

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(/* savedInstanceState = */ savedInstanceState)
        setContentView(R.layout.activity_main)

        resultTextView = findViewById(R.id.resultTextView)

        val numberButtons = listOf(
            R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
            R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9
        )

        numberButtons.forEach { buttonId: Int ->
            findViewById<Button>(buttonId).setOnClickListener { numberButtonClicked(buttonId) }
        }

        val operatorButtons = listOf(R.id.buttonPlus, R.id.buttonMinus, R.id.buttonMultiply, R.id.buttonDivide)

        operatorButtons.forEach { buttonId: Int ->
            findViewById<Button>(buttonId).setOnClickListener { operatorButtonClicked(buttonId) }
        }

        findViewById<Button>(R.id.buttonEquals).setOnClickListener { calculateResult() }
        findViewById<Button>(R.id.buttonClear).setOnClickListener { clearInputs() }
    }

    private fun numberButtonClicked(buttonId: Int) {
        if (isOperatorPressed) {
            resultTextView.text = ""
            isOperatorPressed = false
        }

        val number = findViewById<Button>(buttonId).text
        val currentText = resultTextView.text.toString()
        resultTextView.text = currentText + number
    }

    private fun operatorButtonClicked(buttonId: Int) {
        val operatorButton = findViewById<Button>(buttonId)
        operator = operatorButton.text[0]
        operand = resultTextView.text.toString().toDouble()
        isOperatorPressed = true
    }

    private fun calculateResult() {
        val secondOperand = resultTextView.text.toString().toDouble()
        when (operator) {
            '+' -> resultTextView.text = (operand + secondOperand).toString()
            '-' -> resultTextView.text = (operand - secondOperand).toString()
            '*' -> resultTextView.text = (operand * secondOperand).toString()
            '/' -> resultTextView.text = if (secondOperand != 0.0) {
                (operand / secondOperand).toString()
            } else {
                "Error"
            }
        }
    }

    private fun clearInputs() {
        resultTextView.text = ""
        operand = 0.0
        isOperatorPressed = false
        operator = ' '
    }
}