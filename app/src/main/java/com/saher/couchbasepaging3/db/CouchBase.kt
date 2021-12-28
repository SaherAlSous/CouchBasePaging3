package com.saher.couchbasepaging3.db

import android.content.Context
import com.couchbase.lite.*

const val DBNAME = "primary"

class CouchBase(context: Context) {

    private val db = createDb(context)

    /**
     * I added the content manually. you can find them in the data class.
     * i executed this in MainActivity.
     */
    fun addContentToDB() {
    for (photo in photos) {
        val mutablePhoto = MutableDocument(photo.id)
        mutablePhoto.setString("id", photo.id)
        mutablePhoto.setString("owner", photo.owner)
        mutablePhoto.setString("secret", photo.secret)
        mutablePhoto.setString("server", photo.server)
        mutablePhoto.setInt("farm", photo.farm)
        mutablePhoto.setString("title", photo.title)
        mutablePhoto.setInt("ispublic", photo.ispublic)
        mutablePhoto.setInt("isfriend", photo.isfriend)
        mutablePhoto.setInt("isfamily", photo.isfamily)
        mutablePhoto.setString("url_s", photo.url_s)
        mutablePhoto.setInt("height_s", photo.height_s)
        mutablePhoto.setInt("width_s", photo.width_s)
        db.save(mutablePhoto)
    }
}

    /**
     * I converted the data manually... but you can create an adapter with Moshi.
     */
    fun getPhotoListFromDb(): MutableList<Photo> {
    val query = QueryBuilder
        .select(
            SelectResult.property("id"),
            SelectResult.property("owner"),
            SelectResult.property("secret"),
            SelectResult.property("server"),
            SelectResult.property("farm"),
            SelectResult.property("title"),
            SelectResult.property("ispublic"),
            SelectResult.property("isfriend"),
            SelectResult.property("isfamily"),
            SelectResult.property("url_s"),
            SelectResult.property("height_s"),
            SelectResult.property("width_s"),
        )
        .from(DataSource.database(db))

    val result = query.execute().allResults()
    val photoList: MutableList<Photo> = mutableListOf()

    for (rs in result) {
        val id = rs.getString("id")
        val owner = rs.getString("owner")
        val secret = rs.getString("secret")
        val server = rs.getString("server")
        val farm = rs.getInt("farm")
        val title = rs.getString("title")
        val ispublic = rs.getInt("ispublic")
        val isfriend = rs.getInt("isfriend")
        val isfamily = rs.getInt("isfamily")
        val url_s = rs.getString("url_s")
        val height_s = rs.getInt("height_s")
        val width_s = rs.getInt("width_s")

        photoList.add(
            Photo(
                id!!,
                owner!!,
                secret!!,
                server!!,
                farm,
                title!!,
                ispublic,
                isfriend,
                isfamily,
                url_s!!,
                height_s,
                width_s
            )
        )

    }
    return photoList
}

    /**
     * you can inject it with Hilt but for speed i did it this way.
     */
    private fun createDb(context: Context): Database {
        CouchbaseLite.init(context)
        val cfg = DatabaseConfiguration().create()
        cfg.directory = context.filesDir.absolutePath

        return Database(DBNAME, cfg)
    }
}