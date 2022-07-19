/**
 * Created on 2022-07-19
 * @author Laszlo TAMAS9
 */
class Georef {

    fun dms2GEOREF(coord: DMSData): String {
        println("getGeoref coord $coord")
        return georefLon15(coord.LonDeg, coord.LonHemisphere) +
                georefLat15(coord.LatDeg, coord.LatHemisphere) +
                georefLon1515(coord.LonDeg, coord.LonHemisphere) +
                georefLat1515(coord.LatDeg, coord.LatHemisphere) +
                georef151560(coord.LonMin, coord.LonSec, coord.LonHemisphere) +
                georef151560(coord.LatMin, coord.LatSec, coord.LatHemisphere)
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
                println("georef2DMS lon1Val $lon1Val")
                val lat1 = matcher.group(2)
                var lat1Val = georef15IDVal(lat1)
                println("georef2DMS lat1Val $lat1Val")
                val lon2 = matcher.group(3)
                val lon2Val = georef1515IDVal(lon2)
                println("georef2DMS lon2Val $lon2Val")
                val lat2 = matcher.group(4)
                val lat2Val = georef1515IDVal(lat2)
                println("georef2DMS lat2Val $lat2Val")
                if (lon1Val < 180) {
                    lon1Val = 180 - lon1Val
                    lonMulti = -1
                } else {
                    lon1Val -= 180
                    lonMulti = 1
                }
                println("georef2DMS lon1Val mod $lon1Val")
                lon1Val = lon1Val + lonMulti * lon2Val
                println("georef2DMS lon1Val mod2 $lon1Val")
                if (lat1Val < 90) {
                    lat1Val = 90 - lat1Val
                    latMulti = -1
                } else {
                    lat1Val -= 90
                    latMulti = 1
                }
                println("georef2DMS lat1Val mod $lat1Val")
                lat1Val = lat1Val + latMulti * lat2Val
                println("georef2DMS lat1Val mod2 $lat1Val")

                if (mLength == 4) {
                    var nums = matcher.group(5)
                    var lon3 = nums.substring(0, 2)
                    var lat3 = nums.substring(2, 4)
                    println("georef2DMS nums $lon3 $lat3")
                    var lon4 = lon1Val.toDouble() + lonMulti * (lon3.toDouble() / 60.0)
                    var lat4 = lat1Val.toDouble() + latMulti * (lat3.toDouble() / 60.0)
                    lon4 = lonMulti * lon4
                    lat4 = latMulti * lat4
                    println("georef2DMS nums2 $lon4 $lat4")
                    retVal.Latitude = lat4
                    retVal.Longitude = lon4
                }
            }
        }
        println("georef2DMS - RETURN $retVal")
        return retVal

    }

    fun georef1515IDVal(coord: String): Int {
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

    fun georef15IDVal(coord: String): Int {

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

    fun georefLon15(lonDeg: Int, lonHem: String): String {
        println("getLon15 lonDeg $lonDeg lonHem $lonHem")
        var tempDeg = lonDeg
        if (lonHem == "E") {
            tempDeg = 180 + tempDeg
        } else {
            tempDeg = 180 - tempDeg
        }
        val retVal = georef15ID(tempDeg)
        println("getLon15 RETURN $retVal")
        return retVal
    }

    fun georefLat15(latDeg: Int, latHem: String): String {
        println("getLat15 latDeg $latDeg latHem $latHem")
        var tempDeg = latDeg
        if (latHem == "N") {
            tempDeg = tempDeg + 90
        } else {
            tempDeg = 90 - tempDeg
        }
        val retVal = georef15ID(tempDeg)
        println("getLat15 RETURN $retVal")
        return retVal
    }

    fun georefLon1515(lonDeg: Int, lonHem: String): String {
        println("getLon1515 lonDeg $lonDeg lonHem $lonHem")
        var tempVal = lonDeg - ((lonDeg.toDouble() / 15).toInt() * 15).toInt()
        if (lonHem == "W") {
            tempVal = 14 - tempVal
        }
        println("getLon1515 tempVal $tempVal")
        val retVal = georef1515ID(tempVal)
        println("getLon1515 RETURN $retVal")
        return retVal
    }

    fun georefLat1515(latDeg: Int, latHem: String): String {
        println("getLat1515 latDeg $latDeg latHem $latHem")
        var tempVal = latDeg - ((latDeg.toDouble() / 15).toInt() * 15).toInt()
        if (latHem == "S") {
            tempVal = 14 - tempVal
        }
        println("getLat1515 tempVal $tempVal")
        val retVal = georef1515ID(tempVal)
        println("getLat1515 RETURN $retVal")
        return retVal
    }


    fun georef151560(min: Int, sec: Double, hem: String): String {
        var temp = min + (sec / 60)
        if (hem == "W" || hem == "S") {
            temp = 60.0 - temp
        }
        var retVal = temp.toInt().toString().padStart(2, '0').take(2)
        return retVal
    }

    fun georef15ID(deg: Int): String {
        if (0 <= deg && deg < 15) {
            return "A"
        }
        if (15 <= deg && deg < 30) {
            return "B"
        }
        if (30 <= deg && deg < 45) {
            return "C"
        }
        if (45 <= deg && deg < 60) {
            return "D"
        }
        if (60 <= deg && deg < 75) {
            return "E"
        }
        if (75 <= deg && deg < 90) {
            return "F"
        }
        if (90 <= deg && deg < 105) {
            return "G"
        }
        if (105 <= deg && deg < 120) {
            return "H"
        }
        if (120 <= deg && deg < 135) {
            return "J"
        }
        if (135 <= deg && deg < 150) {
            return "K"
        }
        if (150 <= deg && deg < 165) {
            return "L"
        }
        if (165 <= deg && deg < 180) {
            return "M"
        }
        if (180 <= deg && deg < 195) {
            return "N"
        }
        if (195 <= deg && deg < 210) {
            return "P"
        }
        if (210 <= deg && deg < 225) {
            return "Q"
        }
        if (225 <= deg && deg < 240) {
            return "R"
        }
        if (240 <= deg && deg < 255) {
            return "S"
        }
        if (255 <= deg && deg < 270) {
            return "T"
        }
        if (270 <= deg && deg < 285) {
            return "U"
        }
        if (285 <= deg && deg < 300) {
            return "V"
        }
        if (300 <= deg && deg < 315) {
            return "W"
        }
        if (315 <= deg && deg < 330) {
            return "X"
        }
        if (330 <= deg && deg < 345) {
            return "Y"
        }
        if (345 <= deg && deg < 360) {
            return "Z"
        }

        return ""
    }

    fun georef1515ID(deg: Int): String {
        val letters: MutableMap<Int, String> = HashMap()
        letters[0] = "A"
        letters[1] = "B"
        letters[2] = "C"
        letters[3] = "D"
        letters[4] = "E"
        letters[5] = "F"
        letters[6] = "G"
        letters[7] = "H"
        letters[8] = "J"
        letters[9] = "K"
        letters[10] = "L"
        letters[11] = "M"
        letters[12] = "N"
        letters[13] = "P"
        letters[14] = "Q"
        val retVal = letters[deg]!!
        return retVal
    }
}