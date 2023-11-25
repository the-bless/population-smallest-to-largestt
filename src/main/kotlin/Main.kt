
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.File

data class Country @JsonCreator constructor(
    @JsonProperty("country") val country: String,
    @JsonProperty("population") val population: Long
)


fun sortCountriesByPopulation(filePath: String): List<Country> {
    // Read JSON file
    val jsonString = File(filePath).readText()

    // Parse JSON using Jackson
    val objectMapper = ObjectMapper()
    val countries: List<Country> = objectMapper.readValue(jsonString)

    // Sort countries by population
    val sortedCountries = countries.sortedBy { it.population }

    return sortedCountries
}

fun main() {
    val filePath = "src/country.json"

    try {
        val sortedCountries = sortCountriesByPopulation(filePath)

        // Print sorted countries
        sortedCountries.forEach { country ->
            println("Country: ${country.country}, Population: ${country.population}")
        }
    } catch (e: Exception) {
        println("Error reading or parsing the JSON file: ${e.message}")
    }
}
