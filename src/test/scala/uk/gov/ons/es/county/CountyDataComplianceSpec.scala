package uk.gov.ons.es.county


import org.scalatest.{FreeSpec, Matchers}
import uk.gov.ons.es.csv.CsvData.csvFile
import uk.gov.ons.es.csv.CsvSchema
import uk.gov.ons.es.csv.Validator.validationOf

class CountyDataComplianceSpec extends FreeSpec with Matchers {
  "The CountyRegion CSV file" - {
    "complies with the target schema" in {
      val schema = CsvSchema.fromResource(named = "schema/county.csvs")

      validationOf(csvFile(named = "data/countyRegion.csv"))(against = schema) should be a 'success
    }
  }
}
