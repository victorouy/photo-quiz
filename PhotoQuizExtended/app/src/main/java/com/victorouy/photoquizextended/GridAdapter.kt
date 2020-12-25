package com.victorouy.photoquizextended

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button

/**
 * GridAdapter class used as a custom adapter
 *
 * @author Victor Ouy
 */
class GridAdapter(private val context: Context, private var questionsList: ArrayList<String>, private var quesFragment : QuestionFragment): BaseAdapter() {

    /**
     *  Overridden method that populates the GridView incorporated buttons. The onClick event
     *  listener fills the buttons with its updated text, along with updating the questionsList
     *
     *  @param position: Int
     *  @param convertView: View?
     *  @param parent: ViewGroup?
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val button : Button
        if (convertView == null) {
            Log.d("convertView IS NULL", questionsList[position])
            button = Button(context)
            button.text = questionsList[position]

            // Event listener whenever a question button is clicked that will calls the answerButtonClick
            // method in QuestionFragment and will update the questionsList and GridView texts
            button.setOnClickListener {
                Log.i("CLICK", "Button clicked")
                quesFragment.answerButtonClick(button.text as String)
                this.questionsList = quesFragment.getQuestionsList()

                if (parent != null) {
                    val button1 = parent.getChildAt(0) as Button
                    val button2 = parent.getChildAt(1) as Button
                    val button3 = parent.getChildAt(2) as Button
                    val button4 = parent.getChildAt(3) as Button
                    button1.text = this.questionsList[0]
                    button2.text = this.questionsList[1]
                    button3.text = this.questionsList[2]
                    button4.text = this.questionsList[3]
                }
            }
        }
        else {
            button = convertView as Button
        }
        return button
    }

    /**
     * Gets the String from questionsList at the given position
     *
     * @param position: Int
     */
    override fun getItem(position: Int): Any {
        return questionsList[position]
    }

    /**
     * Gets the position enetered
     *
     * @param position: Int
     */
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    /**
     * Gets the size of the questionsList
     *
     * @param position: Int
     */
    override fun getCount(): Int {
        return questionsList.size
    }
}