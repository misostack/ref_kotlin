package net.jsbase.helloworld

import java.time.LocalDate

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main(args: Array<String>) {
    val defaultName = "Kotlin"
    var output = if (args.isNotEmpty()) args[0] else defaultName

    output = "Hello, $output!"

    //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
    // to see how IntelliJ IDEA suggests fixing it.
    println(output)

    // February 15, 2016
    val kotlinFirstReleaseDate = LocalDate.parse("2016-02-15")
    val yearsSinceKotlinFirstRelease: Int = LocalDate.now().year - kotlinFirstReleaseDate.year

    println("The first version of Kotlin was released $yearsSinceKotlinFirstRelease years ago at ${kotlinFirstReleaseDate.toString()}.")

    for (i in 1..5) {
        //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
        // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
        println("i = $i")
    }
}