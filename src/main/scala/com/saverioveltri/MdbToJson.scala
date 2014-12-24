package com.saverioveltri

import scala.collection.JavaConversions._
import com.healthmarketscience.jackcess.Database
import java.io.{ File, PrintWriter }
import com.healthmarketscience.jackcess.DatabaseBuilder
import com.healthmarketscience.jackcess.Table
import play.api.libs.json._
import play.api.libs.json.Json.toJson
import com.healthmarketscience.jackcess.DataType
import java.io._

case class converter(database: Database) {

  private def jsonTable(table: Table): JsValue = {

    val columns = table.getColumns();

    val tableMap = table.flatMap(
      row =>
        columns.map(
          column => {
           column.getType match {
              //              case DataType.MONEY => Map(column.getName -> row.getString(column.getName))
              case DataType.BOOLEAN => Map(column.getName -> (if (row.getBoolean(column.getName)) "1" else "0"))
              case _ => Map(column.getName -> row.get(column.getName).toString())
            }           
          })) reduce (_ ++ _)

    toJson(Map(table.getName -> tableMap))
  }

  def convert = {
    val jsonList = database.getTableNames.map(x => jsonTable(database.getTable(x)))

    toJson(jsonList)
  }

}

object MdbToJson {

  lazy val usage = "usage :  run [mdb file name] [json output]"

  def main(args: Array[String]): Unit = {

    if (args.length < 2) {
      println(usage)
    } else {

//      val database = DatabaseBuilder.open(new File("/Users/saverio/Documents/medicalOffice/import.mdb"))

      val database = DatabaseBuilder.open(new File(args(0)))
      
      val json = converter(database).convert

      val pw = new PrintWriter(new File(args(1)))
      pw.write(json.toString())
      pw.close
      
      println("successfully created " + args(1))
    }
  }
}
