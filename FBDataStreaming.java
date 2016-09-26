/*
 * The MIT License
 *
 * Copyright 2014 Acer.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
/**
 *
 * @author Acer
 */
package com.source;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.DefaultLegacyFacebookClient;
import com.restfb.Facebook;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.LegacyFacebookClient;
import com.restfb.Parameter;
import com.restfb.json.JsonArray;
import com.restfb.json.JsonObject;
import com.restfb.types.Comment;
import com.restfb.types.Insight;
import com.restfb.types.Page;
import com.restfb.types.Photo;
import com.restfb.types.Post;
import com.restfb.types.Post.Comments;
import com.restfb.types.User;
import java.io.PrintWriter;
import static java.lang.System.currentTimeMillis;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class FBDataStreaming {

    // You need to provide your access token here.
    // Instructions are available on http://restfb.com.
    private static final String MY_EXTENDED_ACCESS_CODE = "CAANCTS8uWtUBABbGqic8wtZCMbx4YKG0LOjTtPcdPIHibdVa8KMyiQvM3bfEjFH5evlpf5tzG1LgYmsGKaNdArnoS7ka9ZA8vuubkqjF4LmCnIWZBcVfZACFTyRGnAXvmv5j6DaXBSmB7JUjbjwI374QgkIfOsBtqcaGNFTkf47KG3cro0GsphwusZAJGY38ZD";
    private static final String MY_ACCESS_TOKEN = "CAACEdEose0cBADC2IfqaiOZANLLDKYCH9XQPwZCCl3rjOZBBTqsNDvWQSkbAY9m6pULi23houFqzIHhcGR8ZBm9c3065yaH4lAZB03S97BFyc2DPzePbZC9o8VlTZBu2KjeG0RB17QSn4vhlwDHHuwrHP2h9fKuqwoFrbiLiGSzZCN9sZAFVlBAhSpnNsa6u1xtWJ7I9HTD2dnlZAlpPI8ZAS1TaYCVZA7d0ZATQZD";
    private static final String MY_APP_SECRET = "51a451b51cb5b977be49ce005d841ea1";
    private static final String MY_APP_ID = "917324201614037";

    public static void main(String[] args) {
        PrintWriter pw;
        FacebookClient facebookClient = new DefaultFacebookClient(MY_ACCESS_TOKEN);
        
        int counter = 0;
        try{
           
            pw = new PrintWriter("Federer_10152903711394941_Times.txt");
            JsonObject federer = facebookClient.fetchObject("10152903711394941/comments", JsonObject.class, Parameter.with("pretty", 0), Parameter.with("limit", 25), Parameter.with("summary", 1), Parameter.with("filter", "stream"));
            JsonObject job = federer.getJsonObject("paging").getJsonObject("cursors");
            String after = job.getString("after");
            JsonArray ja = federer.getJsonArray("data");
            for(int i=0; i<ja.length(); i++)
            {
                //out.println("Message "+i+" :" + ja.getJsonObject(i).get("message"));
                //pw.write(ja.getJsonObject(i).get("created_time")+"\n");
                long epoch = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse(ja.getJsonObject(i).get("created_time").toString()).getTime() / 1000;
                pw.write(epoch+"\n");
            }
            out.println("Size:"+federer.getJsonArray("data").length());
            out.println("Message:"+federer.getJsonArray("data").getJsonObject(3).get("message"));
            long epoch = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse("2014-12-13T19:52:14+0000").getTime() / 1000;
            out.println("timestamp = " + epoch);
            //after = null;
            while(after!=null)
            {
                federer = facebookClient.fetchObject("10152903711394941/comments", JsonObject.class, Parameter.with("pretty", 0), Parameter.with("limit", 25), Parameter.with("summary", 1), Parameter.with("filter", "stream"), Parameter.with("after", after));
                ja = federer.getJsonArray("data");
                for(int i=0; i<ja.length(); i++)
                {
                    //pw.write(ja.getJsonObject(i).get("message")+"\n");
                    epoch = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse(ja.getJsonObject(i).get("created_time").toString()).getTime() / 1000;
                    pw.write(epoch+"\n");
                }
                out.println("Couner:"+counter);
                out.println("Size:"+federer.getJsonArray("data").length());
                out.println("Message:"+federer.getJsonArray("data").getJsonObject(3).get("message"));
                job = federer.getJsonObject("paging").getJsonObject("cursors");
                after = job.getString("after");
                counter++;
            }
            pw.close();
        }catch(Exception e)
        {
            out.println("Exception Iteration Counter = " + counter);
        }
        out.println("Counter = " + counter);
       
        
    }
}
