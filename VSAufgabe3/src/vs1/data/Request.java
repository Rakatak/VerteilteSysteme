// Datei: Request.java
// Autor: Brecht
// Datum: 24.05.14
// Thema: Stream-Socket-Verbindungen zwischen Browser und Web-
//        Server. GET-Request herausfiltern, falls POST-Requests
//        nicht benutzt werden.
// -------------------------------------------------------------

import java.io.*;      // Fuer den Reader
import java.net.*;     // Fuer den Socket
import java.util.ArrayList;


class Request {
    public static void main(String[] args) throws Exception {
        
        // Vereinbarungen
        // ---------------------------------------------------------
        ServerSocket ss       = null;  // Fuer das accept()
        Socket cs             = null;  // Fuer die Requests
        InputStream is        = null;  // Aus dem Socket lesen
        InputStreamReader isr = null;
        BufferedReader br     = null;
        OutputStream os       = null;  // In den Socket schreiben
        PrintWriter pw        = null;
        String zeile1          = null;  // Eine Zeile aus dem Socket
        String host           = null;  // Der Hostname
        int port              = 0;     // Der lokale Port
        
        
        // Programmstart und Portbelegung
        // ---------------------------------------------------------
        host = InetAddress.getLocalHost().getHostName();
        port = 9876;
        System.out.println();
        System.out.println("##########################################");
        System.out.println("#### Server startet auf " + host + " an " + port + " ####");
        System.out.println("##########################################");

        // ServerSocket einrichten und in einer Schleife auf
        // Requests warten.
        // ---------------------------------------------------------
        ss = new ServerSocket(port);
        while(true) {
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println(">>>>>Warte im accept()<<<<<<");

            cs = ss.accept();               // <== Auf Requests warten
            
            // Den Request lesen (Hier nur erste Zeile)
            // -------------------------------------------------------
            is    = cs.getInputStream();
            isr   = new InputStreamReader(is, "UTF-8");
            br    = new BufferedReader(isr, 8192);
            
            // decode url for umlaute 

            String name           = null;             // input name
            String number         = null;	      // input number
            ArrayList<String> numResult = new ArrayList<String>();
            ArrayList<String> namResult = new ArrayList<String>();
            PhoneThread numThread = null;
            NameThread namThread = null;
            
            
            zeile1 = br.readLine();
            // decode url for umlaute 

            String zeile = java.net.URLDecoder.decode(zeile1, "UTF-8");
            
            System.out.println();
            System.out.println("Kontrollausgabe: " + zeile);
            System.out.println("------------------------");

           // ersten Requests nicht bearbeiten

            if(zeile.equals(null)){
                System.out.println("First empty Request");
                continue;
            }
            
            // Favicon-Requests nicht bearbeiten
            // -------------------------------------------------------
            if(zeile.startsWith("GET /favicon")) {
                System.out.println();
                System.out.println(">>>>>>>Request  Ende<<<<<<<<");
                br.close();
                continue;                       // Zum naechsten Request
            }
            
            
           //html page for ending server with console output too

            if (zeile.endsWith("server=Server beenden HTTP/1.1")){
                if (ss != null && !ss.isClosed()) {
                    try {
                        
                        System.out.println();
                        System.out.println("############################################");
                        System.out.println("############### Closing Server #############");
                        System.out.println("############################################");
                        System.out.println();
                        
                        os  = cs.getOutputStream();
                        pw  = new PrintWriter(os);
                        
                        pw.println("HTTP/1.1 200 OK");               // Der Header
                        pw.println("Connection: close");
                        pw.println("Content-Type: text/html;charset=utf-8");
                        pw.println();
                        pw.println("<html>");                    // Die HTML-Seite
                        pw.println("<head>");
                        pw.println("<meta charset='utf-8'>");
                        pw.println("</head>");
                        pw.println("<body>");
                        pw.println("<h2 align=center>Server wurde beendet</h2>");
                        pw.println("<h2 align=center>Kommen Sie wieder!</h2>");
                        pw.println("</body>");
                        pw.println("</html>");
                        
                        pw.flush();
                        pw.close();
                        br.close();
                        Thread.sleep(500);
                        ss.close();
                        System.out.println();
                        System.out.println("############################################");
                        System.out.println("############### Server Closed ##############");
                        System.out.println("############################################");
                        System.out.println();
                        return;
                       
                    } catch (IOException e)
                    {
                        e.printStackTrace(System.err);
                    }
                }
            }
            
            //if user entered nothging enter request will be sent
            if(zeile.startsWith("GET /?Name=&Nummer= HTTP/1.1") || zeile.startsWith("GET /?back=Zurück HTTP/1.1") || zeile.startsWith("GET /?Name= &Nummer=  HTTP/1.1")  || zeile.startsWith("GET /?Name= &Nummer= HTTP/1.1") || zeile.startsWith("GET /?Name=&Nummer=  HTTP/1.1")) {
                System.out.println("Please Enter Name or Number");
                System.out.println();
                
            } else if (zeile.startsWith("GET /?Name") && zeile.length() > 28) {
                       
                //trace for input of user, splits the given URL
                        
                zeile = zeile.substring(6, zeile.length() - 9);
                String[] nn = zeile.split("&");
                
                name = nn[0].substring(5, nn[0].length());
                number = nn[1].substring(7, nn[1].length());
                System.out.println("Input received:  Name = " + name + "    Number = " + number);
                System.out.println();
                System.out.println();

            }
            
           //Start of configure request
            if (name != null || number != null) {
               if (!name.isEmpty() || !number.isEmpty()) {
		  if (!name.trim().isEmpty() || !number.trim().isEmpty()) {
		                  if (number.matches(".*\\w.*") || name.matches(".*\\w.*")) {

			System.out.println("Request wird bearbeitet");
			System.out.println("***********************");
			
			//starting number thread
			if (number.matches(".*\\w.*")) {
			    System.out.println("+++PhoneThread started+++");
			    numThread = new PhoneThread(number, PhoneBook.list, numResult);
			    numThread.start();	
			}
			
			//starting name thread
			if (name.matches(".*\\w.*"))  {
			    System.out.println("+++NameThread started+++");
			    namThread = new NameThread(name, PhoneBook.list, namResult);
			    namThread.start();	
			}
			
			//join both threads
			if (numThread != null)  {
			    try {
				System.out.println("+++PhoneThread joined+++");
				numThread.join();
			    } catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			    }
			}
			
			if (namThread != null) {
			    try {
				System.out.println("+++NameThread joined+++");
				namThread.join();
			    } catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			    }
			}
			  // after threads are joined, both array are joined and printed on console
			numResult.addAll(namResult);
			  if (numResult.size() == 0){
			      System.out.println();
			      System.out.println("Suche nach " + name +  "  " + number + "   war erfolglos");
			      System.out.println();

			  } else {
			      System.out.println();
			      System.out.println("-----------------Results-----------------");
			      for (int i = 0; i < numResult.size() - 1 ; i += 2){
				  System.out.println("Name: " + numResult.get(i) + "   Number: " + numResult.get(i + 1));

			      }
			      System.out.println();

			  }
			
		      
			}
		  }
	      }

                
            }
            
            
            //Open outpotstream  for creating html page
            os  = cs.getOutputStream();
            pw  = new PrintWriter(os);
            
            //HTML page
            pw.println("HTTP/1.1 200 OK");               // Der Header
            pw.println("Connection: close");
            pw.println("Content-Type: text/html;charset=utf-8");
            pw.println();
            pw.println("<html>");                    // Die HTML-Seite
            pw.println("<head>");
            pw.println("<meta charset='utf-8'>");
            pw.println("</head>");
            pw.println("<body>");
            pw.println("<h2 align=center>Telefonverzeichnis</h2>");
            pw.println("<h3>Sie können nach Name oder nach Telefonnummer oder nach beiden (nebenläufig) suchen.</h3>");
            pw.println("<form charset=utf-8 method=get action='http://" + host + ":" + port + "' accept-charset='utf-8' enctype='multipart/form-data'>");
            
            pw.println("<table>");
            pw.println("<tr> <td valign=top>Name:</td>    <td><input name=Name></td>    <td></td> </tr>");
            pw.println("<tr> <td valign=top>Nummer:</td> <td><input name=Nummer></td>    <td></td> </tr>");
            pw.println("<tr> <td valign=top><input type=submit value=Suchen></td>");
            pw.println("<td><input type=reset></td>");
            pw.println("<td><input type=submit value='Server beenden' name=server></td> </tr>");
            pw.println("</table>");
            pw.println("</form>");
            pw.println("<br><br>");
            
            //Output for success of search with given input
            
            if(zeile.startsWith("GET /?Name=&Nummer= HTTP/1.1") || zeile.startsWith("GET /?Name= &Nummer=  HTTP/1.1")  || zeile.startsWith("GET /?Name= &Nummer= HTTP/1.1") || zeile.startsWith("GET /?Name=&Nummer=  HTTP/1.1")) {
		pw.println("<h3> Bitte geben Sie Zahlen bzw. Buchstaben ein!</h3>");
	     
            } else if (numResult.size() > 0){
            

                pw.println("<h3>Ergebnisse für Suche nach " + name + " " + number +"</h3>");
                pw.println("<br><br>");
                pw.println("<table style='width:30%'>");
                pw.println("<tr>");
                pw.println("<th>Name</th>");
                pw.println("<th>Number</th>");
                pw.println("</tr>");
                           
            //table of results
                for (int i = 0; i < numResult.size() - 1 ; i += 2){
                    pw.println("<tr>");
                    pw.println("<td>" + numResult.get(i) + "</td>");
                    pw.println("<td>" + numResult.get(i + 1) + "</td>");
                    pw.println(" </tr>");
                    
                }
                           
                pw.println("</table>");
                pw.println("<br><br>");

                pw.println("<form charset=utf-8 method=get action='http://" + host + ":" + port + "' accept-charset='utf-8' enctype='multipart/form-data'>");
                pw.println("<td><input type=submit value='Zurück' name=back></td> </tr>");
                pw.println("</form>");

                
                } else if (name != null || number != null) {
		if (!name.isEmpty() || !number.isEmpty()) {
			if (!name.trim().isEmpty() || !number.trim().isEmpty()) {
			  if (number.matches(".*\\w.*") || name.matches(".*\\w.*")) {
			    //Output for fail of search with given input      
				  pw.println("<h3>Suche nach " + name + " " + number +" erfolglos</h3>");
				  pw.println("<br><br>");
				  pw.println("<form charset=utf-8 method=get action='http://" + host + ":" + port + "' accept-charset='utf-8' enctype='multipart/form-data'>");
				  pw.println("<td><input type=submit value='Zurück' name=back></td> </tr>");
				  pw.println("</form>");
			      }
		      }
		    }
                
            }
                
            pw.println("<br><br><br><br>");
            pw.println("Kontrollausgabe: "  + zeile);
            pw.println("</body>");
            pw.println("</html>");
            pw.println();
            pw.flush();
            pw.close();
            br.close();
            System.out.println(">>>>>>>Request  Ende<<<<<<<<");

        }  // end while
    }  // end main()
}  // end class