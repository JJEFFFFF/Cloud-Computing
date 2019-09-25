
public class test {
public static void main(String[] args) {
	String line = "10.153.239.5 - - [29/Jul/2009:09:00:16 -0700] \"GET /assets/js/lightbox.js HTTP/1.1\" 304 -";
/*	int firstquote = line.indexOf('\"');
    int secondquote = line.indexOf('\"',firstquote+1);
    if(firstquote != -1 && secondquote != -1) {
    String r = line.substring(firstquote+1,secondquote);
    String[] r1 = r.split(" ");
    String path = r1[1];
    System.out.println(path);
    }*/


    String[] line1 = line.split("\"");
    String r = line1[1];
    String[] r1 = r.split(" ");
    String path = r1[1];
    System.out.println(path);

}
}
