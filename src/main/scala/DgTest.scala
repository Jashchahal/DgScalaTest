
import com.mongodb.casbah.{MongoClient, MongoClientURI}
import com.mongodb.spark.MongoSpark
import com.mongodb.spark.config.ReadConfig
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._


/**
  * Created by root on 3/1/18.
  */
object DgTest {

  var cols = ""
  var colList: List[(String, String, Boolean)] = List()


  def main(args: Array[String]): Unit = {

    var dburi ="mongodb://192.168.6.118:27017/"

    //GET SPARK SESSION
    val conf = new SparkConf().setMaster("local[2]").setAppName("DgMongoSparkConnect").set("spark.app.id", "DgMongoDetection")
    val spark = SparkSession.builder().config(conf).getOrCreate()
    Logger.getRootLogger.setLevel(Level.ERROR)


    //GET METADATA using CASBAH
    val clientUri = MongoClientURI(dburi)
    val mongoClient = MongoClient(clientUri)
    val dbNames = mongoClient.dbNames()
    dbNames.foreach(db => processDatabases(spark, mongoClient, db, dburi))


    //CLEANUP
    mongoClient.close()
    spark.stop()


  }


  def processDatabases(spark: SparkSession, mongoClient: MongoClient, db: String, mongodbURI : String): Unit = {

    val collections = mongoClient.getDB(db).collectionNames().foreach(c => {
      println("Now Processing :: " + db + "." + c)
      processData(db, c, mongodbURI, spark)

    })


  }

  def processData(d: String, c: String, dbURI: String, spark: SparkSession): Unit = {
    colList = List()
    cols = ""

    val uri = s"$dbURI$d.$c"
    val readConfig2 = ReadConfig(Map("uri" -> uri))

    val rdd2 = MongoSpark.load(spark, readConfig2)  //GET DATA

    if (rdd2.columns.length > 1) {

      findFields("", rdd2.schema, false) //GET COLUMNS
      cols = cols.substring(0, cols.length - 1)

      rdd2.createOrReplaceTempView("temp1")

      val data2 = spark.sql("select " + cols + " from temp1 limit 2")

      //data.map(t => t(0) + "," + t(1)).collect().foreach(println)
      data2.foreach(r => {
        var i = 0
        for (i <- 0 to colList.length - 1) {
          if (colList(i)._3)
            print(r.getList(i) + " || ")
          else

            print(r.get(i) + " || ")

        }
        println()


      })


    }
  }


  def findFields(path: String, dt: DataType, array: Boolean): Unit = dt match {

    case s: StructType =>
      if (path.equals(""))
        s.fields.foreach(f => findFields(f.name, f.dataType, array))
      else
        s.fields.foreach(f => findFields(path + "." + f.name, f.dataType, array))
    case a: ArrayType =>
      // println(a.elementType.typeName )

      if (a.elementType.isInstanceOf[StructType])

        a.elementType.asInstanceOf[StructType].fields.foreach(f => findFields(path + "." + f.name, f.dataType, true))

      else {
        // println(s"$path: " + "Array[" + a.elementType.sql + "] : " + array)
        cols += s"$path,"
        colList = colList :+(s"$path", "Array[" + a.elementType.typeName + "]", array)

      }
    //   println( a.elementType )
    //   findFields(path + "." , a.elementType)
    // println(a.elementType.sql )

    case other =>
      cols += s"$path,"
      colList = colList :+(s"$path", s"$other", array)
    // println(s"$path: $other  : $array")
  }


}

// var uri = "mongodb://192.168.6.118/northwind.complex"
//val readConfig = ReadConfig(Map("uri" -> uri))


//val rdd = MongoSpark.load(spark,readConfig);
//findFields("", rdd.schema, false)
//cols = cols.substring(0, cols.length - 1)

//rdd.createOrReplaceTempView("temp1")

// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@


//colList = List()
//cols=""
//
//uri = "mongodb://192.168.6.118/northwind.customers"
//// conf.set("spark.mongodb.input.uri", uri).set("spark.mongodb.output.uri", uri)
//
//// spark = SparkSession.builder().config(conf).getOrCreate()
////   Logger.getRootLogger.setLevel(Level.ERROR)
//
//val readConfig2 = ReadConfig(Map("uri" -> uri))
//
//val rdd2 = MongoSpark.load(spark,readConfig2);
//findFields("", rdd2.schema, false)
//cols = cols.substring(0, cols.length - 1)
//
//rdd2.createOrReplaceTempView("temp1")
//
//val data2 = spark.sql("select " + cols + " from temp1 limit 2")
//
////data.map(t => t(0) + "," + t(1)).collect().foreach(println)
//data2.foreach(r => {
//  var i = 0
//  for (i <- 0 to colList.length - 1) {
//    if (colList(i)._3)
//      print(r.getList(i) + " || ")
//    else
//
//      print(r.get(i) + " || ")
//
//  }
//  println()
//
//
//})


//val data = spark.sql("select " + cols + " from temp1 limit 2")
//
//
//
////data.map(t => t(0) + "," + t(1)).collect().foreach(println)
//data.foreach(r => {
//  var i = 0
//  for (i <- 0 to colList.length - 1) {
//    if (colList(i)._3)
//      print(r.getList(i) + " || ")
//    else
//
//      print(r.get(i) + " || ")
//
//  }
//  println()
//
//
//})
//
//

