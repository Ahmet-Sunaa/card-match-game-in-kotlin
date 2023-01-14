package com.suna.harrypotter_cardgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.suna.harrypotter_cardgame.databinding.ActivityEndgameBinding

class EndgameActivity : AppCompatActivity() {
    lateinit var binding: ActivityEndgameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=ActivityEndgameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var playerspoint=intent.getSerializableExtra("info") as EndGameInfo

        binding.button.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.firstplayerpointText.text=playerspoint.firstplayer.toString()
        if(playerspoint.secondplayer==-5000){
            binding.secondplayerText.visibility=View.INVISIBLE
            binding.secondpointText.visibility=View.INVISIBLE
        }
        else{
            binding.secondpointText.text=playerspoint.secondplayer.toString()
        }
    }
}