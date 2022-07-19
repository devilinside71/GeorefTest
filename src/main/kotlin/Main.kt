fun main(args: Array<String>) {
    val geoRef = Georef()
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")



    //region Testdata
    val test01 = DMSData(23,35,40.1,"S",46,35,28.99,"W")
    val test02 = DMSData(16,42,27.89,"N",2,59,11.41,"W")
    val test03 = DMSData(34,32,27.91,"N",69,18,25.33,"E")
    val test04 = DMSData(47,9,48.69,"N",17,58,11.73,"E")


    //endregion

    println("Test 01  23°35'40.10\"S,46°35'28.99\"W >> JEPG2424 >> ${geoRef.getGeoref(test01)}")
    println("Test 02  16°42'27.89\"N,2°59'11.41\"W >> MHNB 042 >> ${geoRef.getGeoref(test02)}")
    println("Test 03  34°32'27.91\"N,69°18'25.33\"E >> SJKE1832 >> ${geoRef.getGeoref(test03)}")
    println("Test 04  47°09'48.69\"N,17°58'11.73\"E >> PKCC58 9 >> ${geoRef.getGeoref(test04)}")

}