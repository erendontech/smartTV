package com.stvc.persistence.util;

import com.stvc.entity.Cast;
import com.stvc.entity.Genre;
import com.stvc.entity.Movie;

import java.util.Set;

public class ContentDaoUtil {

    public static String getGenresFormatDB(Set<Genre> genres) {
        StringBuilder sb = new StringBuilder();
        for (Genre g : genres) {
            sb.append(g.getId()).append("-").append(g.getName()).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public static String getCastsFormatDB(Set<Cast> casts) {
        StringBuilder sb = new StringBuilder();
        for (Cast c : casts) {
            sb.append(c.getId()).append("-").append(c.getName()).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * If the request of dynamic insert for the fields <code>audio,quality,director</code>
     * are null put empty the ideail case would be do a rollback but for the MVP is ok for now
     */
    public static void dataIntegrity(Movie movie) {
        if (movie.getAudio().getName() == null) {
            movie.getAudio().setName("");
        }

        if (movie.getQuality().getName() == null) {
            movie.getQuality().setName("");
        }

        if (movie.getDirector().getName() == null) {
            movie.getQuality().setName("");
        }
    }
}
