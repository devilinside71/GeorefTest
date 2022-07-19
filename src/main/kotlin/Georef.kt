/**
 * Created on 2022-07-19
 * @author Laszlo TAMAS9
 */
class Georef {

    fun getGeoref(coord: DMSData): String {
        println("getGeoref coord $coord" )
        return getLon15(coord.LonDeg, coord.LonHemisphere) +
                getLat15(coord.LatDeg, coord.LatHemisphere) +
                getLon1515(coord.LonDeg, coord.LonHemisphere) +
                getLat1515(coord.LatDeg, coord.LatHemisphere)
    }

    fun getLon15(lonDeg: Int, lonHem: String): String {
        println("getLon15 lonDeg $lonDeg lonHem $lonHem")
        var tempDeg = lonDeg
        if (lonHem == "E") {
            tempDeg = 180 + tempDeg
        } else {
            tempDeg = 180 - tempDeg
        }
        val retVal = get15ID(tempDeg)
        println("getLon15 RETURN $retVal")
        return retVal
    }

    fun getLat15(latDeg: Int, latHem: String): String {
        println("getLat15 latDeg $latDeg latHem $latHem")
        var tempDeg = latDeg
        if (latHem == "N") {
            tempDeg = tempDeg + 90
        }
        val retVal = get15ID(tempDeg)
        println("getLat15 RETURN $retVal")
        return retVal
    }

    fun getLon1515(lonDeg: Int, lonHem: String): String {
        println("getLon1515 lonDeg $lonDeg lonHem $lonHem")
        var tempVal = lonDeg - ((lonDeg.toDouble() / 15).toInt() * 15).toInt()
        if (lonHem == "W") {
            tempVal = 14 - tempVal
        }
        println("getLon1515 tempVal $tempVal")
        val retVal = get1515ID(tempVal)
        println("getLon1515 RETURN $retVal")
        return retVal
    }

    fun getLat1515(latDeg: Int, latHem: String): String {
        println("getLat1515 latDeg $latDeg latHem $latHem")
        var tempVal = latDeg - ((latDeg.toDouble() / 15).toInt() * 15).toInt()
        if (latHem == "S") {
            tempVal = 14 - tempVal
        }
        println("getLat1515 tempVal $tempVal")
        val retVal = get1515ID(tempVal)
        println("getLat1515 RETURN $retVal")
        return retVal
    }

    fun get15ID(deg: Int): String {
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

    fun get1515ID(deg: Int): String {
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