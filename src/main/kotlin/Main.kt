import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.File

data class Country @JsonCreator constructor(
    @JsonProperty("country") val country: String,
    @JsonProperty("population") val population: Long
)

fun sortCountriesByLetters(filePath: String, letters: Set<Char>): List<Country> {
    // Read JSON file
    val jsonString = File(filePath).readText()

    // Parse JSON using Jackson
    val objectMapper = ObjectMapper()
    val countries: List<Country> = objectMapper.readValue(jsonString)

    // Sort countries by the presence of specified letters in the country name
    val sortedCountries = countries.filter { country ->
        country.country.toLowerCase().any { it in letters }
    }.sortedBy { it.country }

    return sortedCountries
}

fun main() {
    val filePath = "src/country.json"
    val lettersToSort = setOf('p', 's', 'k')

    try {
        val sortedCountries = sortCountriesByLetters(filePath, lettersToSort)

        // Print sorted countries
        sortedCountries.forEach { country ->
            println("Country: ${country.country}, Population: ${country.population}")
        }
    } catch (e: Exception) {
        println("Error reading or parsing the JSON file: ${e.message}")
    }
}
