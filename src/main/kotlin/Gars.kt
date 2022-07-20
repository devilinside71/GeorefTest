import kotlin.math.ceil

/**
 * Created on 2022-07-19
 * @author Laszlo TAMAS9
 */
class Gars {

    fun deg2GARS(coord: DEGData): String {
        println("deg2GARS coord $coord")
        var retVal = ""
        var lon1 = 180.0 + coord.Longitude
        var lon2 = ceil(lon1 * 2).toInt().toString().padStart(3, '0').take(3)

        println("deg2GARS lon2 $lon2")
        retVal += lon2 + garsLatBand(coord.Latitude) +
                garsQudrant(coord).toString() + garsNinth(coord).toString()
        return retVal
    }

    fun gars2DEG(coord:String):DEGData{
        var retVal=DEGData(0.0,0.0)

        return retVal
    }
    fun garsLatBand(lat: Double): String {
        println("garsLatBand lat $lat")
        var retVal = ""
        var tempLatMin = (90.0 + lat) * 2
        println("garsLatBand tempLatMin $tempLatMin")

        val letters01 = "ABCDEFGHJKLMNPQ"
        val letters02 = "ABCDEFGHJKLMNPQRSTUVWXYZ"
        val index01 = (tempLatMin / 24.0).toInt()
        println("garsLatBand index01 $index01")
        val char01 = letters01.toCharArray()[index01]
        println("garsLatBand char01 $char01")
        val index02 = (tempLatMin - (index01 * 24)).toInt()
        println("garsLatBand index02 $index02")
        val char02 = letters02.toCharArray()[index02]
        println("garsLatBand char02 $char02")
        retVal = char01.toString() + char02.toString()
        println("garsLatBand RETURN $retVal")
        return retVal
    }

    fun garsQudrant(coord: DEGData): Int {
        println("garsQudrant coord $coord")
        var retVal = 0
        var tempLatMin = (90.0 + coord.Latitude) * 2
        var tempLonMin = (180.0 + coord.Longitude) * 2
        println("garsQudrant tempLatMin $tempLatMin tempLonMin $tempLonMin")
        val quadLat = ((tempLatMin - tempLatMin.toInt()) * 2).toInt()
        val quadLon = ((tempLonMin - tempLonMin.toInt()) * 2).toInt()
        println("garsQudrant quadLat $quadLat quadLon $quadLon")
        val valArr: Array<IntArray> = arrayOf(
            intArrayOf(3, 4),
            intArrayOf(1, 2)
        )
        retVal = valArr[quadLat][quadLon]
        println("garsQudrant RETURN $retVal")
        return retVal
    }

    fun garsNinth(coord: DEGData): Int {
        var retVal = 0
        var tempLatMin = (90.0 + coord.Latitude) * 2
        var tempLonMin = (180.0 + coord.Longitude) * 2
        val valArr: Array<IntArray> = arrayOf(
            intArrayOf(7, 8, 9, 7, 8, 9),
            intArrayOf(4, 5, 6, 4, 5, 6),
            intArrayOf(1, 2, 3, 1, 2, 3),
            intArrayOf(7, 8, 9, 7, 8, 9),
            intArrayOf(4, 5, 6, 4, 5, 6),
            intArrayOf(1, 2, 3, 1, 2, 3),
        )
        val quadLat = ((tempLatMin - tempLatMin.toInt()) * 6).toInt()
        val quadLon = ((tempLonMin - tempLonMin.toInt()) * 6).toInt()
        retVal = valArr[quadLat][quadLon]
        println("garsNinth RETURN $retVal")
        return retVal
    }
}