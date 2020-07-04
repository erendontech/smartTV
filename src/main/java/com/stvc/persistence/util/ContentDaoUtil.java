package com.stvc.persistence.util;

import com.stvc.entity.Cast;
import com.stvc.entity.Genre;

import java.util.Set;

public class ContentDaoUtil {

    public static String getGenresFormatDB(Set<Genre> genres){
        StringBuilder sb = new StringBuilder();
        for(Genre g: genres){
            sb.append(g.getId()).append("-").append(g.getName()).append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    public static String getCastsFormatDB(Set<Cast> casts) {
        StringBuilder sb = new StringBuilder();
        for(Cast c: casts){
            sb.append(c.getId()).append("-").append(c.getName()).append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }
}
