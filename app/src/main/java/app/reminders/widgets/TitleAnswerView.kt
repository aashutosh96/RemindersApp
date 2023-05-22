package app.reminders.widgets

import android.content.Context
import android.text.InputType
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import app.common.extension.compatDrawable
import app.common.extension.setOnSingleClickListener
import com.aashutosh.reminders.R
import com.aashutosh.reminders.databinding.LayoutTitleAnswerBinding

class TitleAnswerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var inputType = 0
    private var hasBorder = false
    private var isRichText = false
    private var isEditable = true
    private var isImeDone = false
    private var isSingleLineTitle = false
    var binding: LayoutTitleAnswerBinding =
        LayoutTitleAnswerBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        context.withStyledAttributes(attrs, R.styleable.TitleAnswer) {
            binding.etAnswer.id = View.generateViewId()
            title = getString(R.styleable.TitleAnswer_tav_title) ?: ""
            answer = getString(R.styleable.TitleAnswer_tav_answer) ?: ""
            isImeDone = getBoolean(R.styleable.TitleAnswer_tav_is_ime_done, false)
            inputType = getInt(R.styleable.TitleAnswer_tav_input_type, 0)
            hasBorder = getBoolean(R.styleable.TitleAnswer_tav_has_border, false)
            isRichText = getBoolean(R.styleable.TitleAnswer_tav_rich_text, false)
            isEditable = getBoolean(R.styleable.TitleAnswer_tav_is_editable, true)
            isSingleLineTitle = getBoolean(R.styleable.TitleAnswer_tav_single_line_title, false)
            if (isSingleLineTitle) {
                binding.tvTitle.apply {
                    maxLines = 1
                    ellipsize = TextUtils.TruncateAt.END
                }
            }
            if (isImeDone) {
                binding.etAnswer.imeOptions = EditorInfo.IME_ACTION_DONE
            }
            if (hasBorder) {
                binding.etAnswer.background =
                    context.compatDrawable(R.drawable.bg_curve_corner_gray_stroke_white_background)
            }
            if (isRichText) {
                with(binding.etAnswer) {
                    layoutParams.height = 220
                    setLines(20)
                    imeOptions = EditorInfo.IME_FLAG_NO_ENTER_ACTION;
                    isSingleLine = false
                    inputType =
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE or InputType.TYPE_TEXT_VARIATION_LONG_MESSAGE
                    setText("")
                }
            }
            binding.etAnswer.isClickable = isEditable
            binding.etAnswer.isFocusable = isEditable
            binding.etAnswer.inputType = when (inputType) {
                0 -> InputType.TYPE_CLASS_TEXT
                1 -> InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                2 -> InputType.TYPE_TEXT_VARIATION_PASSWORD
                3 -> InputType.TYPE_CLASS_PHONE
                else -> InputType.TYPE_CLASS_TEXT
            }
        }
    }

    var title: String
        get() = binding.tvTitle.text.toString()
        set(value) {
            binding.tvTitle.text = value
        }

    var answer: String
        get() = binding.etAnswer.text.toString().trim()
        set(value) {
            binding.etAnswer.hint = value
        }

    var value: String
        get() = binding.etAnswer.text.toString().trim()
        set(value) {
            binding.etAnswer.setText(value)
        }

    fun error(message: String) {
        binding.etAnswer.requestFocus()
        binding.etAnswer.error = message
    }

    fun onClick(onClick:()->Unit){
        binding.etAnswer.setOnSingleClickListener {
            onClick.invoke()
        }
    }
}