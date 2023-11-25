import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.File

data class Country @JsonCreator constructor(
    @JsonProperty("country") val country: String,
    @JsonProperty("population") val population: Long
)

fun filterCountriesByPopulationAndLetters(filePath: String): List<Country> {
    // Read JSON file
    val jsonString = File(filePath).readText()

    // Parse JSON using Jackson
    val objectMapper = ObjectMapper()
    val countries: List<Country> = objectMapper.readValue(jsonString)

    // Filter countries with odd population and 5, 6, or 7 letters in the name
    val filteredCountries = countries.filter { country ->
        country.population % 2 != 0.toLong() && country.country.length in 5..7
    }

    return filteredCountries
}

fun main() {
    val filePath = "src/country.json"

    try {
        val filteredCountries = filterCountriesByPopulationAndLetters(filePath)

        // Print filtered countries
        if (filteredCountries.isNotEmpty()) {
            println("Countries with odd population and 5, 6, or 7 letters in the name:")
            filteredCountries.forEach { country ->
                println("Country: ${country.country}, Population: ${country.population}")
            }
        } else {
            println("No countries found with the specified criteria.")
        }
    } catch (e: Exception) {
        println("Error reading or parsing the JSON file: ${e.message}")
    }
}
