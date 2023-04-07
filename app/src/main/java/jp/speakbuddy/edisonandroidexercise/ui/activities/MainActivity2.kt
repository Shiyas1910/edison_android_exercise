package jp.speakbuddy.edisonandroidexercise.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import jp.speakbuddy.edisonandroidexercise.R
import jp.speakbuddy.edisonandroidexercise.databinding.ActivityMainBinding
import jp.speakbuddy.edisonandroidexercise.network.Status
import jp.speakbuddy.edisonandroidexercise.ui.fact.FactViewModel
import jp.speakbuddy.edisonandroidexercise.utils.AppConstants.CAT_STRING
import jp.speakbuddy.edisonandroidexercise.utils.AppConstants.LENGTH
import jp.speakbuddy.edisonandroidexercise.utils.AppConstants.NO_FACT_STRING
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity2 : AppCompatActivity() {

    private val factViewModel: FactViewModel by viewModels()
    private lateinit var mViewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mViewBinding.root)

        mViewBinding.updateFactButton.setOnClickListener {
            factViewModel.updateFact { Log.e("MainActivity","Done!") }
        }

        lifecycleScope.launch {
            factViewModel.viewState.collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        mViewBinding.progressBarLayout.visibility = View.GONE

                        val fact = it.data?.fact
                        val length = it.data?.length
                        mViewBinding.factDescriptionTextView.text = fact ?: NO_FACT_STRING
                        hideViews()
                        if (!fact.isNullOrEmpty() && fact.contains(CAT_STRING, true)) {
                            mViewBinding.multipleCatLabelTextView.visibility = View.VISIBLE
                        }
                        if (length != null && length > LENGTH) {
                            mViewBinding.lengthTextView.visibility = View.VISIBLE
                            mViewBinding.lengthTextView.text =
                                getString(R.string.length, length.toString())
                        }
                    }
                    Status.ERROR -> {
                        mViewBinding.factDescriptionTextView.text = it.message
                        mViewBinding.factLabelTextView.text = getString(R.string.error)
                        hideViews()
                    }
                    Status.LOADING -> {
                        mViewBinding.progressBarLayout.visibility = View.VISIBLE
                        hideViews()
                    }
                }
            }
        }
    }

    private fun hideViews() {
        mViewBinding.lengthTextView.visibility = View.GONE
        mViewBinding.multipleCatLabelTextView.visibility = View.GONE
    }
}