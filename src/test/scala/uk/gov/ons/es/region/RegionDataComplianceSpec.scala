package uk.gov.ons.es.region


import org.scalatest.{FreeSpec, Matchers}
import uk.gov.ons.es.csv.CsvData.csvFile
import uk.gov.ons.es.csv.CsvSchema
import uk.gov.ons.es.csv.Validator.validationOf

class RegionDataComplianceSpec extends FreeSpec with Matchers {
  "The RegionLookup CSV file" - {
    "complies with the target schema" in {
      val schema = CsvSchema.fromResource(named = "schema/region.csvs")

      validationOf(csvFile(named = "data/regionLookup.csv"))(against = schema) should be a 'success
    }
  }
}