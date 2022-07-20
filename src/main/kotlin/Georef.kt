import kotlin.math.abs

/**
 * Created on 2022-07-19
 * @author Laszlo TAMAS9
 */
class Georef {

    fun deg2GEOREF(coord: DEGData): String {
        println("deg2GEOREF coord $coord")
        var retVal = georefLon15(coord.Longitude) +
                georefLat15(coord.Latitude) +
                georefLon1515(coord.Longitude) +
                georefLat1515(coord.Latitude) +
                georef151560(coord.Longitude) +
                georef151560(coord.Latitude)
        println("deg2GEOREF RETURN $retVal")
        return retVal
    }

    fun georef2DEG(coord: String): DEGData {
        var retVal = DEGData(0.0, 0.0)
        val tempStr = coord.replace("\\s+".toRegex(), "")
        val pattern = GeneralData.patternGEOREF
        val matcher = pattern.matcher(tempStr)
        var matchCount = 0
        while (matcher.find()) {
//            matchCount++
//            System.out.printf(
//                "Match count: %s, Group Zero Text: '%s'%n", matchCount,
//                matcher.group()
//            )
//            for (i in 1..matcher.groupCount()) {
//                System.out.printf(
//                    "Capture Group Number: %s, Captured Text: '%s'%n", i,
//                    matcher.group(i)
//                )
//            }
            var mLength = matcher.group(5).length
            if (mLength % 2 == 0) {
                var lonMulti = 1
                var latMulti = 1
                val lon1 = matcher.group(1)
                var lon1Val = georef15IDVal(lon1)
                println("georef2DEG lon1Val $lon1Val")
                val lat1 = matcher.group(2)
                var lat1Val = georef15IDVal(lat1)
                println("georef2DEG lat1Val $lat1Val")
                val lon2 = matcher.group(3)
                val lon2Val = georef1515IDVal(lon2)
                println("georef2DEG lon2Val $lon2Val")
                val lat2 = matcher.group(4)
                val lat2Val = georef1515IDVal(lat2)
                println("georef2DEG lat2Val $lat2Val")
                if (lon1Val < 180) {
                    lon1Val = 180 - lon1Val
                    lonMulti = -1
                } else {
                    lon1Val -= 180
                    lonMulti = 1
                }
                println("georef2DEG lon1Val mod $lon1Val")
                lon1Val += lonMulti * lon2Val
                println("georef2DEG lon1Val mod2 $lon1Val")
                if (lat1Val < 90) {
                    lat1Val = 90 - lat1Val
                    latMulti = -1
                } else {
                    lat1Val -= 90
                    latMulti = 1
                }
                println("georef2DEG lat1Val mod $lat1Val")
                lat1Val += latMulti * lat2Val
                println("georef2DEG lat1Val mod2 $lat1Val")
                var nums = matcher.group(5).take(4)
                var lon3 = nums.substring(0, 2)
                var lat3 = nums.substring(2, 4)
                println("georef2DEG nums $lon3 $lat3")
                var lon4 = lon1Val.toDouble() + lonMulti * (lon3.toDouble() / 60.0)
                var lat4 = lat1Val.toDouble() + latMulti * (lat3.toDouble() / 60.0)
                lon4 *= lonMulti
                lat4 *= latMulti
                println("georef2DEG nums2 $lon4 $lat4")
                retVal.Latitude = lat4
                retVal.Longitude = lon4

            }
        }
        println("georef2DEG - RETURN $retVal")
        return retVal

    }

    private fun georefLon15(longitude: Double): String {
        println("getLon15 lonDeg $longitude")
        var tempDeg = abs(longitude.toInt())
        if (longitude >= 0) {
            tempDeg += 180
        } else {
            tempDeg = 180 - tempDeg
        }
        val retVal = georef15ID(tempDeg)
        println("getLon15 RETURN $retVal")
        return retVal
    }

    private fun georefLat15(latitude: Double): String {
        println("getLat15 latDeg $latitude")
        var tempDeg = abs(latitude.toInt())
        if (latitude >= 0) {
            tempDeg += 90
        } else {
            tempDeg = 90 - tempDeg
        }
        val retVal = georef15ID(tempDeg)
        println("getLat15 RETURN $retVal")
        return retVal
    }

    private fun georefLon1515(longitude: Double): String {
        println("getLon1515 lonDeg $longitude")
        var tempDeg = abs(longitude.toInt())
        var tempVal = tempDeg - ((tempDeg.toDouble() / 15).toInt() * 15).toInt()
        if (longitude < 0) {
            tempVal = 14 - tempVal
        }
        println("getLon1515 tempVal $tempVal")
        val retVal = georef1515ID(tempVal)
        println("getLon1515 RETURN $retVal")
        return retVal
    }

    private fun georefLat1515(latitude: Double): String {
        println("getLat1515 latDeg $latitude")
        var tempDeg = abs(latitude.toInt())
        var tempVal = tempDeg - ((tempDeg.toDouble() / 15).toInt() * 15).toInt()
        if (latitude < 0) {
            tempVal = 14 - tempVal
        }
        println("getLat1515 tempVal $tempVal")
        val retVal = georef1515ID(tempVal)
        println("getLat1515 RETURN $retVal")
        return retVal
    }

    private fun georef151560(coord: Double): String {
        var temp = abs(coord - coord.toInt()) * 60
        if (coord < 0) {
            temp = 60.0 - temp
        }
        var retVal = temp.toInt().toString().padStart(2, '0').take(2)
        return retVal
    }

    private fun georef15ID(deg: Int): String {
        val letters01 = "ABCDEFGHJKLMNPQRSTUVWXYZ"
        val char01 = letters01.toCharArray()[deg / 15]
        return char01.toString()
    }

    private fun georef1515ID(index: Int): String {
        val letters01 = "ABCDEFGHJKLMNPQ"
        val char01 = letters01.toCharArray()[index]
        val retVal = char01.toString()
        return retVal
    }

    private fun georef1515IDVal(coord: String): Int {
        val letters: MutableMap<String, Int> = HashMap()
        letters["A"] = 0
        letters["B"] = 1
        letters["C"] = 2
        letters["D"] = 3
        letters["E"] = 4
        letters["F"] = 5
        letters["G"] = 6
        letters["H"] = 7
        letters["J"] = 8
        letters["K"] = 9
        letters["L"] = 10
        letters["M"] = 11
        letters["N"] = 12
        letters["P"] = 13
        letters["Q"] = 14
        val retVal = letters[coord]!!
        return retVal
    }

    private fun georef15IDVal(coord: String): Int {

        val letters: MutableMap<String, Int> = HashMap()
        letters["A"] = 0
        letters["B"] = 15
        letters["C"] = 30
        letters["D"] = 45
        letters["E"] = 60
        letters["F"] = 75
        letters["G"] = 90
        letters["H"] = 105
        letters["J"] = 120
        letters["K"] = 135
        letters["L"] = 150
        letters["M"] = 165
        letters["N"] = 180
        letters["P"] = 195
        letters["Q"] = 210
        letters["R"] = 225
        letters["S"] = 240
        letters["T"] = 255
        letters["U"] = 270
        letters["V"] = 285
        letters["W"] = 300
        letters["X"] = 315
        letters["Y"] = 330
        letters["Z"] = 345
        val retVal = letters[coord]!!
        return retVal
    }

}