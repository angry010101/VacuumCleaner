package com.pythonanywhere.mm.vacuumcleaner

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.LinearLayout
import java.util.*
import kotlin.concurrent.schedule



class MainActivity : AppCompatActivity() {
    var m = array2dOfInt(5, 5)
    val vh = VacuumCleaner()
    var trash_count = 0
    var lastEaten = false
    var g = false

    var garbage_step = 0
    var garbage_success = 0
    var game_counter = 0
    var results = mutableListOf<Int>()
    public inline fun <reified INNER> array2d(sizeOuter: Int, sizeInner: Int, noinline innerInit: (Int)->INNER): Array<Array<INNER>>
            = Array(sizeOuter) { Array<INNER>(sizeInner, innerInit) }
    public fun array2dOfInt(sizeOuter: Int, sizeInner: Int): Array<IntArray>
            = Array(sizeOuter) { IntArray(sizeInner) }
    fun ClosedRange<Int>.random() =
            Random().nextInt(endInclusive - start) +  start


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialization()
        val h = Handler()
        val r = object : Runnable {
            override fun run() {
                Log.d("Handlers", "Called on main thread");
                if (game_counter < 200){
                    calculate()
                    update()
                }
                else {
                    stats()
                    initialization()
                }
                game_counter++
                h.postDelayed(this, 1001L/(speed_view.progress+1));
            }
        }
        h.post(r);
    }

    fun count_apples(): Int{
        var apples = 0
        for (i in 0..4) {
            for (j in 0..4) {
                if (m[i][j] == Constants.GARBAGE){
                    apples++
                }
            }
        }
        return apples
    }
    fun stats(){
        results.add(count_apples())
        game_stats_view.text = ((results.sum()).toDouble()/ results.size).toString()
        game_number_view.text = results.size.toString()
    }

    fun initialization(){
        game_counter = 0
        val v_x = (0..5).random()
        val v_y = (0..5).random()
        vh.x = v_x
        vh.y = v_y
        for (i in 0..4) {
            for (j in 0..4) {
                m[i][j] = Constants.NOTHING
            }
        }
    }

    fun calculate(){
        if (!g) garbage_calculate()
        else
            vehicle_calculate()
        g = !g
    }

    fun vehicle_calculate(){
        val isThere = vh.checkGarbage(m)
        Log.d("Pair","" + isThere.first  + " " + isThere.second)
        if (!Pair<Int,Int>(-9,-9).equals(isThere)){
            //go
            //removee
            //mark last removed
            //return to route
            val shifted = vh.shift(isThere.first,isThere.second)
            m[shifted.first][shifted.second] = Constants.NOTHING


            val eaten = vh.eat()
            m[eaten.first][eaten.second] = Constants.NOTHING
            trash_count--
            lastEaten = true
            Log.d("garbage","eaten")
        }
        else{
            if (!vh.onMainRoute()){
                //reset visited

                val x = vh.GoToRouteIfTrue()

                if (x.equals(Pair(-9,-9))) throw Exception()
                m[x.first][x.second] = Constants.NOTHING

            }
            else {
                //val e = vh.do4thRoute()
               val e = vh.do5thRoute()
                m[e.first][e.second] = Constants.NOTHING

                //val e = vh.moveRoute()
                //m[e.first][e.second] = Constants.NOTHING
                /*
                if (lastEaten){
                    vh.GoToRoute()
                }
                else {
                    vh.GoToRouteIfTrue()
                }

                 */
            }
            //if not in route return to route
            //do route
            lastEaten = false
        }
    }

    /* Hi there */
    private fun garbage_calculate() {
        garbage_step++;
        val fate = (0..2).random() > 0.5
        Log.d("fate","booL : " + fate)
        if (!fate){
            garbage_success++
            var t_x = -1
            var t_y = -1
            do {
                 t_x = (0..5).random()
                 t_y = (0..5).random()
            } while ((t_x == vh.x && t_y == vh.y )|| m[t_x][t_y] == Constants.GARBAGE)
            m[t_x][t_y] = Constants.GARBAGE
            trash_count++
        }
        else {
            //another time, pal
        }
    }

    fun update(){
        Log.d("there","last" + " " + vh.last_x + " " + vh.last_y)

        Log.d("there","current" + " " + vh.x + " " + vh.y)
        main_grid.removeAllViews()
        game_apples_view.text = count_apples().toString()
        m[vh.x][vh.y] = Constants.VACUUM_CLEANER
        for (i in 0..4) {
            for (j in 0..4) {
                val v = ImageView(this);
                when(m[i][j]){
                    Constants.GARBAGE -> v.setImageResource(R.drawable.apple)
                    Constants.VACUUM_CLEANER ->  v.setImageResource(R.drawable.ic_launcher)
                    else -> v.setImageResource(R.drawable.ic_launcher_background)
                }

                val layoutParams = LinearLayout.LayoutParams(128, 128)
                v.setLayoutParams(layoutParams)

                main_grid.addView(v)
                v.requestLayout()
            }
        }
        if (garbage_step!=0) game_prob_view.text = (garbage_success.toDouble() / garbage_step).toString()
        game_counter_view.text = (game_counter / 2).toString()
        //vh.last_x = vh.x
        //vh.last_y = vh.y
    }
}
