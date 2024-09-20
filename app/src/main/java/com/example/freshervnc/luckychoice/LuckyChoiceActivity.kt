package com.example.freshervnc.luckychoice

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.freshervnc.util.DialogUtil
import com.example.freshervnc.R
import com.example.freshervnc.databinding.ActivityLuckyChoiceBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LuckyChoiceActivity : AppCompatActivity() {
    lateinit var binding: ActivityLuckyChoiceBinding
    private val viewModel: LuckyChoiceViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLuckyChoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpObservers()
        changeColor(binding.btnPlay, R.color.green)
        binding.btnPlay.setOnClickListener {
            if(viewModel.choiceLiveData.value == 0){
                showDialog(getString(R.string.msg_choose_even_odd))
                reset()
            }else{
                val bet= binding.edtMoney.text.toString().trim()
                if(bet==""){
                    showDialog(getString(R.string.msg_type_bet))
                    reset()
                }else{
                    viewModel.betLiveData.value = bet.toInt()
                    if(viewModel.moneyLiveData.value!!.toInt() < bet.toInt()){
                        showDialog(getString(R.string.msg_bet_too_big))
                        resetAll()
                    }else{
                        binding.tvResult.visibility = View.GONE
                        viewModel.startGame()
                        CoroutineScope(Dispatchers.Main).launch {
                            val imageList = viewModel.imageListLiveData.value
                            if (imageList != null) {
                                for (i in imageList.indices) {
                                    binding.imgContent.setImageResource(imageList[i])
                                    delay(100L)
                                }
                            }
                            binding.imgContent.setImageResource(R.drawable.ic_heads_tails)
                            viewModel.showResult()
                        }

                    }

                }
            }
        }
        binding.btnEven.setOnClickListener {
            viewModel.choiceLiveData.value = 2
            changeColor(binding.btnEven, R.color.red)
            changeColor(binding.btnOdd, R.color.grey)
        }
        binding.btnOdd.setOnClickListener {
            viewModel.choiceLiveData.value = 1
            changeColor(binding.btnEven, R.color.grey)
            changeColor(binding.btnOdd, R.color.blue)
        }
    }
    private fun changeColor(button:Button,color:Int){
        val background = button.background as GradientDrawable
        background.setColor(ContextCompat.getColor(this, color))
    }
    private fun reset(){
        changeColor(binding.btnEven, R.color.grey)
        changeColor(binding.btnOdd, R.color.grey)
        binding.edtMoney.setText("")
        viewModel.betLiveData.value = 0
        viewModel.choiceLiveData.value=0
        viewModel.resultLiveData.value=0
    }
    private fun resetAll(){
        changeColor(binding.btnEven, R.color.grey)
        changeColor(binding.btnOdd, R.color.grey)
        binding.edtMoney.setText("")
        viewModel.betLiveData.value = 0
        viewModel.choiceLiveData.value=0
        viewModel.resultLiveData.value=0
        viewModel.oddImagesLiveData.value = emptyList()
        viewModel.evenImagesLiveData.value = emptyList()
        binding.tvResult.visibility = View.GONE
    }
    private fun showDialog(message: String) {
        DialogUtil.showDialog(
            context = this,
            message = message,
            positiveText = getString(R.string.next),
            positiveAction = { }
        )
    }
    private fun showDialog(message: String, show: () -> Unit) {
        DialogUtil.showDialog(
            context = this,
            message = message,
            positiveText = getString(R.string.next),
            positiveAction = {
                show()
            }
        )
    }

    private fun setUpObservers() {
        viewModel.imageListLiveData.observe(this, Observer { imageList ->

        })
        viewModel.resultLiveData.observe(this, Observer { result ->
            if(result == 2){
                binding.tvResult.text = getString(R.string.even)
                binding.tvResult.visibility = View.VISIBLE
            }else if(result==1){
                binding.tvResult.text = getString(R.string.odd)
                binding.tvResult.visibility = View.VISIBLE
            }else{
                binding.tvResult.visibility= View.GONE
            }
        })
        viewModel.evenImagesLiveData.observe(this, Observer { chanImages ->
            binding.llEven.removeAllViews()
            chanImages.forEach { imageRes ->
                val imageView = ImageView(this)
                imageView.setImageResource(imageRes)

                val widthInDp = 50
                val heightInDp = 50

                val scale = resources.displayMetrics.density
                val widthInPx = (widthInDp * scale).toInt()
                val heightInPx = (heightInDp * scale).toInt()

                val layoutParams = LinearLayout.LayoutParams(
                    widthInPx,
                    heightInPx
                )
                imageView.layoutParams = layoutParams
                binding.llEven.addView(imageView)
            }
        })
        viewModel.oddImagesLiveData.observe(this, Observer { leImages ->
            binding.llOdd.removeAllViews()
            leImages.forEach { imageRes ->
                val imageView = ImageView(this)
                imageView.setImageResource(imageRes)

                val widthInDp = 50
                val heightInDp = 50

                val scale = resources.displayMetrics.density
                val widthInPx = (widthInDp * scale).toInt()
                val heightInPx = (heightInDp * scale).toInt()

                val layoutParams = LinearLayout.LayoutParams(
                    widthInPx,
                    heightInPx
                )
                imageView.layoutParams = layoutParams
                binding.llOdd.addView(imageView)
            }
        })
        viewModel.isPlayingLiveData.observe(this, Observer { isPlaying ->
            binding.btnPlay.isClickable = !isPlaying
            binding.btnPlay.text = if (isPlaying) "..." else getString(R.string.play)
            changeColor(binding.btnPlay,if(isPlaying) R.color.orange else R.color.green)
        })
        viewModel.moneyLiveData.observe(this, Observer { money ->
            binding.tvMoney.text = ""+money
        })
        viewModel.state.observe(this, Observer { state ->
            if(state == true){
                showDialog(getString(R.string.msg_win)+" "+viewModel.betLiveData.value.toString()){resetAll()}
            }else{
                showDialog(getString(R.string.msg_lose)+" "+viewModel.betLiveData.value.toString()){resetAll()}
            }
        })
    }
}
/*
chuyen sang viewmodel
tao cac string
tao dialog util
tao cac int
xu li su kien
 */