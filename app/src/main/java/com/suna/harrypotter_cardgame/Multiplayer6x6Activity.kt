package com.suna.harrypotter_cardgame

import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Base64
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.google.firebase.database.*
import com.suna.harrypotter_cardgame.databinding.ActivityMultiplayer6x6Binding
import java.util.*
import kotlin.concurrent.schedule

class Multiplayer6x6Activity : AppCompatActivity() {
    var mMediaPlayer: MediaPlayer? = null
    var winplayer: MediaPlayer? = null
    private var dbRef: DatabaseReference?=null
    var db: FirebaseDatabase?=null
    var a : Int=0
    var str1: String? = null
    var controller2: Int = 0
    var PlayerController: Int = 0
    var str2: String? = null
    var int1: Int = 0
    private val list2: MutableList<ImageView> = mutableListOf()
    private val list: MutableList<ImageView> = mutableListOf()
    private var totalpoint1: Int = 0
    private var totalpoint2: Int = 0
    private var livepoint: Int = 0
    private val cardlist: MutableList<Card> = mutableListOf()
    lateinit var binding: ActivityMultiplayer6x6Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=ActivityMultiplayer6x6Binding.inflate(layoutInflater)
        setContentView(binding.root)
        val givenList = mutableListOf<ImageView>(binding.imageView1,binding.imageView2,binding.imageView3
            ,binding.imageView4,binding.imageView5,binding.imageView6,binding.imageView7,binding.imageView8
            ,binding.imageView9,binding.imageView10,binding.imageView11,binding.imageView12,binding.imageView13
            ,binding.imageView14,binding.imageView15,binding.imageView16,binding.imageView17,binding.imageView18
            ,binding.imageView19,binding.imageView20,binding.imageView21,binding.imageView22,binding.imageView23
            ,binding.imageView24,binding.imageView25,binding.imageView26,binding.imageView27,binding.imageView28
            ,binding.imageView29,binding.imageView30,binding.imageView31,binding.imageView32,binding.imageView33
            ,binding.imageView34,binding.imageView35,binding.imageView36)
        list2.addAll(givenList)
        val finish = Intent(this, EndgameActivity::class.java)
        db = FirebaseDatabase.getInstance()
        dbRef = db?.getReference("pictures")
        var indexes = intent.getIntArrayExtra("randindexes")
        var indexList = indexes!!.toList()
        object : CountDownTimer(4000,2000){
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
                object : CountDownTimer(60000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        if (int1 == 35) {
                            cancel()
                        }
                        isfinish()
                        binding.viewTimer.setText("süre:" + millisUntilFinished / 1000)
                    }

                    override fun onFinish() {
                        val data = SoundData(a)
                        val data2 = EndGameInfo(totalpoint1, totalpoint2)
                        finish.putExtra("key", data)
                        finish.putExtra("info", data2)
                        endTimesound()
                        pauseSound()
                        startActivity(finish)
                        finish()
                    }
                }.start()


                val b: SoundData = intent.getSerializableExtra("key") as SoundData
                a = b.x
                if (a == 1)
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
                    binding.imageView2.setImageBitmap(BitmapFactory.decodeByteArray(Base64.decode(cardlist[1].base_64,Base64.DEFAULT),0,Base64.decode(cardlist[1].base_64,Base64.DEFAULT).size))
                    Timer().schedule(1000){
                        controller(binding.imageView2,cardlist[1].card_name,binding)
                    }
                }
                binding.imageView3.setOnClickListener{ it->
                    binding.imageView3.setImageBitmap(BitmapFactory.decodeByteArray(Base64.decode(cardlist[2].base_64,Base64.DEFAULT),0,Base64.decode(cardlist[2].base_64,Base64.DEFAULT).size))
                    Timer().schedule(1000){
                        controller(binding.imageView3,cardlist[2].card_name,binding)
                    }
                }
                binding.imageView4.setOnClickListener{ it->
                    binding.imageView4.setImageBitmap(BitmapFactory.decodeByteArray(Base64.decode(cardlist[3].base_64,Base64.DEFAULT),0,Base64.decode(cardlist[3].base_64,Base64.DEFAULT).size))
                    Timer().schedule(1000){
                        controller(binding.imageView4,cardlist[3].card_name,binding)
                    }
                }
                binding.imageView5.setOnClickListener{ it->
                    binding.imageView5.setImageBitmap(BitmapFactory.decodeByteArray(Base64.decode(cardlist[4].base_64,Base64.DEFAULT),0,Base64.decode(cardlist[4].base_64,Base64.DEFAULT).size))
                    Timer().schedule(1000){
                        controller(binding.imageView5,cardlist[4].card_name,binding)
                    }
                }
                binding.imageView6.setOnClickListener{ it->
                    binding.imageView6.setImageBitmap(BitmapFactory.decodeByteArray(Base64.decode(cardlist[5].base_64,Base64.DEFAULT),0,Base64.decode(cardlist[5].base_64,Base64.DEFAULT).size))
                    Timer().schedule(1000){
                        controller(binding.imageView6,cardlist[5].card_name,binding)
                    }
                }
                binding.imageView7.setOnClickListener{ it->
                    binding.imageView7.setImageBitmap(BitmapFactory.decodeByteArray(Base64.decode(cardlist[6].base_64,Base64.DEFAULT),0,Base64.decode(cardlist[6].base_64,Base64.DEFAULT).size))
                    Timer().schedule(1000){
                        controller(binding.imageView7,cardlist[6].card_name,binding)
                    }
                }
                binding.imageView8.setOnClickListener{ it->
                    binding.imageView8.setImageBitmap(BitmapFactory.decodeByteArray(Base64.decode(cardlist[7].base_64,Base64.DEFAULT),0,Base64.decode(cardlist[7].base_64,Base64.DEFAULT).size))
                    Timer().schedule(1000){
                        controller(binding.imageView8,cardlist[7].card_name,binding)
                    }
                }
                binding.imageView9.setOnClickListener{
                    binding.imageView9.setImageBitmap(BitmapFactory.decodeByteArray(Base64.decode(cardlist[8].base_64,Base64.DEFAULT),0,Base64.decode(cardlist[8].base_64,Base64.DEFAULT).size))
                    Timer().schedule(1000){
                        controller(binding.imageView9,cardlist[8].card_name,binding)
                    }
                }
                binding.imageView10.setOnClickListener{
                    binding.imageView10.setImageBitmap(BitmapFactory.decodeByteArray(Base64.decode(cardlist[9].base_64,Base64.DEFAULT),0,Base64.decode(cardlist[9].base_64,Base64.DEFAULT).size))
                    Timer().schedule(1000){
                        controller(binding.imageView10,cardlist[9].card_name,binding)
                    }
                }
                binding.imageView11.setOnClickListener{
                    binding.imageView11.setImageBitmap(BitmapFactory.decodeByteArray(Base64.decode(cardlist[10].base_64,Base64.DEFAULT),0,Base64.decode(cardlist[10].base_64,Base64.DEFAULT).size))
                    Timer().schedule(1000){
                        controller(binding.imageView11,cardlist[10].card_name,binding)
                    }
                }
                binding.imageView12.setOnClickListener{
                    binding.imageView12.setImageBitmap(BitmapFactory.decodeByteArray(Base64.decode(cardlist[11].base_64,Base64.DEFAULT),0,Base64.decode(cardlist[11].base_64,Base64.DEFAULT).size))
                    Timer().schedule(1000){
                        controller(binding.imageView12,cardlist[11].card_name,binding)
                    }
                }
                binding.imageView13.setOnClickListener{
                    binding.imageView13.setImageBitmap(BitmapFactory.decodeByteArray(Base64.decode(cardlist[12].base_64,Base64.DEFAULT),0,Base64.decode(cardlist[12].base_64,Base64.DEFAULT).size))
                    Timer().schedule(1000){
                        controller(binding.imageView13,cardlist[12].card_name,binding)
                    }
                }
                binding.imageView14.setOnClickListener{
                    binding.imageView14.setImageBitmap(BitmapFactory.decodeByteArray(Base64.decode(cardlist[13].base_64,Base64.DEFAULT),0,Base64.decode(cardlist[13].base_64,Base64.DEFAULT).size))
                    Timer().schedule(1000){
                        controller(binding.imageView14,cardlist[13].card_name,binding)
                    }
                }
                binding.imageView15.setOnClickListener{
                    binding.imageView15.setImageBitmap(BitmapFactory.decodeByteArray(Base64.decode(cardlist[14].base_64,Base64.DEFAULT),0,Base64.decode(cardlist[14].base_64,Base64.DEFAULT).size))
                    Timer().schedule(1000){
                        controller(binding.imageView15,cardlist[14].card_name,binding)
                    }
                }
                binding.imageView16.setOnClickListener {
                    binding.imageView16.setImageBitmap(BitmapFactory.decodeByteArray(Base64.decode(cardlist[15].base_64,Base64.DEFAULT),0,Base64.decode(cardlist[15].base_64,Base64.DEFAULT).size))
                    Timer().schedule(1000) {
                        controller(binding.imageView16,cardlist[15].card_name,binding)
                    }
                }

                binding.imageView17.setOnClickListener{ it->
                    binding.imageView17.setImageBitmap(BitmapFactory.decodeByteArray(Base64.decode(cardlist[16].base_64,Base64.DEFAULT),0,Base64.decode(cardlist[16].base_64,Base64.DEFAULT).size))
                    Timer().schedule(1000){
                        controller(binding.imageView17,cardlist[16].card_name,binding)
                    }
                }
                binding.imageView18.setOnClickListener{ it->
                    binding.imageView18.setImageBitmap(BitmapFactory.decodeByteArray(Base64.decode(cardlist[17].base_64,Base64.DEFAULT),0,Base64.decode(cardlist[17].base_64,Base64.DEFAULT).size))
                    Timer().schedule(1000){
                        controller(binding.imageView18,cardlist[17].card_name,binding)
                    }
                }
                binding.imageView19.setOnClickListener{ it->
                    binding.imageView19.setImageBitmap(BitmapFactory.decodeByteArray(Base64.decode(cardlist[18].base_64,Base64.DEFAULT),0,Base64.decode(cardlist[18].base_64,Base64.DEFAULT).size))
                    Timer().schedule(1000){
                        controller(binding.imageView19,cardlist[18].card_name,binding)
                    }
                }
                binding.imageView20.setOnClickListener{ it->
                    binding.imageView20.setImageBitmap(BitmapFactory.decodeByteArray(Base64.decode(cardlist[19].base_64,Base64.DEFAULT),0,Base64.decode(cardlist[19].base_64,Base64.DEFAULT).size))
                    Timer().schedule(1000){
                        controller(binding.imageView20,cardlist[0].card_name,binding)
                    }
                }
                binding.imageView21.setOnClickListener{ it->
                    binding.imageView21.setImageBitmap(BitmapFactory.decodeByteArray(Base64.decode(cardlist[20].base_64,Base64.DEFAULT),0,Base64.decode(cardlist[20].base_64,Base64.DEFAULT).size))
                    Timer().schedule(1000){
                        controller(binding.imageView21,cardlist[20].card_name,binding)
                    }
                }
                binding.imageView22.setOnClickListener{ it->
                    binding.imageView22.setImageBitmap(BitmapFactory.decodeByteArray(Base64.decode(cardlist[21].base_64,Base64.DEFAULT),0,Base64.decode(cardlist[21].base_64,Base64.DEFAULT).size))
                    Timer().schedule(1000){
                        controller(binding.imageView22,cardlist[21].card_name,binding)
                    }
                }
                binding.imageView23.setOnClickListener{ it->
                    binding.imageView23.setImageBitmap(BitmapFactory.decodeByteArray(Base64.decode(cardlist[22].base_64,Base64.DEFAULT),0,Base64.decode(cardlist[22].base_64,Base64.DEFAULT).size))
                    Timer().schedule(1000){
                        controller(binding.imageView23,cardlist[22].card_name,binding)
                    }
                }
                binding.imageView24.setOnClickListener{ it->
                    binding.imageView24.setImageBitmap(BitmapFactory.decodeByteArray(Base64.decode(cardlist[23].base_64,Base64.DEFAULT),0,Base64.decode(cardlist[23].base_64,Base64.DEFAULT).size))
                    Timer().schedule(1000){
                        controller(binding.imageView24,cardlist[23].card_name,binding)
                    }
                }
                binding.imageView25.setOnClickListener{ it->
                    binding.imageView25.setImageBitmap(BitmapFactory.decodeByteArray(Base64.decode(cardlist[24].base_64,Base64.DEFAULT),0,Base64.decode(cardlist[24].base_64,Base64.DEFAULT).size))
                    Timer().schedule(1000){
                        controller(binding.imageView25,cardlist[24].card_name,binding)
                    }
                }
                binding.imageView26.setOnClickListener{ it->
                    binding.imageView26.setImageBitmap(BitmapFactory.decodeByteArray(Base64.decode(cardlist[25].base_64,Base64.DEFAULT),0,Base64.decode(cardlist[25].base_64,Base64.DEFAULT).size))
                    Timer().schedule(1000){
                        controller(binding.imageView26,cardlist[25].card_name,binding)
                    }
                }
                binding.imageView27.setOnClickListener{ it->
                    binding.imageView27.setImageBitmap(BitmapFactory.decodeByteArray(Base64.decode(cardlist[26].base_64,Base64.DEFAULT),0,Base64.decode(cardlist[26].base_64,Base64.DEFAULT).size))
                    Timer().schedule(1000){
                        controller(binding.imageView27,cardlist[26].card_name,binding)
                    }
                }
                binding.imageView28.setOnClickListener{ it->
                    binding.imageView28.setImageBitmap(BitmapFactory.decodeByteArray(Base64.decode(cardlist[27].base_64,Base64.DEFAULT),0,Base64.decode(cardlist[27].base_64,Base64.DEFAULT).size))
                    Timer().schedule(1000){
                        controller(binding.imageView28,cardlist[27].card_name,binding)
                    }
                }
                binding.imageView29.setOnClickListener{ it->
                    binding.imageView29.setImageBitmap(BitmapFactory.decodeByteArray(Base64.decode(cardlist[28].base_64,Base64.DEFAULT),0,Base64.decode(cardlist[28].base_64,Base64.DEFAULT).size))
                    Timer().schedule(1000){
                        controller(binding.imageView29,cardlist[28].card_name,binding)
                    }
                }
                binding.imageView30.setOnClickListener{ it->
                    binding.imageView30.setImageBitmap(BitmapFactory.decodeByteArray(Base64.decode(cardlist[29].base_64,Base64.DEFAULT),0,Base64.decode(cardlist[29].base_64,Base64.DEFAULT).size))
                    Timer().schedule(1000){
                        controller(binding.imageView30,cardlist[29].card_name,binding)
                    }
                }
                binding.imageView31.setOnClickListener{ it->
                    binding.imageView31.setImageBitmap(BitmapFactory.decodeByteArray(Base64.decode(cardlist[30].base_64,Base64.DEFAULT),0,Base64.decode(cardlist[30].base_64,Base64.DEFAULT).size))
                    Timer().schedule(1000){
                        controller(binding.imageView31,cardlist[30].card_name,binding)
                    }
                }
                binding.imageView32.setOnClickListener{ it->
                    binding.imageView32.setImageBitmap(BitmapFactory.decodeByteArray(Base64.decode(cardlist[31].base_64,Base64.DEFAULT),0,Base64.decode(cardlist[31].base_64,Base64.DEFAULT).size))
                    Timer().schedule(1000){
                        controller(binding.imageView32,cardlist[31].card_name,binding)
                    }
                }
                binding.imageView33.setOnClickListener{ it->
                    binding.imageView33.setImageBitmap(BitmapFactory.decodeByteArray(Base64.decode(cardlist[32].base_64,Base64.DEFAULT),0,Base64.decode(cardlist[32].base_64,Base64.DEFAULT).size))
                    Timer().schedule(1000){
                        controller(binding.imageView33,cardlist[32].card_name,binding)
                    }
                }
                binding.imageView34.setOnClickListener{ it->
                    binding.imageView34.setImageBitmap(BitmapFactory.decodeByteArray(Base64.decode(cardlist[33].base_64,Base64.DEFAULT),0,Base64.decode(cardlist[33].base_64,Base64.DEFAULT).size))
                    Timer().schedule(1000){
                        controller(binding.imageView34,cardlist[33].card_name,binding)
                    }
                }
                binding.imageView35.setOnClickListener{ it->
                    binding.imageView35.setImageBitmap(BitmapFactory.decodeByteArray(Base64.decode(cardlist[34].base_64,Base64.DEFAULT),0,Base64.decode(cardlist[34].base_64,Base64.DEFAULT).size))
                    Timer().schedule(1000){
                        controller(binding.imageView35,cardlist[34].card_name,binding)
                    }
                }
                binding.imageView36.setOnClickListener{ it->
                    binding.imageView36.setImageBitmap(
                        BitmapFactory.decodeByteArray(
                            Base64.decode(cardlist[35].base_64,
                                Base64.DEFAULT),0, Base64.decode(cardlist[35].base_64, Base64.DEFAULT).size))
                    Timer().schedule(1000){
                        controller(binding.imageView36,cardlist[35].card_name,binding)
                    }
                }

            }
        }.start()

    }
    fun controller(imageView: ImageView, String:String,binding: ActivityMultiplayer6x6Binding){
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

                if(PlayerController==0){
                    totalpoint1 += livepoint
                    Handler(Looper.getMainLooper()).post(kotlinx.coroutines.Runnable { binding.player1point.text = totalpoint1.toString() })
                }
                else{
                    totalpoint2 += livepoint
                    Handler(Looper.getMainLooper()).post(kotlinx.coroutines.Runnable { binding.player2point.text = totalpoint2.toString() })
                }
            }else{
                livepoint = findwrongpoint(str1!!, str2!!)
                Handler(Looper.getMainLooper()).post(kotlinx.coroutines.Runnable { binding.livepointText.text = livepoint.toString() })
                if(PlayerController==0){
                    PlayerController++
                    Handler(Looper.getMainLooper()).post(Runnable { binding.playerText.text = "Player 2" })
                    totalpoint1 += livepoint
                    Handler(Looper.getMainLooper()).post(kotlinx.coroutines.Runnable { binding.player1point.text = totalpoint1.toString() })
                }
                else{
                    PlayerController=0
                    Handler(Looper.getMainLooper()).post(Runnable { binding.playerText.text = "Player 1" })
                    totalpoint2 += livepoint
                    Handler(Looper.getMainLooper()).post(kotlinx.coroutines.Runnable { binding.player2point.text = totalpoint2.toString() })
                }
                visibility(imageView,list[0])
                list.removeAt(0)
            }
        }
    }
    fun same(string: String, string2: String): Int? {
        var x : Int
        if (string.contains(string2)) x=1
        else x=0
        return x
    }
    fun visibility(imageView: ImageView, imageViewx: ImageView){
        Handler(Looper.getMainLooper()).post(Runnable { imageView.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.genel)) })
        Handler(Looper.getMainLooper()).post(Runnable { imageViewx.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.genel)) })
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
        if(cardlist[i].card_name==cardlist[j].card_name){
            val gecici: Float = (-(cardlist[i].card_point+cardlist[j].card_point)/cardlist[i].house_point.toFloat())
            return gecici.toInt()
        }
        else{
            val gecici: Float = (-((cardlist[i].card_point+cardlist[i].card_point)/2.toFloat())*cardlist[i].house_point*cardlist[i].house_point)
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
        Handler(Looper.getMainLooper()).post(Runnable {
            while(list2.size>i) {
                if (list2[i].isClickable == true) {
                    break
                }
                i++
                if(i==35){
                    int1=i
                    val finish = Intent(this, EndgameActivity::class.java)
                    val data = SoundData(a)
                    val data2 = EndGameInfo(totalpoint1,totalpoint2)
                    finish.putExtra("key",data)
                    finish.putExtra("info",data2)
                    finishgamesound()
                    pauseSound()
                    startActivity(finish)
                    finish()
                }
            }
        })
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
    fun playSound() {
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(this, R.raw.prologue)
            mMediaPlayer!!.isLooping = true
            mMediaPlayer!!.start()
        } else mMediaPlayer!!.start()
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