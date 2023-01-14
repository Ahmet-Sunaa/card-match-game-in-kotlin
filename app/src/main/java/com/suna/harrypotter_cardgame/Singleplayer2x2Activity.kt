package com.suna.harrypotter_cardgame


import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Base64
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.database.*
import com.suna.harrypotter_cardgame.databinding.ActivitySingleplayer2x2Binding
import java.io.OutputStreamWriter
import java.util.*
import kotlin.concurrent.schedule


class Singleplayer2x2Activity : AppCompatActivity() {
    lateinit var binding: ActivitySingleplayer2x2Binding
    private var dbRef: DatabaseReference?=null
    var db:FirebaseDatabase?=null
    var mMediaPlayer: MediaPlayer? = null
    var winplayer: MediaPlayer? = null
    var a : Int=0
    private var str1: String? = null
    private var controller2: Int = 0
    private var str2: String? = null
    var totalpoint: Int = 0
    var livepoint: Int = 0
    private val list: MutableList<ImageView> = mutableListOf()
    private val list2: MutableList<ImageView> = mutableListOf()
    private val cardlist:MutableList<Card> = mutableListOf()
    var int1: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding=ActivitySingleplayer2x2Binding.inflate(layoutInflater)

        setContentView(binding.root)
        val finish = Intent(this, EndgameActivity::class.java)
        db = FirebaseDatabase.getInstance()
        dbRef = db?.getReference("pictures")
        var indexes = intent.getIntArrayExtra("randindexes")
        var indexList = indexes!!.toList()
        val givenList = mutableListOf<ImageView>(binding.imageView1,binding.imageView2,binding.imageView3,binding.imageView4)
        list2.addAll(givenList)


        object :CountDownTimer(4000,4000){
            override fun onTick(millisUntilFinished: Long) {
                for (i in indexList) {
                    dbRef?.child(i.toString())!!.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            var card_name = snapshot.child("name").value.toString()
                            var card_point = snapshot.child("value").value.toString().toInt()
                            var house = snapshot.child("house").value.toString()
                            var house_point = snapshot.child("houseP").value.toString().toInt()
                            var base_64=snapshot.child("base64").value.toString()
                            var card_sample = Card(
                                card_name = card_name,
                                card_point = card_point,
                                house = house,
                                house_point = house_point,
                                base_64 = base_64
                            )
                            cardlist.add(card_sample)
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }

                    })
                }
            }
            override fun onFinish() {
                for(i in 0 until cardlist.size){
                    cardlist.add(cardlist[i])
                }

                cardlist.shuffle()


                    try {
                        val fileout = openFileOutput("card_info.txt", MODE_PRIVATE)
                        val outputWriter = OutputStreamWriter(fileout)
                        val card_info:String=cardlist[0].card_name
                        outputWriter.write(card_info)
                        outputWriter.close()

                    }catch (e:Exception){
                        println(e.message)
                    }



                object : CountDownTimer(45000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        if(int1==3){
                            cancel()
                        }
                        isfinish()
                        binding.viewTimer.setText("süre:"+millisUntilFinished / 1000)
                    }
                    override fun onFinish() {
                        val data = SoundData(a)
                        val data2 = EndGameInfo(totalpoint,-5000)
                        finish.putExtra("key",data)
                        finish.putExtra("info",data2)
                        endTimesound()
                        pauseSound()
                        startActivity(finish)
                        finish()
                    }
                }.start()


                val b: SoundData = intent.getSerializableExtra("key") as SoundData
                a =b.x
                if(a==1)
                    playSound()
                else
                    pauseSound()
                binding.imageView1.setOnClickListener{ it->
                    binding.imageView1.setImageBitmap(
                        BitmapFactory.decodeByteArray(
                            Base64.decode(cardlist[0].base_64,
                                Base64.DEFAULT),0, Base64.decode(cardlist[0].base_64, Base64.DEFAULT).size))
                    Timer().schedule(1000){
                        controller(binding.imageView1,cardlist[0].card_name,binding)

                    }
                }
                binding.imageView2.setOnClickListener{ it->
                    binding.imageView2.setImageBitmap(BitmapFactory.decodeByteArray(Base64.decode(cardlist[3].base_64,Base64.DEFAULT),0,Base64.decode(cardlist[3].base_64,Base64.DEFAULT).size))
                    Timer().schedule(1000){
                        controller(binding.imageView2,cardlist[3].card_name,binding)
                    }
                }
                binding.imageView3.setOnClickListener{ it->
                    binding.imageView3.setImageBitmap(BitmapFactory.decodeByteArray(Base64.decode(cardlist[2].base_64,Base64.DEFAULT),0,Base64.decode(cardlist[2].base_64,Base64.DEFAULT).size))
                    Timer().schedule(1000){
                        controller(binding.imageView3,cardlist[2].card_name,binding)
                    }
                }
                binding.imageView4.setOnClickListener{ it->
                    binding.imageView4.setImageBitmap(BitmapFactory.decodeByteArray(Base64.decode(cardlist[1].base_64,Base64.DEFAULT),0,Base64.decode(cardlist[1].base_64,Base64.DEFAULT).size))
                    Timer().schedule(1000){
                        controller(binding.imageView4,cardlist[1].card_name,binding)
                    }
                }
            }
        }.start()
    }

    fun controller(imageView: ImageView,String:String,binding: ActivitySingleplayer2x2Binding){

        if(controller2==0){
            controller2++
            str1 = String
            list.add(imageView)
        }
        else {
            controller2 = 0
            str2 = String
            var x: Int?
            x = same(str1!!, str2!!)!!
            if (x == 1) {
                findcardsound()
                list[0].isClickable = false
                imageView.isClickable = false
                list.removeAt(0)
                livepoint = findrightpoint(str1!!)
                Handler(Looper.getMainLooper()).post(kotlinx.coroutines.Runnable { binding.livepointText.text = livepoint.toString() })
                totalpoint += livepoint
                Handler(Looper.getMainLooper()).post(kotlinx.coroutines.Runnable { binding.totalpointText.text = totalpoint.toString() })
            }else{
                visibility(imageView,list[0])
                list.removeAt(0)
                livepoint = findwrongpoint(str1!!, str2!!)
                Handler(Looper.getMainLooper()).post(kotlinx.coroutines.Runnable { binding.livepointText.text = livepoint.toString() })
                totalpoint += livepoint
                Handler(Looper.getMainLooper()).post(kotlinx.coroutines.Runnable { binding.totalpointText.text = totalpoint.toString() })
            }

        }
    }
    fun same(string: String, string2: String): Int? {
        var x : Int
        if (string.contains(string2)) x=1
        else x=0
        return x
    }
    fun visibility(imageView: ImageView,imageViewx: ImageView){
        Handler(Looper.getMainLooper()).post(kotlinx.coroutines.Runnable {
            imageView.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.genel)
            )
        })
        Handler(Looper.getMainLooper()).post(kotlinx.coroutines.Runnable {
            imageViewx.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.genel)
            )
        })
    }

    fun findwrongpoint(str3: String,str4: String): Int {
        var i: Int = 0
        var j:Int = 0
        while (cardlist.size>i){
            if(cardlist[i].card_name.equals(str3)){
                break
            }
            i++
        }
        while (cardlist.size>j){
            if(cardlist[j].card_name.equals(str4)){
                break
            }
            j++
        }
        if(cardlist[i].house==cardlist[j].house){
            val gecici: Float = (-(cardlist[i].card_point+cardlist[j].card_point)/cardlist[i].house_point.toFloat())
            return gecici.toInt()
        }
        else{
            val gecici: Float = (-((cardlist[i].card_point+cardlist[j].card_point)/2.toFloat())*cardlist[i].house_point*cardlist[j].house_point)
            return gecici.toInt()
        }
    }
    fun findrightpoint(str3: String): Int {
        var i: Int = 0
        while (cardlist.size>i){
            if(cardlist[i].card_name.equals(str3)){
                break
            }
            i++
        }
        val gecici: Float =((2*cardlist[i].card_point*cardlist[i].house_point).toFloat())
        return gecici.toInt()
    }
    fun isfinish(){
        var i:Int = 0
        Handler(Looper.getMainLooper()).post(kotlinx.coroutines.Runnable {
            while (list2.size > i) {
                if (list2[i].isClickable == true) {
                    break
                }
                i++
                if (i == 3) {
                    int1 = i
                    val finish = Intent(this, EndgameActivity::class.java)
                    val data = SoundData(a)
                    val data2 = EndGameInfo(totalpoint, -5000)
                    finish.putExtra("key", data)
                    finish.putExtra("info", data2)
                    finishgamesound()
                    pauseSound()
                    startActivity(finish)
                    finish()
                }
            }
        })
    }
    fun playSound() {
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(this, R.raw.prologue)
            mMediaPlayer!!.isLooping = true
            mMediaPlayer!!.start()
        } else mMediaPlayer!!.start()
    }
    fun findcardsound(){
        if (winplayer == null) {
            winplayer = MediaPlayer.create(this, R.raw.rightcard)
            winplayer!!.start()
        } else winplayer!!.start()
    }
    fun endTimesound(){
        winplayer = MediaPlayer.create(this, R.raw.endtime)
        winplayer!!.start()
    }
    fun finishgamesound(){
        winplayer = MediaPlayer.create(this, R.raw.congratulations)
        winplayer!!.start()
    }
    // 2. Pause playback
    fun pauseSound() {
        if (mMediaPlayer?.isPlaying == true) {
            mMediaPlayer?.pause()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.voice,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.app_bar_switch){
            if(a==0){
                a++;
                playSound()
            }else{
                a=0;
                pauseSound()
            }
        }
        return super.onOptionsItemSelected(item)
    }









}