


ServerSocket ss = new ServerSocket(9876);
Socket cs = ss.accept();
BufferedReader br = new BufferedReader(new InputStreamReader(
cs.getInputStream() ));
String zeile = br.readLine();
Zeile verarbeiten;
PrintWriter pw = new PrintWriter(cs.getOutputStream() );
pw.println("HTTP/1.0 200 OK");
pw.println("Connection:close");
pw.println("Content-Type:text/html");
pw.println("\n");
pw.println("");
...
pw.println("");
pw.flush();
cs.close();
ss.close();