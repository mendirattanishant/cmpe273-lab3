package edu.sjsu.cmpe.cache.client;

import java.util.ArrayList;
import java.util.List;
import com.google.common.hash.Hashing;

public class Client {

    public static void main(String[] args) throws Exception {
        //System.out.println("Starting Cache Client...");
        CacheServiceInterface cache = new DistributedCacheService(
                "http://localhost:3000");

        CacheServiceInterface cache1 = new DistributedCacheService(
                "http://localhost:3001");
        CacheServiceInterface cache2 = new DistributedCacheService(
                "http://localhost:3002");
        List<CacheServiceInterface> server=new ArrayList<CacheServiceInterface>();
        server.add(cache);
        server.add(cache1);
        server.add(cache2);


        String [] string1=new String[]{"0","a","b","c","d","e","f","g","h","i","j"};
        System.out.println("Implementing PUT");
        for(int key=1;key<11;key++)
        {

            int bucket = Hashing.consistentHash(Hashing.md5().hashString(Integer.toString(key)), server.size());
            server.get(bucket).put(key,string1[key]);
            System.out.println("put("+key +"=> "+string1[key]+")");
            System.out.println("routed to: " + (bucket+1)+ " key="+key+" value="+string1[key]);

        }
        System.out.println("Implementing Get");
        for(int key=1;key<11;key++)
        {
            int bucket = Hashing.consistentHash(Hashing.md5().hashString(Integer.toString(key)), server.size());
            String value=server.get(bucket).get(key);
            System.out.println("get"+key+ "=> " + value);
            //System.out.println("key= "+key+"bucket = " + (bucket+1)+ "value="+value );
        }

    }

}
