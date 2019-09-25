package CCProject33.CCP3;

import com.datastax.driver.core.*;
import java.util.HashMap;
import java.util.Map;
import com.google.common.hash.Hashing;

public class accesslog3 {
    public static void main(String[] args) throws Exception {

    	Cluster cluster;

        cluster = Cluster.builder().addContactPoint("159.65.253.68").build();
        Session session = cluster.connect();

        try {
            String query = "SELECT requestline, count(*) AS count FROM access_log2.log5 GROUP BY requestline";

            ResultSet result = session.execute(query);
            String path = "";
            int count = 0;
            for(Row row:result) {
            	int pathCount = row.getInt("count");
            	if(pathCount > count) {
            		count = pathCount;
            		path = row.getString("path");
            	}
            }
            System.out.println(path + " " + count);
        }
        finally {
        	if(cluster != null) {
            cluster.close();
        	}
        }
    }
}