import kotlin.math.ceil

/**
 * Created on 2022-07-19
 * @author Laszlo TAMAS9
 */
class Gars {

    fun deg2GARS(coord: DEGData): String {
        var retVal = ""
        var lon1 = 180.0 + coord.Longitude
        var lon2 = ceil(lon1 * 2).toInt().toString()
        println("deg2GARS lon2 $lon2")
        retVal += lon2
        return retVal
    }
}