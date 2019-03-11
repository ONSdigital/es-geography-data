package uk.gov.ons.es.csv


import java.io.{FileNotFoundException, InputStream, InputStreamReader, Reader}

import scalaz.{NonEmptyList, ValidationNel}
import uk.gov.nationalarchives.csv.validator.FailMessage
import uk.gov.nationalarchives.csv.validator.api.CsvValidator
import uk.gov.nationalarchives.csv.validator.schema.Schema

private object ResourceLoader {
  private val loader = this.getClass.getClassLoader

  private def getStream(resourceName: String): Option[InputStream] =
    Option(loader.getResourceAsStream(resourceName))

  def getReaderFor(resourceName: String): Option[InputStreamReader] =
    getStream(resourceName).map(new InputStreamReader(_))

  def requireReaderFor(resourceNamed: String): InputStreamReader =
    getReaderFor(resourceNamed).getOrElse(throw new FileNotFoundException(s"Unable to find resource [$resourceNamed] on the classpath"))
}

object Validator {
  private val csvValidator = CsvValidator.createValidator(
    failFast = false,
    pathSubstitutionsList = Nil,
    enforceCaseSensitivePathChecksSwitch = false,
    traceSwitch = false
  )

  def validateSchema(schemaReader: Reader): ValidationNel[FailMessage, Schema] =
    csvValidator.parseSchema(schemaReader)

  def requireValidSchema(schemaReader: Reader): Schema =
    validateSchema(schemaReader).fold(
      failMessages => throw new IllegalStateException(s"Target schema is invalid [$failMessages]"),
      identity
    )

  def validationOf(csvReader: Reader)(against: Schema): ValidationNel[NonEmptyList[FailMessage], Any] =
    csvValidator.validate(csvReader, against, progress = None).toValidationNel
}

object CsvSchema {
  def fromResource(named: String): Schema =
    Validator.requireValidSchema(ResourceLoader.requireReaderFor(named))
}

object CsvData {
  def csvFile(named: String): InputStreamReader =
    ResourceLoader.requireReaderFor(named)
}