package com.pythonanywhere.mm.vacuumcleaner

import android.util.Log

/**
 * Created by User on 2/5/2018.
 */

class VacuumCleaner{
   /* var route = mutableListOf<Triple<Int,Int,Boolean>>(
            Triple(0,2,false),
            Triple(1,0,false),
            Triple(1,4,false),
            Triple(2,2,false),
            Triple(3,0,false),
            Triple(3,4,false),
            Triple(4,2,false)
            )
            */

    var current_position=0;
    var additionalRoute = mutableListOf<Triple<Int,Int,Boolean>>(
            Triple(1,0,false),
            Triple(0,3,false),
            Triple(4,1,false),
            Triple(3,4,false)
    )
    var route4 = mutableListOf<Triple<Int,Int,Boolean>>(
            Triple(1,1,false),
            Triple(2,1,false),
    Triple(3,1,false),
     //       Triple(4,1,false),
     //       Triple(3,1,false),

    Triple(3,2,false),
    Triple(3,3,false),
     //       Triple(3,4,false),
      //      Triple(3,3,false),

    Triple(2,3,false),
    Triple(1,3,false),
     //       Triple(0,3,false),
      //      Triple(1,3,false),
    Triple(1,2,false)
      //      Triple(1,1,false),
       //     Triple(1,0,false)


    //, Triple(2,2,false)

           )

    var route = mutableListOf<Triple<Int,Int,Boolean>>(
            Triple(1,0,false),
            Triple(2,0,false),
            Triple(3,0,false),

            Triple(3,1,false),
            Triple(3,2,false),
            Triple(3,3,false),
            Triple(3,4,false),
            Triple(2,4,false),
            Triple(1,4,false),
            Triple(1,3,false),
            Triple(1,2,false),
            Triple(1,1,false)


    )

    var current_pair_x=0
    var current_pair_y=0

    var orderSecond = false
    var x = -9
    var y = -9
    var last_x = -9
    var last_y = -9
    var turn = true


    var visitedBorder1 = false;
    var visitedBorder2 = false;
    var visitedBorder3 = false;
    var visitedBorder4 = false;

    var visitedBorderC1 = 0;
    var visitedBorderC2 = 0;
    var visitedBorderC3 = 0;
    var visitedBorderC4 = 0;

    init {
       var a = route.find{ t ->
           Log.d("","t.first: " + t.first + " " + t.second + " " + t.third )
           t.third
       }
        var b = route.find{ t ->
            Log.d("","t.first: " + t.first + " " + t.second + " " + t.third )
            true
        }

        Log.d("","" + a.toString() + " " + b.toString())
    }


    fun doRoute(){
        do{
            var f = route.filter { t : Triple<Int,Int,Boolean> -> t.second == current_pair_y  && t.third == false}
            if (!f.isEmpty()){
                current_pair_y += 2
                var ind = route.indexOf(f.first())
                var TripleToReplace = Triple<Int,Int,Boolean>(f.first().first,f.first().second,true)
                /*for (i in route){
                    Log.d("before", "delete " + i.toString())
                }
                route.removeAt(ind)
                route.add(ind,TripleToReplace)
                for (i in route){
                    Log.d("before", "delete " + i.toString())
                }*/
            }
            else {

                current_pair_x = if (current_pair_x==4) 0 else current_pair_x+2
                current_pair_y = if (current_pair_x == 2)  4 else 0
            }
        } while (f.isEmpty())

    }

    /*fun GoToRoute(){
        for (i in -1..1){
            for (j in -1..1){
                var e = route.find { t -> x == t.first+i && y == t.second+j }
                if (e != null) shift(i,j)
            }
        }
    }*/

    fun GoToRouteIfTrue(): Pair<Int,Int>{
        /*for (i in -1..1){
            for (j in -1..1){
                var e = route.find { t -> (t.first+i in (0..4) && t.second+j in (0..4))&& x == t.first+i && y == t.second+j && t.third }
                if (e != null) shift(i,j)
            }
        }*/
        for (i in -1..1){
            var e = route.find { t -> ((x+i) == t.first && y == t.second) } // && t.third
            if (e != null) {
                return shift(i,0)

            }
            e = route.find { t -> (x == t.first && (y+i) == t.second) } // && t.third
            if (e != null) {
                return shift(0,i)

            }

        }

        for (i in -1..1){
            var e = additionalRoute.find { t -> ((x+i) == t.first && (y) == t.second) } // && t.third
            if (e != null) {
                return shift(i,0)

            }
            e = additionalRoute.find { t -> (x == t.first && (y+i) == t.second) } // && t.third
            if (e != null) {
                return shift(0,i)

            }
        }



        return Pair(-9,-9)
    }

    fun doSecondRoute(): Pair<Int,Int>{
        if (onRoute()){
            if (y==2){
                if (x == 3){
                    if (orderSecond){
                        return shift(0,-1)
                    }
                    else {
                        return shift(0,1)
                    }
                }
                return shift(1,0)
            }
            else {
                if ((y == 0 || y == 4) && x == 1){
                    if (orderSecond){
                        orderSecond = !orderSecond
                        return shift(0,1)
                    }
                    else {
                        orderSecond = !orderSecond
                        return shift(0,-1)

                    }
                }
                else {
                  if (y == 0 || y ==4){
                      return shift(-1,0)
                  }
                    else {
                      if (orderSecond){
                          return shift(0,-1)

                      }
                      else {
                          return shift(0,1)
                      }
                  }
                }
            }
            //return nextRoute()
        }
        else {
            return GoToRouteIfTrue()
        }
    }


    fun doNext(): Pair<Int,Int>{
        current_position++;
        var p = route[current_position % route.size];
        return Pair<Int,Int>(p.first,p.second)
    }
    fun do5thRoute(): Pair<Int,Int> {
        if(onMainRoute()){
            if (y == 0){
                if (x==3){
                    return shift(0,1)
                }
                else{
                    return  shift(1,0)
                }
            }
            else{
                if (y==4){
                    if (x==1){
                        return shift(0,-1)
                    }
                    else{
                        return shift(-1,0)
                    }
                }
                else {
                    if (x==1){
                        return shift(0,-1)
                    }
                    else {
                        return shift(0,1)
                    }
                }
            }
        }
        else {
            return GoToRouteIfTrue()
        }
    }

    fun do4thRoute(): Pair<Int,Int>{
        if (onMainRoute()){
            if (x==1){
                visitedBorderC1 += 1;
                if (y == 1 ){
                    visitedBorder1 = !visitedBorder1

                    visitedBorderC1=0
                    if (visitedBorder1){
                        return shift(0,-1)
                    }
                    else {
                        return shift(1,0)
                    };
                }
                else {
                    visitedBorderC2 += 1;
                    if (y ==3){
                        visitedBorder2 = !visitedBorder2
                        visitedBorderC2=0
                        if (visitedBorder2 ){
                            return shift(0,-1)
                        }
                        else {

                            return shift(-1,0);
                        };
                    }
                    else {
                        return shift(0,-1);
                    }

                }
            }
            else {
                if (x==3){
                    if (y == 3 ){
                        visitedBorderC3=0
                        visitedBorder3 = !visitedBorder3
                        if (visitedBorder3){
                            return shift(-1,0)
                        }
                        else {
                            return shift(0,1);
                        };

                    }
                    else {
                        if (y==1 ){
                            visitedBorderC4=0
                            visitedBorder4 = !visitedBorder4
                            if (visitedBorder4){
                                return shift(1,0)
                            }
                            else {
                                return shift(0,1);
                            };
                        }
                        else {
                            return shift(0,1);
                        }

                    }
                }
                else {
                    if (y==1){
                        return shift(1,0);
                    }
                    else return shift(-1,0)
                }
            }
        }
        else {
            return GoToRouteIfTrue()
        }
    }

    fun doThirdRoute(): Pair<Int,Int>{
        if (onRoute()){
            if (x == 2){
                if (y == 4){
                    return shift(1,0)
                }
                return shift(-1,0)
            }
            else {
                if (x == 1){
                    if (y == 4){
                        return shift(1,0)
                    }
                    else return shift(0,1)
                }
                if (x == 3){
                    if (y == 0){
                        return shift(-1,0)
                    }
                    else return shift(0,-1)
                }
                return Pair(-9,-9)
            }
        }
        else {
            return GoToRouteIfTrue()
        }
    }
    /*
    fun moveRoute(): Pair<Int,Int>{
        //logic here
        if (onRoute()){
            if (onMainRoute()){
                //to additional anywhay
                //bug there
                if (last_y == 2 && last_x == 1 ){
                    if (turn){
                        turn = !turn
                        return shift(0, 1)
                    }
                    else {
                        turn = !turn
                        return shift(0, -1)
                    }
                }
                else {

                }
                for (i in listOf(-1,1)) {
                    var r = additionalRoute.find { t -> ((x + i) == t.first && y == t.second) }
                    if (r != null && !isLastPoint(r)) {
                        return shift(i, 0)
                    }
                }
                for (i in listOf(-1,1)) {
                    var r = additionalRoute.find { t -> ((x == t.first) && ((y + i) == t.second)) }
                    if (r != null && !isLastPoint(r)) {
                        return shift(0, i)
                    }
                }
            }
            else {
                if (x == 1 && y == 1 || x == 4 && y ==3){
                    return shift(0,-1);

                }

                if (x == 1 && y == 3 ){
                    return shift(0,1);

                }
                if (x == 0 || x == 4 || y == 0 || y ==4){
                    //go msin
                    //bug there
                    for (i in listOf(-1,1)) {
                        var r = route.find { t -> ((x + i) == t.first && y == t.second) }
                        if (r != null&& !isLastPoint(r)) {
                            return shift(i, 0)
                        }
                    }
                    for (i in listOf(-1,1)) {
                        var r = route.find { t -> (x == t.first && (y + i) == t.second) }
                        if (r != null && !isLastPoint(r)) {
                            return shift(0, i)
                        }
                    }
                    for (i in listOf(-1,1)) {

                        var r = additionalRoute.find { t -> ((x + i) == t.first && y == t.second) }
                        if (r != null && !isLastPoint(r)) {
                            return shift(i, 0)
                        }
                    }
                    for (i in listOf(-1,1)) {

                        var r = additionalRoute.find { t -> (x == t.first && (y + i) == t.second) }
                        if (r != null && !isLastPoint(r)) {
                            return shift(0, i)
                        }
                    }
                }
                else {
                    for (i in listOf(-1,1)){
                        Log.d("crash3", "zero 0 " )
                        var r = route.find { t -> ((x+i) == t.first && y == t.second) }
                        if (r != null && !isLastPoint(r)){
                            return shift(i,0)
                        }
                        Log.d("","one 1")
                        r = route.find { t -> (x.equals(t.first) && (y+i).equals(t.second)) }
                        if (r != null && !isLastPoint(r)){
                            return shift(0,i)
                        }

                        Log.d("","two 2")

                             r = additionalRoute.find { t -> ((x + i) == t.first && y == t.second) }
                            if (r != null && !isLastPoint(r)) {
                                return shift(i, 0)
                            }

                        Log.d("","three 3")
                            r = additionalRoute.find { t -> (x == t.first && (y + i) == t.second) }
                            if (r != null && !isLastPoint(r)) {
                                return shift(0, i)
                            }
                        Log.d("","four 4")

                    }
                }
            }
        }
        else {
            return  GoToRouteIfTrue()
        }
        return Pair(-9,-9)
    }*/

    fun isLastPoint(p: Triple<Int,Int,Boolean>): Boolean{
        if (last_x == p.first){
            if (last_y == p.second){
                return true
            }
        }
        return false;
    }

    fun onRoute(): Boolean{
        var bmain = onMainRoute()
        return bmain
         if (!bmain){
            return if (additionalRoute.find { t -> x == t.first && y == t.second } != null) true else false
        }
        else {
            return true
        }
    }

    fun onMainRoute(): Boolean{
        return if (route.find { t -> x == t.first && y == t.second } != null) true else false
    }

    fun position(x: Int,y:Int){
        //last_x = this.x
        //last_y = this.y
        this.x = x
        this.y = y

        Log.d("here","last" + " " + this.last_x + " " + this.last_y)

        Log.d("here","current" + " " + x + " " + y)
    }



    fun shift(x: Int, y:Int): Pair<Int,Int>{
        val lx = this.x
        val ly = this.y
        if (this.x + x in (0..4))
            position(this.x + x,this.y)
        if (this.y + y in (0..4))
            position(this.x,this.y + y)
        last_x = lx
        last_y = ly

        return Pair<Int,Int>(lx,ly)
    }

    fun checkGarbage(m: Array<IntArray>): Pair<Int,Int>{
        for (i in -1..1){
                if ( x+i in (0..4) && y in (0..4) &&  m[x+i][y] == Constants.GARBAGE){
                    return Pair(i,0)
                }
        }
        for (i in -1..1){
            if ( y+i in (0..4) && x in (0..4) &&  m[x][y+i] == Constants.GARBAGE){
                return Pair(0,i)
            }
        }
        return Pair(-9,-9)
    }

    fun eat(): Pair<Int,Int>{

        return Pair(x,y)
    }
}